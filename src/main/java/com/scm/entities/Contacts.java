package com.scm.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Contacts {
	
	@Id
	private String id;
	private String name;
	private String email;
	private String phoneNumber;
	private String address;
	private String picture;
	@Column(length = 5000)
	private String description;
	private boolean favourite;
	private String websiteLink;
	private String linkedinLink;
//	private List<String> socialLinks = new ArrayList<>();
	
	private String cloudinaryImagePublicId;
	
	@ManyToOne
	@JsonIgnore
	private User user;
	
	@OneToMany(mappedBy = "contacts",cascade = CascadeType.ALL, fetch = FetchType.EAGER ,orphanRemoval = true)
	private List<SocialLink> links=new ArrayList<>();
	

}
