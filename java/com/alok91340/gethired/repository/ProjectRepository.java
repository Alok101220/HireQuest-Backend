/**
 * 
 */
package com.alok91340.gethired.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alok91340.gethired.entities.Project;

/**
 * @author alok91340
 *
 */
public interface ProjectRepository extends JpaRepository<Project,Long>{
//	List<ProjectDto> findAllByUserProfileId(Long userProfileId);
}
