/**
 * 
 */
package com.alok91340.gethired.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alok91340.gethired.utils.Constant;
import com.alok91340.gethired.utils.isAuthenticatedAsAdminOrUser;
import com.alok91340.gethired.dto.LoginDto;
import com.alok91340.gethired.dto.RegistrationResponse;
import com.alok91340.gethired.dto.Response;
import com.alok91340.gethired.dto.UserDto;
import com.alok91340.gethired.entities.User;
import com.alok91340.gethired.exception.GlobalExceptionHandler;
import com.alok91340.gethired.exception.ResourceNotFoundException;
import com.alok91340.gethired.repository.UserRepo;
import com.alok91340.gethired.security.JwtAuthResponse;
import com.alok91340.gethired.security.JwtTokenProvider;
import com.alok91340.gethired.service.UserService;

/**
 * @author alok91340
 *
 */
@Controller
@RequestMapping("api/gethired")
public class UserController {
	
	private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private JwtTokenProvider tokenProvider;
    
    

    public UserController(UserService userService, AuthenticationManager authenticationManager,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
	
    @Autowired
    private UserRepo userRepo;
//	get user by id
    @isAuthenticatedAsAdminOrUser
	@GetMapping("/user/{userId}")
	public ResponseEntity<?> getUser(@AuthenticationPrincipal Authentication authentication,@PathVariable Long userId){
		
//			UserDto userDto= userService.getUser(userId);
		UserDto user= this.userService.getUser(userId);
		
			return new ResponseEntity<>(user,HttpStatus.OK);
		
	}
	
//	get all users
	@GetMapping("/users")
	public ResponseEntity<List<UserDto>> getUsers(
			@RequestParam(value = "pageNo", defaultValue = Constant.DEFAULT_PAGE_NUMBER) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = Constant.DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = Constant.DEFAULT_SORT_BY) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = Constant.DEFAULT_SORT_DIRECTION) String sortDir
			){
		
		return ResponseEntity.ok(userService.getAllUser(pageNo,pageSize,sortBy,sortDir));
	}
	
//	create user
	@PostMapping("create-user")
	public ResponseEntity<RegistrationResponse> createUser(@RequestBody UserDto userDto){

		RegistrationResponse response=new RegistrationResponse();
		// check for userName exists in DB
        if (userRepo.existsByUsername(userDto.getUsername())) {
        	response.setMessage("Username already exists");
            return new ResponseEntity<>(response,
                    HttpStatus.BAD_REQUEST);
        }
        if (userRepo.existsByEmail(userDto.getEmail())) {
        	response.setMessage("Email already exists");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
		UserDto result=userService.createUser(userDto);
		response.setMessage("User is successfully registered");
		return new ResponseEntity<>(response,HttpStatus.OK);
		
	}
	
//	authenticate
	@PostMapping(value="login", produces = "application/json")
    public ResponseEntity<JwtAuthResponse> authenticateUser(@RequestBody LoginDto loginDto) throws Exception {

        authenticate(loginDto.getUsername(),

                loginDto.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(loginDto.getUsername());

        final String token = tokenProvider.generateToken(userDetails);
        
        User user=this.userRepo.findByUsername(loginDto.getUsername()).orElseThrow(()-> new ResourceNotFoundException("user",(long)0));
        user.setFcmToken(loginDto.getFcmToken());
        this.userRepo.save(user);
        return ResponseEntity.ok(new JwtAuthResponse(token));
    }

	
	
//	update user
	@PutMapping("{userId}/update-user")
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto,@PathVariable Long userId){
		UserDto updatedUserDto= userService.updateUser(userId, userDto);
		return ResponseEntity.ok(updatedUserDto);
	}
	
//	delete user
	@DeleteMapping("/delete-user/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable Long userId){
		userService.deleteUser(userId);
		return ResponseEntity.ok("Deleted user with id:"+userId);
	}
	
//	@GetMapping("{username}/{password}/{fcmToken}/user-login")
//	public ResponseEntity<UserDto> login(@PathVariable("username") String username,@PathVariable("password") String password,@PathVariable("fcmToken") String fcmToken) {
//		User user=this.userRepo.findUserByUsername(username);
//		user.setFcmToken(fcmToken);
//		User savedUser=this.userRepo.save(user);
//		Boolean isMatched = bCryptPasswordEncoder.matches(password, savedUser.getPassword());
//		if((user==null)||(!user.getUsername().equals(username)||!isMatched)) {
//			return new ResponseEntity<>(new UserDto(),HttpStatus.BAD_REQUEST);
//		}
//		UserDto userDto=mapToDto(savedUser);
//		
//		return new ResponseEntity<>(userDto,HttpStatus.OK);
//	}
	
	@GetMapping("{username}/check-username")
	public ResponseEntity<Boolean> checkUsername(@PathVariable String username){
		boolean isAvailable=this.userRepo.existsByUsername(username);
		return new ResponseEntity<>(isAvailable,HttpStatus.OK);
	}
	
	@GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String query) {
        List<User> users = userRepo.searchUsers(query);
        return ResponseEntity.ok(users);
    }
	
	@GetMapping("{token}/get-user-info")
	public ResponseEntity<UserDto> getUsername(@PathVariable String token){
		String username= this.tokenProvider.getUserNameFromToken(token);
		User user=this.userRepo.findByUsername(username).orElseThrow(()-> new ResourceNotFoundException("user",(long)0));
		UserDto userDto=this.mapToDto(user);
		return new ResponseEntity<>(userDto,HttpStatus.OK);
	}
	
	
public UserDto mapToDto(User user) {
		
	UserDto userDto= new UserDto();
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setUsername(user.getUsername());
		userDto.setEmail(user.getEmail());
		userDto.setPassword(user.getPassword());
		userDto.setHeadline(user.getHeadline());
		userDto.setIsRecuriter(user.getIsRecuriter());
		userDto.setIsRecuritie(user.getIsRecuritie());
		userDto.setStatus(user.isStatus());
		userDto.setBirthdate(user.getBirthdate());
		userDto.setCurrentCompany(user.getCurrentCompany());
		userDto.setPhone(user.getPhone());
		userDto.setFcmToken(user.getFcmToken());
		return userDto;
	}

private void authenticate(String username, String password) throws

Exception {

    Objects.requireNonNull(username);

    Objects.requireNonNull(password);

    try {

        authenticationManager.authenticate(new

        UsernamePasswordAuthenticationToken(username, password));
        System.out.println("hello");
    } catch (DisabledException e) {

        throw new Exception("USER_DISABLED", e);

    } catch (BadCredentialsException e) {

        throw new Exception("INVALID_CREDENTIALS", e);
    }
}
	
}