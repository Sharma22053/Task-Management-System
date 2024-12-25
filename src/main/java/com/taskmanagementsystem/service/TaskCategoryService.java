package com.taskmanagementsystem.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskmanagementsystem.dto.CategoryProjection;
import com.taskmanagementsystem.dto.TaskProjection;
import com.taskmanagementsystem.entity.TaskCategory;
import com.taskmanagementsystem.entity.TaskCategoryId;
import com.taskmanagementsystem.exception.TaskCategoryOperationException;
import com.taskmanagementsystem.repository.CategoryRepository;
import com.taskmanagementsystem.repository.TaskCategoryRepository;
import com.taskmanagementsystem.repository.TasksRepository;

@Service
public class TaskCategoryService {

    private TaskCategoryRepository taskCategoryRepository;
    
    private TasksRepository tasksRepository;
   
    private CategoryRepository categoryRepository;
    
    public TaskCategoryService(TaskCategoryRepository taskCategoryRepository,TasksRepository tasksRepository,CategoryRepository categoryRepository) {
    	this.taskCategoryRepository = taskCategoryRepository;
    	this.tasksRepository = tasksRepository;
    	this.categoryRepository = categoryRepository;
    }

    public Map<String, String> addTaskCategory(TaskCategory taskCategory) {
        TaskCategoryId taskCategoryId = taskCategory.getId();
        if (taskCategoryId == null) {
            throw new IllegalArgumentException("TaskCategoryId must not be null");
        }
        if (taskCategoryRepository.existsById(taskCategoryId)) {
            throw new TaskCategoryOperationException("ADDFAILS", "Task-Category already exists.");
        }

        taskCategoryRepository.save(taskCategory);

        Map<String, String> response = new HashMap<>();
        response.put("code", "POSTSUCCESS");
        response.put("message", "Task-Category added successfully.");

        return response;
    }

   
    public List<CategoryProjection> getAllCategoriesByTaskId(int taskId) {
        List<CategoryProjection> taskCategories = taskCategoryRepository.findCategoriesByTaskId(taskId);
        if (taskCategories.isEmpty()) {
            throw new TaskCategoryOperationException("GETFAILS", "No category found for a particular task.");
        }
        return taskCategories;
    }

    
    public List<TaskProjection> getAllTasksByCategoryId(int categoryId) {
        List<TaskProjection> taskCategories = taskCategoryRepository.findTasksByCategoryId(categoryId);
        if (taskCategories.isEmpty()) {
            throw new TaskCategoryOperationException("GETFAILS", "No task found for a particular category.");
        }
        return taskCategories;
    }
}
