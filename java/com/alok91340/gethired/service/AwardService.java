/**
 * 
 */
package com.alok91340.gethired.service;

import java.util.List;
import java.util.Set;

import com.alok91340.gethired.dto.AwardDto;

/**
 * @author alok91340
 *
 */
public interface AwardService {
	
	AwardDto addAward(AwardDto awardDto, Long userProfileId);
	AwardDto updateAward(AwardDto awardDto, Long awardId);
	Set<AwardDto> getAllAward(Long userProfileId);
	void deleteAward(long awardId);
	AwardDto getAward(Long awardId);
}
