/**
 * 
 */
package com.alok91340.gethired.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alok91340.gethired.service.SkillService;
import com.alok91340.gethired.dto.*;

/**
 * @author alok91340
 *
 */
@RestController
@RequestMapping("api/hireQuest")
public class SkillController {
	
	@Autowired
	private SkillService skillService;
	
	@PostMapping("/{userProfileId}/add-skill")
	public ResponseEntity<SkillDto> addSkill(@RequestBody SkillDto skillDto, @PathVariable Long userProfileId){
		
		SkillDto result=skillService.addSkill(userProfileId, skillDto);
		return ResponseEntity.ok(result);
	}
	@GetMapping("/{skillId}/get-skill")
	public ResponseEntity<SkillDto> getSkill(@PathVariable Long skillId){
		SkillDto result=this.skillService.getSkill(skillId);
		return new ResponseEntity<>(result,HttpStatus.OK);
	}
	
	@GetMapping("/{userProfileId}/get-skills")
	public ResponseEntity<List<SkillDto>> getAllSkill(@PathVariable Long userProfileId){
		
		List<SkillDto> result=this.skillService.getAllSkill(userProfileId);
		return new ResponseEntity<>(result,HttpStatus.OK);
		
	}
	@PutMapping("/{skillId}/update-skill")
	public ResponseEntity<SkillDto> updateSkill(@RequestBody SkillDto skillDto, @PathVariable Long skillId){
		SkillDto result=skillService.updateSkill(skillId, skillDto);
		return ResponseEntity.ok(result);
	}
	
	@DeleteMapping("/{skillId}/delete-skill")
	public ResponseEntity<String> deleteSkill(@PathVariable Long skillId){
		skillService.deleteSkill(skillId);
		return ResponseEntity.ok("deleted");
	}
	

}
