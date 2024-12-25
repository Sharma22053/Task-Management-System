package com.taskmanagementsystem.service;
 
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import com.taskmanagementsystem.dto.ProjectProjection;
import com.taskmanagementsystem.entity.Project;
import com.taskmanagementsystem.exception.ProjectOperationException;
import com.taskmanagementsystem.repository.ProjectRepository;
import com.taskmanagementsystem.service.ProjectService;
 
public class ProjectServiceTests {
    @Mock
    private ProjectRepository projectRepository;
    private ProjectService projectService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        projectService = new ProjectService(projectRepository);
    }   

// Test for getOngoingProjects method
    @Test
    public void testGetOngoingProjects() {
        List<ProjectProjection> projects = new ArrayList<>();
        when(projectRepository.findOngoingProjects(ArgumentMatchers.any(LocalDate.class))).thenReturn(projects);
        List<ProjectProjection> result = projectService.getOngoingProjects();
        assertEquals(projects, result);
        verify(projectRepository, times(1)).findOngoingProjects(ArgumentMatchers.any(LocalDate.class));
    }   
    @Test
    public void testGetOngoingProjects_NoOngoingProjects() {
        List<ProjectProjection> projects = new ArrayList<>();    
        when(projectRepository.findOngoingProjects(any(LocalDate.class))).thenReturn(projects);
        List<ProjectProjection> result = projectService.getOngoingProjects();
        assertTrue(result.isEmpty(), "The list of ongoing projects should be empty when no ongoing projects exist.");
        verify(projectRepository, times(1)).findOngoingProjects(any(LocalDate.class));
    }   
// Test for getProjectsInDateRange method - Success
    @Test
    public void testGetProjectsInDateRange_Success() throws Exception {
        String startDate = "2023-01-01";
        String endDate = "2023-12-31";
        List<ProjectProjection> projects = new ArrayList<>();
        ProjectProjection project1 = mock(ProjectProjection.class);
        projects.add(project1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date start = sdf.parse(startDate);
        Date end = sdf.parse(endDate);
        when(projectRepository.findProjectsInDateRange(any(Date.class), any(Date.class))).thenReturn(projects);
        List<ProjectProjection> result = projectService.getProjectsInDateRange(startDate, endDate);
        assertEquals(projects, result);
        verify(projectRepository, times(1)).findProjectsInDateRange(any(Date.class), any(Date.class));
    }    
// Test for getProjectsInDateRange method - No Projects Found (Failure)
    @Test
    public void testGetProjectsInDateRange_NoProjectsFound() throws Exception {
        String startDate = "2023-01-01";
        String endDate = "2023-12-31";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date start = sdf.parse(startDate);
        Date end = sdf.parse(endDate);
        when(projectRepository.findProjectsInDateRange(any(Date.class), any(Date.class)))
                .thenReturn(Collections.emptyList());
        ProjectOperationException thrown = assertThrows(ProjectOperationException.class, () -> {
            projectService.getProjectsInDateRange(startDate, endDate);
        });
        assertEquals("No projects found in the given date range.", thrown.getMessage());
        verify(projectRepository, times(1)).findProjectsInDateRange(any(Date.class), any(Date.class));
    }  
    @Test
    public void testGetProjectsInDateRange_InvalidDateFormat() {
        String startDate = "01/01/2023"; // Invalid date format
        String endDate = "12/31/2023";  // Invalid date format
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            projectService.getProjectsInDateRange(startDate, endDate);
        });
        assertEquals("Invalid date format. Please use 'yyyy-MM-dd'.", thrown.getMessage());
    }   
    @Test
    public void testGetProjectsInDateRange_ValidDateRange() throws Exception {
        String startDate = "2023-01-01";
        String endDate = "2023-12-31";
        List<ProjectProjection> projects = new ArrayList<>();
        ProjectProjection project1 = mock(ProjectProjection.class);
        projects.add(project1);  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date start = sdf.parse(startDate);
        Date end = sdf.parse(endDate);      
        when(projectRepository.findProjectsInDateRange(any(Date.class), any(Date.class))).thenReturn(projects);
        List<ProjectProjection> result = projectService.getProjectsInDateRange(startDate, endDate);
        assertEquals(projects, result);
        verify(projectRepository, times(1)).findProjectsInDateRange(any(Date.class), any(Date.class));
    }
// Test for getProjectsByStatus method
    @Test
    public void testGetProjectsByStatus_Success() {
        // Create a mock list of projects
        List<ProjectProjection> projects = new ArrayList<>();
        ProjectProjection project1 = mock(ProjectProjection.class);
        ProjectProjection project2 = mock(ProjectProjection.class);
        projects.add(project1);
        projects.add(project2);
        when(projectRepository.findProjectsByTaskStatus("ACTIVE")).thenReturn(projects);
        List<ProjectProjection> result = projectService.getProjectsByStatus("ACTIVE");
        assertEquals(projects.size(), result.size(), "The size of the result list should match the mocked list.");
        assertEquals(projects, result, "The result should match the mocked list of projects.");
        verify(projectRepository, times(1)).findProjectsByTaskStatus("ACTIVE");
    }
    @Test
    public void testGetProjectsByStatus_EmptyList() {
        when(projectRepository.findProjectsByTaskStatus("INACTIVE")).thenReturn(Collections.emptyList());
        ProjectOperationException thrown = assertThrows(ProjectOperationException.class, () -> {
            projectService.getProjectsByStatus("INACTIVE");
        });
 
        assertEquals("Project List is empty", thrown.getMessage());
    }   
// Test for getProjectsWithHighPriority method
    @Test
    public void testGetProjectsWithHighPriority_Success() {
        List<ProjectProjection> projects = new ArrayList<>();
        ProjectProjection project1 = mock(ProjectProjection.class);
        ProjectProjection project2 = mock(ProjectProjection.class);
        projects.add(project1);
        projects.add(project2);
        when(projectRepository.findProjectsByTaskPriority("HIGH")).thenReturn(projects);
        List<ProjectProjection> result = projectService.getProjectsWithHighPriority();
        assertEquals(projects.size(), result.size(), "The size of the result list should match the mocked list.");
        assertEquals(projects, result, "The result should match the mocked list of high-priority projects.");
        verify(projectRepository, times(1)).findProjectsByTaskPriority("HIGH");
    }
   @Test
    public void testGetProjectsWithHighPriority_EmptyList() {
        when(projectRepository.findProjectsByTaskPriority("HIGH")).thenReturn(Collections.emptyList());
        ProjectOperationException thrown = assertThrows(ProjectOperationException.class, () -> {
            projectService.getProjectsWithHighPriority();
        });
        assertEquals("Project List is empty", thrown.getMessage());
    }
    @Test
    public void testUpdateProjectDetails_ProjectNotFound11() {
        Project updatedProject = new Project();
        updatedProject.setProjectName("Updated Project");
        when(projectRepository.findById(1)).thenReturn(Optional.empty());
        ProjectOperationException thrown = assertThrows(ProjectOperationException.class, () -> {
            projectService.updateProjectDetails(1, updatedProject);
        });
        assertEquals("Project doesn't exist", thrown.getMessage());
    }
// Test for deleteProjectById method
    @Test
    public void testDeleteProjectById_Success() {
        Project project = new Project();
        project.setProjectId(1);
        when(projectRepository.findById(1)).thenReturn(Optional.of(project));
        projectService.deleteProjectById(1);
        verify(projectRepository, times(1)).delete(project);
    }
    @Test
    public void testDeleteProjectById_ProjectNotFound() {
        when(projectRepository.findById(1)).thenReturn(Optional.empty());
        ProjectOperationException thrown = assertThrows(ProjectOperationException.class, () -> {
            projectService.deleteProjectById(1);
        });
        assertEquals("Project not found", thrown.getMessage());
    }   
    @Test
    public void testUpdateProjectDetails_ProjectNotFound1() {
        Project updatedProject = new Project();
        updatedProject.setProjectName("Updated Project");
        when(projectRepository.findById(1)).thenReturn(Optional.empty());
        ProjectOperationException thrown = assertThrows(ProjectOperationException.class, () -> {
            projectService.updateProjectDetails(1, updatedProject);
        });
        assertEquals("Project doesn't exist", thrown.getMessage());
    }
 
}