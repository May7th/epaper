package com.oyun.media.epaper.controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.oyun.media.epaper.common.ApiResponse;
import com.oyun.media.epaper.domain.*;
import com.oyun.media.epaper.repository.PageRepository;
import com.oyun.media.epaper.repository.PaperRepository;
import com.oyun.media.epaper.service.IAuthorityService;
import com.oyun.media.epaper.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * 主页控制器.
 * @author changzhen
 */
@Controller
public class MainController {
	
	private static final Long ROLE_USER_AUTHORITY_ID = 2L;
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IAuthorityService authorityService;

	@Autowired
	private PageRepository pageRepository;

	@Autowired
	private PaperRepository paperRepository;
	
	@GetMapping("/")
	public String root() {
		return "redirect:/index";
	}
	
	@GetMapping("/index")
	public String index() {
	    return "redirect:/paper";
	}



	@GetMapping("/404")
	public String notFoundPage() {
		return "404";
	}

	@GetMapping("/403")
	public String accessError() {
		return "403";
	}

	@GetMapping("/500")
	public String internalError() {
		return "500";
	}

















}
