package com.oyun.media.epaper.controller;

import com.oyun.media.epaper.domain.Article;
import com.oyun.media.epaper.domain.Page;
import com.oyun.media.epaper.service.IArticleService;
import com.oyun.media.epaper.service.IPageService;
import com.oyun.media.epaper.utils.ConstraintViolationExceptionHandler;
import com.oyun.media.epaper.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolationException;

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
        System.out.println(page);
        Article article = new Article();
        article.setParentId(page.getParentId());
        model.addAttribute("page",page);
        model.addAttribute("article",article);
        return new ModelAndView("article/article","articleModel",model);
    }

    @GetMapping("{id}")
    public ModelAndView ArticleDetails(@PathVariable("id")Long id,Model model){
        Article article = articleService.getArticleById(id);
        Page page = pageService.getPageById(article.getParentId());
        model.addAttribute("page",page);
        model.addAttribute("article",article);
        return new ModelAndView("article/article","articleModel",model);
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

    @PostMapping
    public ResponseEntity<Response> saveOrUpdatePaper(@RequestBody Article article) {
        try {

            articleService.saveOrUpdateArticle(article);

        }  catch (ConstraintViolationException e)  {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        }

        return ResponseEntity.ok().body(new Response(true, "处理成功", article));

    }

}
