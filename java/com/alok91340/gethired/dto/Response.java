/**
 * 
 */
package com.alok91340.gethired.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author alok91340
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {
	private String url;
	private String message;
	private HttpStatus status;
}
