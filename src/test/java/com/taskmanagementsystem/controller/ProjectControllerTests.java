package com.taskmanagementsystem.controller;
 
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
 
import java.time.LocalDate;

import java.util.HashMap;

import java.util.List;

import java.util.Map;
 
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
 
import com.taskmanagementsystem.dto.ProjectProjection;

import com.taskmanagementsystem.entity.Project;

import com.taskmanagementsystem.exception.ProjectOperationException;

import com.taskmanagementsystem.service.ProjectService;
 
@WebMvcTest(ProjectController.class)

class ProjectControllerTests {
 
    @Autowired

    private MockMvc mockMvc;
 
    @MockBean

    private ProjectService projectService;
 
    @Test

    void testCreateNewUser_Success() throws Exception {

        Project project = new Project();

        project.setProjectName("Test Project");
 
        Map<String, String> response = new HashMap<>();

        response.put("message", "Project created successfully");
 
        when(projectService.createNewUser(any(Project.class))).thenReturn(response);
 
        mockMvc.perform(post("/api/projects/post")

                .contentType(MediaType.APPLICATION_JSON)

                .content("{\"name\": \"Test Project\"}"))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$.message").value("Project created successfully"));

    }
 
 
    @Test

    void testGetOngoingProjects_Success() throws Exception {

        when(projectService.getOngoingProjects()).thenReturn(List.of());
 
        mockMvc.perform(get("/api/projects/ongoing"))

                .andExpect(status().isOk());

    }
 
    @Test

    void testGetProjectsInDateRange_Success() throws Exception {

        when(projectService.getProjectsInDateRange(anyString(), anyString())).thenReturn(List.of());
 
        mockMvc.perform(get("/api/projects/date-range/2023-01-01/2023-12-31"))

                .andExpect(status().isOk());

    }
 
    @Test

    void testGetProjectsByStatus_Success() throws Exception {

        when(projectService.getProjectsByStatus("active")).thenReturn(List.of());
 
        mockMvc.perform(get("/api/projects/status/active"))

                .andExpect(status().isOk());

    }
 
    @Test

    void testGetProjectsWithHighPriority_Success() throws Exception {

        when(projectService.getProjectsWithHighPriority()).thenReturn(List.of());
 
        mockMvc.perform(get("/api/projects/high-priority-tasks"))

                .andExpect(status().isOk());

    }
 
    @Test

    void testUpdateProjectDetails_Success() throws Exception {

        // Create a mock or real implementation of ProjectProjection

        ProjectProjection updatedProject = new ProjectProjection() {

            @Override

            public Integer getProjectId() {

                return 1;

            }
 
            @Override

            public String getProjectName() {

                return "Updated Project";

            }
 
            @Override

            public LocalDate getEndDate() {

                return LocalDate.of(2023, 12, 31);

            }
 
            @Override

            public String getDescription() { // Implement this method

                return "This is a test project description.";

            }
 
			@Override

			public LocalDate getStartDate() {

				// TODO Auto-generated method stub

				return null;

			}

        };
 
        when(projectService.updateProjectDetails(eq(1), any(Project.class))).thenReturn(updatedProject);
 
        mockMvc.perform(put("/api/projects/update/1")

                .contentType(MediaType.APPLICATION_JSON)

                .content("{\"projectName\": \"Updated Project\"}"))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$.projectId").value(1))

                .andExpect(jsonPath("$.projectName").value("Updated Project"))

                .andExpect(jsonPath("$.endDate").value("2023-12-31"))

                .andExpect(jsonPath("$.description").value("This is a test project description."));

    }
 
    @Test

    void testDeleteProjectByProjectId_Success() throws Exception {

        doNothing().when(projectService).deleteProjectById(1);
 
        mockMvc.perform(delete("/api/projects/delete/1"))

                .andExpect(status().isOk())

                .andExpect(content().string("Project deleted successfully"));

    }


    @Test

    void testGetProjectsByUserRole_Success() throws Exception {

        when(projectService.getProjectsByUserRole("admin")).thenReturn(List.of());
 
        mockMvc.perform(get("/api/projects/user-role/admin"))

                .andExpect(status().isOk());

    }

 
}
 