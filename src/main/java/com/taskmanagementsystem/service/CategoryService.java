package com.taskmanagementsystem.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taskmanagementsystem.dto.CategoryProjection;
import com.taskmanagementsystem.entity.Category;
import com.taskmanagementsystem.exception.CategoryOperationException;
import com.taskmanagementsystem.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Transactional
	public Map<String, String> createNewCategory(Category category) {

		Optional<Category> existingCategory = categoryRepository.findById(category.getCategoryId());
		if (existingCategory.isPresent()) {
			throw new CategoryOperationException("ADDFAILS", "Category already exists.");
		}
		categoryRepository.save(category);

		Map<String, String> response = new LinkedHashMap<>();
		response.put("code", "POSTSUCCESS");
		response.put("message", "Category created successfully");
		return response;
	}

	public List<CategoryProjection> getListsOfCategory() {
		List<CategoryProjection> listOfCategories = categoryRepository.findAllProjectedBy();
		if(listOfCategories.isEmpty()) {
			throw new CategoryOperationException("GETFAILS", "List is empty");
		}
		return listOfCategories;
	}

	public CategoryProjection getCategoryByCategoryId(int categoryId) {
	    Optional<CategoryProjection> existingCategory = categoryRepository.findCategoryProjectionById(categoryId);
	    if (!existingCategory.isPresent()) {
	        throw new CategoryOperationException("GETFAILS", "Category doesn't exist");
	    }
	    
	    return existingCategory.get();
	}
	
	public Map<String, Integer> getCategoriesWithTaskCount() {
        List<Category> categories = categoryRepository.findAll();
        Map<String, Integer> categoryTaskCount = new LinkedHashMap<>();

        for (Category category : categories) {
            int taskCount = category.getTasks().size(); // Counting the tasks in each category
            categoryTaskCount.put(category.getCategoryName(), taskCount);
        }

        return categoryTaskCount;
    }

	public Map<String,String> updateCategory(int categoryId, Category category) {
		 Category existingCategory = categoryRepository.findById(categoryId)
		            .orElseThrow(() -> new CategoryOperationException("UPDTFAILS", "Category doesn't exist"));
		 
        
        existingCategory.setCategoryName(category.getCategoryName());
        categoryRepository.save(existingCategory);

        Map<String, String> response = new LinkedHashMap<>();
		response.put("code", "UPDATESUCCESS");
		response.put("message", "Category updated successfully");
		return response;
    }
	
	@Transactional
	public Map<String, String> deleteCategory(int categoryId) {
		Category existingCategory = categoryRepository.findById(categoryId)
	            .orElseThrow(() -> new CategoryOperationException("UPDTFAILS", "Category doesn't exist"));
		
		categoryRepository.deleteById(categoryId);
		Map<String, String> response = new LinkedHashMap<>();
		response.put("code", "UPDATESUCCESS");
		response.put("message", "Category updated successfully");
		return response;
		
	}

}
