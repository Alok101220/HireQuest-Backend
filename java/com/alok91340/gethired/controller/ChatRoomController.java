/**
 * 
 */
package com.alok91340.gethired.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alok91340.gethired.dto.ChatRoomDto;
import com.alok91340.gethired.entities.ChatRoom;
import com.alok91340.gethired.entities.User;
import com.alok91340.gethired.entities.UserChatRoom;
import com.alok91340.gethired.repository.ChatRoomRepository;
import com.alok91340.gethired.repository.UserChatRoomRepository;
import com.alok91340.gethired.repository.UserRepository;
import com.alok91340.gethired.service.ChatRoomService;
import com.alok91340.gethired.service.ChatService;

/**
 * @author aloksingh
 *
 */
@RestController
@RequestMapping("api/hireQuest")
public class ChatRoomController {
	
	
	@Autowired
	private ChatRoomRepository chatRoomRepository;
	
	@Autowired 
	private ChatRoomService chatRoomService;
	
	@Autowired
	private UserChatRoomRepository userChatRoomRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@PostMapping("/{senderUsername}/{receiverUsername}/send-chatRequest")
	public ResponseEntity<String> createChatRoom(@PathVariable String senderUsername, @PathVariable String receiverUsername){
		
		this.chatRoomService.sendChatRequest(senderUsername, receiverUsername);
		
	
		return ResponseEntity.ok("Cerated");
		
	}
	
	@PutMapping("/{chatRoomId}/accept-chatRequest")
	public ResponseEntity<String> acceptChatRequest(@PathVariable Long chatRoomId){
		this.chatRoomService.acceptChatRequest(chatRoomId);
		return ResponseEntity.ok("accepted");
		
	}
	
	@DeleteMapping("/{chatRoomId}/delete-chatRequest")
	public ResponseEntity<String> deleteChatRequest(@PathVariable Long chatRoomId){
		this.chatRoomService.deleteChatRoom(chatRoomId);
		return ResponseEntity.ok("deleted");
		
	}
	
	@GetMapping("/{username}/get-pendingChatRequest")
	public ResponseEntity<List<UserChatRoom>> getPendingChatRequestsForUser(@PathVariable String username) {
		
		User user=this.userRepository.findById(username).orElseThrow();
		
		List<ChatRoom> chatRoom=this.userChatRoomRepository.findChatRoomIdsByUser(user);
		
		System.out.println();
		System.out.println();System.out.println();System.out.println();System.out.println();System.out.println();
		System.out.println(chatRoom);
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
	
		List<UserChatRoom> userChatRooms=userChatRoomRepository.findUserChatRoomsByChatroomAndIsRequestSenderAndNotSameUser(user,chatRoom);
		return ResponseEntity.ok(userChatRooms);
	}


}
