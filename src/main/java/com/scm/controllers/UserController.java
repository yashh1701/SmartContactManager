package com.scm.controllers;


import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.entities.Contacts;
import com.scm.entities.User;
import com.scm.helpers.Helper;
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
		public String userDashboard(Model model, Principal principal, Authentication authentication) {

			 // validate principal
		    if (principal == null) {
		        return "redirect:/login?error=notLoggedIn";
		    }

		    // get email
		    String email = principal.getName();
		    logger.info("Logged-in email = {}", email);

		    // fetch user
		    String username = 	Helper.getEmailOfLoggedInUser(authentication);
		    User loggedInUser = userService.getUserByEmail(username);

		    if (loggedInUser == null) {
		        logger.error("No User found in database for email: {}", email);
		        return "redirect:/login?error=userNotFound";
		    }

		    // add loggedUser
		    model.addAttribute("loggedInUser", loggedInUser);

		    // recent contacts
		    List<Contacts> recentContacts = loggedInUser.getContacts()
		            .stream()
		            .sorted((a, b) -> b.getId().compareTo(a.getId()))
		            .limit(5)
		            .toList();

		    model.addAttribute("recentContacts", recentContacts);

		    // count favourite
		    long favouriteCount = loggedInUser.getContacts()
		            .stream()
		            .filter(Contacts::isFavourite)
		            .count();

		    model.addAttribute("favouriteCount", favouriteCount);
		    
		    String phoneNumber = loggedInUser.getPhoneNumber();
		    model.addAttribute("phoneNumber", phoneNumber);
		    

		    return "user/dashboard";
		}
		
		// user profile page
		@GetMapping("/profile")
		public String userProfile(Model model, Principal principal, Authentication authentication) {
			  String email = principal.getName();

			    String username = 	Helper.getEmailOfLoggedInUser(authentication);
			    User loggedInUser = userService.getUserByEmail(username);
			  

			    model.addAttribute("loggedInUser", loggedInUser);

			    long totalContacts = loggedInUser.getContacts().size();
			    model.addAttribute("totalContacts", totalContacts);

			    long favouriteCount = loggedInUser.getContacts()
			            .stream().filter(c -> c.isFavourite()).count();
			    model.addAttribute("favouriteCount", favouriteCount);

			    return "user/profile";
		}
		
		// user add contacts page
		
		//user view contacts
		
		//user edit contacts 
		
		// user delete contact 
	
	

}
