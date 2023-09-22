/**
 * 
 */
package com.alok91340.gethired.dto;

import java.time.LocalDateTime;

import com.alok91340.gethired.entities.Image;

import lombok.Data;

/**
 * @author aloksingh
 *
 */
@Data
public class ChatUser {
    private Long id;
    private String username;
    private Image image;
    private LocalDateTime lastMessageTime;
    private Long unseenMessageCount;
    private String lastMessageContent;

    
}
