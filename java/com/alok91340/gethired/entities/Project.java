/**
 * 
 */
package com.alok91340.gethired.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author alok91340
 *
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
public class Project {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String title;
	
	@Size(max = 1000)
	private String description;
	private String start;
	private String end;
	
	private String projectUrl;
	
	@ManyToOne
	@JsonBackReference
	private UserProfile userProfile;
}
