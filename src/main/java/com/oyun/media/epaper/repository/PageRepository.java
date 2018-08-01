package com.oyun.media.epaper.repository;

import com.oyun.media.epaper.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author changzhen
 */
public interface PageRepository extends JpaRepository<Page,Long> {

    /**
     * 根据parentId查询所有page
     * @param parentId
     * @param pageable
     * @return
     */
    org.springframework.data.domain.Page<Page> findAllByAndParentId(Long parentId, Pageable pageable);
}
