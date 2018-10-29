package com.oyun.media.epaper.service.impl;

import com.oyun.media.epaper.domain.Catalog;
import com.oyun.media.epaper.repository.CatalogRepository;
import com.oyun.media.epaper.repository.UserRepository;
import com.oyun.media.epaper.service.ICatalogService;
import com.oyun.media.epaper.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: epaper
 * @description: 分类实现类
 * @author: changzhen
 * @create: 2018-10-10 16:07
 **/
@Service
public class CatalogServiceImp implements ICatalogService {

    @Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Catalog saveCatalog(Catalog catalog) {

        if (catalogRepository.findByName(catalog.getName()) != null && catalog.getId() == null){
            throw new IllegalArgumentException("分类已经存在");
        }
        catalog.setUser(UserUtil.getCurrentUser());

        return catalogRepository.save(catalog);
    }

    @Override
    public void removeCatalog(Long id) {
        catalogRepository.deleteById(id);
    }

    @Override
    public Catalog getCatalogById(Long id) {
        return catalogRepository.getOne(id);
    }

    @Override
    public List<Catalog> getCatalogList() {
        return catalogRepository.findAll();
    }

    @Override
    public List<Catalog> saveCatalogList(List<Catalog> list) {
        return catalogRepository.saveAll(list);
    }
}
