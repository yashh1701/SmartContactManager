package com.scm.config;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.scm.services.impl.SecurityCustomUserDetailService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Configuration
public class SecurityConfig {

	/* Below is video 15 
     User create and login using java code with in memory service
     @Bean
     public UserDetailsService userDetailsService() {

     UserDetails user1 = User
     .withDefaultPasswordEncoder()
     .username("admin123")
     .password("admin123")
     .roles("ADMIN", "USER")
     .build();

     UserDetails user2 = User
     .withDefaultPasswordEncoder()
     .username("user123")
     .password("password")
     // .roles(null)
     .build();

     var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user1,user2);
	    InMemoryUserDetailsManager is an impl of UserDetailService 
     
     return inMemoryUserDetailsManager;
    	 
    }
	*/
	
	 /* Below is for video 16
	 UserDetailService is use to fetch user, UserDetailService has a method loadUserbyUsername() to load user 
	 and then it matches with loaded user and user in db and if matches then it allow login */
	
	@Autowired
	private SecurityCustomUserDetailService userDetailService;
	
	@Autowired
	private OauthAuthenticationSucessHandler handler;
	
	@Autowired
	 private AuthFailtureHandler authFailtureHandler;
	
	
	// Below is configuration of authentication provider
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
	   
	   // DaoAuthenticationProvider has all methods to register our service
	   DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();	   
	   // yaha hame detailService ka object lana hai;
	   daoAuthenticationProvider.setUserDetailsService(userDetailService);
	   // yaha hame passwordEncoder ka object lana hai;
	   daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
	   
	   return daoAuthenticationProvider;
	   
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

	        // configuration
	        // urls configure kiye hai ki koun se public rangenge aur koun se private
	        httpSecurity.authorizeHttpRequests(authorize -> {
	            // authorize.requestMatchers("/home", "/signup", "/services").permitAll();  // these url's have access
	             authorize.requestMatchers("/user/**").authenticated();   // these url's are protected 
	             authorize.anyRequest().permitAll();
	        });
	        
	        // form default login 
	        httpSecurity.formLogin(formLogin->{
	        	
	        	formLogin.loginPage("/login");    // this is our custom login page
	        	formLogin.loginProcessingUrl("/authenticate");  // the will submit to this url
//	        	formLogin.successForwardUrl("/user/profile");   // not working shows error after login error 404
	        	formLogin.defaultSuccessUrl("/user/profile");
//	        	formLogin.failureForwardUrl("/login?error=true");
	        	formLogin.usernameParameter("email");
	        	formLogin.passwordParameter("password");
//	        	formLogin.successHandler(new AuthenticationSuccessHandler() {
//					
//					@Override
//					public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//							Authentication authentication) throws IOException, ServletException {
//						// TODO Auto-generated method stub
//						
//					}
//				});
	        	
	        	formLogin.failureHandler(authFailtureHandler);
	        	
	        });
	        
	        httpSecurity.csrf(AbstractHttpConfigurer::disable);
	       
	        //oauth configurations
	        
	        httpSecurity.oauth2Login(oauth->{
	        	oauth.loginPage("/login");
	        	oauth.defaultSuccessUrl("/user/profile", true);
	        	oauth.successHandler(handler);
	        });
	        
	        httpSecurity.logout(logoutForm->{
	        	logoutForm.logoutUrl("/logout");
	        	logoutForm.logoutSuccessUrl("/login?logout=true");
	        });	        
	        
	        return httpSecurity.build();
	        
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	     return new BCryptPasswordEncoder();
	}
   
}
