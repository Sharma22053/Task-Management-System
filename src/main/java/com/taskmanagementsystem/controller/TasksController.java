package com.taskmanagementsystem.controller;

import java.util.List;
import java.util.Map;

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

@CrossOrigin(origins = {"http://localhost:4200"})  //// Allows cross-origin requests from Angular frontend.
@RestController
@RequestMapping("/api/tasks")
public class TasksController {
	
	private final TasksService tasksService;   /*@Autowired can also be used*/
	public TasksController(TasksService tasksService) {
		this.tasksService = tasksService;
	}
	
	@PostMapping("/post") //http://localhost:8091/api/tasks/post
	public ResponseEntity<Map<String,String>> createNewTask(@RequestBody Tasks tasks){
		
		return new ResponseEntity<>(tasksService.createNewTask(tasks),HttpStatus.OK);
	}
	
	@GetMapping("/overdue")  //http://localhost:8091/api/tasks/overdue
	public ResponseEntity<List<TaskProjection>> getListOfOverdueTasks(){
		
		return new ResponseEntity<>(tasksService.getListOfOverdueTasks(),HttpStatus.OK);
	}
	
	@GetMapping("/priority/{priority}/status/{status}") //http://localhost:8091/api/tasks/priority/{priority}/status/{status}
	public ResponseEntity<List<TaskProjection>> getTasksByPriorityAndStatus(@PathVariable("priority") String priority, @PathVariable("status") String status) {
	    
	    List<TaskProjection> tasksListByPriorityAndStatus = tasksService.getTasksByPriorityAndStatus(priority, status);
	    return new ResponseEntity<>(tasksListByPriorityAndStatus, HttpStatus.OK);
	}
	
	@GetMapping("/due-soon") //http://localhost:8091/api/tasks/due-soon
	public ResponseEntity<List<TaskProjection>> getTasksDueSoon(){
		List<TaskProjection> listTasksDueSoon = tasksService.getTasksDueSoon();
		return new ResponseEntity<>(listTasksDueSoon,HttpStatus.OK);
	}
	
	@GetMapping("/user/{userId}/status/{status}") //http://localhost:8091/api/tasks/user/{userId}/status/{status}
	public ResponseEntity<List<TaskProjection>> getTasksByUserIdAndStatus(@PathVariable("userId") int userId,@PathVariable String status)
	{
		List<TaskProjection> listUserByIdStatus = tasksService.getTasksByUserIdAndStatus(userId,status);
		return new ResponseEntity<>(listUserByIdStatus,HttpStatus.OK);
	}
	
	@GetMapping("/category/{categoryId}") //http://localhost:8091/api/tasks/category/{categoryId}
	public ResponseEntity<List<TaskProjection>> getTasksByCategoryId(@PathVariable int categoryId) {
	    List<TaskProjection> tasksByCategoryId = tasksService.getTasksByCategoryId(categoryId);
	    return new ResponseEntity<>(tasksByCategoryId, HttpStatus.OK);
	}
	
	@PutMapping("/update/{taskId}") //http://localhost:8091/api/tasks/update/{taskId}
	public ResponseEntity<Map<String,String>> updateTaskDetails(@PathVariable("taskId") int taskId ,  @RequestBody Tasks updatedTaskDetails ){
		return new ResponseEntity<>(tasksService.updateTaskDetails(taskId,updatedTaskDetails),HttpStatus.OK);
	}
	
	@DeleteMapping("/{taskId}") //http://localhost:8091/api/tasks/{taskId}
	public ResponseEntity<Map<String,String>> deleteTask(@PathVariable("taskId") int taskId){
		return new ResponseEntity<>(tasksService.deleteTask(taskId),HttpStatus.OK);
	}

}
