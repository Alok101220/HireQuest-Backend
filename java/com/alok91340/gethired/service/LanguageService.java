/**
 * 
 */
package com.alok91340.gethired.service;

import java.util.List;

import com.alok91340.gethired.dto.LanguageDto;

/**
 * @author alok91340
 *
 */
public interface LanguageService {
	
	LanguageDto addLanguage(LanguageDto languageDto, Long userProfileId);
	LanguageDto updateLanguage(LanguageDto languageDto, Long languageId);
	List<LanguageDto> getAllLanguage(Long userProfileId);
	void deleteLanguage(Long languageId);
	LanguageDto getLanguage(Long languageId);
}
