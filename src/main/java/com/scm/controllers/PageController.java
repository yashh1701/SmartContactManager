package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class PageController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String index() {
		
		return "redirect:/home";
	}
	
	@GetMapping("/home")
	public String home(){
		System.out.println("Home Page Handler...");
		return "home";
	}
	
	
	@GetMapping("/base")
	public String base(){
		System.out.println("Base Page Handler...");
		return "base";
	}
	
	
	@GetMapping("/services")
	public String services(){
		System.out.println("Services Page Handler...");
		return "services";
	}
	
	@GetMapping("/about")
	public String about(){
		System.out.println("About Page Handler...");
		return "about";
	}
	
	
	@GetMapping("/contact")
	public String contact(){
		System.out.println("Contact Page Handler...");
		return "contact";
	}
	
	
	@GetMapping("/login")   // login view page
	public String login(){
		System.out.println("Get Login Page Handler...");
		return "login";
	}

	
	@GetMapping("/signup")  // registration page
	public String signup(Model model){
		
		UserForm userForm = new UserForm();
//		userForm.setName("Yash"); 
		//we can send default value to form(front-end) from here
//		userForm.setEmail("yashsherekar17@gmail.com");
		
		model.addAttribute("userForm", userForm);  // add object data to model & which we can use anywhere using model 
		System.out.println("SignUp Page Handler...");
		return "signup";
	}
	
	
	
	
	// Processing signUp (registration)
	@PostMapping("/do-signup")
	public String  processSignup(@Valid @ModelAttribute UserForm userForm, BindingResult bindingResult, HttpSession session) {
			System.out.println("Processing SignUp");
			
			// fetch form data
			System.out.println(userForm);
			
			// validate form data (video -14) (@valid, @BindingResult)
			// error hai to return to signup page else do the remaining task
			// all these error are displayed on front end (signup.html) 
			if(bindingResult.hasErrors()) {
				return "signup";
			}
		
        
		
			// save data to database
			// Sending Data from UserForm -> User(object)		
			User user = new User();
	        user.setName(userForm.getName()); 
	        user.setEmail(userForm.getEmail());
	        user.setPassword(userForm.getPassword());
	        user.setAbout(userForm.getAbout());
	        user.setPhoneNumber(userForm.getPhoneNumber());
	        user.setEnabled(false);
	        user.setProfilePic(null);
	        User savedUser = userService.saveUser(user);
	        System.out.println("User saved...");
			
	        
			// message registration successful
	        // add the message:
	        Message message = Message.builder().content("Registration Successful").type(MessageType.blue).build(); // message ek hi baar dikhe (alert mein)
	        session.setAttribute("message", message);
	        
	        
			// redirect to login page
			return "redirect:/signup";  // redirecting it to signUp page (i.e to above route)
	}
	
}
