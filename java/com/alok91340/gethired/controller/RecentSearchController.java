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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alok91340.gethired.dto.RecentSearchDto;
import com.alok91340.gethired.service.RecentSearchService;

/**
 * @author aloksingh
 *
 */

@RestController
@RequestMapping("api/hireQuest")
public class RecentSearchController {
	
	@Autowired
	private RecentSearchService recentSearchService;
	
	@PostMapping("/add-recentSearch")
	public ResponseEntity<RecentSearchDto> addRecentSearch(@RequestBody RecentSearchDto recentSearchDto){
		
		RecentSearchDto recentSearch=this.recentSearchService.addRecentSearch(recentSearchDto);
		return new ResponseEntity<>(recentSearch,HttpStatus.OK);
		
	}
	
	@GetMapping("/{username}/get-recentSearches")
	public ResponseEntity<List<RecentSearchDto>> getAllRecentSearch(@PathVariable String username){
		
		List<RecentSearchDto> recentSearches=this.recentSearchService.findLast8DistinctSearchesByUsername(username);
		return ResponseEntity.ok(recentSearches);
	}
	
	@DeleteMapping("/{recentSearchId}/delete-recentSearch")
	public ResponseEntity<String> deleteRecentSearch(@PathVariable Long recentSearchId){
		
		this.recentSearchService.deleteRecentSearch(recentSearchId);
		return ResponseEntity.ok("deleted");
	}
	
	@DeleteMapping("/{username}/delete-recentSearches")
	public ResponseEntity<String> deleteRecentSearch(@PathVariable String username){
		
		this.recentSearchService.deleteAllRecentSearch(username);
		return ResponseEntity.ok("deleted");
	}

}
