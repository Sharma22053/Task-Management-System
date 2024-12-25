package com.taskmanagementsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.taskmanagementsystem.dto.CategoryProjection;
import com.taskmanagementsystem.entity.Category;

public interface CategoryRepository extends JpaRepository<Category,Integer>{
	 
	List<CategoryProjection> findAllProjectedBy();
	
	@Query("SELECT c.categoryId AS categoryId, c.categoryName AS categoryName FROM Category c WHERE c.categoryId = :categoryId")
    Optional<CategoryProjection> findCategoryProjectionById(@Param("categoryId") int categoryId);
	
	@Query("SELECT c.categoryId AS categoryId, c.categoryName AS categoryName FROM Category c " +
	           "JOIN c.tasks t " +
	           "JOIN t.user u " +
	           "WHERE u.userId = :userId")
	List<CategoryProjection> findCategoriesByUserId(int userId);
}