package com.oyun.media.epaper.service.impl;

import com.oyun.media.epaper.common.Const;
import com.oyun.media.epaper.domain.Paper;
import com.oyun.media.epaper.repository.PaperRepository;
import com.oyun.media.epaper.service.IPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * @program: epaper
 * @description:
 * @author: changzhen
 * @create: 2018-05-29 10:11
 **/
@Service
public class PaperServiceImpl implements IPaperService {

    @Autowired
    private PaperRepository paperRepository;

    @Transactional(rollbackFor = Exception.class )
    @Override
    public Paper saveOrUpdatePaper(Paper paper) {
        if (paper.getId() == null){
            return paperRepository.save(paper);
        }else {
            Paper oldPaper = paperRepository.getOne(paper.getId());
            oldPaper.setPaperName(paper.getPaperName());
            oldPaper.setReleaseDate(paper.getReleaseDate());
            oldPaper.setModifyTime(new Timestamp(System.currentTimeMillis()));

            return paperRepository.save(oldPaper);
        }
    }

    @Transactional(rollbackFor = Exception.class )
    @Override
    public Paper addNewPaper(Paper paper) {
        return paperRepository.save(paper);
    }

    @Transactional(rollbackFor = Exception.class )
    @Override
    public void removePaper(Long id) {
        Paper paper = paperRepository.getOne(id);
        paper.setState(Const.State.DELETED);
        paperRepository.save(paper);
    }

    @Override
    public Paper getPaperById(Long id) {
        return paperRepository.getOne(id);
    }

    @Override
    public Page<Paper> findPapersByReleaseDate(Date date, Pageable pageable) {

        Page<Paper> papers = paperRepository.findPapersByReleaseDate(date,pageable);
        return papers;
    }

    @Override
    public Page<Paper> findAllPapers(Pageable pageable){

        Page<Paper> papers = paperRepository.findAll(pageable);

        return papers;
    }

    @Transactional(rollbackFor = Exception.class )
    @Override
    public void addNewPage(com.oyun.media.epaper.domain.Page page){

        Paper paper = paperRepository.getOne(page.getParentId());

        List<com.oyun.media.epaper.domain.Page> pageList =  paper.getPageList();

        page.setReleaseDate(paper.getReleaseDate());

        pageList.add(page);

        paper.setPageList(pageList);

        paperRepository.save(paper);
    }
}
