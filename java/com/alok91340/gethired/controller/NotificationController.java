/**
 * 
 */
package com.alok91340.gethired.controller;

import java.util.List;

/**
 * @author alok91340
 *
 */
//NotificationController.java
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
import org.springframework.web.bind.annotation.RestController;

import com.alok91340.gethired.dto.NotificationDto;
import com.alok91340.gethired.dto.NotificationRequest;
import com.alok91340.gethired.entities.User;
import com.alok91340.gethired.exception.ResourceNotFoundException;
import com.alok91340.gethired.repository.UserRepository;
import com.alok91340.gethired.service.NotificationService;
import com.alok91340.gethired.service.serviceImpl.FcmNotificationService;

@RestController
@RequestMapping("api/hireQuest")
public class NotificationController {
	

	@Autowired
	private NotificationService notificationService;
	

	private final FcmNotificationService fcmNotificationService;
 
	@Autowired
	private UserRepository userRepository;

	@Autowired
	public NotificationController(FcmNotificationService fcmNotificationService) {
		this.fcmNotificationService = fcmNotificationService;
	}
	@PostMapping("/send-notification")
	public ResponseEntity<String> sendNotification(@RequestBody NotificationRequest request) {
		// Call the notificationService to send the notification
		User user = userRepository.findById(request.getReceiverId())
				.orElseThrow(() -> new ResourceNotFoundException("user",(long)0));
		
		String notificationId="";
		if(user.getFcmToken()==null||user.getFcmToken().isEmpty()) {
			
		}else {
			notificationId = fcmNotificationService.sendNotification(user.getFcmToken(), request);
		}
//		String notificationId = fcmNotificationService.sendNotification(user.getFcmToken(), request);
     
		if(notificationId!=null) {
			
			NotificationDto notification=this.notificationService.saveNotification(request);
			
		}else {
			NotificationDto notification=this.notificationService.saveNotification(request);

			
		}
		return ResponseEntity.ok("Notification sent successfully");
	}
	
	@PutMapping("/{notificationId}/update-notification")
	public ResponseEntity<NotificationDto> updateNotification(@PathVariable Long notificationId){
		NotificationDto notification= this.notificationService.updateNotification(notificationId);
		return new ResponseEntity<>(notification,HttpStatus.OK);
	}
	
	@DeleteMapping("/{notificationId}/delete-notification")
	public ResponseEntity<String> deleteNotification(@PathVariable Long notificationId){
		this.notificationService.deleteNotification(notificationId);
		return new ResponseEntity<>("deleted",HttpStatus.OK);
	}
	
	@GetMapping("/{receiverId}/get-notifications")
	public ResponseEntity<List<NotificationDto>> getNotifications(@PathVariable Long receiverId){
		List<NotificationDto> notifications=this.notificationService.getAllNotification(receiverId);
		return new ResponseEntity<>(notifications, HttpStatus.OK);
	}
	
	
	
}
