/**
 * 
 */
package com.alok91340.gethired.service;

import java.time.LocalDateTime;
import java.util.List;

import com.alok91340.gethired.dto.ChatUser;
import com.alok91340.gethired.entities.Message;
import com.alok91340.gethired.entities.User;

/**
 * @author aloksingh
 *
 */
public interface ChatService {
	
	List<ChatUser> getChattingUsersWithLastMessage(Long currentUserId);

	/**
	 * @param messageEntity
	 */
	Message saveMessage(Message messageEntity);
	
	List<Message> getMessages(Long senderId, Long receiverId,String timeStamp);

}
