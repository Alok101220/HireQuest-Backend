/**
 * 
 */
package com.alok91340.gethired.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alok91340.gethired.entities.ChatRoom;
import com.alok91340.gethired.entities.User;

/**
 * @author aloksingh
 *
 */
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
	
    List<ChatRoom> findBySender(User sender);
}
