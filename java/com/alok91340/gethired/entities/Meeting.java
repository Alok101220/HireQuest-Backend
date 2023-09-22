/**
 * 
 */
package com.alok91340.gethired.entities;

import java.util.Date;

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
@Setter
@Getter
@NoArgsConstructor
public class Meeting {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String timeStamp;
	private String url;
	private boolean isAttended;
	
	@ManyToOne
	@JsonBackReference
	private User user;

}
