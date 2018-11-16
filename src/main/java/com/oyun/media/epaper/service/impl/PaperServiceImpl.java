package com.oyun.media.epaper.service.impl;

import com.oyun.media.epaper.common.Const;
import com.oyun.media.epaper.common.ServiceMultiResult;
import com.oyun.media.epaper.domain.Attachment;
import com.oyun.media.epaper.domain.Paper;
import com.oyun.media.epaper.form.QueryData;
import com.oyun.media.epaper.repository.PaperRepository;
import com.oyun.media.epaper.service.IAttachmentService;
import com.oyun.media.epaper.service.IPaperService;
import com.oyun.media.epaper.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.sql.Timestamp;
import java.util.Date;
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

    @Autowired
    private IAttachmentService attachmentService;

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
        paper.setState(Const.State.DELETE);
        paperRepository.save(paper);
    }

    @Override
    public Paper getPaperById(Long id) {
        return paperRepository.getOne(id);
    }

    @Override
    public Paper findPapersByReleaseDate(Date date) {

        Paper paper = paperRepository.findPaperByReleaseDate(date);

        return paper;
    }

    @Override
    public ServiceMultiResult<Paper> getAllPapers(QueryData queryData){
        Sort sort;
        if (queryData.getOrder().isEmpty()||queryData.getSort().isEmpty()){
            sort = new Sort(Sort.Direction.ASC,"id");
        }else {
            sort = new Sort(queryData.getOrder().isEmpty() ? null:Sort.Direction.fromString(queryData.getOrder()),queryData.getSort());

        }

        int page = queryData.getOffset()/queryData.getLimit();

        Pageable pageable = PageRequest.of(page,queryData.getLimit(),sort);

//        Specification<Paper> specification = (root, criteriaQuery, criteriaBuilder) -> {
//
//            System.out.println(LoginUserUtil.getLoginUserName());
//            Predicate predicate = criteriaBuilder.equal(root.get("username"), LoginUserUtil.getLoginUserName());
//
//            if (queryData.getPaperName() != null){
//                predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("paperName"),queryData.getPaperName()));
//            }
//
//            if (queryData.getReleaseDate() != null){
//                predicate = criteriaBuilder.and(predicate,criteriaBuilder.greaterThanOrEqualTo(root.get("releaseDate"),queryData.getReleaseDate()));
//            }
//
//            System.out.println(predicate.toString());
//            return predicate;
//        };

        Page<Paper> paperPage = paperRepository.findAllByStateIsNot(pageable, Const.State.DELETE);
        return new ServiceMultiResult<>(paperPage.getTotalElements(),paperPage.getContent());

    }

    @Transactional(rollbackFor = Exception.class )
    @Override
    public void addNewPage(com.oyun.media.epaper.domain.Page page){

        Paper paper = paperRepository.getOne(page.getParentId());

        List<com.oyun.media.epaper.domain.Page> pageList =  paper.getPageList();

        page.setReleaseDate(paper.getReleaseDate());

        attachmentService.save(attachmentService.switchStatus(page.getPageImagePath()));

        pageList.add(page);

        paper.setPageList(pageList);

        paperRepository.save(paper);
    }
}
