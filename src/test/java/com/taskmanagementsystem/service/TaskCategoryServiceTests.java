package com.taskmanagementsystem.service;
 
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
 
import com.taskmanagementsystem.dto.CategoryProjection;

import com.taskmanagementsystem.dto.TaskProjection;

import com.taskmanagementsystem.entity.TaskCategory;

import com.taskmanagementsystem.entity.TaskCategoryId;

import com.taskmanagementsystem.exception.TaskCategoryOperationException;

import com.taskmanagementsystem.repository.CategoryRepository;

import com.taskmanagementsystem.repository.TaskCategoryRepository;

import com.taskmanagementsystem.repository.TasksRepository;
 
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;

import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
 
import java.util.*;
 
@ExtendWith(MockitoExtension.class)

public class TaskCategoryServiceTests {
 
    @Mock

    private TaskCategoryRepository taskCategoryRepository;
 
    @Mock

    private TasksRepository tasksRepository;
 
    @Mock

    private CategoryRepository categoryRepository;
 
    @InjectMocks

    private TaskCategoryService taskCategoryService;
 
    private TaskCategory taskCategory;

    private TaskCategoryId taskCategoryId;
 
    @BeforeEach

    public void setUp() {

        taskCategoryId = new TaskCategoryId(1, 1);

        taskCategory = new TaskCategory();

        taskCategory.setId(taskCategoryId);

    }
 
    // Test for addTaskCategory

    @Test

    public void testAddTaskCategory_Success() {

        when(taskCategoryRepository.existsById(taskCategoryId)).thenReturn(false);
 
        Map<String, String> result = taskCategoryService.addTaskCategory(taskCategory);
 
        assertEquals("POSTSUCCESS", result.get("code"));

        assertEquals("Task-Category added successfully.", result.get("message"));

        verify(taskCategoryRepository, times(1)).save(taskCategory);

    }
 
    @Test

    public void testAddTaskCategory_TaskCategoryAlreadyExists() {

        when(taskCategoryRepository.existsById(taskCategoryId)).thenReturn(true);
 
        TaskCategoryOperationException exception = assertThrows(TaskCategoryOperationException.class, () -> {

            taskCategoryService.addTaskCategory(taskCategory);

        });
 
        assertEquals("ADDFAILS", exception.getCode());

        assertEquals("Task-Category already exists.", exception.getMessage());

        verify(taskCategoryRepository, never()).save(any());

    }
 
    @Test

    public void testAddTaskCategory_NullTaskCategoryId() {

        taskCategory.setId(null);
 
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {

            taskCategoryService.addTaskCategory(taskCategory);

        });
 
        assertEquals("TaskCategoryId must not be null", exception.getMessage());

        verify(taskCategoryRepository, never()).save(any());

    }
 
    // Test for getAllCategoriesByTaskId

    @Test

    public void testGetAllCategoriesByTaskId_Success() {

        int taskId = 1;

        List<CategoryProjection> mockCategories = List.of(mock(CategoryProjection.class));
 
        when(taskCategoryRepository.findCategoriesByTaskId(taskId)).thenReturn(mockCategories);
 
        List<CategoryProjection> result = taskCategoryService.getAllCategoriesByTaskId(taskId);
 
        assertNotNull(result);

        assertEquals(1, result.size());

    }
 
    @Test

    public void testGetAllCategoriesByTaskId_NoCategoriesFound() {

        int taskId = 1;
 
        when(taskCategoryRepository.findCategoriesByTaskId(taskId)).thenReturn(Collections.emptyList());
 
        TaskCategoryOperationException exception = assertThrows(TaskCategoryOperationException.class, () -> {

            taskCategoryService.getAllCategoriesByTaskId(taskId);

        });
 
        assertEquals("GETFAILS", exception.getCode());

        assertEquals("No category found for a particular task.", exception.getMessage());

    }
 
    // Test for getAllTasksByCategoryId

    @Test

    public void testGetAllTasksByCategoryId_Success() {

        int categoryId = 1;

        List<TaskProjection> mockTasks = List.of(mock(TaskProjection.class));
 
        when(taskCategoryRepository.findTasksByCategoryId(categoryId)).thenReturn(mockTasks);
 
        List<TaskProjection> result = taskCategoryService.getAllTasksByCategoryId(categoryId);
 
        assertNotNull(result);

        assertEquals(1, result.size());

    }
 
    @Test

    public void testGetAllTasksByCategoryId_NoTasksFound() {

        int categoryId = 1;
 
        when(taskCategoryRepository.findTasksByCategoryId(categoryId)).thenReturn(Collections.emptyList());
 
        TaskCategoryOperationException exception = assertThrows(TaskCategoryOperationException.class, () -> {

            taskCategoryService.getAllTasksByCategoryId(categoryId);

        });
 
        assertEquals("GETFAILS", exception.getCode());

        assertEquals("No task found for a particular category.", exception.getMessage());

    }

}

 