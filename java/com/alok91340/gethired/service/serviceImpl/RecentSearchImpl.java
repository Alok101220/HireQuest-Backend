/**
 * 
 */
package com.alok91340.gethired.service.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alok91340.gethired.dto.RecentSearchDto;
import com.alok91340.gethired.entities.RecentSearch;
import com.alok91340.gethired.repository.RecentSearchRepository;
import com.alok91340.gethired.service.RecentSearchService;

/**
 * @author aloksingh
 *
 */
@Service
public class RecentSearchImpl implements RecentSearchService{
	
	@Autowired
	private RecentSearchRepository recentSearchRepository;

	@Override
	public RecentSearchDto addRecentSearch(RecentSearchDto recentSearchDto) {
		RecentSearch recentSearch= new RecentSearch();
		recentSearch.setSearchedText(recentSearchDto.getSearchedText());
		recentSearch.setUsername(recentSearchDto.getUsername());
		RecentSearch savedRecentSearch=this.recentSearchRepository.save(recentSearch);
		return mapToDto(savedRecentSearch);
	}

	/**
	 * @param savedRecentSearch
	 * @return
	 */
	private RecentSearchDto mapToDto(RecentSearch recentSearch) {
		RecentSearchDto recentSearchDto= new RecentSearchDto();
		recentSearchDto.setId(recentSearch.getId());
		recentSearchDto.setSearchedText(recentSearch.getSearchedText());
		recentSearchDto.setUsername(recentSearch.getUsername());
		return recentSearchDto;
	}

	@Override
	public void deleteRecentSearch(Long recentSearchId) {
		RecentSearch recentSearch=this.recentSearchRepository.findById(recentSearchId).orElseThrow();
		this.recentSearchRepository.delete(recentSearch);
		
	}

	@Override
	public List<RecentSearchDto> findLast8DistinctSearchesByUsername(String username) {
		
		List<RecentSearch> recentSearches=this.recentSearchRepository.findDistinctTop8ByUsernameOrderByIdDesc(username);
		
		List<RecentSearchDto> recentSearchDtos=recentSearches.stream().map(recentSearch->mapToDto(recentSearch)).collect(Collectors.toList());
		return recentSearchDtos;
	}

	@Override
	public void deleteAllRecentSearch(String username) {
		this.recentSearchRepository.deleteAllByUsername(username);
		
	}

}
