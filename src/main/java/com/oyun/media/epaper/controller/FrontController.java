package com.oyun.media.epaper.controller;

import com.oyun.media.epaper.common.ApiResponse;
import com.oyun.media.epaper.common.ServiceMultiResult;
import com.oyun.media.epaper.domain.Article;
import com.oyun.media.epaper.domain.Page;
import com.oyun.media.epaper.repository.ArticleRepository;
import com.oyun.media.epaper.search.ArticleSearch;
import com.oyun.media.epaper.service.IArticleService;
import com.oyun.media.epaper.service.IPageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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

    @Autowired
    private IArticleService articleService;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private IPageService pageService;

    @GetMapping("article/{id}")
    public ModelAndView articleDetail(@PathVariable("id") Long id, Model model){

        Article article = articleService.getArticleById(id);

        if (articleService.increaseReadSize(article) == null){
            log.error("article read error,id: "+id);
        }

        Page page = pageService.getPageById(article.getParentId());

        model.addAttribute(article);
        model.addAttribute(page);

        return new ModelAndView("article/detail", "articleModel", model);
    }

    @GetMapping("article/search")
    public ModelAndView articleSearch(@ModelAttribute ArticleSearch articleSearch,Model model){

        ServiceMultiResult<Article> multiResult = articleService.query(articleSearch);

        List<Article> articleList = multiResult.getResult();

        model.addAttribute(articleList);

        return new ModelAndView("article/search-list","articleList",articleList);
    }
}
