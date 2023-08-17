/**
 * 
 */
package com.alok91340.gethired.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alok91340.gethired.dto.UserDto;
import com.alok91340.gethired.entities.UserProfile;

/**
 * @author alok91340
 *
 */
public interface UserProfileRepo extends JpaRepository<UserProfile,Long> {
	
}
