package com.taskmanagementsystem.controller;

import java.util.List;

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

import com.taskmanagementsystem.dto.TaskProjection;
import com.taskmanagementsystem.entity.Tasks;
import com.taskmanagementsystem.service.TasksService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/tasks")
public class TasksController {
	
	private final TasksService tasksService;
	public TasksController(TasksService tasksService) {
		this.tasksService = tasksService;
	}
	
	@PostMapping("/post")
	public ResponseEntity<Object> createNewTask(@RequestBody Tasks tasks){
		
		return new ResponseEntity<>(tasksService.createNewTask(tasks),HttpStatus.OK);
	}
	
	@GetMapping("/overdue")
	public ResponseEntity<Object> getListOfOverdueTasks(){
		
		return new ResponseEntity<>(tasksService.getListOfOverdueTasks(),HttpStatus.OK);
	}
	
	@GetMapping("/priority/{priority}/status/{status}")
	public ResponseEntity<Object> getTasksByPriorityAndStatus(@PathVariable("priority") String priority, @PathVariable("status") String status) {
	    
	    List<TaskProjection> tasksListByPriorityAndStatus = tasksService.getTasksByPriorityAndStatus(priority, status);
	    return new ResponseEntity<>(tasksListByPriorityAndStatus, HttpStatus.OK);
	}
	
	@GetMapping("/due-soon")
	public ResponseEntity<Object> getTasksDueSoon(){
		List<TaskProjection> listTasksDueSoon = tasksService.getTasksDueSoon();
		return new ResponseEntity<>(listTasksDueSoon,HttpStatus.OK);
	}
	
	@GetMapping("/user/{userId}/status/{status}")
	public ResponseEntity<Object> getTasksByUserIdAndStatus(@PathVariable("userId") int userId,@PathVariable String status)
	{
		List<TaskProjection> listUserByIdStatus = tasksService.getTasksByUserIdAndStatus(userId,status);
		return new ResponseEntity<>(listUserByIdStatus,HttpStatus.OK);
	}
	
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<List<TaskProjection>> getTasksByCategoryId(@PathVariable int categoryId) {
	    List<TaskProjection> tasksByCategoryId = tasksService.getTasksByCategoryId(categoryId);
	    return new ResponseEntity<>(tasksByCategoryId, HttpStatus.OK);
	}
	
	@PutMapping("/update/{taskId}")
	public ResponseEntity<Object> updateTaskDetails(@PathVariable("taskId") int taskId ,  @RequestBody Tasks updatedTaskDetails ){
		return new ResponseEntity<>(tasksService.updateTaskDetails(taskId,updatedTaskDetails),HttpStatus.OK);
	}
	
	@DeleteMapping("/{taskId}")
	public ResponseEntity<Object> deleteTask(@PathVariable("taskId") int taskId){
		return new ResponseEntity<>(tasksService.deleteTask(taskId),HttpStatus.OK);
	}

}
