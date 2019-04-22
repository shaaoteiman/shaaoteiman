package com.fnst.springboot01.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fnst.springboot01.entity.User;
import com.fnst.springboot01.service.UserService;

@Controller
public class UserController {
	
	@Resource
	private UserService userService;

	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping("/zeroException")
    public int zeroException(){
       return 100/0;
    }
	@GetMapping("/save")
	@ResponseBody
	public String save(){
		User user = new User();
		user.setName("xiaoming");
		userService.save(user);
		return "OK";
	}
	@GetMapping("/user/{id}")
	@ResponseBody
	public User getUserById(@PathVariable String id){
		return userService.getUserById(Long.valueOf(id));
	}
}
