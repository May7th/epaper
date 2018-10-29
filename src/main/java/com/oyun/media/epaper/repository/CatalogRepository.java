package com.oyun.media.epaper.repository;

import com.oyun.media.epaper.domain.Attachment;
import com.oyun.media.epaper.domain.Catalog;
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
public interface CatalogRepository extends JpaRepository<Catalog,Long>,
        JpaSpecificationExecutor<Catalog>{

    Catalog findByName(String name);

}
