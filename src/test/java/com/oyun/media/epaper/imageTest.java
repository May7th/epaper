//package com.oyun.media.epaper;
//
//import com.oyun.media.epaper.domain.Attachment;
//import com.oyun.media.epaper.domain.Page;
//import com.oyun.media.epaper.repository.AttachmentRepository;
//import com.oyun.media.epaper.repository.PageRepository;
//import net.coobird.thumbnailator.Thumbnails;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//
///**
// * @program: epaper
// * @description:
// * @author: changzhen
// * @create: 2018-11-29 10:11
// **/
//public class imageTest extends EpaperApplicationTests {
//
//    @Autowired
//    private PageRepository pageRepository;
//
//    @Autowired
//    private AttachmentRepository attachmentRepository;
//
//    public void getFrontImage(String imageUrl) {
//
//        File fromFile = new File(imageUrl);
//
//        try {
//            String frontImageUrl = imageUrl.replace("upload/","upload/mobile/");
//            Thumbnails.of(fromFile).scale(0.3f).outputQuality(0.3f)
//                    .toFile(frontImageUrl);
//            System.out.println(frontImageUrl);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//    @Test
//    public void handleImage(){
//        List<Page> pageList = pageRepository.findAll();
//
//        pageList.forEach(page -> {
//
//            String pageImagePath = page.getPageImagePath();
//
//            Attachment attachment = attachmentRepository.findByUrl(pageImagePath);
//
//            String imageUrl = attachment.getRateSourceUrl().replace("/IdeaProjects/","/Users/changzhen/work/oyun/epaper/");
//
//            getFrontImage(imageUrl);
//
//            System.out.println("complete!");
//        });
//
//    }
//}
