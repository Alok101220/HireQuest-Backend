/**
 * 
 */
package com.alok91340.gethired.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alok91340.gethired.dto.LanguageDto;
import com.alok91340.gethired.service.LanguageService;

/**
 * @author alok91340
 *
 */
@RestController
@RequestMapping("api/gethired")
public class LanguageController {
	
	@Autowired
	private LanguageService languageService;
	
	@PostMapping("{userProfileId}/add-language")
	public ResponseEntity<LanguageDto> addLanguage(@PathVariable Long userProfileId, @RequestBody LanguageDto languageDto){
		LanguageDto result=this.languageService.addLanguage(languageDto, userProfileId);
		return ResponseEntity.ok(result);
	}
	
	@PutMapping("{languageId}/update-language")
	public ResponseEntity<LanguageDto> updateLanguage(@PathVariable Long languageId,@RequestBody LanguageDto languageDto){
		LanguageDto language=this.languageService.updateLanguage(languageDto, languageId);
		return new ResponseEntity<>(language,HttpStatus.OK);
	}
	
	@GetMapping("{userProfileId}/languages")
	public ResponseEntity<Set<LanguageDto>> getAllLanguage(@PathVariable Long userProfileId){
		Set<LanguageDto> languageDtos=this.languageService.getAllLanguage(userProfileId);
		return new ResponseEntity<>(languageDtos,HttpStatus.OK);
	}
	@DeleteMapping("{languageId}/delete-language")
	public ResponseEntity<String> deleteLanguage(@PathVariable Long languageId){
		this.languageService.deleteLanguage(languageId);
		return new ResponseEntity<>("deleted",HttpStatus.OK);
	}

}
