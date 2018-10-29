package com.oyun.media.epaper.service;

import com.oyun.media.epaper.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @program: epaper
 * @description:
 * @author: changzhen
 * @create: 2018-05-25 15:07
 **/
public interface IPageService {

    /**
     * 根据Id获取页面
     * @param id
     * @return
     */
    Page getPageById(Long id);

    /**
     * 根据Id删除报纸
     * @param id
     */
    void removePage(Long id);

    /**
     * 储存更新报纸
     * @param page
     * @return
     */
    Page updatePage(Page page);

    /**
     * 获取所有报纸信息
     * @param pageable
     * @return
     */
    org.springframework.data.domain.Page<Page> findAllPages(Pageable pageable);


    /**
     * 根据paperId查找page
     * @param id
     * @return
     */
    List<Page> getPaperPage(Long id);
}
