/**
 * 
 */
package com.alok91340.gethired.service.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.alok91340.gethired.dto.UserDto;
import com.alok91340.gethired.entities.User;
import com.alok91340.gethired.entities.UserProfile;
import com.alok91340.gethired.exception.ResourceNotFoundException;
import com.alok91340.gethired.repository.UserProfileRepo;
import com.alok91340.gethired.repository.UserRepo;
import com.alok91340.gethired.service.UserService;

/**
 * @author alok91340
 *
 */
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private UserProfileRepo userProfileRepository;
	
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public UserServiceImpl(UserRepo userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepo = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
	
	@Override
	public UserDto createUser(UserDto userDto) {
	
		User user=new User();
		mapToEntity(user,userDto);
		User savedUser=userRepo.save(user);
		
		return mapToDto(savedUser);
	}

	@Override
	public UserDto getUser(Long userId)throws ResourceNotFoundException {
		User user=userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user not available",userId));
		
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
	public UserDto updateUser(Long userId, UserDto userDto) {
	    User user = userRepo.findById(userId)
	                        .orElseThrow(() -> new ResourceNotFoundException("user", userId));

	    if (user.getSutdentprofile() == null && userDto.getIsRecuritie() == 1) {
	        UserProfile userProfile = new UserProfile();
	        userProfile.setCreatedAt(LocalDateTime.now());
	        userProfile.setCreatedBy(user.getCreatedBy());
	        userProfile.setUser(user);
	        UserProfile savedUserProfile = this.userProfileRepository.save(userProfile);
	        user.setSutdentprofile(savedUserProfile);
	        user.setIsRecuritie(1);
	    }
	    
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
		user.setIsRecuritie(userDto.getIsRecuritie());
		user.setCurrentCompany(userDto.getCurrentCompany());

	    user.setUpdatedAt(LocalDateTime.now());
	    user.setUpdatedBy(userDto.getUsername());

	    User savedUser = userRepo.save(user);
	    UserDto savedUserDto = mapToDto(savedUser);
	    return savedUserDto;
	}

	// ...


	@Override
	public String deleteUser(Long userId) {
		User user= this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user",userId));
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
		user.setIsRecuritie(userDto.getIsRecuritie());
		user.setCurrentCompany(userDto.getCurrentCompany());
		user.setFcmToken(userDto.getFcmToken());
		return user;
		
	}
	
	public UserDto mapToDto(User user) {
		
		UserDto userDto= new UserDto();
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setPassword(user.getPassword());
		userDto.setUsername(user.getUsername());
		userDto.setBirthdate(user.getBirthdate());
		userDto.setHeadline(user.getHeadline());
		userDto.setStatus(user.isStatus());
		userDto.setPhone(userDto.getPhone());
		userDto.setIsRecuriter(user.getIsRecuriter());
		userDto.setIsRecuritie(user.getIsRecuritie());
		userDto.setCurrentCompany(user.getCurrentCompany());
		userDto.setFcmToken(user.getFcmToken());
		return userDto;
	}
}
