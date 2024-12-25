package com.taskmanagementsystem.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.taskmanagementsystem.dto.CategoryProjection;
import com.taskmanagementsystem.dto.TaskProjection;
import com.taskmanagementsystem.entity.Tasks;

public interface TasksRepository extends JpaRepository<Tasks, Integer> {
    

	
	@Query("SELECT t.taskId AS taskId, t.taskName AS taskName, t.description AS description, t.dueDate AS dueDate, t.priority AS priority, t.status AS status " +
	       "FROM Tasks t WHERE t.dueDate < :currentDate AND t.status != 'completed'")
	List<TaskProjection> findOverdueTasks(@Param("currentDate") LocalDate currentDate);

	
	@Query("SELECT t.taskId AS taskId, t.taskName AS taskName, t.description AS description, t.dueDate AS dueDate, t.priority AS priority, t.status AS status " +
		       "FROM Tasks t WHERE t.priority = :priority AND t.status = :status")
		List<TaskProjection> findByPriorityAndStatus(@Param("priority") String priority, @Param("status") String status);
	
	@Query("SELECT t.taskId AS taskId, t.taskName AS taskName, t.description AS description, t.dueDate AS dueDate, t.priority AS priority, t.status AS status " +
	           "FROM Tasks t WHERE t.dueDate BETWEEN :currentDate AND :dueSoonDate AND t.status != 'completed'")
	    List<TaskProjection> findTasksDueSoon(@Param("currentDate") LocalDate currentDate, @Param("dueSoonDate") LocalDate dueSoonDate);
	
	 @Query("SELECT t.taskId AS taskId, t.taskName AS taskName, t.description AS description, t.dueDate AS dueDate, t.priority AS priority, t.status AS status " +
	           "FROM Tasks t WHERE t.user.userId = :userId AND t.status = :status")
	    List<TaskProjection> findByUserIdAndStatus(@Param("userId") int userId, @Param("status") String status);
	 
	 @Query("SELECT t.taskId AS taskId, t.taskName AS taskName, t.description AS description, " +
		       "t.dueDate AS dueDate, t.priority AS priority, t.status AS status " +
		       "FROM Tasks t JOIN t.categories c WHERE c.categoryId = :categoryId")
		List<TaskProjection> findTasksByCategoryId(@Param("categoryId") int categoryId);
	 
	    List<TaskProjection> findByUser_UserId(int userId);


}