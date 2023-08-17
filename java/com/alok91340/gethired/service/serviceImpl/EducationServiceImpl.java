/**
 * 
 */
package com.alok91340.gethired.service.serviceImpl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alok91340.gethired.dto.EducationDto;
import com.alok91340.gethired.entities.Education;
import com.alok91340.gethired.entities.UserProfile;
import com.alok91340.gethired.exception.ResourceNotFoundException;
import com.alok91340.gethired.repository.EducationRepository;
import com.alok91340.gethired.repository.UserProfileRepo;
import com.alok91340.gethired.service.EducationService;

/**
 * @author alok91340
 *
 */

@Service
public class EducationServiceImpl implements EducationService{

	@Autowired
	private UserProfileRepo userProfileRepository;
	
	@Autowired
	private EducationRepository educationRepository;
	
	
	@Override
	public EducationDto addEducation(EducationDto educationDto, Long userProfileId) {
		UserProfile userProfile=this.userProfileRepository.findById(userProfileId).orElseThrow(()->new ResourceNotFoundException("user-profile",userProfileId));
		Education education=new Education();
		mapToEntity(educationDto,education);
		education.setStudentProfile(userProfile);
		Education savedEducation=this.educationRepository.save(education);
		return mapToDto(savedEducation);
	}
	
	@Override
	public EducationDto getEducation(Long educationId) {
		Education education=this.educationRepository.findById(educationId).orElseThrow(()->new ResourceNotFoundException("Education", educationId));
		return mapToDto(education);
	}

	@Override
	public EducationDto updateEducation(EducationDto educationDto, Long educationId) {
		Education education=this.educationRepository.findById(educationId).orElseThrow(()->new ResourceNotFoundException("Education", educationId));
		mapToEntity(educationDto,education);
		Education savedEducation=this.educationRepository.save(education);
		
		return mapToDto(savedEducation);
	}

	@Override
	public Set<EducationDto> getAllEducation(Long userprofileId) {
		UserProfile userProfile=this.userProfileRepository.findById(userprofileId).orElseThrow(()->new ResourceNotFoundException("user with userprofile Id",userprofileId));
		Set<Education> educations=userProfile.getEducations();
		Set<EducationDto>educationDtos=educations.stream().map(education -> mapToDto(education))
                .collect(Collectors.toSet());
				return educationDtos;
	}

	@Override
	public void deleteEducation(Long educationId) {
		Education education=this.educationRepository.findById(educationId).orElseThrow(()->new ResourceNotFoundException("Education", educationId));
		this.educationRepository.delete(education);
		
	}
	
	void mapToEntity(EducationDto educationDto,Education education) {
		
		education.setName(educationDto.getName());
		education.setStart(educationDto.getStart());
		education.setEnd(educationDto.getEnd());
		education.setStandard(educationDto.getStandard());
		
	}
	
	EducationDto mapToDto(Education education) {
		EducationDto educationDto=new EducationDto();
		educationDto.setId(education.getId());
		educationDto.setName(education.getName());
		educationDto.setStart(education.getStart());
		educationDto.setEnd(education.getEnd());
		educationDto.setStandard(education.getStandard());
		return educationDto;
	}

}
