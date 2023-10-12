/**
 * 
 */
package com.alok91340.gethired.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author alok91340
 *
 */
@Entity
@Table(name="user_profile")
@Setter
@Getter
@NoArgsConstructor
public class UserProfile extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String about;
	
	@OneToMany(mappedBy="userProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
	@JsonManagedReference
	private List<Profile> profiles;
	
	@OneToMany(mappedBy="userProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
	@JsonManagedReference
	private List<Education> educations;
	
	@OneToMany(mappedBy="userProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
	@JsonManagedReference
	private List<Certificate> certificates;
	
	
	private List<String> skills= new ArrayList<>();
	
	@OneToMany(mappedBy="userProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
	@JsonManagedReference
	private List<Experience> experiences;
	
	@OneToMany(mappedBy="userProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
	@JsonManagedReference
	private List<Project> projects;
	
	
	private List<String> languages;
	
	
	private List<String> hobbies=new ArrayList<>();;
	
	@OneToMany(mappedBy="userProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
	@JsonManagedReference
	private List<Appreciation> appreciations;
	
	@OneToMany(mappedBy="userProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
	@JsonManagedReference
	private List<Pdf> pdfs;
	
	@OneToOne
	@JsonManagedReference
	private User user;
	
}
