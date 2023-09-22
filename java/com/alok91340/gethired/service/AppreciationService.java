/**
 * 
 */
package com.alok91340.gethired.service;

import java.util.Set;

import com.alok91340.gethired.dto.AwardDto;

/**
 * @author alok91340
 *
 */
public interface AppreciationService {
	
	AwardDto addAppreciation(AwardDto awardDto, Long userProfileId);
	AwardDto updateAppreciation(AwardDto awardDto, Long awardId);
	Set<AwardDto> getAllAppreciation(Long userProfileId);
	void deleteAppreciation(long awardId);
	AwardDto getAppreciation(Long awardId);
}
