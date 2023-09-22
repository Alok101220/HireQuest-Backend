/**
 * 
 */
package com.alok91340.gethired.websocket;

import org.springframework.beans.factory.annotation.Autowired;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.alok91340.gethired.entities.Message;
import com.alok91340.gethired.entities.User;
import com.alok91340.gethired.exception.ResourceNotFoundException;
import com.alok91340.gethired.repository.MessageRepository;
import com.alok91340.gethired.repository.UserRepository;
import com.alok91340.gethired.security.JwtTokenProvider;
import com.alok91340.gethired.service.serviceImpl.NotificationService;

@Component
public class WebSocketHandler extends TextWebSocketHandler {
	
	private final MessageRepository messageRepository;
    private final PresenceService presenceService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    
    
    public WebSocketHandler(MessageRepository messageRepository, PresenceService presenceService,JwtTokenProvider jwtTokenProvider,UserRepository userRepository,NotificationService notificationService) {
        this.messageRepository = messageRepository;
        this.presenceService = presenceService;
        this.jwtTokenProvider=jwtTokenProvider;
        this.userRepository=userRepository;
        this.notificationService=notificationService;
    }
	
    private final Map<Long, WebSocketSession> activeSessions = new HashMap<>();
    
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Extract the user token from the URI path
        String token = session.getUri().getPath().replace("/ws/", "");
        
        try {
            // Authenticate user using the token (e.g., verify token with your authentication service)
            String userName = this.jwtTokenProvider.getUserNameFromToken(token);
            Optional<User> userOptional = this.userRepository.findByUsername(userName);

            if (userOptional.isPresent()) {
                Long userId = userOptional.get().getId();
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
        Long senderId = getUserIdFromSession(session);

        // Parse the message content and extract sender, receiver, and content
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> messageData = objectMapper.readValue(message.getPayload(), new TypeReference<Map<String,Object>>(){});
        Long receiverId=((Integer) messageData.get("receiverId")).longValue();
        
        String content = (String) messageData.get("content");
       
        // Save the message to the database
        senderId=((Integer)messageData.get("senderId")).longValue();
        Message messageEntity = new Message();
        messageEntity.setSenderId(senderId);
        messageEntity.setReceiverId(receiverId);
        messageEntity.setContent(content);
        messageEntity.setTimestamp((String)messageData.get("timestamp"));
        messageEntity.setSeen(false);
//        presenceService.addUserSession(receiverId, session);

        // Process and send messages to recipients
        Set<WebSocketSession> receiverSessions = presenceService.getSessionsForUser(receiverId);
        if(receiverSessions.isEmpty()) {
        	User user = userRepository.findById(receiverId)
                    .orElseThrow(() -> new ResourceNotFoundException("user",receiverId));
            notificationService.sendNotification(user.getFcmToken(), "message", content);
        	
        }else {
        	for (WebSocketSession receiverSession : receiverSessions) {
                try {
                    if (receiverSession.isOpen()) {
                        // Create a message to send to the recipient
                    	String responseMessage = "{\"id\": " + 0 +
                    			", \"senderId\": \"" + senderId + "\"" +
                                ", \"receiverId\": \"" + receiverId + "\"" +
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
        Long userId = getUserIdFromSession(session);

        // Remove the WebSocket session for the user
        activeSessions.remove(userId);

        // Notify others that this user is offline
//        broadcastPresenceUpdate(userId, false);
    }

    private void broadcastPresenceUpdate(Long userId, boolean isOnline) {
        // Notify all connected WebSocket clients about the presence update
        for (WebSocketSession session : activeSessions.values()) {
            try {
                if (session.isOpen()) {
                    // Create a message to notify clients about the presence update
                    String message = "{\"type\": \"presenceUpdate\", \"userId\": " + userId + ", \"isOnline\": " + isOnline + "}";
                    session.sendMessage(new TextMessage(message));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Implement getUserIdFromSession method to retrieve user ID from the WebSocketSession
    private Long getUserIdFromSession(WebSocketSession session) {
        // Assume that you've stored the user ID in the session attributes during login
        Map<String, Object> attributes = session.getAttributes();
        Object userIdObject = attributes.get("userId"); // Replace "userId" with the actual key you use
        if (userIdObject instanceof Long) {
            return (Long) userIdObject;
        } else {
            return null;
        }
    }

    
}
