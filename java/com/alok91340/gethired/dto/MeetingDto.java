/**
 * 
 */
package com.alok91340.gethired.dto;

import java.util.Date;

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
