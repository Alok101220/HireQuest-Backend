/**
 * 
 */
package com.alok91340.gethired.service;

import java.util.List;

import com.alok91340.gethired.dto.UserDto;

/**
 * @author alok91340
 *
 */

public interface UserService {
	
	UserDto createUser(UserDto userDto);
	
	UserDto getUser(String username);
	
	List<UserDto> getAllUser(int pageNo, int pageSize, String sortBy, String sortDir);
	
	UserDto updateUser(String username, UserDto userDto);
	
	String deleteUser(String username);
	
	UserDto updatePassword(String email,String password);
	
	
}
