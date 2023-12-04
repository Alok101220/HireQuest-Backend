/**
 * 
 */
package com.alok91340.gethired.dto;

import lombok.Data;

/**
 * @author alok91340
 *
 */
//NotificationRequest.java
@Data
public class NotificationRequest {
	
	private Long senderId;
	private String title;
	private String body;
	private Long receiverId;
	private String notificationType;
	
}
