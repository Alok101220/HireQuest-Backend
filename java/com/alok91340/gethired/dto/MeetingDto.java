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
public class MeetingDto {

	private long id;
	private String timeStamp;
	private String url;
	private boolean isAttended;
}
