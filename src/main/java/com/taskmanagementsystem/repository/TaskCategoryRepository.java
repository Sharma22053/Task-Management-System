package com.taskmanagementsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.taskmanagementsystem.dto.CategoryProjection;
import com.taskmanagementsystem.dto.TaskProjection;
import com.taskmanagementsystem.entity.TaskCategory;
import com.taskmanagementsystem.entity.TaskCategoryId;

@Repository
public interface TaskCategoryRepository extends JpaRepository<TaskCategory, TaskCategoryId> {
    
    
//	// Custom query to fetch categories by taskId
    @Query("SELECT c.categoryId AS categoryId, c.categoryName AS categoryName " +
            "FROM TaskCategory tc " +
            "JOIN tc.category c " +
            "WHERE tc.task.taskId = :taskId")
     List<CategoryProjection> findCategoriesByTaskId(@Param("taskId") int taskId);

     // Custom query to fetch tasks by categoryId
    @Query("SELECT t.taskId AS taskId, t.taskName AS taskName, t.description AS description, t.dueDate AS dueDate, " +
            "t.priority AS priority, t.status AS status " +
            "FROM TaskCategory tc " +
            "JOIN tc.task t " +
            "WHERE tc.category.categoryId = :categoryId")
 List<TaskProjection> findTasksByCategoryId(@Param("categoryId") int categoryId);

    
    
 
    
    
}
