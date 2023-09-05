/**
 * 
 */
package com.alok91340.gethired.controller;

import java.util.List;
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

import com.alok91340.gethired.dto.EducationDto;
import com.alok91340.gethired.service.EducationService;

/**
 * @author alok91340
 *
 */
@RestController
@RequestMapping("api/gethired")
public class EducationController {
	
	@Autowired
	private EducationService educationService;
	
	@PostMapping("/{userProfileId}/add-education")
	public ResponseEntity<EducationDto> addEducation(@PathVariable Long userProfileId, @RequestBody EducationDto educationDto){
		EducationDto result=this.educationService.addEducation(educationDto, userProfileId);
		return ResponseEntity.ok(result);
	}
	
	@PutMapping("update-education/{educationId}")
	public ResponseEntity<EducationDto> updateEducation(@RequestBody EducationDto educationDto,@PathVariable Long educationId){
		EducationDto result=this.educationService.updateEducation(educationDto, educationId);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/{userProfileId}/educations")
	public ResponseEntity<Set<EducationDto>> getEducation(@PathVariable Long userProfileId){
		Set<EducationDto> educationDtos=this.educationService.getAllEducation(userProfileId);
		return new ResponseEntity<>(educationDtos,HttpStatus.OK);
	}
	
	@DeleteMapping("{educationId}/education")
	public ResponseEntity<String> deleteEducation(@PathVariable Long educationId){
		this.educationService.deleteEducation(educationId);
		return ResponseEntity.ok("deleted");
	}
}
