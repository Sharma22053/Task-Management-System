package com.taskmanagementsystem.service;
 
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
 
import com.taskmanagementsystem.dto.TaskProjection;
import com.taskmanagementsystem.entity.Project;
import com.taskmanagementsystem.entity.Tasks;
import com.taskmanagementsystem.entity.User;
import com.taskmanagementsystem.exception.ProjectOperationException;
import com.taskmanagementsystem.exception.TasksOperationException;
import com.taskmanagementsystem.repository.ProjectRepository;
import com.taskmanagementsystem.repository.TasksRepository;
import com.taskmanagementsystem.repository.UserRepository;
import java.time.LocalDate;
import java.util.*;
@ExtendWith(MockitoExtension.class)
public class TasksServiceTests {
    @Mock
    private TasksRepository tasksRepository;
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private TasksService tasksService;
    @BeforeEach
    public void setUp() {
   
    }
    // Test for createNewTask
    @Test
    public void testCreateNewTask_Success() {
        // Arrange
        Tasks task = new Tasks();
        task.setTaskName("Test Task");
        task.setDescription("Description");
        Project project = new Project();
        project.setProjectId(1);
        task.setProject(project);
        User user = new User();
        user.setUserId(1);
        task.setUser(user);
        when(projectRepository.findById(1)).thenReturn(Optional.of(project));
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        Map<String, String> result = tasksService.createNewTask(task);
        assertEquals("POSTSUCCESS", result.get("code"));
        assertEquals("Task added successfully", result.get("message"));
        verify(tasksRepository, times(1)).save(task);
    }
    @Test
    public void testCreateNewTask_ProjectNotFound() {
        Tasks task = new Tasks();
        task.setTaskName("Test Task");
        Project project = new Project();
        project.setProjectId(1);
        task.setProject(project);   
        when(projectRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ProjectOperationException.class, () -> tasksService.createNewTask(task));
    }
    @Test
    public void testCreateNewTask_UserNotFound() {
        Tasks task = new Tasks();
        task.setTaskName("Test Task");
        Project project = new Project();
        project.setProjectId(1);
        task.setProject(project);
        task.setUser(new User());
        when(projectRepository.findById(1)).thenReturn(Optional.of(project));
        assertThrows(TasksOperationException.class, () -> tasksService.createNewTask(task));
    }   
// Test for getListOfOverdueTasks
    @Test
    public void testGetListOfOverdueTasks_Success() {
        LocalDate currentDate = LocalDate.now();
        List<TaskProjection> overdueTasks = List.of(mock(TaskProjection.class));
        when(tasksRepository.findOverdueTasks(currentDate)).thenReturn(overdueTasks);
        List<TaskProjection> result = tasksService.getListOfOverdueTasks();
        assertNotNull(result);
        assertEquals(1, result.size());
    }
    @Test
    public void testGetListOfOverdueTasks_NoTasks() {
        LocalDate currentDate = LocalDate.now();
        when(tasksRepository.findOverdueTasks(currentDate)).thenReturn(Collections.emptyList());
        assertThrows(TasksOperationException.class, () -> tasksService.getListOfOverdueTasks());
    }
    // Test for getTasksByPriorityAndStatus
    @Test
    public void testGetTasksByPriorityAndStatus_Success() {
        String priority = "High";
        String status = "Pending";
        List<TaskProjection> tasks = List.of(mock(TaskProjection.class));
        when(tasksRepository.findByPriorityAndStatus(priority, status)).thenReturn(tasks);
        List<TaskProjection> result = tasksService.getTasksByPriorityAndStatus(priority, status);
        assertNotNull(result);
        assertEquals(1, result.size());
    }
    @Test
    public void testGetTasksByPriorityAndStatus_NoTasksFound() {
        String priority = "High";
        String status = "Pending";
        when(tasksRepository.findByPriorityAndStatus(priority, status)).thenReturn(Collections.emptyList());
        assertThrows(TasksOperationException.class, () -> tasksService.getTasksByPriorityAndStatus(priority, status));
    }
    // Test for getTasksDueSoon
    @Test
    public void testGetTasksDueSoon_Success() {
        LocalDate currentDate = LocalDate.now();
        LocalDate dueSoonDate = currentDate.plusDays(7);
        List<TaskProjection> tasksDueSoon = List.of(mock(TaskProjection.class));
        when(tasksRepository.findTasksDueSoon(currentDate, dueSoonDate)).thenReturn(tasksDueSoon);
        List<TaskProjection> result = tasksService.getTasksDueSoon();
        assertNotNull(result);
        assertEquals(1, result.size());
    }
    @Test
    public void testGetTasksDueSoon_NoTasksDueSoon() {
        LocalDate currentDate = LocalDate.now();
        LocalDate dueSoonDate = currentDate.plusDays(7);
        when(tasksRepository.findTasksDueSoon(currentDate, dueSoonDate)).thenReturn(Collections.emptyList());
        assertThrows(TasksOperationException.class, () -> tasksService.getTasksDueSoon());
    }
 
    // Test for getTasksByUserIdAndStatus
    @Test
    public void testGetTasksByUserIdAndStatus_Success() {
 
        int userId = 1;
        String status = "In Progress";
        List<TaskProjection> tasks = List.of(mock(TaskProjection.class));
        when(tasksRepository.findByUserIdAndStatus(userId, status)).thenReturn(tasks);
        List<TaskProjection> result = tasksService.getTasksByUserIdAndStatus(userId, status);
        assertNotNull(result);
        assertEquals(1, result.size());
    }
    @Test
    public void testGetTasksByUserIdAndStatus_NoTasks() {
        // Arrange
        int userId = 1;
        String status = "Completed";
        when(tasksRepository.findByUserIdAndStatus(userId, status)).thenReturn(Collections.emptyList());
        assertThrows(TasksOperationException.class, () -> tasksService.getTasksByUserIdAndStatus(userId, status));
    }  
    // Test for getTasksByCategoryId
    @Test
    public void testGetTasksByCategoryId_Success() {
        int categoryId = 1;
        List<TaskProjection> tasks = List.of(mock(TaskProjection.class));
        when(tasksRepository.findTasksByCategoryId(categoryId)).thenReturn(tasks);
        List<TaskProjection> result = tasksService.getTasksByCategoryId(categoryId);
        assertNotNull(result);
        assertEquals(1, result.size());
    }
 
    @Test
    public void testGetTasksByCategoryId_NoTasksFound() {
        int categoryId = 1;
        when(tasksRepository.findTasksByCategoryId(categoryId)).thenReturn(Collections.emptyList());
        assertThrows(TasksOperationException.class, () -> tasksService.getTasksByCategoryId(categoryId));
    }
 
// Test for updateTaskDetails
    @Test
    public void testUpdateTaskDetails_Success() {
        int taskId = 1;
        Tasks updatedTask = new Tasks();
        updatedTask.setTaskName("Updated Task");
        Tasks existingTask = new Tasks();
        existingTask.setTaskId(1);   
        when(tasksRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        Map<String, String> result = tasksService.updateTaskDetails(taskId, updatedTask);
        assertEquals("UPDATESUCCESS", result.get("code"));
        assertEquals("Task updated successfully", result.get("message"));
        verify(tasksRepository, times(1)).save(existingTask);
    }
    
    @Test
    public void testUpdateTaskDetails_TaskNotFound() {
        int taskId = 1;
        Tasks updatedTask = new Tasks();
        updatedTask.setTaskName("Updated Task");
        
        when(tasksRepository.findById(taskId)).thenReturn(Optional.empty());
        assertThrows(TasksOperationException.class, () -> tasksService.updateTaskDetails(taskId, updatedTask));
    }
    
    
    // Test for deleteTask
    @Test
    public void testDeleteTask_Success() {
        int taskId = 1;
        Tasks task = new Tasks();
        task.setTaskId(taskId);       
        when(tasksRepository.findById(taskId)).thenReturn(Optional.of(task));
        Map<String, String> result = tasksService.deleteTask(taskId);
        assertEquals("DELETESUCCESS", result.get("code"));
        assertEquals("Task deleted successfully", result.get("message"));
        verify(tasksRepository, times(1)).deleteById(taskId);
    }
    
    @Test
    public void testDeleteTask_TaskNotFound() {
        int taskId = 1;
        when(tasksRepository.findById(taskId)).thenReturn(Optional.empty());
        assertThrows(TasksOperationException.class, () -> tasksService.deleteTask(taskId));
    }
 
}
 
 
 
 