package com.scm.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@AllArgsConstructor
@Builder 
@Getter
@Setter
@ToString
public class UserForm {
	
	@NotBlank(message = "Username is required!")
	@Size(min=3, message = "Minimum 3 charcters are required!" )
	private String name;
	
	@NotBlank(message = "Email is required!")
	@Email(message = "Invalid email address!")
	private String email;
	
	@NotBlank(message = "Password is required!")
	@Size(min=6, message = "Minimum 6 characters are required")
	private String password;
	
	@NotBlank(message = "About is required!")
	private String about;
	
	@Size(min=8, max=12, message = "Invalid phone number!")
	private String phoneNumber;

	
	
	
	
	

}
