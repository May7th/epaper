package com.oyun.media.epaper.search;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.primitives.Longs;
import com.oyun.media.epaper.common.ArticleSort;
import com.oyun.media.epaper.common.ServiceMultiResult;
import com.oyun.media.epaper.domain.Article;
import com.oyun.media.epaper.domain.Catalog;
import com.oyun.media.epaper.repository.ArticleRepository;
import com.oyun.media.epaper.service.IArticleService;
import com.oyun.media.epaper.service.ICatalogService;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.ExtendedBounds;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @program: epaper
 * @description:
 * @author: changzhen
 * @create: 2018-08-08 10:32
 **/
@Slf4j
@Service
@Transactional
public class SearchServiceImpl implements ISearchService {

    private static final String INDEX_NAME = "epaper";

    private static final String INDEX_TYPE = "article";

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransportClient esClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ICatalogService catalogService;

    @Autowired
    private IArticleService articleService;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    public boolean index(Long articleId) {

        Article article = articleRepository.getOne(articleId);

        if (article == null){
            log.error("Index article {} dose not exist",articleId);
            return false;
        }

        Article intermediateArticle = intermediateArticle(article);


        ArticleIndexTemplate indexTemplate = new ArticleIndexTemplate();

        modelMapper.map(intermediateArticle,indexTemplate);

        SearchRequestBuilder builder = this.esClient
                .prepareSearch(INDEX_NAME)
                .setTypes(INDEX_TYPE)
                .setQuery(QueryBuilders.termQuery(ArticleIndexKey.ARTICLE_ID,articleId));

        log.debug(builder.toString());

        SearchResponse searchResponse = builder.get();

        long totalHit = searchResponse.getHits().getTotalHits();

        boolean success;

        if (totalHit == 0){
            success = create(indexTemplate);
        }else if (totalHit == 1){
            String esId = searchResponse.getHits().getAt(0).getId();
            success = update(indexTemplate,esId);
        }else {
            success = deleteAndCreate(totalHit,indexTemplate);
        }

        if (success){
            log.debug("index success with article " + articleId);
        }
        return success;
    }

    private boolean create(ArticleIndexTemplate articleIndexTemplate){

        try {
            IndexResponse response = this.esClient.prepareIndex(INDEX_NAME,INDEX_TYPE)
                    .setSource(objectMapper.writeValueAsBytes(articleIndexTemplate), XContentType.JSON)
                    .get();

            log.debug("Create index with article "+articleIndexTemplate.getArticleId());

            if (response.status() == RestStatus.CREATED){
                return true;
            }else {
                return false;
            }
        } catch (JsonProcessingException e) {
            log.error("Error to index article "+articleIndexTemplate.getArticleId(),e);
            return false;
        }

    }

    private boolean update(ArticleIndexTemplate articleIndexTemplate, String esId){

        try {
            UpdateResponse response = this.esClient.prepareUpdate(INDEX_NAME,INDEX_TYPE,esId)
                    .setDoc(objectMapper.writeValueAsBytes(articleIndexTemplate), XContentType.JSON)
                    .get();

            log.debug("Update index with article "+articleIndexTemplate.getArticleId());

            if (response.status() == RestStatus.OK){
                return true;
            }else {
                return false;
            }
        } catch (JsonProcessingException e) {
            log.error("Error to index article "+articleIndexTemplate.getArticleId(),e);
            return false;
        }


    }

    private boolean deleteAndCreate(long totalHit,ArticleIndexTemplate articleIndexTemplate){

        DeleteByQueryRequestBuilder builder = DeleteByQueryAction.INSTANCE
                .newRequestBuilder(esClient)
                .filter(QueryBuilders.termQuery(ArticleIndexKey.ARTICLE_ID,articleIndexTemplate.getArticleId()))
                .source(INDEX_NAME);

        log.debug("Delete by query for article " + builder);
        BulkByScrollResponse response = builder.get();
        long deleted = response.getDeleted();
        if (deleted != totalHit){
            log.warn("need delete {}, but {} was deleted",deleted,totalHit);
            return false;
        }else {
            return create(articleIndexTemplate);
        }

    }

    @Override
    public void remove(Long articleId) {
        DeleteByQueryRequestBuilder builder = DeleteByQueryAction.INSTANCE
                .newRequestBuilder(esClient)
                .filter(QueryBuilders.termQuery(ArticleIndexKey.ARTICLE_ID,articleId))
                .source(INDEX_NAME);

        log.debug("Delete by query for article " + builder);
        BulkByScrollResponse response = builder.get();
        long deleted = response.getDeleted();
        log.debug("Delete total: "+deleted);
    }

    @Override
    public ServiceMultiResult<Long> query(ArticleSearch articleSearch) {
        String intermediatekeywords = articleService.getIntermediateCode(articleSearch.getKeywords());

        articleSearch.setKeywords(intermediatekeywords);

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        if (articleSearch.getOrderDirection().isEmpty()){
            articleSearch.setOrderDirection("desc");
        }

        if (articleSearch.getCatalogId() > -1){
            boolQuery.filter(
                            QueryBuilders.termQuery(ArticleIndexKey.CATALOG_ID,articleSearch.getCatalogId())
                    );
        }

        if (articleSearch.getState() > -1){
            boolQuery.filter(
                    QueryBuilders.termQuery(ArticleIndexKey.STATE,articleSearch.getState())
            );
        }

        if (articleSearch.getRecommend() > -1){
            boolQuery.filter(
                    QueryBuilders.termQuery(ArticleIndexKey.RECOMMEND,articleSearch.getRecommend())
            );
        }
        if (articleSearch.getReleaseDate() != null){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
            String date = format.format(articleSearch.getReleaseDate());
            boolQuery.filter(
                    QueryBuilders.termQuery(ArticleIndexKey.RELEASE_DATE,date)
            );
        }

        if (articleSearch.getWordType()!= null && !articleSearch.getWordType().isEmpty()){

            String wordType = articleSearch.getWordType();

            switch (wordType){
                case "title":
                    boolQuery.must(
                            QueryBuilders.matchPhraseQuery(
                                    ArticleIndexKey.TITLE,
                                articleSearch.getKeywords()
                                ).slop(2)
                            );
                    break;
                case "content":
                    boolQuery.must(
                                QueryBuilders.matchPhraseQuery(
                                        ArticleIndexKey.CONTENT,
                                        articleSearch.getKeywords()
                                ).slop(2)
                    );
                    break;
                case "author":
                    boolQuery.must(
                            QueryBuilders.matchPhraseQuery(
                            ArticleIndexKey.AUTHOR,
                            articleSearch.getKeywords()
                            ).slop(2)
                    );
                    break;

                default :
                    log.info("keyword type is "+wordType);
            }
        }else {
            boolQuery.must(QueryBuilders.multiMatchQuery(
                    articleSearch.getKeywords(),
                    ArticleIndexKey.TITLE,
                    ArticleIndexKey.SUB_TITLE,
                    ArticleIndexKey.AUTHOR,
                    ArticleIndexKey.CONTENT
            ).operator(Operator.AND).slop(30)
            );
        }
        if (articleSearch.getStart()>0){
            articleSearch.setStart(articleSearch.getStart()-1);
        }
        SearchRequestBuilder requestBuilder = this.esClient.prepareSearch(INDEX_NAME)
                .setTypes(INDEX_TYPE)
                .setQuery(boolQuery)
                .addSort("_score",
                        SortOrder.fromString(articleSearch.getOrderDirection()))
                .setFrom(articleSearch.getStart())
                .setSize(articleSearch.getSize());

        log.info(requestBuilder.toString());

        List<Long> articleIds = new ArrayList<>();

        SearchResponse response = requestBuilder.get();
        if (response.status() != RestStatus.OK){
            log.warn("Search status is no ok for "+requestBuilder);
            return new ServiceMultiResult<>(0,articleIds);
        }

        response.getHits().forEach(searchHitFields -> {
            articleIds.add(Longs.tryParse(searchHitFields.getSource().get(ArticleIndexKey.ARTICLE_ID).toString()));
        });
        return new ServiceMultiResult<>(response.getHits().totalHits,articleIds);
    }


    private Article intermediateArticle(Article article){

        Article intermediateArticle = new Article();
        modelMapper.map(article,intermediateArticle);

        if (!"".equals(intermediateArticle.getAuthor())){
            String cleanHtml = removeHtmlTag(intermediateArticle.getAuthor());
            String cleanbd = cleanHtml.replaceAll( "[\\p{P}+~$`^=|<>～｀＄＾＋＝｜＜＞￥×]" , " ");
            String author = articleService.getIntermediateCode(cleanbd);
            intermediateArticle.setAuthor(author.trim());
        }

        if (!"".equals(intermediateArticle.getSubTitle())){
            String cleanHtml = removeHtmlTag(intermediateArticle.getSubTitle());
            String cleanbd = cleanHtml.replaceAll( "[\\p{P}+~$`^=|<>～｀＄＾＋＝｜＜＞￥×]" , " ");
            String subTitle = articleService.getIntermediateCode(cleanbd);
            intermediateArticle.setSubTitle(subTitle.trim());
        }

        if (!"".equals(intermediateArticle.getTitle())){
            String cleanHtml = removeHtmlTag(intermediateArticle.getTitle());
            String cleanbd = cleanHtml.replaceAll( "[\\p{P}+~$`^=|<>～｀＄＾＋＝｜＜＞￥×]" , " ");
            String title = articleService.getIntermediateCode(cleanbd);
            intermediateArticle.setTitle(title.trim());
        }

        if (!"".equals(intermediateArticle.getContentHtml())){
            String cleanHtml = removeHtmlTag(intermediateArticle.getContentHtml());
            String cleanbd = cleanHtml.replaceAll( "[\\p{P}+~$`^=|<>～｀＄＾＋＝｜＜＞￥×]" , " ");
            String content = articleService.getIntermediateCode(cleanbd);
            intermediateArticle.setContent(content.trim());
        }

        return intermediateArticle;
    }

    @Override
    public void testData() {
        BoolQueryBuilder qbs = QueryBuilders.boolQuery();

        AggregationBuilder aggregation = AggregationBuilders
                .dateHistogram("agg")
                .field(ArticleIndexKey.CREATE_TIME)
                .dateHistogramInterval(DateHistogramInterval.MONTH)
                .minDocCount(0)
                .extendedBounds(new ExtendedBounds("2018-01-01","2018-12-31"))
                .format("yyyy-MM-dd");

        SearchRequestBuilder requestBuilder = esClient.prepareSearch(INDEX_NAME)
                .setTypes(INDEX_TYPE);
        requestBuilder.setQuery(qbs);
        requestBuilder.addAggregation(aggregation);
        SearchResponse response = requestBuilder.execute().actionGet();
        Histogram agg = response.getAggregations().get("agg");

// For each entry
        for (Histogram.Bucket entry : agg.getBuckets()) {
            String key = entry.getKey().toString();
            String keyAsString = entry.getKeyAsString();
            long docCount = entry.getDocCount();

            System.out.println("key [{" + keyAsString + "}]");
            System.out.println("date [{" + key + "}]");
            System.out.println("doc_count [{" + docCount + "}]");
        }
    }

    public static String removeHtmlTag(String inputString) {
        if (inputString == null)
            return null;
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;
        java.util.regex.Pattern p_special;
        java.util.regex.Matcher m_special;
        try {
//定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
//定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
// 定义HTML标签的正则表达式
            String regEx_html = "<[^>]+>";
// 定义一些特殊字符的正则表达式 如：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            String regEx_special = "\\&[a-zA-Z]{1,10};";

            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签
            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签
            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签
            p_special = Pattern.compile(regEx_special, Pattern.CASE_INSENSITIVE);
            m_special = p_special.matcher(htmlStr);
            htmlStr = m_special.replaceAll(""); // 过滤特殊标签
            textStr = htmlStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return textStr;// 返回文本字符串
    }

}
