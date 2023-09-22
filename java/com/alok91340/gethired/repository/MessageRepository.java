/**
 * 
 */
package com.alok91340.gethired.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alok91340.gethired.entities.Message;

/**
 * @author aloksingh
 *
 */ 
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
	
    @Query("SELECT MAX(m.timestamp) FROM Message m WHERE m.roomId = :roomId")
    LocalDateTime findLatestMessageTime(@Param("roomId") Long roomId);
    
    @Query("SELECT COUNT(m) FROM Message m WHERE m.roomId = :roomId AND m.receiverId = :userId AND m.seen = false")
    Long countUnseenMessages(@Param("roomId") Long roomId, @Param("userId") Long userId);
    
    @Query("SELECT m FROM Message m WHERE " +
            "((m.senderId = :senderId AND m.receiverId = :receiverId) OR " +
            "(m.senderId = :receiverId AND m.receiverId = :senderId)) AND " +
            "m.timestamp > :timeStamp " +
            "ORDER BY m.timestamp DESC")
    List<Message> fetchAllMessage(
            @Param("senderId") Long senderId,
            @Param("receiverId") Long receiverId,
            @Param("timeStamp") String timeStamp);



}

    

