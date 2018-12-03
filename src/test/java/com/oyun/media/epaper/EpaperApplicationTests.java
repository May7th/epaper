//package com.oyun.media.epaper;
//
//import com.oyun.media.epaper.domain.Article;
//import com.oyun.media.epaper.domain.Recommend;
//import com.oyun.media.epaper.repository.ArticleRepository;
//import com.oyun.media.epaper.repository.RecommendRepository;
//import com.oyun.media.epaper.search.SearchServiceImpl;
//import com.oyun.media.epaper.service.IArticleService;
//import com.oyun.media.epaper.service.impl.DataHandleService;
//import org.apache.commons.lang3.StringUtils;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest()
//public class EpaperApplicationTests {
//
//    @Autowired
//    private ArticleRepository articleRepository;
//
//    @Autowired
//    private RecommendRepository recommendRepository;
//
//    @Autowired
//    private IArticleService articleService;
//
//    @Autowired
//    private SearchServiceImpl searchService;
//
//    @Autowired
//    private DataHandleService dataHandleService;
//
//    @Test
////    @Transactional
//    public void contextLoads() {
//
//        List<Article> articleList = articleRepository.findAll();
//
//        articleList.forEach(article -> {
//            System.out.println(article.getId());
//            Article replaceArticle = resetArticle(article);
//            articleRepository.save(replaceArticle);
//
//        });
//
//        List<Recommend> recommends = recommendRepository.findAll();
//
//        recommends.forEach(recommend -> {
//            System.out.println(recommend.getArticleName());
//            String name = articleService.checkContent(recommend.getArticleName());
//
//            String s = replaceStr(name);
//
//            recommend.setArticleName(s);
//
//            recommendRepository.save(recommend);
//        });
//
////        Article article = articleRepository.getOne(5064L);
////
////        Article replaceArticle = resetArticle(article);
////        articleRepository.save(replaceArticle);
//    }
//
//    private Article resetArticle(Article article){
//
//        if (article.getTitle() !=null){
//            String title = article.getTitle();
//            System.out.println(title);
//            System.out.println(replaceStr(title));
//            article.setTitle(replaceStr(title));
//        }
//
//        if (article.getSubTitle() != null){
//
//            String subTitle = article.getSubTitle();
//            article.setSubTitle(replaceStr(subTitle));
//
//        }
//
//        if (article.getAuthor() != null){
//            String author = article.getAuthor();
//            article.setAuthor(replaceStr(author));
//        }
//
//        if (article.getContentHtml() != null){
//            String contentHtml = article.getContentHtml();
//            article.setContentHtml(replaceStr(contentHtml));
//        }
//
//        return article;
//
//    }
//
//    private String replaceStr(String str){
//
//        String s = str.replaceAll("︽","《")
//                .replaceAll("︾","》")
//                .replaceAll("︕","!")
//                .replaceAll("︖","?")
//                .replaceAll("︵","(")
//                .replaceAll("︶",")");
//
//        return s;
//
//    }
//
//    @Test
//    public void indexElasticSearch(){
//
//        List<Article> articleList = articleRepository.findAllByState(1);
//
//        System.out.println(articleList.size());
//        articleList.forEach(article -> {
//
//            searchService.index(article.getId());
//
//            article.setState(5);
//
//            articleRepository.saveAndFlush(article);
//
//            System.out.println(article.getId());
//
//        });
//
//    }
//
//    @Test
//    public void rollbackDate(){
//
//        List<Article> articleList = articleRepository.findAllByState(5);
//
//        System.out.println(articleList.size());
//        articleList.forEach(article -> {
//
//            article.setState(1);
//
//            articleRepository.saveAndFlush(article);
//
//        });
//
//    }
//
//    @Test
//    public void dataHandle(){
//        dataHandleService.handleImageNews();
//    }
//
//}
