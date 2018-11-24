package com.oyun.media.epaper;

import com.oyun.media.epaper.domain.Article;
import com.oyun.media.epaper.domain.Recommend;
import com.oyun.media.epaper.repository.ArticleRepository;
import com.oyun.media.epaper.repository.RecommendRepository;
import com.oyun.media.epaper.search.SearchServiceImpl;
import com.oyun.media.epaper.service.IArticleService;
import com.oyun.media.epaper.task.ESMonitor;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.annotation.Transient;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EpaperApplicationTests {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private RecommendRepository recommendRepository;

    @Autowired
    private IArticleService articleService;

    @Autowired
    private SearchServiceImpl searchService;

	@Test
//    @Transactional
	public void contextLoads() {

        List<Article> articleList = articleRepository.findAllByState(5);

        articleList.forEach(article -> {
            article.setState(1);
            articleRepository.save(article);
            System.out.println("================");
        });

    }

}
