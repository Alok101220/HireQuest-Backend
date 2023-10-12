/**
 * 
 */
package com.alok91340.gethired.service;

import java.util.List;

import com.alok91340.gethired.dto.RecentSearchDto;

/**
 * @author aloksingh
 *
 */
public interface RecentSearchService {
	
	RecentSearchDto addRecentSearch(RecentSearchDto recentSearchDto);
	
	void deleteRecentSearch(Long recentSearchId);
	
	List<RecentSearchDto> findLast8DistinctSearchesByUsername(String username);
	
	void deleteAllRecentSearch(String username);
	
	

}
