package com.scm.helpers;


import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;


public class Helper {

	
	public static String getEmailOfLoggedInUser(Authentication authentication) {
		
		if(authentication instanceof OAuth2AuthenticationToken) {

			OAuth2AuthenticationToken oauth2AuthenticationToken =(OAuth2AuthenticationToken) authentication;
			String clientRegistrationId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();
			DefaultOAuth2User  oauth2User=(DefaultOAuth2User)authentication.getPrincipal();
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


	public static String getLinkForEmailVerificatiton(String emailToken) {

        return "http://localhost:8080/" + emailToken;

    }
	
}
