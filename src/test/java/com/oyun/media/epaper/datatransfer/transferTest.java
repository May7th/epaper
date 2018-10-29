package com.oyun.media.epaper.datatransfer;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.oyun.media.epaper.EpaperApplicationTests;
import com.oyun.media.epaper.domain.Article;
import com.oyun.media.epaper.domain.Attachment;
import com.oyun.media.epaper.domain.Page;
import com.oyun.media.epaper.domain.Paper;
import com.oyun.media.epaper.repository.AttachmentRepository;
import com.oyun.media.epaper.repository.PageRepository;
import com.oyun.media.epaper.search.ISearchService;
import com.oyun.media.epaper.service.IArticleService;
import com.oyun.media.epaper.service.IAttachmentService;
import com.oyun.media.epaper.service.IPageService;
import com.oyun.media.epaper.service.IPaperService;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: epaper
 * @description:
 * @author: changzhen
 * @create: 2018-10-25 14:10
 **/
@Log4j2
public class transferTest extends EpaperApplicationTests {

    @Autowired
    private DRDao drDao;

    @Autowired
    private IPaperService paperService;

    @Autowired
    private IAttachmentService attachmentService;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private IPageService pageService;

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private ISearchService searchService;

    @Autowired
    private IArticleService articleService;

    @Test
    public void transferTest() throws ParseException {
        DR dr = drDao.findDemoById("5bac9739bd9a9a34f4636b4b");

        String paperPage = dr.getName();
        String paperDate = new StringBuffer(paperPage.substring(0,8)).insert(4,"-").insert(7,"-").toString();
        String pageIndex = paperPage.substring(8,10);

        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

        Date releaseDate = dateFormat1.parse(paperDate);

        Paper paper = paperService.findPapersByReleaseDate(releaseDate);

        if (paper==null){
            paper = new Paper();
            paper.setPaperName("ᠦᠪᠦᠷ ᠮᠤᠨᠭᠭᠤᠯ ᠦᠨ ᠡᠳᠦᠷ ᠦᠨ ᠰᠤᠨᠢᠨ");
            paper.setReleaseDate(releaseDate);
        }
        Paper savedPaper = paperService.saveOrUpdatePaper(paper);
        Page page = new Page();
        page.setParentId(savedPaper.getId());
        page.setPageName(pageIndex+" ᠬᠡᠪᠯᠡᠯ");
        if (dr.getContent()!=null){
            page.setDescription(dr.getContent());
        }
        String ImagePath = getImage(dr.getId(),dr.getName());
        page.setPageImagePath(ImagePath);
        Page returnPage = pageRepository.save(page);

        Map<String, Map<String,Map<Integer,String>>> articleMap = new HashMap<>();
        if (!dr.getSubPics().isEmpty()){
            List<SubPic> subPicList = dr.getSubPics();
            subPicList.forEach(subPic -> {
                String fullName = subPic.getName();
                String[] subName = fullName.substring(0,fullName.lastIndexOf(".")).split("-");
                String type = subName[1].substring(0,subName[1].length()-1);
                String order = subName[1].substring(subName[1].length()-1);

                if (articleMap.containsKey(subName[0])){
                    Map<String,Map<Integer,String>> typeMap = articleMap.get(subName[0]);
                    String content;
                    if (!"p".equals(type)){
                        content = subPic.getContent();
                    }else {
                        content = getImage(subPic.getId(),subPic.getName());
                    }

                    if (typeMap.containsKey(type)){
                        Map<Integer,String> partMap = typeMap.get(type);
                        partMap.put(Integer.parseInt(order),content);
                    }else {
                        Map<Integer,String> partMap = new HashMap<>();
                        partMap.put(Integer.parseInt(order),content);
                        typeMap.put(type,partMap);
                    }

                }else {
                    Map<Integer,String> part = new HashMap<>();
                    part.put(Integer.parseInt(order),subPic.getContent());
                    Map<String,Map<Integer,String>> typeMap = new HashMap<>();
                    typeMap.put(type,part);
                    articleMap.put(subName[0],typeMap);
                }
            });
        List<Article> articles = new ArrayList<>();
            articleMap.forEach((k,v)->{
                Article article = new Article();
                article.setParentId(returnPage.getId());
                StringBuffer content = new StringBuffer();
                v.forEach((type,part)->{
                    if ("p".equals(type)){
                        StringBuffer p = new StringBuffer();
                        part.forEach((i,s)->{
                            p.append("<img src='"+s+"' style='width: 220px;height: 150px;'/>");
                            if (v.containsKey("bp")&&v.get("bp").get(i)!=null){
                                p.append(v.get("bp").get(i));
                            }
                        });
                        content.insert(0,p);
                        article.setContentHtml(content.toString());
                    }else {
                        StringBuffer stringBuffer = new StringBuffer();
                        part.forEach((i,s)->{
                            if (s!=null){
                                stringBuffer.append(s+" ");
                            }
                        });
                        switch (type){
                            case "c":
                                content.append(stringBuffer);
                                article.setContentHtml(content.toString());
                                break;
                            case "f":
                                article.setSubTitle(getClearString(stringBuffer));
                                break;
                            case "z":
                                article.setAuthor(getClearString(stringBuffer));
                                break;
                            case "b":
                                article.setTitle(getClearString(stringBuffer));
                                break;
                        }
                    }
                });
                article.setContent(articleService.getIntermediateCode(article.getContentHtml()));
                articles.add(article);
            });
           page.setArticleList(articles);

            paperService.addNewPaper(paper);

            paperService.addNewPage(page);


            List<Article> savedArticleList = articleService.findArticlesByParentId(returnPage.getId());
            savedArticleList.forEach(article -> {
                searchService.index(article.getId());
            });
        }

    }

    public String getImage(String id,String name){
        DB picDB = MongoUtilTest.instance.getDBDB("picsdb");
        GridFS gfsPhoto = new GridFS(picDB);
//        GridFSFindIterable iterable = gridFsTemplate.find(query(whereFilename().is(dr.getName())));
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

    public String getClearString(StringBuffer stringBuffer){
        return  stringBuffer.toString().replaceAll("</p>","")
                                        .replaceAll("<br>","")
                                        .replaceAll("&nbsp;","")
                                        .replaceAll("<p>","");

    }

    public static void main(String[] args) {
        String pageIndex = "02";

        String first = pageIndex.substring(0,1);
        if (first.equals("0")){
            String pageName = pageIndex.substring(1)+"ᠬᠡᠪᠯᠡᠯ";
        }else {
            String pageName = pageIndex+"ᠬᠡᠪᠯᠡᠯ";
        }


    }
}