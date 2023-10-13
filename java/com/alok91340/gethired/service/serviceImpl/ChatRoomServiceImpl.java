/**
 * 
 */
package com.alok91340.gethired.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alok91340.gethired.dto.ChatRoomDto;
import com.alok91340.gethired.entities.ChatRoom;
import com.alok91340.gethired.entities.Message;
import com.alok91340.gethired.entities.User;
import com.alok91340.gethired.entities.UserChatRoom;
import com.alok91340.gethired.entities.UserChatRoomId;
import com.alok91340.gethired.repository.ChatRoomRepository;
import com.alok91340.gethired.repository.MessageRepository;
import com.alok91340.gethired.repository.UserChatRoomRepository;
import com.alok91340.gethired.repository.UserRepository;
import com.alok91340.gethired.service.ChatRoomService;

/**
 * @author aloksingh
 *
 */
@Service
public class ChatRoomServiceImpl implements ChatRoomService{
	
	@Autowired
	private UserChatRoomRepository userChatRoomRepository;
	
	@Autowired
	private ChatRoomRepository chatRoomRepository;
	
	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired
	private UserRepository userRepository;
	

	@Override
	public List<ChatRoomDto> getChatRoomsAndUnseenMessageCount(String senderUsername, String receiverUsername) {
		
//		User sender= this.userRepository.findById(senderUsername).orElseThrow();
//		User receiver = this.userRepository.findById(receiverUsername).orElseThrow();
//		
//		List<ChatRoom> chatRooms = chatRoomRepository.findAllBySenderOrReceiverAndIsRequest(sender, receiver, false);
//
//	    List<ChatRoomDto> chatRoomDTOs = new ArrayList<>();
//
//	    for (ChatRoom chatRoom : chatRooms) {
//	        int unSeenMessageCount = messageRepository.countByRoomIdAndReceiverUsernameAndSeen(chatRoom.getId(), receiver.getUsername(), false);
//
//	        Message lastMessage = messageRepository.findTopByRoomIdOrderByTimestampDesc(chatRoom.getId());
//
//	        ChatRoomDto chatRoomDTO = new ChatRoomDto();
//	        chatRoomDTO.setId(chatRoom.getId());
//	        chatRoomDTO.setUnSeenMessageCount(unSeenMessageCount);
//	        chatRoomDTO.setIsRequest(chatRoom.isRequest());
//	        chatRoomDTO.setLastMessage(lastMessage);
//
//	        chatRoomDTOs.add(chatRoomDTO);
//	    }
//
//	    return chatRoomDTOs;
		return null;
	}
	

	@Override
	public void sendChatRequest(String senderUsername, String receiverUsername) {
		
		User user1= this.userRepository.findById(senderUsername).orElseThrow();
		User user2 = this.userRepository.findById(receiverUsername).orElseThrow();
		
		// Check if there's an existing chat room between user1 and user2
	    Optional<ChatRoom> existingChatRoom = chatRoomRepository.findChatRoomByUsers(user1, user2);

	    ChatRoom chatRoom;
	    if (existingChatRoom.isPresent() && !existingChatRoom.get().isGroup()) {
	        // Use the existing chat room
	        chatRoom = existingChatRoom.get();
	        chatRoom.setDeleted(false);
	        chatRoomRepository.save(chatRoom);
	    } else {
	        // Create a new chat room
	        chatRoom = new ChatRoom();
	        chatRoom.setDeleted(false);
	        chatRoom.setGroup(false);
	        chatRoom.setRequest(true); // Set it as a request
	        chatRoomRepository.save(chatRoom);

	        // Add users to the chat room
	        chatRoom.getUsers().add(user1);
	        chatRoom.getUsers().add(user2);

	        // Save the chat room with users
	        chatRoomRepository.save(chatRoom);
	    }
		
		UserChatRoomId userChatRoomId1 = new UserChatRoomId();
		userChatRoomId1.setUserId(user1.getUsername()); // Set the user's ID
		userChatRoomId1.setChatRoomId(chatRoom.getId());

		
		UserChatRoomId userChatRoomId2 = new UserChatRoomId();
		userChatRoomId2.setUserId(user2.getUsername()); // Set the user's ID
		userChatRoomId2.setChatRoomId(chatRoom.getId());

		
		// Create UserChatRoom entries for User1 and User2
		UserChatRoom userChatRoom1 = new UserChatRoom();
		userChatRoom1.setId(userChatRoomId1);
		userChatRoom1.setChatRoom(chatRoom);
		userChatRoom1.setUser(user1);
		userChatRoom1.setRequestSender(true);
		
		UserChatRoom userChatRoom2 = new UserChatRoom();
		userChatRoom2.setId(userChatRoomId2);
		userChatRoom2.setChatRoom(chatRoom);
		userChatRoom2.setUser(user2);
		userChatRoom2.setRequestSender(false);
		
		
		userChatRoomRepository.save(userChatRoom1);
		userChatRoomRepository.save(userChatRoom2);

		
		
		return ;
	}

	@Override
	public void deleteChatRoom(Long chatRoomId) {
		
		ChatRoom chatRoom= this.chatRoomRepository.findById(chatRoomId).orElseThrow();
		
		
		
	}

	

	@Override
	public ChatRoomDto findUserChattingWith(String username) {
		
		User user=this.userRepository.findById(username).orElse(null);
		
		List<User> usersChattingWith = new ArrayList<>();
	    
	    // Assuming you have a UserChatRoomRepository
	    List<UserChatRoom> userChatRooms = userChatRoomRepository.findByUserAndIsDeleted(user, false);

	    for (UserChatRoom userChatRoom : userChatRooms) {
	        // Get the other user from the UserChatRoom
	        User otherUser = userChatRoom.getChatRoom().getUsers()
	                .stream()
	                .filter(u -> !u.equals(user)) // Filter out the original user
	                .findFirst()
	                .orElse(null);

	        if (otherUser != null) {
	            usersChattingWith.add(otherUser);
	        }
	    }

	    
		return null;
	}

	@Override
	public void acceptChatRequest(Long chatRoomId) {
		// Find the chat room (assuming you have the chat room ID)
		ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElse(null);

		if (chatRoom != null && chatRoom.isRequest()) {
		    // Set it as an active chat
		    chatRoom.setRequest(false);
		    chatRoomRepository.save(chatRoom);

		    // Update UserChatRoom entries if needed (e.g., mark as active)
		}
		
	}

	@Override
	public void rejectChatRequest(Long chatRoomId) {
		// Find the chat room (assuming you have the chat room ID)
		ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElse(null);

		if (chatRoom != null && chatRoom.isRequest()) {
		    // Delete the chat room and associated entries
			
			chatRoom.setDeleted(true);
			chatRoomRepository.save(chatRoom);
			
//		    // Delete UserChatRoom entries if needed
//		    List<UserChatRoom> userChatRooms=this.userChatRoomRepository.findByChatRoom(chatRoom);
//		    for(UserChatRoom  userChatRoom:userChatRooms) {
//		    	
//		    	this.userChatRoomRepository.delete(userChatRoom);
//		    }
		}

		
	}

}
