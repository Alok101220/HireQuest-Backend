/**
 * 
 */
package com.alok91340.gethired.dto;

import com.alok91340.gethired.entities.Message;
import com.alok91340.gethired.entities.User;

import lombok.Data;

/**
 * @author aloksingh
 *
 */
@Data
public class ChatRoomDto {

    private Long id;
    private User sender;
    private User receiver;
    private int unSeenMessageCount;
    private Boolean isRequest;
    private Message lastMessage;
}
