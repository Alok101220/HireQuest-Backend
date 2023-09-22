/**
 * 
 */
package com.alok91340.gethired.dto;

import com.alok91340.gethired.entities.ChatRoom;

import lombok.Data;

/**
 * @author aloksingh
 *
 */
@Data
public class SendMessageRequest {
	
	private Long senderId;
    private Long receiverId;
    private String content;

}
