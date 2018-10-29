package com.oyun.media.epaper.task;

import com.oyun.media.epaper.domain.Attachment;
import com.oyun.media.epaper.service.IAttachmentService;
import com.oyun.media.epaper.utils.FileUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;

/**
 * @program: epaper
 * @description: 附件定时任务
 * @author: changzhen
 * @create: 2018-08-10 16:17
 **/
@Component
@Log4j2
public class AttachmentTask {

    @Autowired
    private IAttachmentService attachmentService;

    @Autowired
    private FileUtil fileUtil;

    private int count=0;

    @Scheduled(cron="*/60 * * * * ?")
    private void process() {

        attachmentService.deleteTempAttachment();

    }


}
