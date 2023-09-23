/**
 * 
 */
package com.alok91340.gethired.dto;

import lombok.Data;

/**
 * @author aloksingh
 *
 */
@Data
public class PdfDto {

private long id;
	
	private String fileName;
	private byte[] data;
}
