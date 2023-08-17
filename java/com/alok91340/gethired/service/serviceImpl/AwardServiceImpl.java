/**
 * 
 */
package com.alok91340.gethired.service.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alok91340.gethired.dto.AwardDto;
import com.alok91340.gethired.service.AwardService;
import com.alok91340.gethired.entities.Award;
import com.alok91340.gethired.entities.UserProfile;
import com.alok91340.gethired.exception.ResourceNotFoundException;
import com.alok91340.gethired.repository.AwardRepository;
import com.alok91340.gethired.repository.UserProfileRepo;

/**
 * @author alok91340
 *
 */
@Service
public class AwardServiceImpl implements AwardService{
	
	@Autowired
	private AwardRepository awardRepository;
	
	@Autowired 
	private UserProfileRepo userProfileRepository;

	@Override
	public AwardDto addAward(AwardDto awardDto, Long userProfileId) {

		UserProfile userProfile=this.userProfileRepository.findById(userProfileId).orElseThrow(()->new ResourceNotFoundException("user-profile",userProfileId));
		Award award=mapToEntity(awardDto);
		award.setStudentProfile(userProfile);
		Award savedAward=this.awardRepository.save(award);
		return mapToDto(savedAward);
	}
	
	@Override
	public AwardDto getAward(Long awardId) {
		Award award=this.awardRepository.findById(awardId).orElseThrow(()->new ResourceNotFoundException("Award",awardId));
		return mapToDto(award);
	}

	@Override
	public AwardDto updateAward(AwardDto awardDto, Long awardId) {
		Award award=this.awardRepository.findById(awardId).orElseThrow(()->new ResourceNotFoundException("award",awardId));
		award.setDate(awardDto.getDate());
		award.setPosition(awardDto.getPosition());
		award.setDescription(award.getDescription());
		award.setTitle(award.getTitle());;
		Award savedAward=this.awardRepository.save(award);
		return mapToDto(savedAward);
	}

	@Override
	public Set<AwardDto> getAllAward(Long userProfileId) {
		UserProfile userProfile=this.userProfileRepository.findById(userProfileId).orElseThrow(()->new ResourceNotFoundException("user-profile",userProfileId));
		Set<Award> awards=userProfile.getAwards();
		Set<AwardDto> awardDtos=awards.stream().map(award->mapToDto(award)).collect(Collectors.toSet());
		return awardDtos;
	}

	@Override
	public void deleteAward(long awardId) {
		// TODO Auto-generated method stub
		
	}
	
	private Award mapToEntity(AwardDto awardDto) {
		Award award= new Award();
		award.setTitle(awardDto.getTitle());
		award.setDescription(awardDto.getDescription());
		award.setPosition(awardDto.getPosition());
		award.setDate(awardDto.getDate());
		return award;
	}
	private AwardDto mapToDto(Award award) {
		AwardDto awardDto= new AwardDto();
		awardDto.setId(award.getId());
		awardDto.setTitle(award.getTitle());
		awardDto.setDescription(award.getDescription());
		awardDto.setPosition(award.getPosition());
		awardDto.setDate(award.getDate());
		return awardDto;
	}

}
