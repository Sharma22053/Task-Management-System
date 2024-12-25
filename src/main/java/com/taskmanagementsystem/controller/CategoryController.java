package com.taskmanagementsystem.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.taskmanagementsystem.dto.CategoryProjection;
import com.taskmanagementsystem.entity.Category;
import com.taskmanagementsystem.service.CategoryService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/api/categories")
@RestController
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping("/post")
	public ResponseEntity<Map<String,String>> createNewCategory(@RequestBody Category category){

		Map<String,String> successResponse = categoryService.createNewCategory(category);
		return new ResponseEntity<>(successResponse,HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<List<CategoryProjection>> getListOfCategory(){

		List<CategoryProjection> listOfCategories = categoryService.getListsOfCategory();
		return new ResponseEntity<>(listOfCategories,HttpStatus.OK);
	}

	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryProjection> getCategoryByCategoryId(@PathVariable int categoryId){

		CategoryProjection categoryById = categoryService.getCategoryByCategoryId(categoryId);
		return new ResponseEntity<>(categoryById,HttpStatus.OK);
	}

	@GetMapping("/task-count")
	public ResponseEntity<Map<String, Integer>> getCategoriesWithTaskCount() {
		Map<String, Integer> categoryTaskCount = categoryService.getCategoriesWithTaskCount();
		return ResponseEntity.ok(categoryTaskCount);
	}

	@PutMapping("/update/{categoryId}")
	public ResponseEntity<Map<String,String>> updateCategory(@PathVariable int categoryId, @RequestBody Category category) {
		Map<String,String> response = categoryService.updateCategory(categoryId, category);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}

	@DeleteMapping("/delete/{categoryId}")
	public ResponseEntity<Map<String,String>> deleteCategory(@PathVariable int categoryId){

		Map<String,String> successResponse = categoryService.deleteCategory(categoryId);
		return new ResponseEntity<>(successResponse,HttpStatus.OK);
	}
}
