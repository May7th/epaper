package com.oyun.media.epaper.service.impl;

import com.oyun.media.epaper.common.ArticleSort;
import com.oyun.media.epaper.domain.Article;
import com.oyun.media.epaper.repository.ArticleRepository;
import com.oyun.media.epaper.search.ArticleSearch;
import com.oyun.media.epaper.service.IArticleSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @program: epaper
 * @description:
 * @author: changzhen
 * @create: 2018-11-26 22:52
 **/

@Service
public class ArticleSearchService implements IArticleSearchService {

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Page<Article> searchContent(ArticleSearch articleSearch){

        String keywords = articleSearch.getKeywords();
        Sort sort = ArticleSort.generateSort(articleSearch.getOrderBy(),articleSearch.getOrderDirection());

        int page = articleSearch.getStart()/articleSearch.getSize();

        Pageable pageable = new PageRequest(page,articleSearch.getSize(),sort);

        Page<Article> articles = articleRepository.findAllByContentHtmlInAndState(keywords,1,pageable);

        return articles;

    }


}
