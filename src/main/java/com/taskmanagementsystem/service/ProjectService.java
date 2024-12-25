package com.taskmanagementsystem.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taskmanagementsystem.dto.ProjectProjection;
import com.taskmanagementsystem.entity.Project;
import com.taskmanagementsystem.entity.User;
import com.taskmanagementsystem.entity.UserRoles;
import com.taskmanagementsystem.exception.ProjectOperationException;
import com.taskmanagementsystem.repository.ProjectRepository;
import com.taskmanagementsystem.repository.UserRolesRepository;

@Service
public class ProjectService {

	
	private final ProjectRepository projectRepository;
	public ProjectService(ProjectRepository projectRepository) {
		this.projectRepository = projectRepository;
	}

	@Transactional
	public Map<String, String> createNewProject(Project project) {

		if (project == null || project.getProjectName() == null || project.getProjectName().isEmpty()) {
			throw new ProjectOperationException("ADDFAILS","Project already exists");
		}

		if (projectRepository.findById(project.getProjectId()).isPresent()) {
			throw new IllegalArgumentException("A project with the same ID already exists. Use a unique ID.");
		}

		projectRepository.save(project);

		Map<String, String> response = new LinkedHashMap<>();
		response.put("code", "POSTSUCCESS");
		response.put("message", "Project created successfully");
		return response;
	}

	@Transactional
	public List<ProjectProjection> getOngoingProjects() {
		LocalDate currentDate = LocalDate.now();
		return projectRepository.findOngoingProjects(currentDate);
	}

	@Transactional
	public List<ProjectProjection> getProjectsInDateRange(String startDateStr, String endDateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {

			Date startDate = sdf.parse(startDateStr);
			Date endDate = sdf.parse(endDateStr);

			List<ProjectProjection> listDateRange = projectRepository.findProjectsInDateRange(startDate, endDate);


			if (listDateRange.isEmpty()) {
				throw new ProjectOperationException("GETFAILS", "No projects found in the given date range.");
			}

			return listDateRange;

		} catch (ParseException e) {
			throw new IllegalArgumentException("Invalid date format. Please use 'yyyy-MM-dd'.", e);
		}
	}

	@Transactional
	public List<ProjectProjection> getProjectsByStatus(String status) {

		List<ProjectProjection> projects = projectRepository.findProjectsByTaskStatus(status);

		if (projects.isEmpty()) {
			throw new ProjectOperationException("GETFAILS", "Project List is empty");
		}

		return projects;
	}
	
	@Transactional
	public List<ProjectProjection> getProjectsWithHighPriority() {
		List<ProjectProjection> projects = projectRepository.findProjectsByTaskPriority("HIGH");

		if (projects.isEmpty()) {
			throw new ProjectOperationException("GETFAILS", 
					"Project List is empty");
		}

		return projects;
	}

	@Transactional
	public ProjectProjection updateProjectDetails(int projectId, Project updatedProjectDetails) {

		Project existingProject = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectOperationException("UPDTFAILS","Project doesn't exist"));
		
		existingProject.setProjectName(updatedProjectDetails.getProjectName());
		existingProject.setDescription(updatedProjectDetails.getDescription());
		existingProject.setStartDate(updatedProjectDetails.getStartDate());
		existingProject.setEndDate(updatedProjectDetails.getEndDate());

		projectRepository.save(existingProject);
		 return projectRepository.findProjectById(projectId)
	                .orElseThrow(() -> new ProjectOperationException("UPDTFAILS","Project doesn't exist"));
	}

	@Transactional
	public String deleteProjectById(int projectId) {
	    Project project = projectRepository.findById(projectId)
	            .orElseThrow(() -> new ProjectOperationException("DELETEFAILS", "Project not found"));
	    projectRepository.delete(project);
	    return "Project deleted successfully";
	}
	
	@Transactional
	public List<ProjectProjection> getProjectsByUserRole(String roleName) {
	    List<ProjectProjection> projects = projectRepository.findProjectsByUserRole(roleName);
	    if (projects.isEmpty()) {
	        throw new ProjectOperationException("GETALLFAILS", "Project list is empty");
	    }
	    return projects;
	}

}

