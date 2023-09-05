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
 * @author alok91340
 *
 */

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Language {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String name;
	
	private String proficencyLevel;
	
	@ManyToOne
	@JsonBackReference
	private CandidateProfile candidateProfile;
	
	@ManyToOne
	@JsonBackReference
	private HrProfile hrProfile;
	
}
