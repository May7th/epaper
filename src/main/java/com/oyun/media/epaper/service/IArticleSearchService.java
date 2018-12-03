package com.oyun.media.epaper.service;

import com.oyun.media.epaper.domain.Article;
import com.oyun.media.epaper.search.ArticleSearch;
import org.springframework.data.domain.Page;

/**
 * @program: epaper
 * @description:
 * @author: changzhen
 * @create: 2018-11-26 22:52
 **/
public interface IArticleSearchService {

    Page<Article> searchContent(ArticleSearch articleSearch);
}
