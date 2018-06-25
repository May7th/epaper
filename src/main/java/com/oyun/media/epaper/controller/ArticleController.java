package com.oyun.media.epaper.controller;

import com.oyun.media.epaper.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
