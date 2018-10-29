package com.oyun.media.epaper.service.impl;

import com.oyun.media.epaper.common.ServiceMultiResult;
import com.oyun.media.epaper.domain.Attachment;
import com.oyun.media.epaper.form.DataTableSearch;
import com.oyun.media.epaper.repository.AttachmentRepository;
import com.oyun.media.epaper.service.IAttachmentService;
import com.oyun.media.epaper.utils.FileUtil;
import com.oyun.media.epaper.utils.LoginUserUtil;
import com.oyun.media.epaper.utils.MD5Util;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sun.security.provider.MD5;

import javax.imageio.ImageIO;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @program: epaper
 * @description: 附件上传实现类
 * @author: changzhen
 * @create: 2018-08-10 00:09
 **/
@Service
@Log4j2
public class AttachmentServiceImpl implements IAttachmentService {

    @Value("${server.address}")
    private String serverAddress;

    @Value("${server.port}")
    private String serverPort;

    @Value("${spring.servlet.multipart.location}")
    private String multipartLocation;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    FileUtil fileUtil;

    /**
     * 储存文件
     * @param file
     * @param request
     * @return
     */
    @Transactional(rollbackFor = Exception.class )
    @Override
    public Attachment save(MultipartFile file, HttpServletRequest request) {
        if (file.isEmpty()){
            log.error("上传文件为空!");
            return null;
        }
        try {
            Attachment attachment = uploadFile(file,request);
            return attachmentRepository.save(attachment);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Attachment save(Attachment attachment){
        return attachmentRepository.save(attachment);
    }

    @Override
    public Attachment switchStatus(Attachment attachment){
        if (attachment == null){
            log.error("无附件需要修改状态！");
            return null;
        }
        if (attachment.isTemp()){
            attachment.setTemp(false);
        }else {
            attachment.setTemp(true);
        }
        return attachment;
    }

    @Override
    public Attachment switchStatus(String url){

        Attachment attachment = attachmentRepository.findByUrl(url);

        return switchStatus(attachment);
    }

    @Override
    public Page<Attachment> getAllAttachmentByTemp(boolean temp, Pageable pageable) {
//        TODO
        return null;
    }

    @Override
    public Attachment getAttachmentByUrl(String url){

        return attachmentRepository.findByUrl(url);
    }

    @Override
    public List<Attachment> getAllAttachmentByTemp(boolean temp){


        List<Attachment> attachmentPage = attachmentRepository.findAllByTemp(temp);

        return attachmentPage;

    }

    @Override
    public ServiceMultiResult<Attachment> getAllAttachment(DataTableSearch searchBody) {

        Sort sort = new Sort(Sort.Direction.fromString(searchBody.getDirection()),searchBody.getOrderBy());

        int page = searchBody.getStart()/searchBody.getLength();

        Pageable pageable = PageRequest.of(page,searchBody.getLength(),sort);

        Specification<Attachment> specification = (root, criteriaQuery, criteriaBuilder) -> {

            Predicate predicate = criteriaBuilder.equal(root.get("username"), LoginUserUtil.getLoginUserName());

            if (searchBody.getTemp() != null){
                predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("temp"),searchBody.getTemp()));
            }

            if (searchBody.getCreateTimeMin() != null){
                predicate = criteriaBuilder.and(predicate,criteriaBuilder.greaterThanOrEqualTo(root.get("uploadDate"),searchBody.getCreateTimeMin()));
            }

            if (searchBody.getCreateTimeMax() != null){
                predicate = criteriaBuilder.and(predicate,criteriaBuilder.lessThanOrEqualTo(root.get("uploadDate"),searchBody.getCreateTimeMax()));
            }

            if (searchBody.getOldName() != null){
                predicate = criteriaBuilder.and(predicate,criteriaBuilder.like(root.get("oldName"),"%"+searchBody.getOldName()+"%"));
            }

            System.out.println(predicate.toString());
            return predicate;
        };

        Page<Attachment> attachmentPage = attachmentRepository.findAll(specification,pageable);
        return new ServiceMultiResult<>(attachmentPage.getTotalElements(),attachmentPage.getContent());
    }

    @Override
    public List<Attachment> getAttachmentsByUrl(Set<String> urls) {
        List<Attachment> attachmentList = new ArrayList<>();
        if (urls.isEmpty()){
            return null;
        }else {
            urls.forEach(url->{
                attachmentList.add(attachmentRepository.findByUrl(url));
            });
            return attachmentList;
        }
    }

    @Override
    public void updateAttachemntsByUrl(List<Attachment> newAttachments,List<Attachment> attachments){
        if (attachments.isEmpty()){
            return;
        }
        attachments.forEach(attachment -> {
            if (!newAttachments.contains(attachment)){
                switchStatus(attachment);
            }
        });
    }

    @Transactional(rollbackFor = Exception.class )
    @Override
    public void delete(Attachment attachment){

        attachmentRepository.delete(attachment);

    }

    @Transactional(rollbackFor = Exception.class )
    @Override
    public void deleteTempAttachment(){

        List<Attachment> attachmentList = getAllAttachmentByTemp(true);

        if (attachmentList.isEmpty()){
            log.info("暂无需要删除的文件！");
        }else {

            attachmentList.forEach(attachment -> {
                delete(attachment);
                boolean success = fileUtil.deleteFile(attachment.getRateSourceUrl());
                log.info("成功删除文件：{}", attachment.getOldName());
            });

            log.info("删除文件完毕！");
        }

    }

    /**
     * 文件上传至服务器
     * @param file
     * @param request
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public Attachment uploadFile(MultipartFile file, HttpServletRequest request) throws IOException, NoSuchAlgorithmException {
        String username = request.getUserPrincipal().getName();
        return this.uploadFile(file,username);
    }

    @Override
    public Attachment uploadFile(MultipartFile file, String username) throws IOException {
        String type = file.getContentType();
        long size = file.getSize();
        String oldName = file.getOriginalFilename();
        System.out.println(oldName);
        String suffix = fileUtil.getSuffix(oldName);
        boolean img = (ImageIO.read(file.getInputStream())==null)?false:true;
        String name = fileUtil.getUUID() + suffix;
        String saveDir = fileUtil.saveFileToDir(file,name);
        String rateSourceUrl = multipartLocation+"/"+saveDir;
        //上传文件地址
        String url = "/upload/image/"+saveDir;
//        String url = "//"+serverAddress+":"+serverPort+"/upload/image/"+saveDir;
        Date uploadDate = new Date();

        Attachment attachment = Attachment.builder()
                .username(username)
                .type(type)
                .size(size)
                .oldName(oldName)
                .suffix(suffix)
                .img(img)
                .name(name)
                .rateSourceUrl(rateSourceUrl)
                .url(url)
                .uploadDate(uploadDate)
                .temp(true)
                .build();
        return attachment;
    }






















}
