/**
 * 
 */
package com.alok91340.gethired.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alok91340.gethired.entities.User;
import com.alok91340.gethired.entities.UserProfile;


/**
 * @author alok91340
 *
 */
public interface UserProfileRepository extends JpaRepository<UserProfile,Long> {
	UserProfile findUserProfileByUser(User user);
}