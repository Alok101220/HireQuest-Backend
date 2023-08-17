/**
 * 
 */
package com.alok91340.gethired.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.alok91340.gethired.dto.Response;
import com.alok91340.gethired.dto.UserDto;
import com.alok91340.gethired.dto.UserProfileDto;
import com.alok91340.gethired.entities.User;
import com.alok91340.gethired.entities.UserProfile;
import com.alok91340.gethired.exception.ResourceNotFoundException;
import com.alok91340.gethired.repository.UserProfileRepo;
import com.alok91340.gethired.repository.UserRepo;
import com.alok91340.gethired.service.UserProfileService;
import com.alok91340.gethired.utils.Constant;

/**
 * @author alok91340
 *
 */

@Controller
@RequestMapping("api/gethired")
public class StudentProfileController {
	static String path="http://localhost:8080/api/gethired";
	
	@Autowired
	private UserProfileService userProfileService;
	
	@Autowired
	private UserRepo userRepository;
	
	@Autowired
	private UserProfileRepo userProfileRepository;
	
//	get user by id
	@GetMapping("/{userProfileId}/user-profile")
	public ResponseEntity<UserProfile> getUser(@PathVariable Long userProfileId){
		UserProfile userProfile= this.userProfileRepository.findById(userProfileId).orElseThrow(()->new ResourceNotFoundException("user-profile",userProfileId));
		return ResponseEntity.ok(userProfile);
	}
	
//	get all users
	@GetMapping("/users-profile")
	public ResponseEntity<List<UserProfile>> getUsers(
			@RequestParam(value = "pageNo", defaultValue = Constant.DEFAULT_PAGE_NUMBER) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = Constant.DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = Constant.DEFAULT_SORT_BY) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = Constant.DEFAULT_SORT_DIRECTION) String sortDir
			){
		List<UserProfile> userProfile=userProfileService.getUsersProfile(pageNo,pageSize,sortBy,sortDir);
		
		return ResponseEntity.ok(userProfile);
	}
	
//	create user
	@PostMapping("/{userId}/create-user-profile")
	public ResponseEntity<?> createUser(@PathVariable Long userId,@RequestBody UserProfileDto userProfileDto){
		
		User user=userRepository.findUserById(userId);
		if(user.getSutdentprofile()!=null) {
			return new ResponseEntity<>(new Response(""," user-profile already exists",HttpStatus.ALREADY_REPORTED),HttpStatus.ALREADY_REPORTED);
		}
		
		UserProfileDto result=userProfileService.createStudentProfile(userProfileDto,userId);
		
		return new ResponseEntity<>(result,HttpStatus.OK);
	}
	
//	update user
	@PutMapping("/{userProfileId}/update-user-profile")
	public ResponseEntity<UserProfileDto> getUser(@RequestBody UserProfileDto userProfileDto,@PathVariable Long userProfileId){
		UserProfileDto result=this.userProfileService.updateUserProfile(userProfileDto, userProfileId);
		return ResponseEntity.ok(result);
	}
	
//	delete user
	@DeleteMapping("/{userProfileId}/delete-user-profile")
	public ResponseEntity<Response> deleteUser(@PathVariable Long userProfileId){
		this.userProfileService.deleteUserProfile(userProfileId);
		return null;
	}
	
	@GetMapping("{userId}/candidate-profile")
	public ResponseEntity<UserProfileDto> getUserProfileDto(@PathVariable("userId") Long userId){
		UserProfileDto result=this.userProfileService.getUserProfile(userId);
		return new ResponseEntity<>(result,HttpStatus.OK);
	}
	
}
