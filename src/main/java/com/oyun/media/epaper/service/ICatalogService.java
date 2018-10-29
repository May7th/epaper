package com.oyun.media.epaper.service;

import com.oyun.media.epaper.domain.Catalog;
import com.oyun.media.epaper.domain.Recommend;

import java.util.List;

/**
 * @program: epaper
 * @description: 分类接口
 * @author: changzhen
 * @create: 2018-10-10 16:05
 **/
public interface ICatalogService {

    Catalog saveCatalog(Catalog catalog);

    void removeCatalog(Long id);

    Catalog getCatalogById(Long id);

    List<Catalog> getCatalogList();

    List<Catalog> saveCatalogList(List<Catalog> list);
}
