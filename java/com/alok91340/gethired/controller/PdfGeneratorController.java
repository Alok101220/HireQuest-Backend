/**
 * 
 */
package com.alok91340.gethired.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alok91340.gethired.repository.PdfGeneratorRepository;
import com.alok91340.gethired.entities.PdfGenerator;

/**
 * @author aloksingh
 *
 */
@RestController
@RequestMapping("api/hireQuest")
public class PdfGeneratorController {
	
	@Autowired
	private PdfGeneratorRepository pdfGeneratorRepository ;
	
	
	@PostMapping("/generate-pdf")
    public ResponseEntity<String> generateAndStorePdf(@RequestBody Map<String, String> userInputMap) {
        String userInput = userInputMap.get("name");
        
        // Generate PDF using iText or another library
        // Store PDF content in database
       PdfGenerator pdfGenerator=new PdfGenerator();
       pdfGenerator.setName(userInput);
        pdfGeneratorRepository.save(pdfGenerator);
        
        return ResponseEntity.ok("PDF generated and stored.");
    }
	
	@GetMapping("/{pdfId}/view")
    public ResponseEntity<byte[]> viewPdf(@PathVariable Long pdfId) {
        Optional<PdfGenerator> optionalPdf = pdfGeneratorRepository.findById(pdfId);
        
        if (optionalPdf.isPresent()) {
        	PdfGenerator pdfDocument = optionalPdf.get();
            byte[] pdfContent = pdfDocument.getName().getBytes(); // Convert to bytes
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("inline", "document.pdf");
            
            return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
