/**
 * 
 */
package com.alok91340.gethired.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.alok91340.gethired.dto.AwardDto;
import com.alok91340.gethired.service.AwardService;

/**
 * @author alok91340
 *
 */
@Controller
public class AwardController {
	
	@Autowired
	private AwardService awardService;
	
	
	@PostMapping("{userProfileId}/add-award")
	public ResponseEntity<AwardDto> addAward(@PathVariable Long userProfileId, @RequestBody AwardDto awardDto){
		
		AwardDto result=this.awardService.addAward(awardDto, userProfileId);
		return ResponseEntity.ok(result);
	}

}
