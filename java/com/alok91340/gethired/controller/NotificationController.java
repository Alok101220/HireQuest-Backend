/**
 * 
 */
package com.alok91340.gethired.controller;

/**
 * @author alok91340
 *
 */
//NotificationController.java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alok91340.gethired.dto.NotificationRequest;
import com.alok91340.gethired.entities.User;
import com.alok91340.gethired.exception.ResourceNotFoundException;
import com.alok91340.gethired.repository.UserRepository;
import com.alok91340.gethired.service.serviceImpl.NotificationService;

@RestController
public class NotificationController {

 private final NotificationService notificationService;
 
 @Autowired
 private UserRepository userRepository;

 @Autowired
 public NotificationController(NotificationService notificationService) {
     this.notificationService = notificationService;
 }
 @PostMapping("/sendNotification")
 public ResponseEntity<String> sendNotification(@RequestBody NotificationRequest request) {
     // Call the notificationService to send the notification
	 User user = userRepository.findById(request.getUserId())
             .orElseThrow(() -> new ResourceNotFoundException("user",request.getUserId()));
     notificationService.sendNotification(user.getFcmToken(), request.getTitle(), request.getBody());
     return ResponseEntity.ok("Notification sent successfully");
 }
}
