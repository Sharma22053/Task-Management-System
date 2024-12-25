package com.taskmanagementsystem.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskmanagementsystem.dto.ProjectProjection;
import com.taskmanagementsystem.entity.Project;
import com.taskmanagementsystem.service.ProjectService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

	private final ProjectService projectService;
	
	public ProjectController(ProjectService projectService) {
		this.projectService = projectService;	}
	
	@PostMapping("/post")
	public ResponseEntity<Object> createNewProject(@RequestBody Project project){
		
		return new ResponseEntity<>(projectService.createNewProject(project),HttpStatus.OK);
	}
	
	@GetMapping("/ongoing")
	public ResponseEntity<List<ProjectProjection>> getOngoingProjects(){
		return new ResponseEntity<>(projectService.getOngoingProjects(),HttpStatus.OK);
	}
	
	@GetMapping("/date-range/{startDate}/{endDate}")
	 public ResponseEntity<List<ProjectProjection>> getProjectsInDateRange(
	            @PathVariable("startDate") String startDateStr,
	            @PathVariable("endDate") String endDateStr){
	return new ResponseEntity<>(projectService.getProjectsInDateRange(startDateStr, endDateStr),HttpStatus.OK);
	}

	            
	@GetMapping("/status/{status}")
	public ResponseEntity<List<ProjectProjection>> getProjectsByStatus(@PathVariable String status)
	{
		
		return new ResponseEntity<>(projectService.getProjectsByStatus(status),HttpStatus.OK);
	}
	
	@GetMapping("/high-priority-tasks")
	public ResponseEntity<Object> getProjectsWithHighPriority() {
	    
	    return new ResponseEntity<>(projectService.getProjectsWithHighPriority(), HttpStatus.OK);
	}
	
	@PutMapping("/update/{projectId}")
	public ResponseEntity<Object> updateProjectDetails(
	        @PathVariable int projectId, 
	        @RequestBody Project updatedProjectDetails) {

		ProjectProjection updatedProject = projectService.updateProjectDetails(projectId, updatedProjectDetails);
	    return new ResponseEntity<>(updatedProject, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{projectId}")
	public ResponseEntity<String> deleteProjectByProjectId(@PathVariable int projectId) {
	    
		String result = projectService.deleteProjectById(projectId);
	    return ResponseEntity.ok(result);
	}
	
	@GetMapping("/user-role/{roleName}")
	public ResponseEntity<List<ProjectProjection>> getProjectsByUserRole(@PathVariable String roleName) {
	    List<ProjectProjection> projects = projectService.getProjectsByUserRole(roleName);
	    return new ResponseEntity<>(projects, HttpStatus.OK);
	}

}
