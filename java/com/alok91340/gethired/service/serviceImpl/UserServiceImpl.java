/**
 * 
 */
package com.alok91340.gethired.service.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.alok91340.gethired.dto.UserDto;
import com.alok91340.gethired.entities.User;
import com.alok91340.gethired.exception.ResourceNotFoundException;
import com.alok91340.gethired.repository.UserProfileRepository;
import com.alok91340.gethired.repository.UserRepository;
import com.alok91340.gethired.service.UserService;
import com.alok91340.gethired.entities.UserProfile;

/**
 * @author alok91340
 *
 */
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UserProfileRepository userProfileRepository;
	
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepo = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
	
	@Override
	public UserDto createUser(UserDto userDto) {
	
		User user=new User();
		mapToEntity(user,userDto);
		User savedUser=userRepo.save(user);
		
		UserProfile userProfile=new UserProfile();
		userProfile.setUser(savedUser);
		userProfile.setCreatedAt(LocalDateTime.now());
		this.userProfileRepository.save(userProfile);
		
		return mapToDto(savedUser);
	}

	@Override
	public UserDto getUser(String username)throws ResourceNotFoundException {
		User user=userRepo.findById(username).orElseThrow(()-> new ResourceNotFoundException("user not available",(long)0));
		
		return mapToDto(user);
	}

	@Override
	public List<UserDto> getAllUser(int pageNo, int pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<User> users = userRepo.findAll(pageable);
        
        List<User> userList= users.getContent();
        List<UserDto> userDtoList = userList.stream()
                .map(user -> mapToDto(user))
                .collect(Collectors.toList());
		return userDtoList;
	}

	// ...

	@Override
	public UserDto updateUser(String username, UserDto userDto) {
	    User user = userRepo.findById(username)
	                        .orElseThrow(() -> new ResourceNotFoundException("user", (long)0));
	    
	    user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setUsername(userDto.getUsername());
		user.setCreatedAt(LocalDateTime.now());
		user.setCreatedBy(userDto.getUsername());
		user.setBirthdate(userDto.getBirthdate());
		user.setHeadline(userDto.getHeadline());
		user.setStatus(userDto.isStatus());
		user.setPhone(userDto.getPhone());
		user.setIsRecuriter(userDto.getIsRecuriter());
		user.setCurrentOccupation(userDto.getCurrentOccupation());

	    user.setUpdatedAt(LocalDateTime.now());
	    user.setUpdatedBy(userDto.getUsername());

	    User savedUser = userRepo.save(user);
	    UserDto savedUserDto = mapToDto(savedUser);
	    return savedUserDto;
	}

	// ...


	@Override
	public String deleteUser(String username) {
		User user= this.userRepo.findById(username).orElseThrow(()->new ResourceNotFoundException("user",(long)0));
		this.userRepo.delete(user);
		return null;
	}
	
	/**
	 * @param user
	 * @param userDto
	 */
	public User mapToEntity(User user,UserDto userDto) {
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		String hashedPassword = bCryptPasswordEncoder.encode(userDto.getPassword());
		user.setPassword(hashedPassword);
		user.setUsername(userDto.getUsername());
		user.setCreatedAt(LocalDateTime.now());
		user.setCreatedBy(userDto.getUsername());
		user.setBirthdate(userDto.getBirthdate());
		user.setHeadline(userDto.getHeadline());
		user.setStatus(userDto.isStatus());
		user.setPhone(userDto.getPhone());
		user.setIsRecuriter(userDto.getIsRecuriter());
		user.setCurrentOccupation(userDto.getCurrentOccupation());
		user.setFcmToken(userDto.getFcmToken());
		return user;
		
	}
	
	public UserDto mapToDto(User user) {
		
		UserDto userDto= new UserDto();
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setPassword(user.getPassword());
		userDto.setUsername(user.getUsername());
		userDto.setBirthdate(user.getBirthdate());
		userDto.setHeadline(user.getHeadline());
		userDto.setStatus(user.isStatus());
		userDto.setPhone(userDto.getPhone());
		userDto.setIsRecuriter(user.getIsRecuriter());
		userDto.setCurrentOccupation(user.getCurrentOccupation());
		userDto.setFcmToken(user.getFcmToken());
		return userDto;
	}

	@Override
	public UserDto updatePassword(String email,String password) {
		User user=this.userRepo.findUserByEmail(email);
		String hashedPassword = bCryptPasswordEncoder.encode(password);
		user.setPassword(hashedPassword);
		User updatedUser=this.userRepo.save(user);
		
		return mapToDto(updatedUser);
	}
}
