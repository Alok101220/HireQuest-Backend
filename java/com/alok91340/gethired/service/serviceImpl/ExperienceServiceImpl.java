/**
 * 
 */
package com.alok91340.gethired.service.serviceImpl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alok91340.gethired.dto.ExperienceDto;
import com.alok91340.gethired.entities.Experience;
import com.alok91340.gethired.entities.UserProfile;
import com.alok91340.gethired.exception.ResourceNotFoundException;
import com.alok91340.gethired.repository.ExperienceRepository;
import com.alok91340.gethired.repository.UserProfileRepo;
import com.alok91340.gethired.service.ExperienceService;

/**
 * @author alok91340
 *
 */
@Service
public class ExperienceServiceImpl implements ExperienceService{

	@Autowired
	private UserProfileRepo userProfileRepository;
	
	@Autowired
	private ExperienceRepository experienceRepository;
	
	@Override
	public ExperienceDto addExperience(ExperienceDto experienceDto, Long userProfileId) {
		UserProfile userProfile=this.userProfileRepository.findById(userProfileId).orElseThrow(()->new ResourceNotFoundException("user-profile",userProfileId));
		Experience experience=mapToEntity(experienceDto);
		experience.setStudentProfile(userProfile);
		Experience savedExperience=this.experienceRepository.save(experience);
		return mapToDto(savedExperience);
	}
	
	@Override
	public ExperienceDto getExperience(Long experienceId) {
		Experience experience=this.experienceRepository.findById(experienceId).orElseThrow(()->new ResourceNotFoundException("Experience",experienceId));
		return mapToDto(experience);
	}

	@Override
	public ExperienceDto updateExperience(ExperienceDto experienceDto, Long experienceId) {
		Experience experience=this.experienceRepository.findById(experienceId).orElseThrow(()->new ResourceNotFoundException("Experience",experienceId));
		experience.setTitle(experienceDto.getTitle());
		experience.setStart(experienceDto.getStart());
		experience.setEnd(experience.getEnd());
		experience.setPosition(experienceDto.getPosition());
		experience.setOrganisation(experienceDto.getOrganisation());
		experience.setDescription(experienceDto.getDescription());
		experience.setLink(experienceDto.getLink());
		Experience savedExperience=this.experienceRepository.save(experience);
		
		return mapToDto(savedExperience);
	}

	@Override
	public Set<ExperienceDto> getAllExperience(Long userProfileId) {

		UserProfile userProfile=this.userProfileRepository.findById(userProfileId).orElseThrow(()->new ResourceNotFoundException("User-Profile",userProfileId));
		Set<Experience> experiences=userProfile.getExperiences();
		Set<ExperienceDto> experienceDtos=experiences.stream().map(experience->mapToDto(experience)).collect(Collectors.toSet());
		return experienceDtos;
	}

	@Override
	public void deleteExperience(Long experienceId) {
		Experience experience=this.experienceRepository.findById(experienceId).orElseThrow(()->new ResourceNotFoundException("Experience",experienceId));
		this.experienceRepository.delete(experience);
		
	}
	
	Experience mapToEntity(ExperienceDto experienceDto) {
		Experience experience=new Experience();
		experience.setDescription(experienceDto.getDescription());
		experience.setPosition(experienceDto.getPosition());
		experience.setOrganisation(experienceDto.getOrganisation());
		experience.setLink(experienceDto.getLink());
		experience.setTitle(experienceDto.getTitle());
		experience.setStart(experienceDto.getStart());
		experience.setEnd(experienceDto.getEnd());
		return experience;
	}
	
	ExperienceDto mapToDto(Experience experience) {
		ExperienceDto experienceDto= new ExperienceDto();
		experienceDto.setId(experience.getId());
		experienceDto.setTitle(experience.getTitle());
		experienceDto.setLink(experience.getLink());
		experienceDto.setDescription(experience.getDescription());
		experienceDto.setPosition(experience.getPosition());
		experienceDto.setOrganisation(experience.getOrganisation());
		experienceDto.setStart(experience.getStart());
		experienceDto.setEnd(experience.getEnd());
		return experienceDto;
	}

}
