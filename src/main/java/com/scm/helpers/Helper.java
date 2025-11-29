package com.scm.helpers;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import lombok.experimental.var;

public class Helper {

	public static String getEmailOfLoggedInUser(Authentication authentication) {
		
		if(authentication instanceof OAuth2AuthenticationToken) {
			
			var oauth2AuthenticationToken =(OAuth2AuthenticationToken) authentication;
			var clientRegistrationId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();
			var oauth2User=(DefaultOAuth2User)authentication.getPrincipal();
			String username="";
			
			if(clientRegistrationId.equalsIgnoreCase("google")) {
				
				// Extract email- when sign-in with Google
				System.out.println("Getting email from google!");
				username = oauth2User.getAttribute("email").toString();
					
			}else if (clientRegistrationId.equalsIgnoreCase("github")) {
				
				// How to extract email- when signin with Git-hub
				System.out.println("Getting email from github!");
				username = oauth2User.getAttribute("login").toString() + "@gmail.com";
					
			}
			return username;   
		}else {
			System.out.println("Getting email from local database!");  //Sign-in from (other)
			return authentication.getName();	
		}
		
	}
	
	
}
