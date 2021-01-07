package com.chagvv.myhome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chagvv.myhome.model.User;
import com.chagvv.myhome.service.UserService;

@Controller
@RequestMapping("/account")
public class AccountController {
	
	private UserService userService;
	
	public AccountController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/login")
	public String login() {
		return "account/login";
	}
	
	@GetMapping("/register")
	public String register() {

		return "account/register";
	}
	
	@PostMapping("/register")
	public String register(User user) {
		userService.save(user);
		
		return "redirect:/";
	}
}
