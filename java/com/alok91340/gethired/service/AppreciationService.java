/**
 * 
 */
package com.alok91340.gethired.service;

import java.util.List;

import com.alok91340.gethired.dto.AwardDto;

/**
 * @author alok91340
 *
 */
public interface AppreciationService {
	
	AwardDto addAppreciation(AwardDto awardDto, Long userProfileId);
	AwardDto updateAppreciation(AwardDto awardDto, Long awardId);
	List<AwardDto> getAllAppreciation(Long userProfileId);
	void deleteAppreciation(Long awardId);
	AwardDto getAppreciation(Long awardId);
}
