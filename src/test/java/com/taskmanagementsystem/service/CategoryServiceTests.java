package com.taskmanagementsystem.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.taskmanagementsystem.dto.CategoryProjection;
import com.taskmanagementsystem.entity.Category;
import com.taskmanagementsystem.exception.CategoryOperationException;
import com.taskmanagementsystem.repository.CategoryRepository;
import com.taskmanagementsystem.service.CategoryService;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTests {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void createNewCategory_CategoryDoesNotExist_Success() {
        // Arrange
        Category category = new Category(1, "Development");
        when(categoryRepository.findById(1)).thenReturn(Optional.empty());

        // Act
        Map<String, String> response = categoryService.createNewCategory(category);

        // Assert
        verify(categoryRepository).save(category);
        Assertions.assertEquals("POSTSUCCESS", response.get("code"));
        Assertions.assertEquals("Category created successfully", response.get("message"));
    }

   
    @Test
    void getListsOfCategory_Found_Success() {
        // Arrange
        CategoryProjection mockProjection = mock(CategoryProjection.class);
        when(categoryRepository.findAllProjectedBy()).thenReturn(List.of(mockProjection));

        // Act
        List<CategoryProjection> result = categoryService.getListsOfCategory();

        // Assert
        Assertions.assertFalse(result.isEmpty());
        verify(categoryRepository).findAllProjectedBy();
    }


    @Test
    void getCategoryByCategoryId_Found_Success() {
        // Arrange
        CategoryProjection mockProjection = mock(CategoryProjection.class);
        when(categoryRepository.findCategoryProjectionById(1)).thenReturn(Optional.of(mockProjection));

        // Act
        CategoryProjection result = categoryService.getCategoryByCategoryId(1);

        // Assert
        Assertions.assertNotNull(result);
        verify(categoryRepository).findCategoryProjectionById(1);
    }

    @Test
    void getCategoryByCategoryId_NotFound_ThrowsException() {
        // Arrange
        when(categoryRepository.findCategoryProjectionById(1)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(CategoryOperationException.class, () -> 
            categoryService.getCategoryByCategoryId(1)
        );
    }

   

    @Test
    void updateCategory_FoundAndUpdated_Success() {
        // Arrange
        Category existingCategory = new Category(1, "Development");
        Category updatedCategory = new Category(1, "Design");

        when(categoryRepository.findById(1)).thenReturn(Optional.of(existingCategory));

        // Act
        Map<String, String> response = categoryService.updateCategory(1, updatedCategory);

        // Assert
        verify(categoryRepository).save(existingCategory);
        Assertions.assertEquals("UPDATESUCCESS", response.get("code"));
        Assertions.assertEquals("Category updated successfully", response.get("message"));
    }

    @Test
    void deleteCategory_FoundAndDeleted_Success() {
        // Arrange
        Category existingCategory = new Category(1, "Development");
        when(categoryRepository.findById(1)).thenReturn(Optional.of(existingCategory));

        // Act
        Map<String, String> response = categoryService.deleteCategory(1);

        // Assert
        verify(categoryRepository).deleteById(1);
        Assertions.assertEquals("UPDATESUCCESS", response.get("code"));
        Assertions.assertEquals("Category updated successfully", response.get("message"));
    }

    
}
