package com.oyun.media.epaper.repository;

import com.oyun.media.epaper.domain.Article;
import com.oyun.media.epaper.domain.Catalog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * @program: epaper
 * @description:
 * @author: changzhen
 * @create: 2018-05-25 15:02
 **/
public interface ArticleRepository extends JpaRepository<Article,Long>{

    /**
     * 根据日期查找文章
     * @param date
     * @param pageable
     * @return
     */
    Page<Article> findArticlesByReleaseDateAndState(Date date, Integer state, Pageable pageable);

    Page<Article> findByCatalog(Catalog catalog, Pageable pageable);

    Page<Article> findAll(Specification<Article> specification, Pageable pageable);

    List<Article> findArticlesByParentId(Long pageId);

    Page<Article> findAllById(List<Long> ids,Pageable pageable);

    Page<Article> findAllByState(Integer state,Pageable pageable);
    List<Article> findTop10ByOrderByReadSizeDesc();

    long countAllByState(Integer state);

    List<Article> findAllByContentHtmlContainsAndTitleIsNull(String contentHtml);
    List<Article> findAllByContentHtmlContainsAndTitle(String contentHtml,String title);

    List<Article> findAllByState(Integer state);


}
