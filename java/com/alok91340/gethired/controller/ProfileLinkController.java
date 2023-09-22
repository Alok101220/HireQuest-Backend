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
import com.alok91340.gethired.dto.ProfileDto;
import com.alok91340.gethired.service.ProfileLinkService;

/**
 * @author alok91340
 *
 */
@RestController
@RequestMapping("api/gethired")
public class ProfileLinkController {
	
	@Autowired
	private ProfileLinkService profileLinkService;
	
	@PostMapping("{userProfileId}/add-profile-link")
	public ResponseEntity<ProfileDto> addProfileLink(@PathVariable Long userProfileId, @RequestBody ProfileDto profileLinkDto){
		ProfileDto result=this.profileLinkService.addProfileLink(profileLinkDto, userProfileId);
		return ResponseEntity.ok(result);
	}
	@PutMapping("{profileLinkId}/update-profile-link")
	public ResponseEntity<ProfileDto> updateProfileLink(@PathVariable Long profileLinkId,@RequestBody ProfileDto profileLinkDto){
		ProfileDto result=this.profileLinkService.updateProfileLink(profileLinkDto, profileLinkId);
		return new ResponseEntity<>(result,HttpStatus.OK);
	}
	
	@GetMapping("{userProfileId}/profile-links")
	public ResponseEntity<Set<ProfileDto>> getAllProfileLink(@PathVariable Long userProfileId){
		Set<ProfileDto> languageDtos=this.profileLinkService.getAllProfileLink(userProfileId);
		return new ResponseEntity<>(languageDtos,HttpStatus.OK);
	}
	@DeleteMapping("{profileLinkId}/delete-profile-link")
	public ResponseEntity<String> deleteProfileLink(@PathVariable Long profileLinkId){
		this.profileLinkService.deleteProfileLink(profileLinkId);
		return new ResponseEntity<>("deleted",HttpStatus.OK);
	}

}
