/**
 * 
 */
package com.alok91340.gethired.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alok91340.gethired.entities.CandidateProfile;


/**
 * @author alok91340
 *
 */
public interface CandidateRepository extends JpaRepository<CandidateProfile,Long> {
	
}