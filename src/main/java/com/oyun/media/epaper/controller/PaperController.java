package com.oyun.media.epaper.controller;

import com.oyun.media.epaper.domain.Authority;
import com.oyun.media.epaper.domain.Paper;
import com.oyun.media.epaper.domain.User;
import com.oyun.media.epaper.service.IPaperService;
import com.oyun.media.epaper.utils.ConstraintViolationExceptionHandler;
import com.oyun.media.epaper.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
@RequestMapping("/papers")
public class PaperController {

    @Autowired
    private IPaperService paperService;

    @GetMapping
    public ModelAndView list(@RequestParam(value="async",required=false) boolean async,
                             @RequestParam(value="pageIndex",required=false,defaultValue="0") int pageIndex,
                             @RequestParam(value="pageSize",required=false,defaultValue="10") int pageSize,
                             Model model) {

        Pageable pageable = new PageRequest(pageIndex, pageSize);
        Page<Paper> page = paperService.findAllPapers(pageable);
        List<Paper> list = page.getContent();

        model.addAttribute("page", page);
        model.addAttribute("paperList", list);
        return new ModelAndView(async==true?"paper/list :: #mainContainerRepleace":"paper/list", "paperModel", model);
    }

    @GetMapping("/add")
    public ModelAndView addPaperForm(Model model){
        model.addAttribute("paper",new Paper());
        return new ModelAndView("paper/add","paperModel",model);
    }

    @PostMapping
    public ResponseEntity<Response> saveOrUpdatePaper(Paper paper) {

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
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") Long id) {
        try {
            paperService.removePaper(id);
        } catch (Exception e) {
            return  ResponseEntity.ok().body( new Response(false, e.getMessage()));
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

    @GetMapping(value = "/{id}")
    public ModelAndView paperDetails(@PathVariable("id")Long id,Model model){
        Paper paper = paperService.getPaperById(id);
        model.addAttribute(paper);
        return new ModelAndView("paper/pages","paperModel",model);
    }

}
