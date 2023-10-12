/**
 * 
 */
package com.alok91340.gethired.controller;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;

import com.alok91340.gethired.service.ChatService;
import com.alok91340.gethired.websocket.PresenceService;
import com.alok91340.gethired.entities.Message;

/**
 * @author aloksingh
 *
 */
@RestController
@RequestMapping("api/hireQuest")
public class MessageController {

    @Autowired
    private ChatService chatService;
    
    @Autowired
    private PresenceService presenceService;

    @PostMapping("/send-message")
    public ResponseEntity<String> sendMessage(@RequestBody Message request) {
        try {
            String senderUsername = request.getSenderUsername();
            String receiverUsername = request.getReceiverUsername();
            String content = request.getContent();

            // Save the message to the database
            Message messageEntity = new Message();
//            messageEntity.setRoom(request.getRoom());
            messageEntity.setSenderUsername(senderUsername);
            messageEntity.setReceiverUsername(receiverUsername);
            messageEntity.setContent(content);
            messageEntity.setTimestamp(request.getTimestamp());
            chatService.saveMessage(messageEntity);

            // Send the message via WebSocket
            Set<WebSocketSession> receiverSessions = presenceService.getSessionsForUser(receiverUsername);
            for (WebSocketSession session : receiverSessions) {
                try {
                    if (session.isOpen()) {
                    	String responseMessage = "{\"id\": " + 0 +
                    			", \"senderUsername\": \"" + senderUsername + "\"" +
                                ", \"receiverUsername\": \"" + receiverUsername + "\"" +
                                ", \"content\": \"" + content + "\"" +
                                ", \"timestamp\": \"" + messageEntity.getTimestamp() + "\"" +
                                
                                ", \"seen\": " + false +
                                "}";
                    	session.sendMessage(new TextMessage(responseMessage));
                        System.out.println(responseMessage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return ResponseEntity.ok("Message sent successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while sending the message.");
        }
    }

    @GetMapping("/{senderUsername}/{receiverUsername}/{timeStamp}/get-message")
    public ResponseEntity<List<Message>> getMessages(
            @PathVariable String senderUsername,
            @PathVariable String receiverUsername,
            @PathVariable String timeStamp
    ) {
        try {
            List<Message> messages = chatService.getMessages(senderUsername, receiverUsername,timeStamp);
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

