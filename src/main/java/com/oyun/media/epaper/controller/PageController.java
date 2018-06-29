package com.oyun.media.epaper.controller;

import com.oyun.media.epaper.domain.Page;
import com.oyun.media.epaper.service.IPageService;
import com.oyun.media.epaper.service.IPaperService;
import com.oyun.media.epaper.vo.Response;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.oyun.media.epaper.utils.ConstraintViolationExceptionHandler;

import javax.validation.ConstraintViolationException;


/**
 * @program: epaper
 * @description: 版面控制器
 * @author: changzhen
 * @create: 2018-05-30 09:15
 **/
@RestController
@RequestMapping(value = "/pages")
public class PageController {

    @Autowired
    private IPageService pageService;

    @Autowired
    private IPaperService paperService;

    @GetMapping("/add/{id}")
    public ModelAndView addPageForm(Model model,@PathVariable("id") Long id){
        model.addAttribute("paperId",id);
        model.addAttribute("page",new Page());
        return new ModelAndView("page/add","pageModel",model);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除版面")
    public ResponseEntity<Response> delete(@PathVariable("id") Long id) {
        try {
            pageService.removePage(id);
        } catch (Exception e) {
            return  ResponseEntity.ok().body( new Response(false, e.getMessage()));
        }
        return  ResponseEntity.ok().body( new Response(true, "处理成功"));
    }

    @PostMapping
    public ResponseEntity<Response> saveOrUpdatePaper(Page page) {
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

    /**
     * 获取修改版面界面
     * @param id
     * @param model
     * @return
     */
    @GetMapping(value = "edit/{id}")
    public ModelAndView modifyForm(@PathVariable("id") Long id, Model model) {
        Page page = pageService.getPageById(id);
        model.addAttribute("page", page);
        return new ModelAndView("page/edit", "pageModel", model);
    }

    @GetMapping(value = "/{id}")
    public ModelAndView pageDetails(@PathVariable("id")Long id,Model model){
        Page page = pageService.getPageById(id);
        model.addAttribute(page);
        return new ModelAndView("page/articles","articleModel",model);
    }


}
