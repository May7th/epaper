package com.oyun.media.epaper.controller;

import com.oyun.media.epaper.common.ApiResponse;
import com.oyun.media.epaper.common.ArticleStatus;
import com.oyun.media.epaper.common.Const;
import com.oyun.media.epaper.common.ServiceMultiResult;
import com.oyun.media.epaper.domain.Article;
import com.oyun.media.epaper.domain.Page;
import com.oyun.media.epaper.search.ArticleSearch;
import com.oyun.media.epaper.search.ISearchService;
import com.oyun.media.epaper.service.IArticleService;
import com.oyun.media.epaper.service.IPageService;
import com.oyun.media.epaper.utils.ConstraintViolationExceptionHandler;
import com.oyun.media.epaper.vo.Response;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("article")
public class ArticleController {

    @Autowired
    private IArticleService articleService;

    @Autowired
    private IPageService pageService;

    @Autowired
    private ISearchService searchService;

    /**
     * 删除文章
     * @param id
     * @return
     */
    @DeleteMapping
    @ApiOperation(value = "删除文章")
    public ResponseEntity<Response> delete(@RequestParam(value="id", required = true) Long id) {
        try {
            articleService.removeArticle(id);
        } catch (Exception e) {
            return  ResponseEntity.ok().body( new Response(false, e.getMessage()));
        }
        return  ResponseEntity.ok().body( new Response(true, "处理成功"));
    }

    @PostMapping("add")
    public ResponseEntity<Response> saveOrUpdatePaper(@RequestBody Article article) {
        try {

            articleService.saveOrUpdateArticle(article);

        }  catch (ConstraintViolationException e)  {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        }

        return ResponseEntity.ok().body(new Response(true, "处理成功", article));

    }
    @PutMapping(value = "/operate/{id}/{operation}")
    public ResponseEntity<Response> operateArticle(@PathVariable(value = "id") Long id,
                                                   @PathVariable("operation") int operation){

        if (id < 0){
            return ResponseEntity.badRequest().body(new Response(false,"id error"));
        }

        if (operation == ArticleStatus.PASSES.getValue()){
            articleService.updateStatus(id, ArticleStatus.PASSES.getValue());
            return ResponseEntity.ok().body(new Response(true, "处理成功"));
        }else if (operation == ArticleStatus.NOT_AUDITED.getValue()){
            articleService.updateStatus(id, ArticleStatus.NOT_AUDITED.getValue());
            return ResponseEntity.ok().body(new Response(true, "处理成功"));
        }
        return ResponseEntity.badRequest().body(new Response(false,"update status error"));

    }

    @GetMapping(value = "list")
    private ApiResponse getArticles(@ModelAttribute ArticleSearch articleSearch){

        ServiceMultiResult<Article> multiResult = articleService.query(articleSearch);

        return ApiResponse.ofSuccess(multiResult);
    }

    @PostMapping(value = "check")
    private ApiResponse checkContent(@RequestBody String content){

        String checkedContent = articleService.checkContent(content);

        if (checkedContent==null){
            return ApiResponse.ofMessage(ApiResponse.Status.NOT_FOUND.getCode(),"校正服务出错，请稍后再试。");
        }

        return ApiResponse.ofSuccess(checkedContent);
    }
}
