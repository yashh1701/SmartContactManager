package com.scm.controllers;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.Contacts;
import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.forms.ContactSearchForm;
import com.scm.helpers.AppConstants;
import com.scm.helpers.Helper;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.ContactService;
import com.scm.services.ImageService;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/user/contacts")
public class ContactController {
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(ContactController.class);
	
	@Autowired
	private ContactService contactService;
	@Autowired
    private UserService userService;
	
	@Autowired
	private ImageService imageService;
	
	@RequestMapping("/add")
	public String addContactView(Model model) {
		
		ContactForm contactForm = new ContactForm();
		contactForm.setFavourite(true);
		model.addAttribute("contactform",contactForm);       	//(attributeName sending to model, type of object)
		
		return "user/add_contact";
	}
	
	@PostMapping("/add")
	public String saveContact(@Valid @ModelAttribute("contactform") ContactForm contactform, BindingResult bindlingResult,Authentication authentication, HttpSession session) {
		
		//process form data 
		
		// Validate form code
		if(bindlingResult.hasErrors()) {
//			bindlingResult.getAllErrors().forEach(error -> logger.info(error.toString()));

            session.setAttribute("message", Message.builder()
                    .content("Please fill the contact details correctly!")
                    .type(MessageType.red)
                    .build());
			return "user/add_contact";
		}
		
		
		String username = 	Helper.getEmailOfLoggedInUser(authentication);
		User user = userService.getUserByEmail(username);
		
		// convert form to contact
		System.out.println(contactform);
		
		//Process Image code
//		logger.info("File Info : {}", contactform.getContactImage().getOriginalFilename());
		 
		//convert contactForm to contact and then save to db
		Contacts contact =  new Contacts();
		contact.setName(contactform.getName());
		contact.setFavourite(contactform.getFavourite());
		contact.setEmail(contactform.getEmail());
		contact.setPhoneNumber(contactform.getPhoneNumber());
		contact.setAddress(contactform.getAddress());
		contact.setDescription(contactform.getDescription());
		contact.setUser(user);
		contact.setLinkedinLink(contactform.getLinkedinLink());
		contact.setWebsiteLink(contactform.getWebsiteLink());
		
		if (contactform.getContactImage() != null && !contactform.getContactImage().isEmpty()) {
            String filename = UUID.randomUUID().toString();
            String fileURL = imageService.uploadImage(contactform.getContactImage(), filename);
            contact.setPicture(fileURL);
            contact.setCloudinaryImagePublicId(filename);

        }
		
		//Save contact to database
		contactService.save(contact);

		// message pop up code
		session.setAttribute("message",
                Message.builder()
                        .content("New Contact added successfully!")
                        .type(MessageType.green)
                        .build());
		
		return "redirect:/user/contacts/add";
	}
	
	//View Contacts
	
	@GetMapping
	public String viewContacts(
		 @RequestParam(value = "page", defaultValue = "0") int page,
         @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE +"") int size,
         @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
         @RequestParam(value = "direction", defaultValue = "asc") String direction,
         Model model, Authentication authentication) {
		
		// load all contacts of user
		String username = Helper.getEmailOfLoggedInUser(authentication);
		
		User user = userService.getUserByEmail(	username);
		
		Page<Contacts> pageContact = contactService.getByUser(user,page, size, sortBy, direction);
		
		model.addAttribute("pageContact", pageContact);
		model.addAttribute("pageSize", size);
		model.addAttribute("contactSearchForm", new ContactSearchForm());
		
		
		return "user/contacts";
	}
	
	
//	 Search handler
	@GetMapping("/search")
	public String searchHandler(
			@ModelAttribute ContactSearchForm contactSearchForm,
			@RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE +"") int size,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
	         @RequestParam(value = "direction", defaultValue = "asc") String direction,	
	         Model model, Authentication authentication) {
		
		logger.info("field->{} : Value->{}", contactSearchForm.getField(),contactSearchForm.getValue());
		
		var user = userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));

        Page<Contacts> pageContact = null;
        
        if (contactSearchForm.getField().equalsIgnoreCase("name")) {
            pageContact = contactService.searchByName(contactSearchForm.getValue(), size, page, sortBy, direction,user);
        } 
        else if (contactSearchForm.getField().equalsIgnoreCase("email")) {
            pageContact = contactService.searchByEmail(contactSearchForm.getValue(), size, page, sortBy, direction,user);
        } 
        else if (contactSearchForm.getField().equalsIgnoreCase("phone")) {
            pageContact = contactService.searchByPhoneNumber(contactSearchForm.getValue(), size, page, sortBy,direction, user);
        }
        
        model.addAttribute("pageContact", pageContact);
		model.addAttribute("pageSize", size);
		model.addAttribute("contactSearchForm", contactSearchForm);
		
		return "/user/search";
	}
	
	

}
