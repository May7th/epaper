package com.oyun.media.epaper.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import com.oyun.media.epaper.common.ApiResponse;
import com.oyun.media.epaper.common.ServiceMultiResult;
import com.oyun.media.epaper.domain.Authority;
import com.oyun.media.epaper.domain.User;
import com.oyun.media.epaper.service.IAuthorityService;
import com.oyun.media.epaper.service.IUserService;
import com.oyun.media.epaper.utils.ConstraintViolationExceptionHandler;
import com.oyun.media.epaper.utils.UserUtil;
import com.oyun.media.epaper.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * User 控制器.
 * 
 */
@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IAuthorityService authorityService;

    @GetMapping(value = "/list")
    public ApiResponse getUserList(){

        List<User> userList = userService.getAllUser();

        ApiResponse response = new ApiResponse(ApiResponse.Status.SUCCESS.getCode(),
                ApiResponse.Status.SUCCESS.getStandardMessage(),userList);

        return response;
    }
	
	/**
     * 查询所有用户
     * @param async
     * @param pageIndex
     * @param pageSize
     * @param name
     * @param model
     * @return
     */
    @GetMapping
    public ModelAndView list(@RequestParam(value="async",required=false) boolean async,
            @RequestParam(value="pageIndex",required=false,defaultValue="0") int pageIndex,
            @RequestParam(value="pageSize",required=false,defaultValue="10") int pageSize,
            @RequestParam(value="name",required=false,defaultValue="") String name,
            Model model) {

        Pageable pageable = new PageRequest(pageIndex, pageSize);
        Page<User> page = userService.listUsersByNameLike(name, pageable);
        List<User> list = page.getContent();

        model.addAttribute("page", page);
        model.addAttribute("userList", list);
        return new ModelAndView(async==true?"users/list :: #mainContainerRepleace":"users/list", "userModel", model);
    }

    /**
     * 保存或者修改用户
     * @param user
     * @return
     */
    @PostMapping("add")
    public ResponseEntity<Response> saveOrUpdateUser(@RequestBody User user) {

        Long authorityId = user.getAuthorityId();
        List<Authority> authorities = new ArrayList<>();
        authorities.add(authorityService.getAuthorityById(authorityId));
        user.setAuthorities(authorities);
        
        try {
            userService.saveOrUpateUser(user);
        }  catch (ConstraintViolationException e)  {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        }

        return ResponseEntity.ok().body(new Response(true, "处理成功", user));
    }

	
    /**
     * 删除用户
     * @param id
     * @return
     */
    @DeleteMapping
    public ResponseEntity<Response> delete(@RequestParam(value="id", required = true) Long id) {
        try {
            userService.removeUser(id);
        } catch (Exception e) {
            return  ResponseEntity.ok().body( new Response(false, e.getMessage()));
        }
        return  ResponseEntity.ok().body( new Response(true, "处理成功"));
    }
	
    /**
     * 获取修改用户的界面
     * @param id
     * @param model
     * @return
     */
    @GetMapping(value = "edit/{id}")
    public ModelAndView modifyForm(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return new ModelAndView("users/edit", "userModel", model);
    }

    @GetMapping(value = "/info")
    public Object getCurrentUser(@AuthenticationPrincipal UserDetails userDetails){
        return userDetails;
    }


    @PostMapping("resetPassword")
    public ResponseEntity<Response> resetPassword(@RequestParam(value = "oldPassword",required = true) String oldPassword,
                                     @RequestParam(value = "newPassword",required = true) String newPassword){

        User currentUser = UserUtil.getCurrentUser();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (passwordEncoder.matches(oldPassword,currentUser.getPassword())){

            currentUser.setPassword(newPassword);

            userService.saveOrUpateUser(currentUser);
            return ResponseEntity.ok().body(new Response(true, "密码修改成功"));
        }else {
            return ResponseEntity.badRequest().body(new Response(false,"update status error"));        }
    }
}
