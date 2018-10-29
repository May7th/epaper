package com.oyun.media.epaper.repository;

import com.oyun.media.epaper.domain.Attachment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @program: epaper
 * @description: 附件
 * @author: changzhen
 * @create: 2018-06-04 11:42
 **/
public interface AttachmentRepository extends JpaRepository<Attachment,Long>,
        JpaSpecificationExecutor<Attachment>{

    /**
     * 根据url查找附件
     * @param url
     * @return
     */
    Attachment findByUrl(String url);

    Page<Attachment> findAllByTemp(boolean temp,Pageable pageable);

    List<Attachment> findAllByTemp(boolean temp);

}
