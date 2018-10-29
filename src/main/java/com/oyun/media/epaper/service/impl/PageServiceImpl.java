package com.oyun.media.epaper.service.impl;

import com.oyun.media.epaper.common.Const;
import com.oyun.media.epaper.domain.Page;
import com.oyun.media.epaper.repository.PageRepository;
import com.oyun.media.epaper.service.IAttachmentService;
import com.oyun.media.epaper.service.IPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.sql.Timestamp;
import java.util.List;

/**
 * @program: epaper
 * @description:
 * @author: changzhen
 * @create: 2018-05-30 09:18
 **/
@Service
@PersistenceContext(type = PersistenceContextType.EXTENDED)
public class PageServiceImpl implements IPageService{

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private IAttachmentService attachmentService;

    @Override
    public Page getPageById(Long id) {
        return pageRepository.getOne(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Page updatePage(Page page) {
        page.setModifyTime(new Timestamp(System.currentTimeMillis()));
        Page oldPage = pageRepository.getOne(page.getId());
        // 更新附件后需切换其临时状态
        if (!oldPage.getPageImagePath().equals(page.getPageImagePath())){
            attachmentService.switchStatus(oldPage.getPageImagePath());
            attachmentService.switchStatus(page.getPageImagePath());

        }
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
        page.setState(Const.State.DELETE);
        pageRepository.save(page);
    }

    @Override
    public List<Page> getPaperPage(Long id){

        List<Page> pages = pageRepository.findAllByAndParentId(id);

        return pages;
    }

}
