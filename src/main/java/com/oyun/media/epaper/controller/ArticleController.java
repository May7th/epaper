package com.oyun.media.epaper.controller;

import com.oyun.media.epaper.domain.Article;
import com.oyun.media.epaper.domain.Page;
import com.oyun.media.epaper.service.IArticleService;
import com.oyun.media.epaper.service.IPageService;
import com.oyun.media.epaper.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @Autowired
    private IPageService pageService;

    @GetMapping("/add/{id}")
    public ModelAndView addPaperForm(Model model,@PathVariable("id") Long pageId){

        Page page = pageService.getPageById(pageId);
        model.addAttribute("page",page);
        model.addAttribute("article",new Article());
        return new ModelAndView("article/articleedit","articleModel",model);
    }

    /**
     * 删除报纸
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") Long id) {
        try {
            articleService.removeArticle(id);
        } catch (Exception e) {
            return  ResponseEntity.ok().body( new Response(false, e.getMessage()));
        }
        return  ResponseEntity.ok().body( new Response(true, "处理成功"));
    }

}
