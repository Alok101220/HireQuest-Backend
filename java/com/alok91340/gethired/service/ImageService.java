/**
 * 
 */
package com.alok91340.gethired.service;

import org.springframework.web.multipart.MultipartFile;

import com.alok91340.gethired.entities.Image;

/**
 * @author alok91340
 *
 */
public interface ImageService {
	
	Image saveImage(Long userId,MultipartFile file);
	Image saveImage(Long imageId);
}
