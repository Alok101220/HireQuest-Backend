/**
 * 
 */
package com.alok91340.gethired.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alok91340.gethired.entities.ChatRoom;
import com.alok91340.gethired.entities.User;
import com.alok91340.gethired.entities.UserChatRoom;
import com.alok91340.gethired.entities.UserChatRoomId;

/**
 * @author aloksingh
 *
 */
public interface UserChatRoomRepository extends JpaRepository<UserChatRoom, UserChatRoomId>{

	/**
	 * @param user
	 * @param b
	 * @return
	 */
	
	List<UserChatRoom> findByUserAndIsDeleted(User user, boolean isDeleted);

	List<UserChatRoom> findByChatRoom(ChatRoom chatRoom);
	
//	@Query("SELECT ucr FROM UserChatRoom ucr " +
//		       "JOIN ucr.chatRoom cr " +
//		       "WHERE ucr.user.username = :username " +
//		       "AND ucr.isRequestSender = true " +
//		       "AND cr.isRequest = true")
//		List<UserChatRoom> findPendingChatRequestsForUser(@Param("username") String username);
	
	@Query("SELECT ucr FROM UserChatRoom ucr " +
		       "WHERE ucr.user = :user " +
		       "AND ucr.chatRoom IN :chatRoom " +
		       "AND ucr.isRequestSender = true " +
		       "AND ucr.user <> :user")
		List<UserChatRoom> findUserChatRoomsByChatroomAndIsRequestSenderAndNotSameUser(
		        @Param("user") User user,
		        @Param("chatRoom") List<ChatRoom> chatRoom);


	
	@Query("SELECT ucr.chatRoom " +
		       "FROM UserChatRoom ucr " +
		       "WHERE ucr.user = :user")
		List<ChatRoom> findChatRoomIdsByUser(@Param("user") User user);


}
