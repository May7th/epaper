package com.oyun.media.epaper.controller;

import com.oyun.media.epaper.common.ApiDataTableResponse;
import com.oyun.media.epaper.common.ApiResponse;
import com.oyun.media.epaper.common.ServiceMultiResult;
import com.oyun.media.epaper.domain.Attachment;
import com.oyun.media.epaper.domain.Page;
import com.oyun.media.epaper.form.DataTableSearch;
import com.oyun.media.epaper.service.IAttachmentService;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: epaper
 * @description: 附件控制器
 * @author: changzhen
 * @create: 2018-06-27 23:10
 **/
@Slf4j
@Controller
public class AttachmentController {

    private final ResourceLoader resourceLoader;

    @Autowired
    private IAttachmentService attachmentService;


    public AttachmentController( ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ApiResponse uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request){

        if(file.isEmpty()){
            return ApiResponse.ofStatus(ApiResponse.Status.NOT_VALID_PARAM);
        }
        Attachment attachment = attachmentService.save(file,request);

        if (attachment == null){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("error", "图片不合法");
            return ApiResponse.ofError(map);
        }else {
            return ApiResponse.ofSuccess(attachment.getUrl());
        }
    }

    @GetMapping("/upload/view")
    public ResponseEntity<Object> serveFileOnline() {

        return ResponseEntity.ok(resourceLoader.getResource("file:" + "/Users/changzhen/work/oyun/epaper/epaper/upload/test.jpg"));

    }

    @GetMapping("attachment/list")
    public String houseListPage() {
        return "attachment/attachment-list";
    }

    @GetMapping("/attachments")
    @ResponseBody
    public ApiDataTableResponse attachments(@ModelAttribute DataTableSearch searchBody){

        ServiceMultiResult<Attachment> attachmentPage = attachmentService.getAllAttachment(searchBody);

        ApiDataTableResponse response = new ApiDataTableResponse(ApiResponse.Status.SUCCESS);

        response.setData(attachmentPage.getResult());
        response.setRecordsFiltered(attachmentPage.getTotal());
        response.setRecordsTotal(attachmentPage.getTotal());

        response.setDraw(searchBody.getDraw());

        return response;
    }


















}
