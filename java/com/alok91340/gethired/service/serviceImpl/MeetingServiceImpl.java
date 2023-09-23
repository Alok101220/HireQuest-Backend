/**
 * 
 */
package com.alok91340.gethired.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alok91340.gethired.dto.MeetingDto;
import com.alok91340.gethired.entities.Meeting;
import com.alok91340.gethired.entities.User;
import com.alok91340.gethired.exception.ResourceNotFoundException;
import com.alok91340.gethired.repository.MeetingRepository;
import com.alok91340.gethired.repository.UserRepository;
import com.alok91340.gethired.service.MeetingService;

/**
 * @author aloksingh
 *
 */
@Service
public class MeetingServiceImpl implements MeetingService{

	@Autowired
	private MeetingRepository meetingRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public MeetingDto addMeeting(String userId, MeetingDto meetingDto) {
		User user= this.userRepository.findUserByUsername(userId);
		Meeting meeting=new Meeting();
		mapToEntity(meetingDto,meeting);
		meeting.setUser(user);
		Meeting savedMeeting=this.meetingRepository.save(meeting);
		return mapToDto(savedMeeting);
	}

	@Override
	public void deleteMeeting(Long meetingId) {
		Meeting meeting=this.meetingRepository.findById(meetingId).orElseThrow(()-> new ResourceNotFoundException("meeting",meetingId));
		this.meetingRepository.delete(meeting);
		return;
	}

	@Override
	public MeetingDto updateMeeting(Long meetingId, MeetingDto meetingDto) {
		Meeting meeting=this.meetingRepository.findById(meetingId).orElseThrow(()-> new ResourceNotFoundException("meeting",meetingId));
		mapToEntity(meetingDto,meeting);
		Meeting savedMeeting=this.meetingRepository.save(meeting);
		return mapToDto(savedMeeting);
	}
	
	private void mapToEntity(MeetingDto meetingDto, Meeting meeting) {
		meeting.setTimeStamp(meetingDto.getTimeStamp());
		meeting.setUrl(meetingDto.getUrl());
		meeting.setAttended(false);
	}
	
	private MeetingDto mapToDto( Meeting meeting) {
		MeetingDto meetingDto =new MeetingDto();
		meetingDto.setTimeStamp(meeting.getTimeStamp());
		meetingDto.setUrl(meeting.getUrl());
		meetingDto.setAttended(meeting.isAttended());
		return meetingDto;
	}

}
