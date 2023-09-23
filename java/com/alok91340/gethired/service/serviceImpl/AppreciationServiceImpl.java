/**
 * 
 */
package com.alok91340.gethired.service.serviceImpl;

import java.util.stream.Collectors;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alok91340.gethired.dto.AwardDto;
import com.alok91340.gethired.service.AppreciationService;
import com.alok91340.gethired.entities.Appreciation;
import com.alok91340.gethired.entities.UserProfile;
import com.alok91340.gethired.exception.ResourceNotFoundException;
import com.alok91340.gethired.repository.AppreciationRepository;
import com.alok91340.gethired.repository.UserProfileRepository;

/**
 * @author alok91340
 *
 */
@Service
public class AppreciationServiceImpl implements AppreciationService{
	
	@Autowired
	private AppreciationRepository awardRepository;
	
	@Autowired 
	private UserProfileRepository userProfileRepository;

	@Override
	public AwardDto addAppreciation(AwardDto awardDto, Long userProfileId) {

		UserProfile userProfile=this.userProfileRepository.findById(userProfileId).orElseThrow(()->new ResourceNotFoundException("user-profile",userProfileId));
		Appreciation award=mapToEntity(awardDto);
		award.setUserProfile(userProfile);
		Appreciation savedAward=this.awardRepository.save(award);
		return mapToDto(savedAward);
	}
	
	@Override
	public AwardDto getAppreciation(Long awardId) {
		Appreciation award=this.awardRepository.findById(awardId).orElseThrow(()->new ResourceNotFoundException("Award",awardId));
		return mapToDto(award);
	}

	@Override
	public AwardDto updateAppreciation(AwardDto awardDto, Long awardId) {
		Appreciation award=this.awardRepository.findById(awardId).orElseThrow(()->new ResourceNotFoundException("award",awardId));
		award.setDate(awardDto.getDate());
		award.setPosition(awardDto.getPosition());
		award.setDescription(award.getDescription());
		award.setTitle(award.getTitle());;
		Appreciation savedAward=this.awardRepository.save(award);
		return mapToDto(savedAward);
	}

	@Override
	public List<AwardDto> getAllAppreciation(Long userProfileId) {
		UserProfile userProfile=this.userProfileRepository.findById(userProfileId).orElseThrow(()->new ResourceNotFoundException("user-profile",userProfileId));
		List<Appreciation> awards=userProfile.getAppreciations();
		List<AwardDto> awardDtos=awards.stream().map(award->mapToDto(award)).collect(Collectors.toList());
		return awardDtos;
	}

	@Override
	public void deleteAppreciation(Long awardId) {
		// TODO Auto-generated method stub
		
	}
	
	private Appreciation mapToEntity(AwardDto awardDto) {
		Appreciation award= new Appreciation();
		award.setTitle(awardDto.getTitle());
		award.setDescription(awardDto.getDescription());
		award.setPosition(awardDto.getPosition());
		award.setDate(awardDto.getDate());
		return award;
	}
	private AwardDto mapToDto(Appreciation award) {
		AwardDto awardDto= new AwardDto();
		awardDto.setId(award.getId());
		awardDto.setTitle(award.getTitle());
		awardDto.setDescription(award.getDescription());
		awardDto.setPosition(award.getPosition());
		awardDto.setDate(award.getDate());
		return awardDto;
	}

}
