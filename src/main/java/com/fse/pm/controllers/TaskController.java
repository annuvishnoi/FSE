package com.fse.pm.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fse.pm.entity.Task;
import com.fse.pm.entity.User;
import com.fse.pm.exceptions.BadRequestException;
import com.fse.pm.exceptions.NotFoundException;
import com.fse.pm.services.TaskService;
import com.fse.pm.services.UserService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="http://localhost:4200")
public class TaskController {
	@Autowired
	TaskService taskService;
	@Autowired
	UserService userService;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/tasks")
	public List<Task> getTasks(){
		logger.info("start getTasks method>>");
		List<Task> tasks = this.taskService.getAllTasks();
		if(tasks==null || tasks.isEmpty()) {
			logger.info("Task Records not Found!!!");
			throw new NotFoundException("Task Records not Found!!!");
		}
		logger.info("end getTasks method>>");
		return tasks;
	}
	
	@GetMapping("/tasks/{taskId}")
	public Task getTask(@PathVariable Long taskId){
		logger.info("start getTask method>>" + taskId);
		if(taskId == null) {
			logger.info("Cannot updateTaskByTaskId!!!");
			throw new BadRequestException("TaskId can not be null!!");
		}
		Task task = this.taskService.getTaskByTaskId(taskId);
		if(task==null) {
			logger.info("Task id not found - " + taskId);
			
			throw new NotFoundException("Task id not found - " + taskId);
		}
		User user = userService.getUserByTaskId(taskId);
		task.setSelectedEmployee(user);
		logger.info("end getTask method>>");
		return task;
	}
	
	@PostMapping("/tasks")
	public Task addTask(@RequestBody Task task){
		
		logger.info("start addTask method>>");
		if(!this.taskService.addTask(task)) {
			logger.info("Cannot add new Task!!!");
			throw new BadRequestException("Cannot add new Task!!!");
		}
		
		
		User selectedEmployee = task.getSelectedEmployee();
		if(selectedEmployee.getUserId() != null) {
			userService.updateTaskId(selectedEmployee.getUserId(), task.getTaskId());
		}
		logger.info("end addTask method>>");
		return task;
	
	}
	
	@GetMapping("/tasks/updateTaskByTaskId")
	public Integer updateTaskByTaskId(@RequestParam(value = "taskId", required = true) Long taskId){
		
		logger.info("start updateTaskByTaskId method>>");
		
		if(taskId == null) {
			logger.info("Cannot updateTaskByTaskId!!!");
			throw new BadRequestException("TaskId can not be null!!");
		}
		int count = this.taskService.updateTaskStatus(taskId);
		if(this.taskService.updateTaskStatus(taskId) != 1) {
			logger.info("Cannot updateTaskByTaskId!!!");
			throw new BadRequestException("Cannot updateTaskByTaskId!!!");
		}
		logger.info("end updateTaskByTaskId method>>");
		return count;
	}
	
	@PutMapping("/tasks")
	public Task editTask(@RequestBody Task task){
		logger.info("start editTask method>>");
		if(!this.taskService.updateTask(task)) {
			logger.info("Cannot update new Task!!!");
			throw new BadRequestException("Cannot update new Task!!!");
		}
		logger.info("end editTask method>>");
		
		return task;
	}
	@DeleteMapping("/tasks/{taskId}")
	public boolean deleteTask(@PathVariable Long taskId){
		logger.info("start deleteTask method>>");
		if(!this.taskService.deleteTask(taskId)) {
			logger.info("Cannot delete Task with id!!!" + taskId);
			throw new BadRequestException("Cannot delete Task with id!!!" + taskId);

		}
		logger.info("start deleteTask method>>");
		return true;
	
	}
}

