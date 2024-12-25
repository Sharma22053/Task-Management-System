package com.taskmanagementsystem.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taskmanagementsystem.dto.TaskProjection;
import com.taskmanagementsystem.entity.Project;
import com.taskmanagementsystem.entity.Tasks;
import com.taskmanagementsystem.entity.User;
import com.taskmanagementsystem.exception.ProjectOperationException;
import com.taskmanagementsystem.exception.TasksOperationException;
import com.taskmanagementsystem.repository.ProjectRepository;
import com.taskmanagementsystem.repository.TasksRepository;
import com.taskmanagementsystem.repository.UserRepository;

@Service
public class TasksService {

	@Autowired
	private TasksRepository tasksRepository;
	@Autowired
	private  ProjectRepository projectRepository;
	@Autowired
	private  UserRepository userRepository;


	@Transactional
	public Map<String, String> createNewTask(Tasks tasks) {

		Project project = projectRepository
				.findById(tasks.getProject().getProjectId())
				.orElseThrow(() -> new ProjectOperationException("ADDFAILS", "Project does not exist"));

		User user = userRepository
				.findById(tasks.getUser().getUserId())
				.orElseThrow(() -> new TasksOperationException("ADDFAILS","User does not exist"));

		tasks.setProject(project);
		tasks.setUser(user);
		tasksRepository.save(tasks);
		Map<String, String> response = new LinkedHashMap<>();
		response.put("code", "POSTSUCCESS");
		response.put("message", "Task added successfully");

		return response;
	}


	@Transactional
	public List<TaskProjection> getListOfOverdueTasks() {
		LocalDate currentDate = LocalDate.now();
		List<TaskProjection> tasksList = tasksRepository.findOverdueTasks(currentDate);
		if (tasksList.isEmpty()) {
			throw new TasksOperationException("GETFAILS","No tasks are overdue");
		}
		return tasksList;
	}

	@Transactional
	public List<TaskProjection> getTasksByPriorityAndStatus(String priority, String status) {

		List<TaskProjection> listByPriorityAndStatus = tasksRepository.findByPriorityAndStatus(priority, status);
		if(listByPriorityAndStatus.isEmpty()) {

			throw new TasksOperationException("GETALLFAILS","Task list is empty");
		}

		return listByPriorityAndStatus;
	}

	@Transactional
	public List<TaskProjection> getTasksDueSoon() {
		LocalDate currentDate = LocalDate.now();
		LocalDate dueSoonDate = currentDate.plusDays(7);  

		List<TaskProjection> tasksDueSoon = tasksRepository.findTasksDueSoon(currentDate, dueSoonDate);

		if (tasksDueSoon.isEmpty()) {
			throw new TasksOperationException("GETFAILS", "No tasks are due soon.");
		}
		return tasksDueSoon;
	}


	public List<TaskProjection> getTasksByUserIdAndStatus(int userId, String status) {
		List<TaskProjection> tasks = tasksRepository.findByUserIdAndStatus(userId, status);

		if (tasks.isEmpty()) {
			throw new TasksOperationException("GETFAILS", "Task list is empty");
		}

		return tasks;
	}
	
	public List<TaskProjection> getTasksByCategoryId(int categoryId) {
	    List<TaskProjection> tasks = tasksRepository.findTasksByCategoryId(categoryId);
	    if (tasks.isEmpty()) {
	        throw new TasksOperationException("GETALLFAILS", "Task list is empty");
	    }
	    return tasks;
	}


	@Transactional
    public Map<String,String> updateTaskDetails(int taskId, Tasks updatedTaskDetails) {
     
        Tasks existingTask = tasksRepository.findById(taskId)
                .orElseThrow(() -> new TasksOperationException("UPDTFAILS", "Task doesn't exist"));
       
        if (updatedTaskDetails.getTaskName() != null) {
            existingTask.setTaskName(updatedTaskDetails.getTaskName());
        }
        if (updatedTaskDetails.getDescription() != null) {
            existingTask.setDescription(updatedTaskDetails.getDescription());
        }
        if (updatedTaskDetails.getDueDate() != null) {
            existingTask.setDueDate(updatedTaskDetails.getDueDate());
        }
        if (updatedTaskDetails.getPriority() != null) {
            existingTask.setPriority(updatedTaskDetails.getPriority());
        }
        if (updatedTaskDetails.getStatus() != null) {
            existingTask.setStatus(updatedTaskDetails.getStatus());
        }
        if (updatedTaskDetails.getProject() != null) {
            existingTask.setProject(updatedTaskDetails.getProject());
        }
        if (updatedTaskDetails.getUser() != null) {
            existingTask.setUser(updatedTaskDetails.getUser());
        }
        
        tasksRepository.save(existingTask);
        Map<String, String> successResponse = new LinkedHashMap<>();
	    successResponse.put("code", "UPDATESUCCESS");
	    successResponse.put("message", "Task updated successfully");
		return successResponse;
    }
	
	@Transactional
    public Map<String,String> deleteTask(int taskId) {
        
		Tasks task = tasksRepository.findById(taskId)
                .orElseThrow(() -> new TasksOperationException("DELETEFAILS", "Task not found with ID: " + taskId));

        tasksRepository.deleteById(taskId);
        Map<String, String> successResponse = new LinkedHashMap<>();
	    successResponse.put("code", "DELETESUCCESS");
	    successResponse.put("message", "Task deleted successfully");
		return successResponse;
    }


	
}

