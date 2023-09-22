/**
 * 
 */
package com.alok91340.gethired.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alok91340.gethired.dto.AwardDto;
import com.alok91340.gethired.service.AppreciationService;

/**
 * @author alok91340
 *
 */
@RestController
@RequestMapping("api/hireQuest")
public class AppreciationController {
	
	@Autowired
	private AppreciationService awardService;
	
	
	@PostMapping("{userProfileId}/add-award")
	public ResponseEntity<AwardDto> addAward(@PathVariable Long userProfileId, @RequestBody AwardDto awardDto){
		
		AwardDto result=this.awardService.addAppreciation(awardDto, userProfileId);
		return ResponseEntity.ok(result);
	}

}
