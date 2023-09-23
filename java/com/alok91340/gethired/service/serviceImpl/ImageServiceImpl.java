/**
 * 
 */
package com.alok91340.gethired.service.serviceImpl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alok91340.gethired.service.ImageService;
import com.alok91340.gethired.utils.ImageUtils;
import com.alok91340.gethired.entities.Image;
import com.alok91340.gethired.entities.User;
import com.alok91340.gethired.exception.ResourceNotFoundException;
import com.alok91340.gethired.repository.ImageRepository;
import com.alok91340.gethired.repository.UserRepository;

/**
 * @author alok91340
 *
 */
@Service
public class ImageServiceImpl implements ImageService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ImageRepository imageRepository;

	@Override
	public Image saveImage(String userId, MultipartFile file) {
		Image image=uploadImage(file);
		User user=this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user",(long)0));
		image.setUser(user);
		
		Image savedImage=this.imageRepository.save(image);
		
		return savedImage;
	}

	
	@Override
	public Image saveImage(Long imageId) {
		
		return null;
	}
	
	/**
	 * @param file
	 */
	private Image uploadImage(MultipartFile file) {
		Image imageData = new Image();
		imageData.setName(file.getName());
		imageData.setType(file.getContentType());
		try {
			imageData.setData(ImageUtils.compressImage(file.getBytes()));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return imageData;
		
	}

}
