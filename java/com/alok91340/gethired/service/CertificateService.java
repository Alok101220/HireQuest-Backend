/**
 * 
 */
package com.alok91340.gethired.service;

import java.util.Set;

import com.alok91340.gethired.dto.CertificateDto;

/**
 * @author alok91340
 *
 */
public interface CertificateService {
	
	CertificateDto addCertificate(CertificateDto certificateDto, Long userProfileId);
	CertificateDto updateCertificate(CertificateDto certificateDto, Long certificateId);
	Set<CertificateDto> getAllCertificate(Long userProfileId);
	void deleteCertificate(Long certificateId);
	CertificateDto getCertificate(Long certificateDto);
}
