/**
 * 
 */
package com.alok91340.gethired.service;

import java.util.List;
import java.util.Set;

import com.alok91340.gethired.dto.ProfileLinkDto;

/**
 * @author alok91340
 *
 */
public interface ProfileLinkService {
	
	ProfileLinkDto addProfileLink(ProfileLinkDto profileLinkDto, Long userProfileId);
	ProfileLinkDto updateProfileLink(ProfileLinkDto profileLinkDto, Long profileLinkId);
	Set<ProfileLinkDto> getAllProfileLink(Long userProfileId);
	void deleteProfileLink(Long profileLinkId);
	ProfileLinkDto getProfileLink(Long profileLinkId);
}
