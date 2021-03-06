package com.oyun.media.epaper.service;

import com.oyun.media.epaper.common.ServiceMultiResult;
import com.oyun.media.epaper.domain.Paper;
import com.oyun.media.epaper.form.QueryData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

/**
 * @program: epaper
 * @description:
 * @author: changzhen
 * @create: 2018-05-25 15:09
 **/
public interface IPaperService {


    /**
     * 根据发行日期查询报纸
     * @param date
     * @return
     */
    Paper findPapersByReleaseDate(Date date);

    /**
     * 根据Id查询
     * @param id
     * @return
     */
    Paper getPaperById(Long id);

    /**
     * 根据Id删除报纸
     * @param id
     */
    void removePaper(Long id);

    /**
     * 增加新报纸
     * @param paper
     * @return
     */
    Paper addNewPaper(Paper paper);

    /**
     * 储存更新报纸
     * @param paper
     * @return
     */
    Paper saveOrUpdatePaper(Paper paper);

    /**
     * 获取所有报纸信息
     * @param queryData
     * @return
     */
    ServiceMultiResult<Paper> getAllPapers(QueryData queryData);

    /**
     * 添加新的版面
     * @param page
     */
    void addNewPage(com.oyun.media.epaper.domain.Page page);


}
