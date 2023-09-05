/**
 * 
 */
package com.alok91340.gethired.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alok91340.gethired.entities.Otp;
import com.alok91340.gethired.entities.User;

/**
 * @author aloksingh
 *
 */
public interface OtpRepository extends JpaRepository<Otp,Long>{
	Otp findOtpByEmail(String email);
}
