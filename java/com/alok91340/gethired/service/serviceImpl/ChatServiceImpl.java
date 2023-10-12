/**
 * 
 */
package com.alok91340.gethired.service.serviceImpl;

import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alok91340.gethired.dto.ChatUser;
import com.alok91340.gethired.entities.ChatRoom;
import com.alok91340.gethired.entities.Message;
import com.alok91340.gethired.entities.User;
import com.alok91340.gethired.repository.ChatRoomRepository;
import com.alok91340.gethired.repository.MessageRepository;
import com.alok91340.gethired.repository.UserRepository;
import com.alok91340.gethired.service.ChatService;

/**
 * @author aloksingh
 *
 */
@Service
public class ChatServiceImpl implements ChatService{
	
	@Autowired
	private ChatRoomRepository chatRoomRepository;
	
	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public List<ChatUser> getChattingUsersWithLastMessage(String currentUserId) {
		User currentUser=this.userRepository.findUserByUsername(currentUserId);
		List<ChatRoom> userChatRoom=this.chatRoomRepository.findBySender(currentUser);
		
		List<ChatUser> chatUsers = new ArrayList<>();
		
		for (ChatRoom room : userChatRoom) {
            User otherUser = room.getSender().equals(currentUser) ? room.getReceiver() : room.getSender();
            LocalDateTime lastMessageTime = messageRepository.findLatestMessageTime(room.getId());
            Long unseenMessageCount = messageRepository.countUnseenMessages(room.getId(), currentUserId);

            ChatUser chatUser = new ChatUser();
//            chatUser.setId(otherUser.getUsername());
            chatUser.setReceiver(otherUser);
            chatUser.setImage(otherUser.getImage());
            chatUser.setLastMessageTime(lastMessageTime);
            chatUser.setUnseenMessageCount(unseenMessageCount);

            chatUsers.add(chatUser);
        }
		return chatUsers;
	}

	@Override
	public Message saveMessage(Message message) {
        Message savedMessage=messageRepository.save(message);
        return savedMessage;
    }

	@Override
	public List<Message> getMessages(String senderId, String receiverId,String timeStamp) {
	    // Assuming you have a MessageRepository
	    List<Message> messages = messageRepository.fetchAllMessage(senderId, receiverId, timeStamp);
	    return messages;
	}


}
