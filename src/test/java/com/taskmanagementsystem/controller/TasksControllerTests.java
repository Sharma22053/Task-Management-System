package com.taskmanagementsystem.controller;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
 
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
 
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskmanagementsystem.dto.TaskProjection;
import com.taskmanagementsystem.entity.Tasks;
import com.taskmanagementsystem.service.TasksService;
 
@ExtendWith(MockitoExtension.class)
public class TasksControllerTests {
 
    @InjectMocks
    private TasksController tasksController;
 
    @Mock
    private TasksService tasksService;
 
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
 
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(tasksController).build();
        objectMapper = new ObjectMapper();
    }
 
    private static class TaskProjectionImpl implements TaskProjection {
        private int taskId;
        private String taskName;
        private String description;
        private Date dueDate;
        private String priority;
        private String status;
 
        public TaskProjectionImpl(int taskId, String taskName, String description, Date dueDate, String priority, String status) {
            this.taskId = taskId;
            this.taskName = taskName;
            this.description = description;
            this.dueDate = dueDate;
            this.priority = priority;
            this.status = status;
        }
 
        @Override
        public int getTaskId() {
            return taskId;
        }
 
        @Override
        public String getTaskName() {
            return taskName;
        }
 
        @Override
        public String getDescription() {
            return description;
        }
 
        @Override
        public Date getDueDate() {
            return dueDate;
        }
 
        @Override
        public String getPriority() {
            return priority;
        }
 
        @Override
        public String getStatus() {
            return status;
        }
    }
 
    @Test
    public void testCreateNewTask() throws Exception {
        Tasks task = new Tasks();
        task.setTaskName("Test Task");
 
        Map<String, String> response = new HashMap<>();
        response.put("code", "POSTSUCCESS");
        response.put("message", "Task added successfully");
 
        when(tasksService.createNewTask(any(Tasks.class))).thenReturn(response);
 
        mockMvc.perform(post("/api/tasks/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("POSTSUCCESS"))
                .andExpect(jsonPath("$.message").value("Task added successfully"));
 
        verify(tasksService, times(1)).createNewTask(any(Tasks.class));
    }
 
    @Test
    public void testGetListOfOverdueTasks() throws Exception {
        TaskProjection task = new TaskProjectionImpl(1, "Overdue Task", "Description", new Date(), "High", "Overdue");
 
        List<TaskProjection> tasks = List.of(task);
        when(tasksService.getListOfOverdueTasks()).thenReturn(tasks);
 
        mockMvc.perform(get("/api/tasks/overdue"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].taskName").value("Overdue Task"));
 
        verify(tasksService, times(1)).getListOfOverdueTasks();
    }
 
    @Test
    public void testGetTasksByPriorityAndStatus() throws Exception {
        TaskProjection task = new TaskProjectionImpl(1, "Task 1", "Description", new Date(), "High", "Pending");
 
        List<TaskProjection> tasks = List.of(task);
        when(tasksService.getTasksByPriorityAndStatus(anyString(), anyString())).thenReturn(tasks);
 
        mockMvc.perform(get("/api/tasks/priority/High/status/Pending"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].priority").value("High"))
                .andExpect(jsonPath("$[0].status").value("Pending"));
 
        verify(tasksService, times(1)).getTasksByPriorityAndStatus("High", "Pending");
    }
 
    @Test
    public void testGetTasksDueSoon() throws Exception {
        TaskProjection task = new TaskProjectionImpl(1, "Due Soon Task", "Description", new Date(), "Medium", "Due Soon");
 
        List<TaskProjection> tasks = List.of(task);
        when(tasksService.getTasksDueSoon()).thenReturn(tasks);
 
        mockMvc.perform(get("/api/tasks/due-soon"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].taskName").value("Due Soon Task"));
 
        verify(tasksService, times(1)).getTasksDueSoon();
    }
 
    @Test
    public void testGetTasksByUserIdAndStatus() throws Exception {
        TaskProjection task = new TaskProjectionImpl(1, "User Task", "Description", new Date(), "Low", "Completed");
 
        List<TaskProjection> tasks = List.of(task);
        when(tasksService.getTasksByUserIdAndStatus(anyInt(), anyString())).thenReturn(tasks);
 
        mockMvc.perform(get("/api/tasks/user/1/status/Completed"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("Completed"));
 
        verify(tasksService, times(1)).getTasksByUserIdAndStatus(1, "Completed");
    }
 
    @Test
    public void testGetTasksByCategoryId() throws Exception {
        TaskProjection task = new TaskProjectionImpl(1, "Category Task", "Description", new Date(), "Medium", "Pending");
 
        List<TaskProjection> tasks = List.of(task);
        when(tasksService.getTasksByCategoryId(anyInt())).thenReturn(tasks);
 
        mockMvc.perform(get("/api/tasks/category/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].taskName").value("Category Task"));
 
        verify(tasksService, times(1)).getTasksByCategoryId(1);
    }
 
    @Test
    public void testUpdateTaskDetails() throws Exception {
        Tasks task = new Tasks();
        task.setTaskName("Updated Task");
 
        Map<String, String> response = new HashMap<>();
        response.put("code", "UPDATESUCCESS");
        response.put("message", "Task updated successfully");
 
        when(tasksService.updateTaskDetails(anyInt(), any(Tasks.class))).thenReturn(response);
 
        mockMvc.perform(put("/api/tasks/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("UPDATESUCCESS"))
                .andExpect(jsonPath("$.message").value("Task updated successfully"));
 
        // Capture the arguments passed to the mock
        ArgumentCaptor<Integer> taskIdCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Tasks> taskCaptor = ArgumentCaptor.forClass(Tasks.class);
 
        verify(tasksService, times(1)).updateTaskDetails(taskIdCaptor.capture(), taskCaptor.capture());
 
        // Verify captured arguments
        assertEquals(1, taskIdCaptor.getValue());
        assertEquals("Updated Task", taskCaptor.getValue().getTaskName());
    }
 
 
    @Test
    public void testDeleteTask() throws Exception {
        Map<String, String> response = new HashMap<>();
        response.put("code", "DELETESUCCESS");
        response.put("message", "Task deleted successfully");
 
        when(tasksService.deleteTask(anyInt())).thenReturn(response);
 
        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("DELETESUCCESS"))
                .andExpect(jsonPath("$.message").value("Task deleted successfully"));
 
        verify(tasksService, times(1)).deleteTask(1);
    }
}
 
 