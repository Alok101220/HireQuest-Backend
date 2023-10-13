/**
 * 
 */
package com.alok91340.gethired.service;

import java.util.List;

import com.alok91340.gethired.dto.ChatRoomDto;

/**
 * @author aloksingh
 *
 */
public interface ChatRoomService {
	
	
	
	ChatRoomDto findUserChattingWith(String user);
	
	List<ChatRoomDto> getChatRoomsAndUnseenMessageCount(String sender, String receiver);
	
	void sendChatRequest(String senderUsername, String receiverUsername);
	
	void acceptChatRequest(Long chatRoomId);
	
	void deleteChatRoom(Long chatRoomId);
	
	void rejectChatRequest(Long chatRoomId);

}
