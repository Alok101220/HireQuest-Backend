/**
 * 
 */
package com.alok91340.gethired.service;

import java.util.List;

import com.alok91340.gethired.dto.SkillDto;

/**
 * @author alok91340
 *
 */
public interface SkillService {
	SkillDto addSkill(Long userProfileId,SkillDto skillDto);
	SkillDto updateSkill(Long skillId,SkillDto skillDto);
	SkillDto deleteSkill(Long skillId);
	
	List<SkillDto> getAllSkill(Long userProfileId);
	SkillDto getSkill(Long skillId);
}
