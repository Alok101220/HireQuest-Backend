package com.alok91340.gethired.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Hr {
	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	
	@OneToOne
	@JsonManagedReference
	private User user;

}
