package com.oyun.media.epaper.repository;

import com.oyun.media.epaper.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
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
    Page<Article> findArticlesByReleaseDate(Date date, Pageable pageable);

}
