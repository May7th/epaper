package com.oyun.media.epaper.controller;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.oyun.media.epaper.common.ApiResponse;
import com.oyun.media.epaper.datatransfer.DR;
import com.oyun.media.epaper.datatransfer.DRDao;
import com.oyun.media.epaper.datatransfer.SubPic;
import com.oyun.media.epaper.domain.Article;
import com.oyun.media.epaper.domain.Page;
import com.oyun.media.epaper.domain.Paper;
import com.oyun.media.epaper.domain.Recommend;
import com.oyun.media.epaper.repository.ArticleRepository;
import com.oyun.media.epaper.repository.RecommendRepository;
import com.oyun.media.epaper.search.ArticleSearch;
import com.oyun.media.epaper.service.IArticleService;
import com.oyun.media.epaper.service.IPageService;
import com.oyun.media.epaper.service.IPaperService;
import com.oyun.media.epaper.service.IRecommendService;
import com.oyun.media.epaper.service.impl.DataHandleService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.criteria.Order;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @program: epaper
 * @description:
 * @author: changzhen
 * @create: 2018-11-05 10:06
 **/
@Controller
@RequestMapping("epaper")
@Log4j2
public class FrontController {

    private static final int TEXT_RECOMMEND = 1;
    private static final int IMAGE_RECOMMEND = 2;
    private static final int ARTICLE_STATE = 1;

    @Autowired
    private IArticleService articleService;

    @Autowired
    private IRecommendService recommendService;

    @Autowired
    private IPaperService paperService;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private IPageService pageService;

    @Autowired
    private RecommendRepository recommendRepository;

    @Autowired
    private DataHandleService dataHandleService;

    @GetMapping("index")
    public String index(Model model){

        List<Article> clickRateList = articleService.clickRateList(6);
        List<Recommend> textRecommendList = recommendService.findAllRecommendsByType(13,TEXT_RECOMMEND);
        List<Recommend> imageRecommendList = recommendService.findAllRecommendsByType(7,IMAGE_RECOMMEND);
        Recommend printRecommend = imageRecommendList.remove(6);
        model.addAttribute("clickRateList",clickRateList);
        model.addAttribute("textRecommendList",textRecommendList);
        model.addAttribute("imageRecommendList",imageRecommendList);
        model.addAttribute("printRecommend",printRecommend);

        return "article/index";
    }

    @GetMapping("mobile/index")
    public String mobileIndex(Model model){

        List<Article> clickRateList = articleService.clickRateList(4);
        List<Recommend> textRecommendList = recommendService.findAllRecommendsByType(8,TEXT_RECOMMEND);
        List<Recommend> imageRecommendList = recommendService.findAllRecommendsByType(4,IMAGE_RECOMMEND);
        Recommend printRecommend = imageRecommendList.remove(3);
        model.addAttribute("clickRateList",clickRateList);
        model.addAttribute("textRecommendList",textRecommendList);
        model.addAttribute("imageRecommendList",imageRecommendList);
        model.addAttribute("printRecommend",printRecommend);

        return "article/mobile-index";
    }

    @GetMapping("paper")
    public String getPaper(String releaseDate,
                           @RequestParam(value="pageId",required=false,defaultValue="0")
                                   long pageId, Model model){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        try {
            date1 = format.parse(releaseDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Paper paper = paperService.findPapersByReleaseDate(date1);
        if (paper == null){
            List<Article> clickRateList = articleService.clickRateList(6);
            List<Recommend> textRecommendList = recommendService.findAllRecommendsByType(13,TEXT_RECOMMEND);
            List<Recommend> imageRecommendList = recommendService.findAllRecommendsByType(7,IMAGE_RECOMMEND);
            Recommend printRecommend = imageRecommendList.remove(6);
            model.addAttribute("clickRateList",clickRateList);
            model.addAttribute("textRecommendList",textRecommendList);
            model.addAttribute("imageRecommendList",imageRecommendList);
            model.addAttribute("printRecommend",printRecommend);
            return "article/index";
        }

        List<Page> pageList = paper.getPageList();

        pageList.sort(Comparator.comparing(Page::getPageName));

        Date date = paper.getReleaseDate();
        Page firstPage;
        if (pageId == 0){
            firstPage = pageList.get(0);
        }else {
            firstPage = pageService.getPageById(pageId);
        }


        List<Article> articleList = articleRepository.findAllByParentIdAndState(firstPage.getId(),1);
        if (articleList.size()<1){
            List<Article> clickRateList = articleService.clickRateList(6);
            List<Recommend> textRecommendList = recommendService.findAllRecommendsByType(13,TEXT_RECOMMEND);
            List<Recommend> imageRecommendList = recommendService.findAllRecommendsByType(7,IMAGE_RECOMMEND);
            Recommend printRecommend = imageRecommendList.remove(6);
            model.addAttribute("clickRateList",clickRateList);
            model.addAttribute("textRecommendList",textRecommendList);
            model.addAttribute("imageRecommendList",imageRecommendList);
            model.addAttribute("printRecommend",printRecommend);
            return "article/index";
        }
        String pageImagePath = firstPage.getPageImagePath();
        Map<Long,String> coordinateMap = new HashMap<>();

        articleList.forEach(article -> {
            coordinateMap.put(article.getId(),article.getCoordinate());
        });

        model.addAttribute("currentId",firstPage.getId());
        model.addAttribute("paper",paper);
        model.addAttribute("pageList",pageList);
        model.addAttribute("releaseDate",date);
        model.addAttribute("articleList",articleList);
        model.addAttribute("pageImagePath",pageImagePath);
        model.addAttribute("coordinateMap",coordinateMap);

        return "article/page-detail";

    }

    @GetMapping("mobile/paper")
    public String getMobilePaper(String releaseDate,
                           @RequestParam(value="pageId",required=false,defaultValue="0")
                                   long pageId, Model model){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        try {
            date1 = format.parse(releaseDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Paper paper = paperService.findPapersByReleaseDate(date1);
        if (paper == null){
            return "redirect:index";
        }

        List<Page> pageList = paper.getPageList();

        pageList.sort(Comparator.comparing(Page::getPageName));

        Date date = paper.getReleaseDate();
        Page firstPage;
        if (pageId == 0){
            firstPage = pageList.get(0);
        }else {
            firstPage = pageService.getPageById(pageId);
        }


        List<Article> articleList = articleRepository.findAllByParentIdAndState(firstPage.getId(),1);
        if (articleList.size()<1){
            return "redirect:index";
        }
        String pageImagePath = firstPage.getPageImagePath();
        Map<Long,String> coordinateMap = new HashMap<>();

        articleList.forEach(article -> {
            coordinateMap.put(article.getId(),article.getCoordinate());
        });

        model.addAttribute("currentId",firstPage.getId());
        model.addAttribute("paper",paper);
        model.addAttribute("pageList",pageList);
        model.addAttribute("releaseDate",date);
        model.addAttribute("articleList",articleList);
        model.addAttribute("pageImagePath",pageImagePath);
        model.addAttribute("coordinateMap",coordinateMap);

        return "article/mobile-page";

    }


    @GetMapping("article/{id}")
    public ModelAndView articleDetail(@PathVariable("id") Long id, Model model){

        Article article = articleService.getArticleById(id);

        if (articleService.increaseReadSize(article) == null){
            log.error("article read error,id: "+id);
        }

        Page page = pageService.getPageById(article.getParentId());
        List<Article> articleList = page.getArticleList();
        Map<Long,String> coordinateMap = new HashMap<>();

        articleList.forEach(pageArticle -> {
            coordinateMap.put(pageArticle.getId(),pageArticle.getCoordinate());
        });
        Long paperId = page.getParentId();
        Paper paper = paperService.getPaperById(paperId);
        List<Page> pageList = paper.getPageList();
        pageList.sort(Comparator.comparing(Page::getPageName));

        model.addAttribute(article);
        model.addAttribute("currentPage",page);
        model.addAttribute("coordinateMap",coordinateMap);
        model.addAttribute("pageList",pageList);

        return new ModelAndView("article/detail", "articleModel", model);
    }

    @GetMapping("mobile/article/{id}")
    public ModelAndView mobileArticleDetail(@PathVariable("id") Long id, Model model){

        Article article = articleService.getArticleById(id);

        if (articleService.increaseReadSize(article) == null){
            log.error("article read error,id: "+id);
        }

        Page page = pageService.getPageById(article.getParentId());
        List<Article> articleList = page.getArticleList();
        Map<Long,String> coordinateMap = new HashMap<>();

        articleList.forEach(pageArticle -> {
            coordinateMap.put(pageArticle.getId(),pageArticle.getCoordinate());
        });
        Long paperId = page.getParentId();
        Paper paper = paperService.getPaperById(paperId);
        List<Page> pageList = paper.getPageList();
        pageList.sort(Comparator.comparing(Page::getPageName));

        model.addAttribute(article);
        model.addAttribute("currentPage",page);
        model.addAttribute("coordinateMap",coordinateMap);
        model.addAttribute("pageList",pageList);

        return new ModelAndView("article/mobile-detail", "articleModel", model);
    }

    @GetMapping("article/search")
    public ModelAndView articleSearch(@ModelAttribute ArticleSearch articleSearch,Model model){

        String keywords = articleSearch.getKeywords();
        String wordType = articleSearch.getWordType();
        Date releaseDate = articleSearch.getReleaseDate();
        if (releaseDate != null){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
            String date = format.format(releaseDate);
            model.addAttribute("releaseDate",date);
        }
        org.springframework.data.domain.Page<Article> page = articleService.queryPage(articleSearch);

        List<Article> articleList = page.getContent();
        model.addAttribute("articleList",articleList);
        model.addAttribute("page",page);
        model.addAttribute("keywords",keywords);
        model.addAttribute("wordType",wordType);
        model.addAttribute("total",page.getTotalElements());

        return new ModelAndView("article/search-list","articleModel",model);
    }

    @GetMapping("article/searchList")
    public ModelAndView searchArticleList(@ModelAttribute ArticleSearch articleSearch,Model model){

        org.springframework.data.domain.Page<Article> articles = articleService.queryPage(articleSearch);

        model.addAttribute("articleList",articles.getContent());

        return new ModelAndView("article/list","articleModel",model);
    }

    @GetMapping("paper/page/{pageId}")
    public ModelAndView getPageDetail(@PathVariable long pageId,Model model){

        Page page = pageService.getPageById(pageId);

        List<Article> articleList = page.getArticleList();

        model.addAttribute("articleList",articleList);

        return new ModelAndView("article/coordinate-list","articleModel",model);
    }

    @GetMapping("paper/pageCoordinate/{pageId}")
    @ResponseBody
    public List<String> getPageCoordinate(@PathVariable long pageId){
        Page page = pageService.getPageById(pageId);

        List<Article> articleList = page.getArticleList();

        List<String> coordinateList = new ArrayList<>();

        articleList.forEach(article -> {
            coordinateList.add(article.getCoordinate());
        });
        return coordinateList;
    }

    @GetMapping("article/clickRate")
    public String clickRateList(Model model){

        long total = articleRepository.countAllByState(ARTICLE_STATE);

        model.addAttribute("total",total);

        return "article/click-list";
    }

    @GetMapping("article/getClickList")
    public ModelAndView getAllClickList(@RequestParam(value="start",required=false,defaultValue="0") int start,
                                         @RequestParam(value="size",required=false,defaultValue="10") int size,
                                        Model model){

        Sort sort = new Sort(Sort.Direction. DESC, "readSize");
        int index = start/size;
        Pageable pageable = new PageRequest(index, size,sort);

        org.springframework.data.domain.Page<Article> articleList = articleRepository.findAllByState(ARTICLE_STATE,pageable);

        model.addAttribute("articleList",articleList.getContent());

        return new ModelAndView("article/list","articleModel",model);

    }

//    @GetMapping("article/test/search")
//    public ModelAndView articleSearchTest(@ModelAttribute ArticleSearch articleSearch,Model model){
//
//        String keywords = articleSearch.getKeywords();
//        String wordType = articleSearch.getWordType();
//        Date releaseDate = articleSearch.getReleaseDate();
//        if (releaseDate != null){
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
//            String date = format.format(releaseDate);
//            model.addAttribute("releaseDate",date);
//        }
//        org.springframework.data.domain.Page<Article> page = articleService.queryPage(articleSearch);
//
//        List<Article> articleList = page.getContent();
//        model.addAttribute("articleList",articleList);
//        model.addAttribute("page",page);
//        model.addAttribute("keywords",keywords);
//        model.addAttribute("wordType",wordType);
//        model.addAttribute("total",page.getTotalElements());
//
//        return new ModelAndView("article/search-list","articleModel",model);
//    }


//    @GetMapping("oyun/data/image/handle/url")
//    private void handleImageNews(){
//        dataHandleService.handleImageNews();
//    }
}
