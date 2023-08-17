/**
 * 
 */
package com.alok91340.gethired.service.serviceImpl;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.alok91340.gethired.dto.UserProfileDto;
import com.alok91340.gethired.entities.User;
import com.alok91340.gethired.entities.UserProfile;
import com.alok91340.gethired.exception.ResourceNotFoundException;
import com.alok91340.gethired.repository.UserProfileRepo;
import com.alok91340.gethired.service.UserProfileService;
import com.alok91340.gethired.repository.*;

/**
 * @author alok91340
 *
 */
@Service
public class UserProfileServiceImpl implements UserProfileService{
	
	
	
	@Autowired
	private UserProfileRepo userProfileRepo;
	
	@Autowired
	private UserRepo userRepository;
	
	@Override
	public UserProfileDto createStudentProfile(UserProfileDto userProfileDto, Long userId) {
		
		
		User user= userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user",userId));
		UserProfile userProfile= new UserProfile();
		userProfile.setUser(user);
		userProfile.setBio(userProfileDto.getBio());
		UserProfile savedUserProfile=userProfileRepo.save(userProfile);
		return mapToDto(savedUserProfile);
		
		
////		link address
//		linkAddress(userProfile,userProfileDto);
//		
////		link skill
//		linkSkill(userProfile, userProfileDto);
//		
////		link Project
//		linkProject(userProfile,userProfileDto);
//		
////		link Education
//		linkEducation(userProfile,userProfileDto);
//		
////		link Experience
//		linkExperience(userProfile,userProfileDto);
//		
////		link Image
////		Multiparts multipart= null;
////		linkImage(userProfile,userProfileDto,multipart);
//		
////		link certificates
//		linkCertificates(userProfile, userProfileDto);
//		
////		link ProjectLinks
//		linkProfileLinks(userProfile, userProfileDto);
//		
////		link Languages
//		linkLanguages(userProfile, userProfileDto);
//		
////		link awards
//		linkAward(userProfile, userProfileDto);
//		
//		userProfileRepo.save(userProfile);
//		return "";
	}

	@Override
	public UserProfileDto getUserProfile(Long userId) {
		User user=this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user",userId));
		UserProfile userProfile=user.getSutdentprofile();
		UserProfileDto userProfileDto= new UserProfileDto();
		userProfileDto.setId(userProfile.getId());
		userProfileDto.setBio(userProfile.getBio());
		
		return userProfileDto;
	}

	@Override
	public List<UserProfile> getUsersProfile(int pageNo, int pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<UserProfile> userProfiles = userProfileRepo.findAll(pageable);
		
		List<UserProfile> userProfileList=userProfiles.getContent();
		
		List<UserProfileDto> userProfileDtoList = userProfileList.stream()
                .map(userProfile -> mapToDto(userProfile))
                .collect(Collectors.toList());
		return userProfileList;
	}
	
	UserProfileDto mapToDto(UserProfile userProfile) {
		UserProfileDto userProfileDto= new UserProfileDto();
		userProfileDto.setId(userProfile.getId());
		userProfileDto.setBio(userProfile.getBio());
		return userProfileDto;
	}
	
	UserProfile mapToEntity(UserProfileDto userProfileDto) {
		UserProfile userProfile= new UserProfile();
		userProfile.setBio(userProfileDto.getBio());
		return userProfile;
		
		
		
	}
//	/**
//	 * @param userProfile
//	 * @param userProfileDto
//	 */
//	private void linkAddress(UserProfile userProfile, UserProfileDto userProfileDto) {
//		if(userProfileDto.getAddressDto()==null) {
//			return;
//		}
//		Address address = new Address();
//		address.setStreet(userProfileDto.getAddressDto().getStreet());
//		address.setState(userProfileDto.getAddressDto().getState());
//		address.setCountry(userProfileDto.getAddressDto().getCountry());
//		userProfile.setAddress(address);
//		
//	}
//	
//	private void linkSkill(UserProfile userProfile, UserProfileDto userProfileDto) {
//		if(userProfileDto.getSkillDtos()==null) {
//			return;
//		}
//		List<Skill> skills= new ArrayList<>();
//		for(SkillDto skill :userProfileDto.getSkillDtos()) {
//			Skill temp= new Skill();
//			
//			temp.setName(skill.getName());
//			temp.setType(skill.getType());
//			
//			skills.add(temp);
//		}
//		userProfile.setSkills(skills);
//		
//	}
//	
//	private void linkProject(UserProfile userProfile, UserProfileDto userProfileDto) {
//		if(userProfileDto.getProjectDtos()==null) {
//			return;
//		}
//		List<Project> projects= new ArrayList<>();
//		for(ProjectDto projectDto:userProfileDto.getProjectDtos()) {
//			Project project= new Project();
//			
//			project.setTitle(projectDto.getTitle());
//			project.setYear(projectDto.getYear());
//			project.setDescription(projectDto.getDescription());
//			project.setLink(projectDto.getLink());
//			
//			projects.add(project);
//		}
//		userProfile.setProjects(projects);
//		
//	}
//	
//	private void linkEducation(UserProfile userProfile, UserProfileDto userProfileDto) {
//		if(userProfileDto.getEducationDtos()==null) {
//			return;
//		}
//		List<Education> educations = new ArrayList<>();
//		for(EducationDto educationDto: userProfileDto.getEducationDtos()) {
//			Education education = new Education();
//			education = educationRepo.save(education);
//			education.setName(educationDto.getName());
//			education.setStartYear(educationDto.getStartYear());
//			education.setFinishyear(educationDto.getFinishyear());
//			education.setStandard(education.getStandard());
//			education.setStudentProfile(userProfile);
//			education = educationRepo.save(education);
//			educations.add(education);	
//			
//			}
//		userProfile.setEducations(educations);
//		
//	}
//	
//	private void linkExperience(UserProfile userProfile, UserProfileDto userProfileDto) {
//		// TODO Auto-generated method stub
//		if(userProfileDto.getExperienceDtos()==null) {
//			return;
//		}
//		List<Experience> experiences= new ArrayList<>();
//		for(ExperienceDto experienceDto:userProfileDto.getExperienceDtos()) {
//			Experience experience= new Experience();
//			experience.setTitle(experienceDto.getTitle());
//			experience.setLink(experienceDto.getLink());
//			experience.setDescription(experienceDto.getDescription());
//			
//			experiences.add(experience);
//		}
//		userProfile.setExperiences(experiences);
//		
//	}
//	
//	private void linkCertificates(UserProfile userProfile, UserProfileDto userProfileDto) {
//		if(userProfileDto.getCertificateDtos()==null) {
//			return;
//		}
//		List<Certificate> certificates = new ArrayList<>();
//		for(CertificateDto certificateDto:userProfileDto.getCertificateDtos()) {
//			Certificate certificate= new Certificate();
//			certificate.setTitle(certificateDto.getTitle());
//			certificate.setDescription(certificateDto.getDescription());
//			certificate.setYear(certificateDto.getYear());
//			
//			certificates.add(certificate);
//		}
//		userProfile.setCertificates(certificates);
//		
//	}
//	
//	private void linkProfileLinks(UserProfile userProfile, UserProfileDto userProfileDto) {
//		if(userProfileDto.getProfileLinkDtos()==null) {
//			return;
//		}
//		List<ProfileLink> profileLinks= new ArrayList<>();
//		for(ProfileLinkDto profileLinkDto:userProfileDto.getProfileLinkDtos()) {
//			ProfileLink profileLink= new ProfileLink();
//			profileLink.setHandleName(profileLinkDto.getHandleName());
//			profileLink.setLink(profileLinkDto.getLink());
//			profileLink.setStudentProfile(userProfile);
//			System.out.println("xcvghjkjhgvcxcvbn     "+profileLink.getHandleName());
//			profileLinks.add(profileLink);
////			profileLinkRepo.save(profileLink);
//		}
//		userProfile.setProfileLinks(profileLinks);
//	}
//	
//	private void linkLanguages(UserProfile userProfile, UserProfileDto userProfileDto) {
//		if(userProfileDto.getLanguageDtos()==null) {
//			return;
//		}
//		List<Language> languages= new ArrayList<>();
//		for(LanguageDto languageDto:userProfileDto.getLanguageDtos()) {
//			Language language= new Language();
//			language.setName(languageDto.getName());
//			language.setProficencyLevel(languageDto.getProficencyLevel());
//			language.setStudentProfile(userProfile);
//			
//			languages.add(language);
//		}
//		userProfile.setLanguages(languages);
//		
//	}
//	
//	private void linkAward(UserProfile userProfile, UserProfileDto userProfileDto) {
//		if(userProfileDto.getAwardDtos()==null) {
//			return;
//		}
//		List<Award> awards = new ArrayList<>();
//		for(AwardDto awardDto:userProfileDto.getAwardDtos()) {
//			Award award= new Award();
//			award.setTitle(awardDto.getTitle());
//			award.setYear(awardDto.getYear());
//			award.setDescription(awardDto.getDescription());
//			
//			awards.add(award);
//		}
//		userProfile.setAwards(awards);
//	}
//	
//	AddressDto linkToAddressDto(Address address) {
//		if(address==null) {
//			return null;
//		}
//		AddressDto addressDto= new AddressDto();
//		addressDto.setStreet(address.getCountry());
//		addressDto.setState(address.getState());
//		addressDto.setCountry(address.getCountry());
//		
//		return addressDto;
//	}
//	
//	List<SkillDto> mapToSkillDto(List<Skill> userSkills){
//		List<SkillDto> userSkillDtos= new ArrayList<>();
//		
//		if(userSkills==null) {
//			return userSkillDtos;
//		}
//		for(Skill skill :userSkills) {
//			SkillDto temp= new SkillDto();
//			temp.setName(skill.getName());
//			temp.setType(skill.getType());
//			
//			userSkillDtos.add(temp);
//		}
//		return userSkillDtos;
//	}

	@Override
	public UserProfileDto updateUserProfile(UserProfileDto userProfileDto, Long userProfileId) {
		UserProfile userProfile=this.userProfileRepo.findById(userProfileId).orElseThrow(()->new ResourceNotFoundException("user-profile",userProfileId));
		userProfile.setBio(userProfileDto.getBio());
		userProfile.setUpdatedAt(LocalDateTime.now());
		userProfile.setUpdatedBy(userProfile.getUser().getEmail());
		UserProfile savedUserProfile=this.userProfileRepo.save(userProfile);
		return mapToDto(savedUserProfile);
	}

	@Override
	public void deleteUserProfile(Long userProfileId) {
		// TODO Auto-generated method stub
		
	}

}
