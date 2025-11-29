package com.scm.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.entities.User;
import com.scm.helpers.Helper;
import com.scm.services.UserService;


//this controller(inside methods) will run for every request
@ControllerAdvice
public class RootController {                                            
	
	Logger logger=LoggerFactory.getLogger(getClass());
	@Autowired
	private UserService userService;
	
	@ModelAttribute
	public void addingLoggedInUserInformation(Model model, Authentication authentication) {
		
		if(authentication==null) {
			return;
		}
		
		System.out.println("Adding loggedIn user Information to Model.....");
		
		String username=Helper.getEmailOfLoggedInUser(authentication);
		logger.info("User logged in : "+ username);
		
		// fetch user from database
		User user = userService.getUserByEmail(username);
		System.out.println("User email :- " + user.getEmail());
		System.out.println("User name :- " + user.getName());
		model.addAttribute("loggedInUser",user);
	}

}
