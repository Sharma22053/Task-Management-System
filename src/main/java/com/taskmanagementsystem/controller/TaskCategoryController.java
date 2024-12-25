package com.taskmanagementsystem.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskmanagementsystem.dto.CategoryProjection;
import com.taskmanagementsystem.dto.TaskProjection;
import com.taskmanagementsystem.entity.TaskCategory;
import com.taskmanagementsystem.service.TaskCategoryService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/taskcategories")
public class TaskCategoryController {

    @Autowired
    private TaskCategoryService taskCategoryService;

    
    @PostMapping("/post")
    public ResponseEntity<Map<String, String>> addTaskCategory(@RequestBody TaskCategory taskCategory) {
        Map<String, String> response = taskCategoryService.addTaskCategory(taskCategory);
        return ResponseEntity.ok(response);
    }

    
    @GetMapping("/{taskId}")
    public ResponseEntity<List<CategoryProjection>> getAllCategoriesByTaskId(@PathVariable int taskId) {
        List<CategoryProjection> categories = taskCategoryService.getAllCategoriesByTaskId(taskId);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<TaskProjection>> getAllTasksByCategoryId(@PathVariable int categoryId) {
        List<TaskProjection> tasks = taskCategoryService.getAllTasksByCategoryId(categoryId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }
}
