/**
 * 
 */
package com.alok91340.gethired.service.serviceImpl;

/**
 * @author alok91340
 *
 */
//NotificationService.java
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

 public void sendNotification(String fcmToken, String title, String body) {
     Notification notification = Notification.builder()
             .setTitle(title)
             .setBody(body)
             .build();

     Message message = Message.builder()
             .setNotification(notification)
             .setToken(fcmToken)
             .build();

     try {
         FirebaseMessaging.getInstance().send(message);
     } catch (Exception e) {
         // Handle the exception
         e.printStackTrace();
     }
 }
}
