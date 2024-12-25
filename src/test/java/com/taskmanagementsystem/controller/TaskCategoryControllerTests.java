package com.taskmanagementsystem.controller;
 
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.ArgumentMatchers.anyInt;

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

import org.mockito.InjectMocks;

import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
 
import com.fasterxml.jackson.databind.ObjectMapper;

import com.taskmanagementsystem.dto.CategoryProjection;

import com.taskmanagementsystem.dto.TaskProjection;

import com.taskmanagementsystem.entity.TaskCategory;

import com.taskmanagementsystem.service.TaskCategoryService;
 
@ExtendWith(MockitoExtension.class)

public class TaskCategoryControllerTests {
 
    @InjectMocks

    private TaskCategoryController taskCategoryController;
 
    @Mock

    private TaskCategoryService taskCategoryService;
 
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;
 
    @BeforeEach

    public void setUp() {

        mockMvc = MockMvcBuilders.standaloneSetup(taskCategoryController).build();

        objectMapper = new ObjectMapper();

    }
 
 
    @Test

    public void testGetAllTasksByCategoryId() throws Exception {

        TaskProjection task = new TaskProjection() {

            @Override

            public String getTaskName() {

                return "Test Task";

            }
 
            @Override

            public String getPriority() {

                return "High";

            }
 
            @Override

            public String getStatus() {

                return "Pending";

            }
 
			@Override

			public int getTaskId() {

				// TODO Auto-generated method stub

				return 0;

			}
 
			@Override

			public String getDescription() {

				// TODO Auto-generated method stub

				return null;

			}
 
			@Override

			public Date getDueDate() {

				// TODO Auto-generated method stub

				return null;

			}

        };
 
        List<TaskProjection> tasks = List.of(task);

        when(taskCategoryService.getAllTasksByCategoryId(anyInt())).thenReturn(tasks);
 
        mockMvc.perform(get("/api/taskcategories/category/{categoryId}", 1))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$[0].taskName").value("Test Task"))

                .andExpect(jsonPath("$[0].priority").value("High"))

                .andExpect(jsonPath("$[0].status").value("Pending"));
 
        verify(taskCategoryService, times(1)).getAllTasksByCategoryId(1);

    }

    @Test

    public void testAddTaskCategory() throws Exception {

        // Setup a valid TaskCategory object using the constructor

        TaskCategory taskCategory = new TaskCategory();
 
        // Mock service response

        Map<String, String> response = new HashMap<>();

        response.put("code", "POSTSUCCESS");

        response.put("message", "Task category added successfully");
 
        when(taskCategoryService.addTaskCategory(any(TaskCategory.class))).thenReturn(response);
 
        // Perform the POST request

        mockMvc.perform(post("/api/taskcategories/post")

                .contentType(MediaType.APPLICATION_JSON)

                .content(objectMapper.writeValueAsString(taskCategory)))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$.code").value("POSTSUCCESS"))

                .andExpect(jsonPath("$.message").value("Task category added successfully"));
 
        verify(taskCategoryService, times(1)).addTaskCategory(any(TaskCategory.class));

    }
 
 
    @Test

    public void testGetAllCategoriesByTaskId() throws Exception {

        // Mock the CategoryProjection object

        CategoryProjection category = new CategoryProjection() {

            @Override

            public String getCategoryName() {

                return "Test Category";

            }
 
			@Override

			public int getCategoryId() {

				// TODO Auto-generated method stub

				return 0;

			}

        };
 
        // Mock the service response

        List<CategoryProjection> categories = List.of(category);

        when(taskCategoryService.getAllCategoriesByTaskId(anyInt())).thenReturn(categories);
 
        // Perform the GET request

        mockMvc.perform(get("/api/taskcategories/{taskId}", 1))

                .andExpect(status().isOk()) // Expect HTTP 200 OK

                .andExpect(jsonPath("$[0].categoryName").value("Test Category"));
 
        // Verify the service method was called

        verify(taskCategoryService, times(1)).getAllCategoriesByTaskId(1);

    }
 
 
}

 