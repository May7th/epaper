package com.oyun.media.epaper.controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.oyun.media.epaper.domain.*;
import com.oyun.media.epaper.repository.PageRepository;
import com.oyun.media.epaper.repository.PaperRepository;
import com.oyun.media.epaper.service.IAuthorityService;
import com.oyun.media.epaper.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 主页控制器.
 * @author changzhen
 */
@RestController
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
	    return "redirect:/epaper";
	}

	@GetMapping("/login")
	public ModelAndView login() {

		ModelAndView modelAndView = new ModelAndView("login");
		return modelAndView;
	}

	@GetMapping("/login-error")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		model.addAttribute("errorMsg", "登陆失败，用户名或者密码错误！");
		return "login";
	}
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
    /**
     * 注册用户
     * @param user
     * @return
     */
    @PostMapping("/register")
    public String registerUser(User user) {
        List<Authority> authorities = new ArrayList<>();
		authorities.add(authorityService.getAuthorityById(ROLE_USER_AUTHORITY_ID));
        user.setAuthorities(authorities);
        
        userService.registerUser(user);
        return "redirect:/login";
    }

    @GetMapping("/test")
	public void insertPage() throws ParseException {
    	List<Page> pages = new ArrayList<>();
		for (int m = 1; m <= 5; m++) {
			for (int i = 1; i <= 10; i++) {
				Page page = new Page();
				List<Article> articles = new ArrayList<>();
				for (int j = 1; j <= 10; j++) {
					Article article = new Article();
					article.setTitle("title"+j);
					article.setContent("content"+j);
					article.setContentHtml("contentHtml"+j);
					article.setCoordinate("1,1,1,10,10,10,10,"+j);
					article.setParentId(new Long(i));
					articles.add(article);
				}
				page.setArticleList(articles);
				page.setPageName("版面"+i+": 新闻"+i);
				page.setParentId((long) m);
				pages.add(page);
			}
			Paper paper = new Paper();
			paper.setPaperName("内蒙古日报 "+m);
			paper.setReleaseDate(new Date(new SimpleDateFormat("yyyy-MM-dd").parse("2016-01-01").getTime()));
			paper.setPageList(pages);
			paperRepository.save(paper);
		}

	}

	@GetMapping("/getPaper")
	public Paper getPaper(){

    	Paper paper = paperRepository.getOne(1L);

    	return paper;

	}



















}
