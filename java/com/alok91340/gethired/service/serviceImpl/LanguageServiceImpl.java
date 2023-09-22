/**
 * 
 */
package com.alok91340.gethired.service.serviceImpl;


import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alok91340.gethired.dto.LanguageDto;
import com.alok91340.gethired.entities.Language;
import com.alok91340.gethired.entities.UserProfile;
import com.alok91340.gethired.exception.ResourceNotFoundException;
import com.alok91340.gethired.repository.LanguageRepository;
import com.alok91340.gethired.repository.UserProfileRepository;
import com.alok91340.gethired.service.LanguageService;

/**
 * @author alok91340
 *
 */
@Service
public class LanguageServiceImpl implements LanguageService{
	
	@Autowired
	private UserProfileRepository userProfileRepository;
	
	@Autowired
	private LanguageRepository languageRepository;

	@Override
	public LanguageDto addLanguage(LanguageDto languageDto, Long userProfileId) {
		UserProfile userProfile=this.userProfileRepository.findById(userProfileId).orElseThrow(()->new ResourceNotFoundException("user-profile",userProfileId));
		Language language= new Language();
		mapToEntity(languageDto,language);
		language.setUserProfile(userProfile);
		Language savedLanguage= this.languageRepository.save(language);
		return mapToDto(savedLanguage);
	}
	
	@Override 
	public LanguageDto getLanguage(Long languageId) {
		Language language=this.languageRepository.findById(languageId).orElseThrow(()->new ResourceNotFoundException("Language",languageId));
		return mapToDto(language);
		
	}

	@Override
	public LanguageDto updateLanguage(LanguageDto languageDto, Long languageId) {
		Language language=this.languageRepository.findById(languageId).orElseThrow(()->new ResourceNotFoundException("Language",languageId));
		mapToEntity(languageDto,language);
		Language savedLanguage=this.languageRepository.save(language);
		return mapToDto(savedLanguage);
	}

	@Override
	public Set<LanguageDto> getAllLanguage(Long userProfileId) {
		UserProfile userProfile=this.userProfileRepository.findById(userProfileId).orElseThrow(()->new ResourceNotFoundException("User-Profile",userProfileId));
		Set<Language> languages=userProfile.getLanguages();
		Set<LanguageDto>languageDtos=languages.stream().map(language->mapToDto(language)).collect(Collectors.toSet());
		return languageDtos;
	}

	@Override
	public void deleteLanguage(Long languageId) {
		Language language=this.languageRepository.findById(languageId).orElseThrow(()->new ResourceNotFoundException("Language",languageId));
		this.languageRepository.delete(language);
		
	}
	
	Language mapToEntity(LanguageDto languageDto, Language language) {
		
		language.setName(languageDto.getName());
		return language;
	}
	
	LanguageDto mapToDto(Language language) {
		LanguageDto languageDto= new LanguageDto();
		languageDto.setId(language.getId());
		languageDto.setName(language.getName());
		return languageDto;
	}

}
