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
public class ProjectDto {
	private Long id;
	private String title;
	private String description;
	private String start;
	private String end;
	private String projectUrl;
}
