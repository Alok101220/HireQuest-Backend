/**
 * 
 */
package com.alok91340.gethired.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alok91340.gethired.dto.MeetingDto;
import com.alok91340.gethired.entities.Meeting;
import com.alok91340.gethired.entities.User;

/**
 * @author aloksingh
 *
 */
public interface MeetingRepository extends JpaRepository<Meeting,Long>{

	@Query("SELECT m FROM Meeting m WHERE m.timeStamp > :timeStamp")
    List<MeetingDto> upcomingMeetings(String  timeStamp);
	
	@Query("SELECT m FROM Meeting m WHERE m.timeStamp <= :timeStamp")
    List<MeetingDto> pastMeetings(String timeStamp);
}
