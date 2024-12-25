package com.taskmanagementsystem.controller;
 
import com.taskmanagementsystem.dto.CategoryProjection;

import com.taskmanagementsystem.entity.Category;

import com.taskmanagementsystem.service.CategoryService;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;

import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.ArgumentMatchers.eq;

import static org.mockito.ArgumentMatchers.anyInt;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.when;

import static org.mockito.Mockito.verify;

import static org.mockito.Mockito.times;
 
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
 
import java.util.List;

import java.util.Map;
 
@ExtendWith(MockitoExtension.class)

public class CategoryControllerTests {
 
    @InjectMocks

    private CategoryController categoryController;
 
    @Mock

    private CategoryService categoryService;
 
    private MockMvc mockMvc;
 
    @BeforeEach

    public void setUp() {

        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();

    }

    // 1. Test for POST: createNewCategory

    @Test

    public void testCreateNewCategory() throws Exception {

        Category category = new Category(1, "Development");
 
        when(categoryService.createNewCategory(any(Category.class))).thenReturn(Map.of("status", "success"));
 
        mockMvc.perform(post("/api/categories/post")

                        .contentType("application/json")

                        .content("{ \"categoryId\": 1, \"categoryName\": \"Development\" }"))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$.status").value("success"));
 
        verify(categoryService, times(1)).createNewCategory(any(Category.class));

    }

// 2. Test for GET all: getListOfCategory

    @Test

    public void testGetListOfCategory() throws Exception {

        List<CategoryProjection> categoryProjections = List.of(

                new CategoryProjection() {

                    @Override

                    public int getCategoryId() {

                        return 1;

                    }
 
                    @Override

                    public String getCategoryName() {

                        return "Development";

                    }

                },

                new CategoryProjection() {

                    @Override

                    public int getCategoryId() {

                        return 2;

                    }
 
                    @Override

                    public String getCategoryName() {

                        return "Testing";

                    }

                }

        );
 
        when(categoryService.getListsOfCategory()).thenReturn(categoryProjections);
 
        mockMvc.perform(get("/api/categories/all"))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$[0].categoryId").value(1))

                .andExpect(jsonPath("$[0].categoryName").value("Development"))

                .andExpect(jsonPath("$[1].categoryId").value(2))

                .andExpect(jsonPath("$[1].categoryName").value("Testing"));
 
        verify(categoryService, times(1)).getListsOfCategory();

    }

// 3. Test for GET by ID: getCategoryByCategoryId

    @Test

    public void testGetCategoryByCategoryId() throws Exception {

        CategoryProjection categoryProjection = new CategoryProjection() {

            @Override

            public int getCategoryId() {

                return 1;

            }
 
            @Override

            public String getCategoryName() {

                return "Development";

            }

        };
 
        when(categoryService.getCategoryByCategoryId(anyInt())).thenReturn(categoryProjection);
 
        mockMvc.perform(get("/api/categories/{categoryId}", 1))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$.categoryId").value(1))

                .andExpect(jsonPath("$.categoryName").value("Development"));
 
        verify(categoryService, times(1)).getCategoryByCategoryId(1);

    }

    // 4. Test for GET task count: getCategoriesWithTaskCount

    @Test

    public void testGetCategoriesWithTaskCount() throws Exception {

        Map<String, Integer> categoryTaskCount = Map.of(

                "Development", 5,

                "Testing", 3

        );
 
        when(categoryService.getCategoriesWithTaskCount()).thenReturn(categoryTaskCount);
 
        mockMvc.perform(get("/api/categories/task-count"))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$.Development").value(5))

                .andExpect(jsonPath("$.Testing").value(3));
 
        verify(categoryService, times(1)).getCategoriesWithTaskCount();

    }

    // 5. Test for PUT: updateCategory
 
 
    @Test

    public void testUpdateCategory() throws Exception {

        // Create a Category object to simulate the input

        Category category = new Category(1, "Development");
 
        // Mock the service method using argument matchers

        when(categoryService.updateCategory(eq(1), any(Category.class))).thenReturn(Map.of("status", "updated"));
 
        // Perform the PUT request and verify the response

        mockMvc.perform(put("/api/categories/update/{categoryId}", 1)

                        .contentType("application/json")

                        .content("{ \"categoryId\": 1, \"categoryName\": \"Development\" }"))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$.status").value("updated"));
 
        // Verify that the service method was called with the expected arguments

        verify(categoryService, times(1)).updateCategory(eq(1), any(Category.class));

    }
 
 
    

    // 6. Test for DELETE: deleteCategory

    @Test

    public void testDeleteCategory() throws Exception {

        when(categoryService.deleteCategory(anyInt())).thenReturn(Map.of("status", "deleted"));
 
        mockMvc.perform(delete("/api/categories/delete/{categoryId}", 1))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$.status").value("deleted"));
 
        verify(categoryService, times(1)).deleteCategory(1);

    }
 
}

 