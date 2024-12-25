package com.taskmanagementsystem.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.taskmanagementsystem.dto.ProjectProjection;
import com.taskmanagementsystem.entity.Project;
import com.taskmanagementsystem.entity.User;

public interface ProjectRepository extends JpaRepository<Project,Integer> {

	@Query("SELECT p FROM Project p WHERE p.endDate IS NULL OR p.endDate > :currentDate")
    List<ProjectProjection> findOngoingProjects(@Param("currentDate") LocalDate currentDate);
	
	@Query("SELECT p.projectId AS projectId, p.projectName AS projectName, " +
	           "p.description AS description, p.startDate AS startDate, p.endDate AS endDate " +
	           "FROM Project p WHERE p.startDate BETWEEN :startDate AND :endDate")
	    List<ProjectProjection> findProjectsInDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	
	@Query("""
		    SELECT p.projectId AS projectId, 
		           p.projectName AS projectName, 
		           p.description AS description, 
		           p.startDate AS startDate, 
		           p.endDate AS endDate
		    FROM Project p
		    JOIN p.tasks t
		    WHERE t.status = :status
		""")
		List<ProjectProjection> findProjectsByTaskStatus(@Param("status") String status);
	
	@Query("""
	        SELECT p.projectId AS projectId, 
	               p.projectName AS projectName, 
	               p.description AS description, 
	               p.startDate AS startDate, 
	               p.endDate AS endDate
	        FROM Project p
	        JOIN p.tasks t
	        WHERE t.priority = :priority
	    """)
	    List<ProjectProjection> findProjectsByTaskPriority(@Param("priority") String priority);
	
	@Query("SELECT p.projectId AS projectId, p.projectName AS projectName, p.description AS description, p.startDate AS startDate, p.endDate AS endDate FROM Project p WHERE p.projectId = :projectId")
	Optional<ProjectProjection> findProjectById(@Param("projectId") int projectId);
	
	
	@Query("SELECT p FROM Project p WHERE p.user.userId IN " +
		       "(SELECT ur.id.userId FROM UserRoles ur WHERE ur.userRole.roleName = :roleName)")
		List<ProjectProjection> findProjectsByUserRole(@Param("roleName") String roleName);
	
	@Query("SELECT p FROM Project p WHERE p.user.userId = :userId")
    List<ProjectProjection> findProjectsByUserId(@Param("userId") int userId);

}
