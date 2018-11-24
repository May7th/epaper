package com.oyun.media.epaper.service.impl;

import com.oyun.media.epaper.common.ArticleSort;
import com.oyun.media.epaper.common.ArticleStatus;
import com.oyun.media.epaper.common.Const;
import com.oyun.media.epaper.common.ServiceMultiResult;
import com.oyun.media.epaper.domain.Article;
import com.oyun.media.epaper.domain.Attachment;
import com.oyun.media.epaper.domain.Catalog;
import com.oyun.media.epaper.repository.ArticleRepository;
import com.oyun.media.epaper.repository.PageRepository;
import com.oyun.media.epaper.search.ISearchService;
import com.oyun.media.epaper.search.ArticleSearch;
import com.oyun.media.epaper.service.IArticleService;
import com.oyun.media.epaper.service.IAttachmentService;
import com.oyun.media.epaper.utils.UserUtil;
import com.oyun.media.port.Exception_Exception;
import com.oyun.media.port.PortFunctionDelegate;
import com.oyun.media.port.PortFunctionService;
import com.oyun.media.transService.PortFuntionDelegate;
import com.oyun.media.transService.PortFuntionService;
import lombok.extern.log4j.Log4j2;
import org.apache.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @program: epaper
 * @description:
 * @author: changzhen
 * @create: 2018-05-25 15:10
 **/
@Service
@Log4j2
public class ArticleServiceImpl implements IArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private IAttachmentService attachmentService;

    @Autowired
    private ISearchService searchService;


    @Transactional(rollbackFor = Exception.class )
    @Override
    public void removeArticle(Long id) {
        updateStatus(id, Const.State.DELETE);
        searchService.remove(id);
    }

    @Override
    public Page<Article> findArticlesByReleaseDate(Date date, Pageable pageable) {
        return null;
    }

    @Override
    public Article getArticleById(Long id) {
        return articleRepository.getOne(id);
    }

    @Override
    public void saveOrUpdateArticle(Article article) {

        com.oyun.media.epaper.domain.Page page = pageRepository.getOne(article.getParentId());


        if (article.getId() == null){

            article.setCreateUsername(UserUtil.getCurrentUser().getUsername());

            article.setParentName(page.getPageName());

            article.setContent(getIntermediateCode(article.getContentHtml()));

            List<Article> articleList = page.getArticleList();

            List<Attachment> attachmentList = attachmentService.getAttachmentsByUrl(getImgStr(article.getContentHtml()));

            article.setContentImages(attachmentList);

            article.setReleaseDate(page.getReleaseDate());

            Article savedArticle = articleRepository.save(article);

            articleList.add(article);

            page.setArticleList(articleList);

            pageRepository.save(page);

            searchService.index(savedArticle.getId());

        }else {
            article.setModifyTime(new Timestamp(System.currentTimeMillis()));

            List<Attachment> attachmentList = attachmentService.getAttachmentsByUrl(getImgStr(article.getContentHtml()));

            attachmentService.updateAttachemntsByUrl(attachmentList,article.getContentImages());

            article.setContentImages(attachmentList);

            article.setContent(getIntermediateCode(article.getContentHtml()));

            if (article.getState() == ArticleStatus.PASSES.getValue()){
                article.setAuditTime(new Timestamp(System.currentTimeMillis()));
                article.setAuditUsername(UserUtil.getCurrentUser().getUsername());
            }
            articleRepository.save(article);
            searchService.index(article.getId());
        }

    }

    @Transactional(rollbackFor = Exception.class )
    @Override
    public void updateStatus(Long articleId, int status) {

        Article article = articleRepository.getOne(articleId);

        if (article == null){
            log.error("not find article by id: " + articleId);
        }
        if (article.getState() == status){
            log.error("状态没有发生变化");
        }
        article.setState(status);
        articleRepository.save(article);
    }

    @Override
    public Page<Article> findArticlesByCatalog(Catalog catalog, Pageable pageable) {
        return articleRepository.findByCatalog(catalog,pageable);
    }

    @Override
    public ServiceMultiResult<Article> query(ArticleSearch articleSearch) {

        if (articleSearch.getKeywords() != null && !articleSearch.getKeywords().isEmpty()){
            ServiceMultiResult<Long> serviceMultiResult = searchService.query(articleSearch);
            if (serviceMultiResult.getTotal() == 0){
                return new ServiceMultiResult<>(0,new ArrayList<>());
            }
            return new ServiceMultiResult<>(serviceMultiResult.getTotal(),wrapperArticleResult(serviceMultiResult.getResult()));
        }

        Sort sort = ArticleSort.generateSort(articleSearch.getOrderBy(),articleSearch.getOrderDirection());

        int page = articleSearch.getStart()/articleSearch.getSize();

        Pageable pageable = new PageRequest(page,articleSearch.getSize(),sort);

        Specification<Article> specification = (root, criteriaQuery, criteriaBuilder) -> {
            
            Predicate predicate = criteriaBuilder.notEqual(root.get("state"), ArticleStatus.DELETED.getValue());

            if (articleSearch.getCatalogId() >-1){
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("catalog"), articleSearch.getCatalogId()));
            }

            if (!articleSearch.getCatalogName().isEmpty()){
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("catalogName"), articleSearch.getCatalogName()));
            }

            if (articleSearch.getRecommend() > -1){
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("recommend"), articleSearch.getRecommend()));
            }

            if (articleSearch.getState() > -1){
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("state"), articleSearch.getState()));
            }


            return predicate;
        };

        Page<Article> articles = articleRepository.findAll(specification, pageable);

        return new ServiceMultiResult<>(articles.getTotalElements(), articles.getContent());
    }

    @Override
    public Page<Article> queryPage(ArticleSearch articleSearch) {

        if (articleSearch.getKeywords() != null && !articleSearch.getKeywords().isEmpty()){
            ServiceMultiResult<Long> serviceMultiResult = searchService.query(articleSearch);

            return wrapperArticleResultByPage(serviceMultiResult.getResult(),articleSearch,serviceMultiResult.getTotal());
        }

        Sort sort = ArticleSort.generateSort(articleSearch.getOrderBy(),articleSearch.getOrderDirection());

        int page = articleSearch.getStart()/articleSearch.getSize();

        Pageable pageable = new PageRequest(page,articleSearch.getSize(),sort);

        Page<Article> articles = null;
        if (articleSearch.getReleaseDate() != null){

            articles = articleRepository.findArticlesByReleaseDateAndState(articleSearch.getReleaseDate(),1,pageable);

        }else {
            articles = articleRepository.findAllByState(1, pageable);

        }
        return articles;
    }



    @Override
    public String checkContent(String content) {
        String checkedContent = null;
        PortFunctionService service = new PortFunctionService();
        PortFunctionDelegate portType = service.getPortFunctionPort();
        if (content == null){
            return null;
        }
        try {
            String result = portType.function(content);
            checkedContent = result.split("\\/\\*\\*\\/")[2];
        } catch (Exception_Exception e) {
            e.printStackTrace();
            return null;
        }

        return checkedContent;
    }

    @Override
    public List<Article> findArticlesByParentId(Long pageId) {
        List<Article> articles = articleRepository.findArticlesByParentId(pageId);
        if (articles.isEmpty()){
            return null;
        }
        return articles;
    }

    private List<Article> wrapperArticleResult(List<Long> articleIds){

        List<Article> articleList = articleRepository.findAllById(articleIds);

        return articleList;

    }

    public static Set<String> getImgStr(String htmlStr) {
        Set<String> pics = new HashSet<>();
        String img = "";
        Pattern p_image;
        Matcher m_image;
        //     String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile
                (regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            // 得到<img />数据
            img = m_image.group();
            // 匹配<img>中的src数据
            Matcher matcher = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (matcher.find()) {
                pics.add(matcher.group(1));
            }
        }
        return pics;
    }

    @Override
    public String getIntermediateCode(String content){
        String intermediateCode = null;
        if (content==null){
            return null;
        }
        PortFuntionService service = new PortFuntionService();
        PortFuntionDelegate portFuntionDelegate = service.getPortFuntionPort();
        try {
            intermediateCode = portFuntionDelegate.fileITMDTrans(content);
            return intermediateCode;
        } catch (com.oyun.media.transService.Exception_Exception e) {
            e.printStackTrace();
        }
        return intermediateCode;
    }

    @Override
    public Article increaseReadSize(Article article){
        article.setReadSize(article.getReadSize()+1);
        return articleRepository.save(article);
    }

    private Page<Article> wrapperArticleResultByPage(List<Long> articleIds,ArticleSearch articleSearch,long total){
        Sort sort = ArticleSort.generateSort(articleSearch.getOrderBy(),articleSearch.getOrderDirection());

        int page = articleSearch.getStart()/articleSearch.getSize();

        Pageable pageable = new PageRequest(page,articleSearch.getSize(),sort);

        List<Article> articleList = articleRepository.findAllById(articleIds);

        Page<Article> articlePage = new PageImpl<Article>(articleList,pageable,total);


        return articlePage;
    }

    @Override
    public List<Article> clickRateList(int indexCount){

        List<Article> articleList = articleRepository.findTop10ByOrderByReadSizeDesc();

        List<Article> printArticles = new ArrayList<>(indexCount);
        for (int i = 0; i < indexCount; i++) {
            printArticles.add(articleList.get(i));
        }

        return printArticles;
    }
}
