package com.oyun.media.epaper.repository;

import com.oyun.media.epaper.common.Const;
import com.oyun.media.epaper.domain.Paper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;

/**
 * @author changzhen
 */
public interface PaperRepository extends JpaRepository<Paper,Long>,
        JpaSpecificationExecutor<Paper> {

    /**
     * 根据发行日期查询报纸
     * @param date
     * @return
     */
    Paper findPapersByReleaseDate(Date date);

    Page<Paper> findAllByStateIsNot(Pageable pageable,int state);

}
