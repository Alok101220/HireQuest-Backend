/**
 * 
 */
package com.alok91340.gethired.service;

import java.util.List;

import com.alok91340.gethired.dto.ChatRoomResponse;
import com.alok91340.gethired.dto.UserChattingInfoDto;

/**
 * @author aloksingh
 *
 */
public interface ChatRoomService {
	
	
	
	List<ChatRoomResponse> findUserChattingWith(Long userId);
	
	List<ChatRoomResponse> getChatRoomsAndUnseenMessageCount(Long senderId, Long receiverId);
	
	UserChattingInfoDto findUserChattingInfo(Long senderId, Long receiverId);
	
	void sendChatRequest(Long senderId, Long receiverId);
	
	void acceptChatRequest(Long chatRoomId);
	
	void deleteChatRoom(Long chatRoomId);
	
	void rejectChatRequest(Long chatRoomId);

}
