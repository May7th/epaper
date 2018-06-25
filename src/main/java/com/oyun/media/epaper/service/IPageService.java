package com.oyun.media.epaper.service;

import com.oyun.media.epaper.domain.Page;
import com.oyun.media.epaper.domain.Paper;
import org.springframework.data.domain.Pageable;

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
     * 增加新报纸
     * @param page
     * @return
     */
    Page addNewPage(Page page);

    /**
     * 储存更新报纸
     * @param page
     * @return
     */
    Page saveOrUpdatePage(Page page);

    /**
     * 获取所有报纸信息
     * @param pageable
     * @return
     */
    org.springframework.data.domain.Page<Page> findAllPages(Pageable pageable);



}
