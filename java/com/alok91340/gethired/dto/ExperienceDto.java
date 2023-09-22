/**
 * 
 */
package com.alok91340.gethired.dto;

import java.util.List;

import lombok.Data;

/**
 * @author alok91340
 *
 */
@Data
public class ExperienceDto {
	private Long id;
	private String title;
	private String description;
	
	private String experienceUrl;
	
	private String start;
	private String end;
	
	private String position;
	private String organisation;
}
