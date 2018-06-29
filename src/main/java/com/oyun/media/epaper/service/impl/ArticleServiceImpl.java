package com.oyun.media.epaper.service.impl;

import com.oyun.media.epaper.common.Const;
import com.oyun.media.epaper.domain.Article;
import com.oyun.media.epaper.repository.ArticleRepository;
import com.oyun.media.epaper.repository.PageRepository;
import com.oyun.media.epaper.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * @program: epaper
 * @description:
 * @author: changzhen
 * @create: 2018-05-25 15:10
 **/
@Service
public class ArticleServiceImpl implements IArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private PageRepository pageRepository;


    @Transactional(rollbackFor = Exception.class )
    @Override
    public void removeArticle(Long id) {
        Article paper = articleRepository.getOne(id);
        paper.setState(Const.State.DELETED);
        articleRepository.save(paper);
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
    public Article addNewArticle(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public void saveOrUpdateArticle(Article article) {
        if (article.getId() == null){

            com.oyun.media.epaper.domain.Page page = pageRepository.getOne(article.getParentId());

            List<Article> articleList = page.getArticleList();

            articleList.add(article);

            page.setArticleList(articleList);

            pageRepository.save(page);
        }else {

            articleRepository.save(article);
        }
    }
}
