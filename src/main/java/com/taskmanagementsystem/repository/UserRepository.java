package com.taskmanagementsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.taskmanagementsystem.dto.UserLoginProjection;
import com.taskmanagementsystem.dto.UserProjection;
import com.taskmanagementsystem.entity.User;

public interface UserRepository extends JpaRepository<User,Integer> {
	
	boolean existsByUsername(String username);
	
	boolean existsByEmail(String email);
	
	Optional<User> findByEmail(String email);
	
	Optional<User> findByUsername(String username);
	
	@Query("SELECT u FROM User u")
    List<User> findAllUsersWithTasks();
	
	@Query("SELECT u FROM User u JOIN u.task t WHERE t.status = 'COMPLETED'")
	List<UserProjection> findUsersWithCompletedTasks();
	
	@Query("SELECT COUNT(t) FROM Tasks t WHERE t.user.userId = :userId")
    Long countTasksForUser(@Param("userId") int userId);
	
	@Query("SELECT u.username AS username, COUNT(t) AS taskCount " +
		       "FROM User u LEFT JOIN u.task t " +
		       "GROUP BY u.userId " +
		       "ORDER BY COUNT(t) DESC")
		List<UserProjection> findUserWithMostTasks();
    
    @Query("SELECT u FROM User u")
    List<UserProjection> findAllProjectedBy();
    
    @Query("SELECT u from User u WHERE u.userId = :userId")
    Optional<UserProjection> findProjectedByUserId(@Param("userId") int userId);
    
    @Query("SELECT u.userId AS userId, u.username AS username, u.email AS email, u.fullName AS fullName, u.password AS password FROM User u WHERE u.email = :email")
    Optional<UserProjection> findByProjectedEmail(@Param("email") String email);

    
    @Query("SELECT u.userId AS userId, u.username AS username, u.email AS email, u.fullName AS fullName FROM User u WHERE u.username = :username")
    Optional<UserProjection> findByProjectedUsername(@Param("username") String username);
    
  
    @Query("SELECT u.userId AS userId, u.username AS username, u.password AS password,u.fullName AS fullName, r.roleName AS roleName " +
           "FROM User u JOIN u.roles r " +
           "WHERE u.username = :username AND u.password = :password")
   List<UserLoginProjection> authenticateUser(@Param("username") String username, @Param("password") String password);
   
}
