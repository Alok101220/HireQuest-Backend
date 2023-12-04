/**
 * 
 */
package com.alok91340.gethired.controller;

import java.util.List;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayInputStream;

import com.alok91340.gethired.dto.PdfDto;
import com.alok91340.gethired.repository.ImageRepository;
import com.alok91340.gethired.repository.PdfRepository;
import com.alok91340.gethired.service.PdfService;

import lombok.extern.java.Log;

/**
 * @author aloksingh
 *
 */
@RestController
@RequestMapping("api/hireQuest")
public class PdfController {
	
	@Autowired
	private PdfService pdfService;
	
	@Autowired
	private PdfRepository pdfRepository;
	
	@Autowired 
	private ImageRepository imageRepository;
	
	
	@PostMapping("/{userProfileId}/add-pdf")
	public ResponseEntity<PdfDto> addPdf(@PathVariable Long userProfileId, @RequestParam("file") MultipartFile file){
		PdfDto pdf=this.pdfService.addPdf(userProfileId, file);
		
		return new ResponseEntity<>(pdf,HttpStatus.OK);
	}
	
	@GetMapping("/{userProfileId}/get-pdfs")
	public ResponseEntity<List<PdfDto>> getAllPdf(@PathVariable Long userProfileId){
		List<PdfDto> pdfs=this.pdfService.getAllPdf(userProfileId);
		return new ResponseEntity<>(pdfs,HttpStatus.OK);
	}
	
	@GetMapping("/{pdfId}/get-pdf")
	public ResponseEntity<String> getPdf(@PathVariable Long pdfId){
		String pdf=this.imageRepository.findById(pdfId).orElseThrow(null).getData().toString();
		return new ResponseEntity<>(pdf,HttpStatus.OK);
	}
	
	@DeleteMapping("/{pdfId}/delete-pdf")
	public void deletePdf(@PathVariable Long pdfId){
		this.pdfService.deletePdf(pdfId);
	}

	
	@GetMapping("/{pdfId}/download-pdf")
    public ResponseEntity<Resource> downloadPdf(@PathVariable Long pdfId) {
        // Retrieve the PDF content by its name
        byte[] pdfContent = this.pdfRepository.findById(pdfId).orElseThrow(null).getFileData();


        // Convert byte array to InputStreamResource
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(pdfContent);
        InputStreamResource resource = new InputStreamResource(byteArrayInputStream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }

}
