/**
 * 
 */
package com.alok91340.gethired.service.serviceImpl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alok91340.gethired.dto.ProfileLinkDto;
import com.alok91340.gethired.entities.ProfileLink;
import com.alok91340.gethired.entities.UserProfile;
import com.alok91340.gethired.exception.ResourceNotFoundException;
import com.alok91340.gethired.repository.ProfileLinkRepository;
import com.alok91340.gethired.repository.UserProfileRepo;
import com.alok91340.gethired.service.ProfileLinkService;

/**
 * @author alok91340
 *
 */
@Service
public class ProfileLinkServiceImpl implements ProfileLinkService{
	
	@Autowired
	private UserProfileRepo userProfileRepository;
	
	@Autowired
	private ProfileLinkRepository profileLinkRespository;

	@Override
	public ProfileLinkDto addProfileLink(ProfileLinkDto profileLinkDto, Long userProfileId) {
		
		UserProfile userProfile=this.userProfileRepository.findById(userProfileId).orElseThrow(()->new ResourceNotFoundException("user-profile",userProfileId));
		ProfileLink profileLink= new ProfileLink();
		mapToEntity(profileLinkDto,profileLink);
		profileLink.setStudentProfile(userProfile);
		ProfileLink savedProfileLink=this.profileLinkRespository.save(profileLink);
		return mapToDto(savedProfileLink);
	}

	@Override
	public ProfileLinkDto updateProfileLink(ProfileLinkDto profileLinkDto, Long profileLinkId) {
		ProfileLink profileLink= this.profileLinkRespository.findById(profileLinkId).orElseThrow(()-> new ResourceNotFoundException("profile-link",profileLinkId));
		mapToEntity(profileLinkDto,profileLink);
		ProfileLink savedProfileLink=this.profileLinkRespository.save(profileLink);
		return mapToDto(savedProfileLink);
	}

	@Override
	public 	ProfileLinkDto getProfileLink(Long profileLinkId) {
		ProfileLink profileLink= this.profileLinkRespository.findById(profileLinkId).orElseThrow(()-> new ResourceNotFoundException("profile-link",profileLinkId));
		return mapToDto(profileLink);
	}
	@Override
	public Set<ProfileLinkDto> getAllProfileLink(Long userProfileId) {
		UserProfile userProfile=this.userProfileRepository.findById(userProfileId).orElseThrow(()->new ResourceNotFoundException("User-Profile",userProfileId));
		Set<ProfileLink> profileLinks=userProfile.getProfileLinks();
		Set<ProfileLinkDto> profileLinkDtos=profileLinks.stream().map(profileLink->mapToDto(profileLink)).collect(Collectors.toSet());
		
		return profileLinkDtos;
	}

	@Override
	public void deleteProfileLink(Long profileLinkId) {
		this.profileLinkRespository.deleteById(profileLinkId);
		
	}
	private ProfileLink mapToEntity(ProfileLinkDto profileLinkDto,ProfileLink profileLink) {
		profileLink.setHandleName(profileLinkDto.getHandleName());
		profileLink.setLink(profileLinkDto.getLink());
		return profileLink;
	}
	private ProfileLinkDto mapToDto(ProfileLink profileLink) {
		
		ProfileLinkDto profileLinkDto= new ProfileLinkDto();
		profileLinkDto.setId(profileLink.getId());
		profileLinkDto.setHandleName(profileLink.getHandleName());
		profileLinkDto.setLink(profileLink.getLink());
		return profileLinkDto;
	}

}
