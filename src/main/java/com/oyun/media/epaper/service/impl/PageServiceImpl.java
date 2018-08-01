package com.oyun.media.epaper.service.impl;

import com.oyun.media.epaper.common.Const;
import com.oyun.media.epaper.domain.Page;
import com.oyun.media.epaper.repository.PageRepository;
import com.oyun.media.epaper.service.IPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

/**
 * @program: epaper
 * @description:
 * @author: changzhen
 * @create: 2018-05-30 09:18
 **/
@Service
public class PageServiceImpl implements IPageService{

    @Autowired
    private PageRepository pageRepository;

    @Override
    public Page getPageById(Long id) {
        return pageRepository.getOne(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Page updatePage(Page page) {
        page.setModifyTime(new Timestamp(System.currentTimeMillis()));
        Page oldPage = pageRepository.getOne(page.getId());
        page.setCreateTime(oldPage.getCreateTime());
        if (!oldPage.getArticleList().isEmpty()){
            page.setArticleList(oldPage.getArticleList());
        }
        return pageRepository.save(page);
    }

    @Override
    public org.springframework.data.domain.Page<Page> findAllPages(Pageable pageable) {
        return pageRepository.findAll(pageable);
    }

    @Transactional(rollbackFor = Exception.class )
    @Override
    public void removePage(Long id) {
        Page page = pageRepository.getOne(id);
        page.setState(Const.State.DELETED);
        pageRepository.save(page);
    }

    @Override
    public org.springframework.data.domain.Page<Page> getPaperPage(Long id, Pageable pageable){

        org.springframework.data.domain.Page<Page> pages = pageRepository.findAllByAndParentId(id,pageable);

        return pages;
    }

}
