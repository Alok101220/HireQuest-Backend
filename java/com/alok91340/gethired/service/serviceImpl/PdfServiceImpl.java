/**
 * 
 */
package com.alok91340.gethired.service.serviceImpl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alok91340.gethired.dto.PdfDto;
import com.alok91340.gethired.entities.UserProfile;
import com.alok91340.gethired.exception.ResourceNotFoundException;
import com.alok91340.gethired.repository.PdfRepository;
import com.alok91340.gethired.repository.UserProfileRepository;
import com.alok91340.gethired.service.PdfService;
import com.alok91340.gethired.entities.Pdf;

/**
 * @author aloksingh
 *
 */
@Service
public class PdfServiceImpl implements PdfService{
	
	@Autowired
	private UserProfileRepository userProfileRepository;
	
	@Autowired
	private PdfRepository pdfRepository;

	@Override
	public PdfDto addPdf(Long userProfileId, MultipartFile file) {
		
		UserProfile userProfile=this.userProfileRepository.findById(userProfileId).orElseThrow(()->new ResourceNotFoundException("user-profile",userProfileId));
		Pdf pdf= new Pdf();
		String fileName = file.getOriginalFilename();
        
		try {
			byte[] fileData;
			fileData = file.getBytes();
			
			 pdf.setFileName(fileName);
		     pdf.setData(fileData);
			Pdf savedPdf=this.pdfRepository.save(pdf);
		     userProfile.getPdfs().add(savedPdf);
			return mapToDto(savedPdf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
       
        
		
	}

	/**
	 * @param savedPdf
	 * @return
	 */
	private PdfDto mapToDto(Pdf savedPdf) {
		PdfDto pdfDto= new PdfDto();
		pdfDto.setId(savedPdf.getId());
		pdfDto.setFileName(savedPdf.getFileName());
		pdfDto.setData(savedPdf.getData());
		return pdfDto;
	}

	@Override
	public List<PdfDto> getAllPdf(Long userProfileId) {
		UserProfile userProfile=this.userProfileRepository.findById(userProfileId).orElseThrow(()->new ResourceNotFoundException("user-profile",userProfileId));
		List<Pdf> pdfs=userProfile.getPdfs();
		List<PdfDto>pdfDtos=pdfs.stream().map(pdf->mapToDto(pdf)).collect(Collectors.toList());
		return pdfDtos;
	}

	@Override
	public PdfDto getPdf(Long pdfId) {
		Pdf pdf=this.pdfRepository.findById(pdfId).orElseThrow(()->new ResourceNotFoundException("pdf",pdfId));
		
		return mapToDto(pdf);
	}

	@Override
	public void deletePdf(Long pdfId) {
		Pdf pdf=this.pdfRepository.findById(pdfId).orElseThrow(()->new ResourceNotFoundException("pdf",pdfId));
		this.pdfRepository.delete(pdf);
		
	}

}
