/**
 * 
 */
package com.alok91340.gethired.service;

import java.util.List;

import com.alok91340.gethired.dto.UserProfileDto;
import com.alok91340.gethired.entities.UserProfile;

/**
 * @author alok91340
 *
 */
public interface UserProfileService {
	
	UserProfileDto getUserProfile(Long studentId);
	
	List<UserProfile> getUsersProfile(int pageNo, int pageSize, String sortBy, String sortDir);

	/**
	 * @param userProfileDto
	 * @return
	 */
	UserProfileDto createStudentProfile(UserProfileDto userProfileDto, Long userId);
	
	UserProfileDto updateUserProfile(UserProfileDto userProfileDto, Long userProfileId);
	
	void deleteUserProfile(Long userProfileId);
	
	
}
