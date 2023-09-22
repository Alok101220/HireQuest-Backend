/**
 * 
 */
package com.alok91340.gethired.entities;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	private Set<Profile> profiles;
	
	@OneToMany(mappedBy="userProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
	@JsonManagedReference
	private Set<Education> educations;
	
	@OneToMany(mappedBy="userProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
	@JsonManagedReference
	private Set<Certificate> certificates;
	
	@OneToMany(mappedBy="userProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
	@JsonManagedReference
	private Set<Skill> skills= new HashSet<>();
	
	@OneToMany(mappedBy="userProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
	@JsonManagedReference
	private Set<Experience> experiences;
	
	@OneToMany(mappedBy="userProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
	@JsonManagedReference
	private Set<Project> projects;
	
	@OneToMany(mappedBy="userProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
	@JsonManagedReference
	private Set<Language> languages;
	
	@OneToMany(mappedBy="userProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
	@JsonManagedReference
	private Set<Hobbies> hobbies;
	
	@OneToMany(mappedBy="userProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
	@JsonManagedReference
	private Set<Appreciation> appreciations;
	
	@OneToOne
	@JsonManagedReference
	private User user;
	
}
