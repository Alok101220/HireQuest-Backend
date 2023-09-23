/**
 * 
 */
package com.alok91340.gethired.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alok91340.gethired.dto.PdfDto;
import com.alok91340.gethired.service.PdfService;

/**
 * @author aloksingh
 *
 */
@RestController
@RequestMapping("api/hireQuest")
public class PdfController {
	
	@Autowired
	private PdfService pdfService;
	
	
	@PostMapping("/{userProfileId}/add-pdf")
	public ResponseEntity<PdfDto> addPdf(@PathVariable Long userProfileId, @RequestParam("file") MultipartFile file){
		PdfDto pdfDto=this.pdfService.addPdf(userProfileId, file);
		if(pdfDto==null) {
			return new ResponseEntity<>(pdfDto,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(pdfDto,HttpStatus.OK);
	}
	
	@GetMapping("/{userProfileId}/get-pdfs")
	public ResponseEntity<List<PdfDto>> getAllPdf(@PathVariable Long userProfileId){
		List<PdfDto> pdfs=this.pdfService.getAllPdf(userProfileId);
		return new ResponseEntity<>(pdfs,HttpStatus.OK);
	}
	
	@GetMapping("/{pdfId}/get-pdf")
	public ResponseEntity<PdfDto> getPdf(@PathVariable Long pdfId){
		PdfDto pdf=this.pdfService.getPdf(pdfId);
		return new ResponseEntity<>(pdf,HttpStatus.OK);
	}
	
	@DeleteMapping("{userId}/delete-pdf")
	public void deletePdf(@PathVariable Long pdfId){
		this.pdfService.deletePdf(pdfId);
	}

}
