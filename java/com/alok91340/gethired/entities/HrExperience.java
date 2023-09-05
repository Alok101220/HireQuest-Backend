/**
 * 
 */
package com.alok91340.gethired.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author aloksingh
 *
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class HrExperience {
	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	
	
	@ManyToOne
	@JsonBackReference
	private HrProfile hrProfile;

}
