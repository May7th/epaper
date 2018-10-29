package com.oyun.media.epaper.controller;

import com.oyun.media.epaper.common.ApiResponse;
import com.oyun.media.epaper.common.ServiceMultiResult;
import com.oyun.media.epaper.domain.Authority;
import com.oyun.media.epaper.domain.Page;
import com.oyun.media.epaper.domain.Paper;
import com.oyun.media.epaper.domain.User;
import com.oyun.media.epaper.form.QueryData;
import com.oyun.media.epaper.service.IPageService;
import com.oyun.media.epaper.service.IPaperService;
import com.oyun.media.epaper.utils.ConstraintViolationExceptionHandler;
import com.oyun.media.epaper.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolationException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: epaper
 * @description: 报刊控制器
 * @author: changzhen
 * @create: 2018-05-29 09:35
 **/
@RestController
@RequestMapping("/paper")
public class PaperController {

    @Autowired
    private IPaperService paperService;

    @Autowired
    private IPageService pageService;

    @GetMapping(value = "/list")
    public ApiResponse getPaperPage(@ModelAttribute QueryData query){

        ServiceMultiResult<Paper> paperPage = paperService.getAllPapers(query);

        ApiResponse response = new ApiResponse(ApiResponse.Status.SUCCESS.getCode(),
                ApiResponse.Status.SUCCESS.getStandardMessage(),paperPage);

        return response;
    }

    @PostMapping("/add")
    public ResponseEntity<Response> saveOrUpdatePaper(@RequestBody Paper paper) {

        try {
            paperService.saveOrUpdatePaper(paper);
        }  catch (ConstraintViolationException e)  {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        }

        return ResponseEntity.ok().body(new Response(true, "处理成功", paper));
    }


    /**
     * 删除报纸
     * @param id
     * @return
     */
    @DeleteMapping
    public ResponseEntity<Response> delete(@RequestParam(value="id", required = true) Long id) {
        try {
            paperService.removePaper(id);
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body( new Response(false, e.getMessage()));
        }
        return  ResponseEntity.ok().body( new Response(true, "处理成功"));
    }

    /**
     * 获取修改报纸界面
     * @param id
     * @param model
     * @return
     */
    @GetMapping(value = "edit/{id}")
    public ModelAndView modifyForm(@PathVariable("id") Long id, Model model) {
        Paper paper = paperService.getPaperById(id);
        model.addAttribute("paper", paper);
        return new ModelAndView("paper/edit", "paperModel", model);
    }

    @GetMapping(value = "/pages")
    public ApiResponse paperDetails(@ModelAttribute QueryData query,
                                     @RequestParam(value = "id", required = true)Long id){

        List<Page> pageList = pageService.getPaperPage(id);
        ApiResponse response = new ApiResponse(ApiResponse.Status.SUCCESS.getCode(),
                ApiResponse.Status.SUCCESS.getStandardMessage(),pageList);

        return response;

    }

}
