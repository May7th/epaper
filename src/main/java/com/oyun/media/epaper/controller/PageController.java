package com.oyun.media.epaper.controller;

import com.oyun.media.epaper.common.ApiResponse;
import com.oyun.media.epaper.domain.Article;
import com.oyun.media.epaper.domain.Page;
import com.oyun.media.epaper.form.QueryData;
import com.oyun.media.epaper.service.IPageService;
import com.oyun.media.epaper.service.IPaperService;
import com.oyun.media.epaper.vo.Response;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.oyun.media.epaper.utils.ConstraintViolationExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.List;


/**
 * @program: epaper
 * @description: 版面控制器
 * @author: changzhen
 * @create: 2018-05-30 09:15
 **/
@RestController
@RequestMapping(value = "/page")
public class PageController {

    @Autowired
    private IPageService pageService;

    @Autowired
    private IPaperService paperService;


    @DeleteMapping
    @ApiOperation(value = "删除版面")
    public ResponseEntity<Response> delete(@RequestParam(value="id", required = true) Long id) {
        try {
            pageService.removePage(id);
        } catch (Exception e) {
            return  ResponseEntity.ok().body( new Response(false, e.getMessage()));
        }
        return  ResponseEntity.ok().body( new Response(true, "版面删除成功"));
    }

    @PostMapping("add")
    public ResponseEntity<Response> saveOrUpdatePaper(@RequestBody Page page) {
        try {
            if (page.getId() == null){
                paperService.addNewPage(page);
            }else {
                pageService.updatePage(page);
            }

        }  catch (ConstraintViolationException e)  {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        }

        return ResponseEntity.ok().body(new Response(true, "处理成功", page));
    }

    @GetMapping(value = "/articles")
    public ApiResponse pageDetails(@ModelAttribute QueryData query,
                                    @RequestParam(value = "id", required = true)Long id){

        List<Article> articleList = pageService.getPageById(id).getArticleList();
        ApiResponse response = new ApiResponse(ApiResponse.Status.SUCCESS.getCode(),
                ApiResponse.Status.SUCCESS.getStandardMessage(),articleList);

        return response;

    }

    @GetMapping(value = "getImagePath")
    public String getImagePath(@RequestParam(value = "pageId", required = true) Long pageId){
        Page page = pageService.getPageById(pageId);
        return page.getPageImagePath();
    }


}
