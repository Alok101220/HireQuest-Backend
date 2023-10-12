/**
 * 
 */
package com.alok91340.gethired.service;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialException;

import org.springframework.web.multipart.MultipartFile;

import com.alok91340.gethired.dto.ImageDto;
import com.alok91340.gethired.entities.Image;

/**
 * @author alok91340
 *
 */
public interface ImageService {
	
	Image saveImage(String username,MultipartFile file) throws SerialException, SQLException, IOException;
//	Image saveImage(Long imageId);
}
