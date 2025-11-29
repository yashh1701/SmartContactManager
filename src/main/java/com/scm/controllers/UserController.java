package com.scm.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.services.UserService;

// This controller contain all the controller regarding users protected URL's


@Controller
@RequestMapping("/user")
public class UserController {
	
	private Logger logger=LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;
	
	
		// user dashBoard page
		@GetMapping("/dashboard")
		public String userDashboard() {
			
			return  "user/dashboard";
		}
		
		// user profile page
		@GetMapping("/profile")
		public String userProfile() {
			return  "user/profile";
		}
		
		// user add contacts page
		
		//user view contacts
		
		//user edit contacts 
		
		// user delete contact 
	
	

}
