/**
 * 
 */
package com.alok91340.gethired.service.serviceImpl;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alok91340.gethired.service.ImageService;
import com.alok91340.gethired.utils.ImageUtils;
import com.alok91340.gethired.dto.ImageDto;
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
        Image image = uploadImage(file);
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", (long) 0));

        Image img = this.imageRepository.findImageByUser(user);
        if (img != null) {
            try {
				img.setData((file.getBytes()));
			} catch (IOException e) {
				// TODO Auto-generated catch block 
				e.printStackTrace();
			}
            Image updatedImage = this.imageRepository.save(img);
//            updatedImage.setData(ImageUtils.decompressImage(updatedImage.getData()));
            return updatedImage;
        } else {
            image.setUser(user);

            Image savedImage = this.imageRepository.save(image);
            savedImage.setData(ImageUtils.decompressImage(savedImage.getData()));
            return savedImage;
        }
    } 

    private Image uploadImage(MultipartFile file) {
        Image imageData = new Image();
        imageData.setName(file.getOriginalFilename()); // Use original filename
        imageData.setType(file.getContentType());
        try {
			imageData.setData(file.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return imageData;
    }

   


}
