package com.oyun.media.epaper.repository;

import com.oyun.media.epaper.domain.Paper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;

/**
 * @author changzhen
 */
public interface PaperRepository extends JpaRepository<Paper,Long> {

    /**
     * @param date
     * @param pageable
     * @return
     */
    Page<Paper> findPapersByReleaseDate(Date date, Pageable pageable);

}
