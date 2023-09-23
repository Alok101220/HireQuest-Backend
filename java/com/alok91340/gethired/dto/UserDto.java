/**
 * 
 */
package com.alok91340.gethired.dto;

import lombok.Data;

/**
 * @author alok91340
 *
 */
@Data
public class UserDto {
	private String username;
	private String name;
	private String email;
	private String password;
	private String headline;
	private String birthdate;
	private String currentOccupation;
	private boolean status;
	private String phone;
	private int isRecuriter;
	private String fcmToken;
}
