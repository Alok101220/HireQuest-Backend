/**
 * 
 */
package com.alok91340.gethired.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alok91340.gethired.dto.ImageDto;
import com.alok91340.gethired.entities.Image;
import com.alok91340.gethired.service.ImageService;

/**
 * @author aloksingh
 *
 */

@RestController
@RequestMapping("api/hireQuest")
public class ImageController {
	
	@Autowired
	private ImageService imageService;
	
	@PostMapping("/{username}/upload-image")
	private ResponseEntity<Image> uploadImage(@PathVariable String username, @RequestParam("file") MultipartFile file){
		
		Image image=null;
		try {
			image = this.imageService.saveImage(username, file);
		} catch (SerialException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(image,HttpStatus.OK);
		
	
	}

}
