/**
 * 
 */
package com.alok91340.gethired.service.serviceImpl;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alok91340.gethired.dto.SkillDto;
import com.alok91340.gethired.entities.Skill;
import com.alok91340.gethired.entities.UserProfile;
import com.alok91340.gethired.exception.ResourceNotFoundException;
import com.alok91340.gethired.repository.SkillRepository;
import com.alok91340.gethired.repository.UserProfileRepository;
import com.alok91340.gethired.service.SkillService;

/**
 * @author alok91340
 *
 */
@Service
public class SkillServiceImpl implements SkillService{
	
	@Autowired
	private SkillRepository skillRepository;
	
	@Autowired
	private UserProfileRepository userProfileRepository;

	@Override
	public SkillDto addSkill(Long userProfileId, SkillDto skillDto) {
		
		UserProfile userProfile=userProfileRepository.findById(userProfileId).orElseThrow(()->new ResourceNotFoundException("user-profile",userProfileId));
		Skill skill= mapToEntity(skillDto);
		skill.setUserProfile(userProfile);
		Skill savedSkill=skillRepository.save(skill);
		
		return mapToDto(savedSkill);
	}
	
	@Override
	public SkillDto updateSkill(Long skillId, SkillDto skillDto) {
		Skill skill= skillRepository.findById(skillId).orElseThrow(()-> new ResourceNotFoundException("skill",skillId));
		skill.setSkillName(skillDto.getSkillName());
		skillRepository.save(skill);
		return mapToDto(skill);
	}

	@Override
	public SkillDto deleteSkill(Long skillId) {
		skillRepository.deleteById(skillId);
		return null;
	}
	
	@Override
	public SkillDto getSkill(Long skillId) {
		Skill skill=this.skillRepository.findById(skillId).orElseThrow(()->new ResourceNotFoundException("Skill",skillId));
		return mapToDto(skill);
	}
	@Override
	public Set<SkillDto> getAllSkill(Long userProfileId){
		UserProfile userProfile=userProfileRepository.findById(userProfileId).orElseThrow(()->new ResourceNotFoundException("user-profile",userProfileId));
		Set<Skill> skills=userProfile.getSkills();
		Set<SkillDto> skillDtos=skills.stream().map(skill->mapToDto(skill)).collect(Collectors.toSet());
		return skillDtos;
	}
	Skill mapToEntity(SkillDto skillDto) {
		Skill skill= new Skill();
		skill.setSkillName(skillDto.getSkillName());
		return skill;
	}
	SkillDto mapToDto(Skill skill) {
		
		SkillDto skillDto= new SkillDto();
		skillDto.setId(skill.getId());
		skillDto.setSkillName(skill.getSkillName());
		
		return skillDto;
	}

	

}
