/**
 * 
 */
package com.alok91340.gethired.service;

import com.alok91340.gethired.dto.MeetingDto;

/**
 * @author aloksingh
 *
 */
public interface MeetingService {
	
	MeetingDto addMeeting(Long userId, MeetingDto meetingDto);
	void deleteMeeting(Long meetingId);
	MeetingDto updateMeeting(Long meetingId,MeetingDto meetingDto);
	
}
