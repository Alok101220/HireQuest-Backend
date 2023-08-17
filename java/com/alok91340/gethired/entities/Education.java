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
public class Education {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String name;
	private String start;
	private String end;
	private String standard;
	
	@ManyToOne
	@JsonBackReference
	private UserProfile studentProfile;
}
