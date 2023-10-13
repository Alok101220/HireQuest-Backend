/**
 * 
 */
package com.alok91340.gethired.service;

import java.util.List;

import com.alok91340.gethired.dto.ChatRoomDto;
import com.alok91340.gethired.dto.ChatUser;
import com.alok91340.gethired.entities.Message;
import com.alok91340.gethired.entities.User;

/**
 * @author aloksingh
 *
 */
public interface ChatService {
	
//	List<ChatUser> getChattingUsersWithLastMessage(String username);
	
	
	/**
	 * @param messageEntity
	 */
	Message saveMessage(Message messageEntity);
	
	List<Message> getMessages(String senderUser, String receiverUser,String timeStamp);

}
