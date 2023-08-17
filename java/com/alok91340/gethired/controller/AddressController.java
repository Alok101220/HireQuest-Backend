/**
 * 
 */
package com.alok91340.gethired.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alok91340.gethired.dto.AddressDto;
import com.alok91340.gethired.service.AddressService;

/**
 * @author alok91340
 *
 */

@Controller
@RequestMapping("api/gethired")
public class AddressController {
	
	@Autowired
	private AddressService addressService;
	
	@PostMapping("{userId}/add-address")
	public ResponseEntity<AddressDto> addAddress(@PathVariable Long userId, @RequestBody AddressDto addressDto){
		AddressDto result= this.addressService.addAddress(addressDto, userId);
		return ResponseEntity.ok(result);
	}
}
