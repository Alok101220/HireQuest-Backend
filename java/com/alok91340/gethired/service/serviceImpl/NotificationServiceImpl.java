/**
 * 
 */
package com.alok91340.gethired.service.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alok91340.gethired.dto.NotificationDto;
import com.alok91340.gethired.dto.NotificationRequest;
import com.alok91340.gethired.entities.Notification;
import com.alok91340.gethired.repository.NotificationRepository;
import com.alok91340.gethired.service.NotificationService;

/**
 * @author aloksingh
 *
 */

@Service
public class NotificationServiceImpl implements NotificationService{
	
	@Autowired
	private NotificationRepository notificationRepository;

	@Override
	public NotificationDto saveNotification(NotificationRequest request) {
		Notification notification= new Notification();
		notification.setBody(request.getBody());
		notification.setNotificationType(request.getNotificationType());
		notification.setReadStatus(false);
		notification.setReceiverId(request.getReceiverId());
		notification.setSenderId(request.getSenderId());
		notification.setTitle(request.getTitle());
		notification.setTimestamp(LocalDateTime.now());
		Notification savedNotification=this.notificationRepository.save(notification);
		return mapToDto(savedNotification);
	}

	@Override
	public void deleteNotification(Long notificationId) {
		Notification notification= this.notificationRepository.findById(notificationId).orElseThrow();
		this.notificationRepository.delete(notification);
		
	}

	@Override
	public List<NotificationDto> getAllNotification(Long receiverId) {
		List<Notification> notifications=this.notificationRepository.getNotificationAccordingToReceiverId(receiverId);
		List<NotificationDto> notificationDtos=notifications.stream().map(notification->mapToDto(notification)).collect(Collectors.toList());
		return notificationDtos;
	}
	
	NotificationDto mapToDto(Notification notification) {
		
		NotificationDto notificationDto= new NotificationDto();
		notificationDto.setId(notification.getId());
		notificationDto.setBody(notification.getBody());
		notificationDto.setTitle(notification.getTitle());
		notificationDto.setNotificationType(notification.getNotificationType());
		notificationDto.setReadStatus(notification.isReadStatus());
		notificationDto.setReceiverId(notification.getReceiverId());
		notificationDto.setSenderId(notification.getSenderId());
		notificationDto.setTimestamp(notification.getTimestamp());
		
		return notificationDto;
	}

	@Override
	public NotificationDto updateNotification(Long notificationId) {
		Notification notification= this.notificationRepository.findById(notificationId).orElseThrow();
		notification.setReadStatus(true);
		Notification updatedNotification=this.notificationRepository.save(notification);
		return mapToDto(updatedNotification);
	}

}
