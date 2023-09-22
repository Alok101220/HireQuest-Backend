/**
 * 
 */
package com.alok91340.gethired.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alok91340.gethired.dto.MeetingDto;
import com.alok91340.gethired.repository.MeetingRepository;
import com.alok91340.gethired.service.MeetingService;
import com.alok91340.gethired.utils.Constant;

/**
 * @author aloksingh
 *
 */
@RestController
@RequestMapping("api/gethired")
public class MeetingController {
	
	@Autowired
	private MeetingService meetingService;
	
	@Autowired
	private MeetingRepository meetingRepository;
	
	@PostMapping("{userId}/create-meeting")
	private ResponseEntity<MeetingDto> createMeeting(@PathVariable Long userId, @RequestBody MeetingDto meetingDto){
		
		MeetingDto createdMeeting=this.meetingService.addMeeting(userId, meetingDto);
		return new ResponseEntity<>(createdMeeting,HttpStatus.OK);
	}
	
	@PutMapping("{meetingId}/update-meeting")
	private ResponseEntity<MeetingDto> updateMeeting(@PathVariable Long meetingId, @RequestBody MeetingDto meetingDto){
		MeetingDto updatedMeeting=this.meetingService.addMeeting(meetingId, meetingDto);
		return new ResponseEntity<>(updatedMeeting,HttpStatus.OK);
	}
	
	@DeleteMapping("{meetingId}/delete-meeting")
	private void deleteMeeting(@PathVariable Long meetingId) {
		this.meetingService.deleteMeeting(meetingId);
	}
	
	@GetMapping("{currentDateTime}/upcomming-meetings")
	private ResponseEntity<List<MeetingDto>> upcommingMeetings(@RequestParam(value = "currentDateTime") Date currentDateTime){
		
		List<MeetingDto> meetings=this.meetingRepository.upcomingMeetings(currentDateTime);
		return new ResponseEntity<>(meetings,HttpStatus.OK);
		
	}
	
	@GetMapping("{currentDateTime}/past-meetings")
	private ResponseEntity<List<MeetingDto>> pastMeetings(@RequestParam(value = "currentDateTime") Date currentDateTime){
		
		List<MeetingDto> meetings=this.meetingRepository.pastMeetings(currentDateTime);
		return new ResponseEntity<>(meetings,HttpStatus.OK);
		
	}

}
