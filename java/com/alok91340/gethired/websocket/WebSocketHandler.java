/**
 * 
 */
package com.alok91340.gethired.websocket;

import org.springframework.stereotype.Component;
/**
 * @author aloksingh
 *
 */
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.alok91340.gethired.entities.Message;
import com.alok91340.gethired.entities.User;
import com.alok91340.gethired.dto.NotificationRequest;
import com.alok91340.gethired.exception.ResourceNotFoundException;
import com.alok91340.gethired.repository.MessageRepository;
import com.alok91340.gethired.repository.UserRepository;
import com.alok91340.gethired.security.JwtTokenProvider;
import com.alok91340.gethired.service.serviceImpl.FcmNotificationService;

@Component
public class WebSocketHandler extends TextWebSocketHandler {
	
	private final MessageRepository messageRepository;
    private final PresenceService presenceService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final FcmNotificationService fcmNotificationService;
    
    
    public WebSocketHandler(MessageRepository messageRepository, PresenceService presenceService,JwtTokenProvider jwtTokenProvider,UserRepository userRepository,FcmNotificationService fcmNotificationService) {
        this.messageRepository = messageRepository;
        this.presenceService = presenceService;
        this.jwtTokenProvider=jwtTokenProvider;
        this.userRepository=userRepository;
        this.fcmNotificationService=fcmNotificationService;
    }
	
    private final Map<String, WebSocketSession> activeSessions = new HashMap<>();
    
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Extract the user token from the URI path
        String token = session.getUri().getPath().replace("/ws/", "");
        
        try {
            // Authenticate user using the token (e.g., verify token with your authentication service)
            String userName = this.jwtTokenProvider.getUserNameFromToken(token);
            Optional<User> userOptional = this.userRepository.findByUsername(userName);

            if (userOptional.isPresent()) {
                String userId = userOptional.get().getUsername();
                session.getAttributes().put("userId", userId);
                
                presenceService.addUserSession(userId, session);
              

                // Store the WebSocket session for the user
                activeSessions.put(userId, session);

                // Notify others that this user is online
//                broadcastPresenceUpdate(userId, true);
            } else {
                // Handle case where user is not found
                session.close();
            }
        } catch (Exception e) {
            // Handle authentication or retrieval error
            e.printStackTrace(); // You may want to log the error
            session.close();
        }
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String senderUsername = getUserIdFromSession(session);

        // Parse the message content and extract sender, receiver, and content
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> messageData = objectMapper.readValue(message.getPayload(), new TypeReference<Map<String,Object>>(){});
        String receiverUsername=messageData.get("receiverUsername").toString();
        
        String content = (String) messageData.get("content");
       
        // Save the message to the database
        senderUsername=messageData.get("senderUsername").toString();
        Message messageEntity = new Message();
        messageEntity.setSenderUsername(senderUsername);
        messageEntity.setReceiverUsername(receiverUsername);
        messageEntity.setContent(content);
        messageEntity.setTimestamp((String)messageData.get("timestamp"));
        messageEntity.setSeen(false);
//        presenceService.addUserSession(receiverId, session);

        // Process and send messages to recipients
        Set<WebSocketSession> receiverSessions = presenceService.getSessionsForUser(receiverUsername);
        if(receiverSessions.isEmpty()) {
        	User user = userRepository.findById(receiverUsername)
                    .orElseThrow(() -> new ResourceNotFoundException("user",(long)0));
        	NotificationRequest request= new NotificationRequest();
        	request.setBody(content);
        	request.setTitle("message");
        	request.setNotificationType("message");
        	request.setReceiverUsername(receiverUsername);
        	request.setSenderUsername(senderUsername);
        	
        	fcmNotificationService.sendNotification(user.getFcmToken(), request);
        	
        }else {
        	for (WebSocketSession receiverSession : receiverSessions) {
                try {
                    if (receiverSession.isOpen()) {
                        // Create a message to send to the recipient
                    	String responseMessage = "{\"id\": " + 0 +
                    			", \"senderUsername\": \"" + senderUsername + "\"" +
                                ", \"receiverUsername\": \"" + receiverUsername + "\"" +
                                ", \"content\": \"" + content + "\"" +
                                ", \"timestamp\": \"" + messageEntity.getTimestamp() + "\"" +
                                
                                ", \"seen\": " + false +
                                "}";

                        receiverSession.sendMessage(new TextMessage(responseMessage));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        Message savedMessage=this.messageRepository.save(messageEntity);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // Extract the user ID from the session
        String userId = getUserIdFromSession(session);

        // Remove the WebSocket session for the user
        activeSessions.remove(userId);

        // Notify others that this user is offline
//        broadcastPresenceUpdate(userId, false);
    }

    private void broadcastPresenceUpdate(String senderUsername, boolean isOnline) {
        // Notify all connected WebSocket clients about the presence update
        for (WebSocketSession session : activeSessions.values()) {
            try {
                if (session.isOpen()) {
                    // Create a message to notify clients about the presence update
                    String message = "{\"type\": \"presenceUpdate\", \"senderUsername\": " + senderUsername + ", \"isOnline\": " + isOnline + "}";
                    session.sendMessage(new TextMessage(message));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Implement getUserIdFromSession method to retrieve user ID from the WebSocketSession
    private String getUserIdFromSession(WebSocketSession session) {
        // Assume that you've stored the user ID in the session attributes during login
        Map<String, Object> attributes = session.getAttributes();
        Object userIdObject = attributes.get("userId"); // Replace "userId" with the actual key you use
        if (userIdObject instanceof String) {
            return userIdObject.toString();
        } else {
            return null;
        }
    }

    
}
