package com.oyun.media.epaper.search;

import com.oyun.media.epaper.common.ServiceMultiResult;
import org.springframework.data.domain.Pageable;

import java.util.Date;

/**
 * 搜索接口
 * @author changzhen
 */
public interface ISearchService {

    /**
     * 索引目标文章
     * @param articleId
     */
    boolean index(Long articleId);

    /**
     * 移除目标文章
     * @param articleId
     */
    void remove(Long articleId);

    /**
     * 查询文章接口
     * @param articleSearch
     * @return
     */
    ServiceMultiResult<Long> query(ArticleSearch articleSearch);

    void testData();
}
