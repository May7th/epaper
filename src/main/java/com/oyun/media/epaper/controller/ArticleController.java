package com.oyun.media.epaper.controller;

import com.oyun.media.epaper.domain.Article;
import com.oyun.media.epaper.domain.Paper;
import com.oyun.media.epaper.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @program: epaper
 * @description: 文章控制器
 * @author: changzhen
 * @create: 2018-05-29 12:03
 **/
@RestController
@RequestMapping("articles")
public class ArticleController {

    @Autowired
    private IArticleService articleService;

    @GetMapping("/add")
    public ModelAndView addPaperForm(Model model){
        model.addAttribute("article",new Article());
        return new ModelAndView("article/articleedit","articleModel",model);
    }


}
