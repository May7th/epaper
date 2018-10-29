package com.oyun.media.epaper.Search;

import com.oyun.media.epaper.EpaperApplicationTests;
import com.oyun.media.epaper.common.ServiceMultiResult;
import com.oyun.media.epaper.search.ArticleSearch;
import com.oyun.media.epaper.search.ISearchService;
import com.oyun.media.epaper.service.IArticleService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
    public void testData(){
        String content = "ᠬᠦᠮᠦᠨ<img src='asdfdsa/fdsafdsa/fdsafd/'>fdafa ";
        articleService.checkContent(content);
    }


}
