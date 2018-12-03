package com.oyun.media.epaper.service.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.oyun.media.epaper.datatransfer.DR;
import com.oyun.media.epaper.datatransfer.DRDao;
import com.oyun.media.epaper.datatransfer.MongoUtilTest;
import com.oyun.media.epaper.datatransfer.SubPic;
import com.oyun.media.epaper.domain.Article;
import com.oyun.media.epaper.domain.Attachment;
import com.oyun.media.epaper.repository.ArticleRepository;
import com.oyun.media.epaper.repository.AttachmentRepository;
import com.oyun.media.epaper.service.IAttachmentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: epaper
 * @description: 数据处理
 * @author: changzhen
 * @create: 2018-11-27 23:08
 **/
@Service
public class DataHandleService {

    @Autowired
    private DRDao drDao;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private IAttachmentService attachmentService;


    public void handleImageNews(){

        List<Article> articleList = articleRepository.findAllByContentHtmlContainsAndStateNotAndTitleIsNot("<img ",1,"ᠵᠢᠷᠤᠭᠲᠤ ᠮᠡᠳᠡᠭᠡ");
//
        System.out.println(articleList.size());

        articleList.forEach(article -> {



            String drName = getDrName(article);

            DR dr = drDao.findeDemoByName(drName);

            List<SubPic> subPicList = dr.getSubPics();

            final String[] articleIndex = {""};
            subPicList.forEach(subPic -> {

                if (subPic.getContent() != null) {
                    StringBuffer mongoTitle = new StringBuffer(subPic.getContent());
                    StringBuffer title = new StringBuffer(article.getTitle());

                    String a = replaceStr(getClearString(mongoTitle).trim());
                    String b = replaceStr(getClearString(title).trim());
                    if (b.contains(a) || a.contains(b) ) {
                        articleIndex[0] = subPic.getName().substring(0, 3);
                    }
                }
            });
            List<String> pictureList = new ArrayList<>();
            List<String> pictureTitleList = new ArrayList<>();
            List<String> contentList = new ArrayList<>();
            for (int i = 0; i < subPicList.size(); i++) {
                for (int j = 1; j < 20; j++) {
                    if (subPicList.get(i).getName().equals(articleIndex[0] + "p" + j + ".jpeg")) {
                        pictureList.add(getImage(subPicList.get(i).getId(),subPicList.get(i).getName()));
                    }
                }

                for (int j = 1; j < 10; j++) {
                    if (subPicList.get(i).getName().equals(articleIndex[0] + "pb" + j + ".jpeg")) {
                        pictureTitleList.add(subPicList.get(i).getContent());
                    }
                }

                for (int j = 1; j < 12; j++) {
                    for (int k = 0; k < 15; k++) {
                        if (subPicList.get(i).getName().equals(articleIndex[0] + "p" + j + "-b" + k + ".jpeg")) {
                            pictureTitleList.add(replaceStr(subPicList.get(i).getContent()));
                        }
                    }

                }

                for (int j = 1; j < 15; j++) {
                    if (subPicList.get(i).getName().equals(articleIndex[0] + "c" + j + ".jpeg")) {
                        contentList.add(replaceStr(subPicList.get(i).getContent()));
                    }
                }
            }

            System.out.println(pictureList);
            System.out.println(pictureTitleList);
            System.out.println(contentList);

            StringBuffer content = new StringBuffer();

            pictureList.forEach(s -> {
                content.append("<img src="+s+"/>");
            });
            pictureTitleList.forEach(s -> {
                content.append(s);
            });
            contentList.forEach(s -> {
                content.append(s);
            });

            article.setContentHtml(content.toString());
            if (pictureList.isEmpty()&&pictureTitleList.isEmpty()&&contentList.isEmpty()){
                System.out.println("未找到图片新闻！");
            }else {
                articleRepository.saveAndFlush(article);
            }

        });

        System.out.println("数据处理完成！");
    }

    private String getDrName(Article article){

        Date date = article.getReleaseDate();

        String parentName = article.getParentName();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String dateString = formatter.format(date);

        String drName = dateString+parentName.substring(0,2)+".jpg";

        System.out.println(drName);
        return drName;
    }

    private String replaceStr(String str){
        if (StringUtils.isEmpty(str)){
            return str;
        }

        String s = str.replaceAll("︽","《")
                .replaceAll("︾","》")
                .replaceAll("︕","!")
                .replaceAll("︖","?")
                .replaceAll("︵","(")
                .replaceAll("︶",")");

        return s;

    }

    public String getClearString(StringBuffer stringBuffer){
        return  stringBuffer.toString().replaceAll("</p>","")
                .replaceAll("<br>","")
                .replaceAll("&nbsp;","")
                .replaceAll("<p>","")
                .replaceAll("<BR>","");

    }

    public String getImage(String id,String name){
        DB picDB = MongoUtilTest.instance.getDBDB("picsdb");
        GridFS gfsPhoto = new GridFS(picDB);
        DBObject query2  = new BasicDBObject("filename", id);
        GridFSDBFile gf = gfsPhoto.findOne(query2);
        if(gf != null){
            try {
                MultipartFile multipartFile = new MockMultipartFile(id+"_"+name,name,gf.getContentType(), gf.getInputStream());
                Attachment attachment = attachmentService.uploadFile(multipartFile,"后台");
                String url = attachmentRepository.save(attachment).getUrl();
                return url;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }



}
