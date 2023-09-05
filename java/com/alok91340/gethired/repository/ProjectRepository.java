/**
 * 
 */
package com.alok91340.gethired.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alok91340.gethired.entities.Project;
import com.alok91340.gethired.dto.ProjectDto;

/**
 * @author alok91340
 *
 */
public interface ProjectRepository extends JpaRepository<Project,Long>{
	List<ProjectDto> findAllByCandidateProfileId(Long userProfileId);
}
