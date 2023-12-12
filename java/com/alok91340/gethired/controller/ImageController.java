/**
 * 
 */
package com.alok91340.gethired.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alok91340.gethired.dto.ImageDto;
import com.alok91340.gethired.entities.Image;
import com.alok91340.gethired.repository.ImageRepository;
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
	
	@Autowired 
	private ImageRepository imageRepository;
	
	@PostMapping("/upload-image")
	private ResponseEntity<ImageDto> uploadImage(@RequestParam("userId") Long userId, @RequestParam("file") MultipartFile file){
		
		ImageDto image=null;
		try {
			image = this.imageService.saveImage(userId, file);
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
	
	@GetMapping("/get-image")
	public ResponseEntity<ImageDto> getImage(@RequestParam("userId") Long userId){
		ImageDto image=this.imageService.getImage(userId);
		return ResponseEntity.ok(image);
	}
	
	@GetMapping("/{imageId}/download-image")
    public ResponseEntity<Resource> downloadPdf(@PathVariable Long imageId) {
        // Retrieve the PDF content by its name
        byte[] pdfContent = this.imageRepository.findById(imageId).orElseThrow(null).getData();

        // Convert byte array to InputStreamResource
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(pdfContent);
        InputStreamResource resource = new InputStreamResource(byteArrayInputStream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "")
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

}
