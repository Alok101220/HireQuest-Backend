/**
 * 
 */
package com.alok91340.gethired.service.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.alok91340.gethired.dto.UserProfileDto;
import com.alok91340.gethired.entities.User;
import com.alok91340.gethired.entities.UserProfile;
import com.alok91340.gethired.exception.ResourceNotFoundException;
import com.alok91340.gethired.repository.UserProfileRepository;
import com.alok91340.gethired.service.UserProfileService;
import com.alok91340.gethired.repository.*;

/**
 * @author alok91340
 *
 */
@Service
public class UserProfileServiceImpl implements UserProfileService{
	
	
	
	@Autowired
	private UserProfileRepository userProfileRepo;
	
	@Autowired
	private UserRepository userRepository;
	
			

	@Override
	public UserProfileDto getUserProfile(Long userId) {
		User user=this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user",userId));
		UserProfile userProfile=userProfileRepo.findUserProfileByUser(user);
		UserProfileDto userProfileDto= new UserProfileDto();
		userProfileDto.setId(userProfile.getId());
		userProfileDto.setAbout(userProfile.getAbout());
		
		return userProfileDto;
	}

	@Override
	public List<UserProfile> getUserProfiles(int pageNo, int pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<UserProfile> userProfiles = userProfileRepo.findAll(pageable);
		
		List<UserProfile> userProfileList=userProfiles.getContent();
		
		return userProfileList;
	}
	
	UserProfileDto mapToDto(UserProfile userProfile) {
		UserProfileDto userProfileDto= new UserProfileDto();
		userProfileDto.setId(userProfile.getId());
		userProfileDto.setAbout(userProfile.getAbout());
		return userProfileDto;
	}
	
	UserProfile mapToEntity(UserProfileDto userProfileDto) {
		UserProfile userProfile= new UserProfile();
		userProfile.setAbout(userProfileDto.getAbout());
		return userProfile;
		
		
		
	}


	@Override
	public UserProfileDto updateUserProfile(UserProfileDto userProfileDto, Long userProfileId) {
		UserProfile userProfile=this.userProfileRepo.findById(userProfileId).orElseThrow(()->new ResourceNotFoundException("user-profile",userProfileId));
		userProfile.setAbout(userProfileDto.getAbout());
		userProfile.setUpdatedAt(LocalDateTime.now());
		userProfile.setUpdatedBy(userProfile.getUser().getEmail());
		UserProfile savedUserProfile=this.userProfileRepo.save(userProfile);
		return mapToDto(savedUserProfile);
	}


}
