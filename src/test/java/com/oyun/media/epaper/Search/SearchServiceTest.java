package com.oyun.media.epaper.Search;

import com.oyun.media.epaper.EpaperApplicationTests;
import com.oyun.media.epaper.common.ServiceMultiResult;
import com.oyun.media.epaper.domain.Article;
import com.oyun.media.epaper.domain.Recommend;
import com.oyun.media.epaper.repository.RecommendRepository;
import com.oyun.media.epaper.search.ArticleSearch;
import com.oyun.media.epaper.search.ISearchService;
import com.oyun.media.epaper.service.IArticleService;
import com.oyun.media.epaper.service.IRecommendService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: epaper
 * @description:
 * @author: changzhen
 * @create: 2018-08-08 11:29
 **/
public class SearchServiceTest extends EpaperApplicationTests {

    @Autowired
    private ISearchService searchService;

    @Autowired
    private IArticleService articleService;

    @Autowired
    private IRecommendService recommendService;

    @Autowired
    private RecommendRepository recommendRepository;

    @Test
    public void testIndex(){

        boolean success = searchService.index(4L);

        Assert.assertTrue(success);
    }


    @Test
    public void testRemove(){

        searchService.remove(4L);
    }

    @Test
    public void testQuery(){
        ArticleSearch rentSearch = new ArticleSearch();
        rentSearch.setCatalogName("1111");

        ServiceMultiResult multiResult = searchService.query(rentSearch);

        Assert.assertEquals(2,multiResult.getTotal());
    }

    @Test
    @Transactional
    public void testData(){
        List<Recommend> recommendList = recommendRepository.findAll();

        List<Recommend> newList = new ArrayList<>();
        recommendList.forEach(recommend -> {
            Article article = articleService.getArticleById(recommend.getArticleId());
            Date date = article.getReleaseDate();
            recommend.setReleaseDate(date);
            newList.add(recommend);
        });

        recommendService.saveRecommendList(newList);
    }


}
