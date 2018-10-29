package com.oyun.media.epaper.service;

import com.oyun.media.epaper.common.ServiceMultiResult;
import com.oyun.media.epaper.domain.Article;
import com.oyun.media.epaper.domain.Catalog;
import com.oyun.media.epaper.search.ArticleSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

/**
 * @program: eArticle
 * @description:
 * @author: changzhen
 * @create: 2018-05-25 15:07
 **/
public interface IArticleService {

    /**
     * 删除文章接口
     * @param id
     */
    void removeArticle(Long id);


    /**
     * 根据发行日期查询
     * @param date
     * @param pageable
     * @return
     */
    Page<Article> findArticlesByReleaseDate(Date date, Pageable pageable);

    /**
     * 根据Id查询
     * @param id
     * @return
     */
    Article getArticleById(Long id);

    /**
     * 储存更新文章
     * @param article
     * @return
     */
    void saveOrUpdateArticle(Article article);

    /**
     * 更新文章状态
     * @param articleId
     * @param status
     * @return
     */
    void updateStatus(Long articleId, int status);

    Page<Article> findArticlesByCatalog(Catalog catalog,Pageable pageable);

    ServiceMultiResult<Article> query(ArticleSearch rentSearch);

    String checkContent(String content);

    List<Article> findArticlesByParentId(Long pageId);

    String getIntermediateCode(String content);
}
