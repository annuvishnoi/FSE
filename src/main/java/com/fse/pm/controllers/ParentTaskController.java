package com.fse.pm.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fse.pm.entity.ParentTask;
import com.fse.pm.exceptions.BadRequestException;
import com.fse.pm.exceptions.NotFoundException;
import com.fse.pm.services.ParentTaskService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="http://localhost:4200")
public class ParentTaskController {
	@Autowired
	ParentTaskService parentTaskService;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/parentTasks")
	public List<ParentTask> getParentTasks(){
		logger.info("start getParentTasks method>>");
		List<ParentTask> parentTasks = this.parentTaskService.getAllParentTasks();
		if(parentTasks==null || parentTasks.isEmpty()) {
			logger.info("ParentTask Records not Found!!!");
			throw new NotFoundException("ParentTask Records not Found!!!");
		}
		logger.info("end getParentTasks method>>");
		return parentTasks;
	}
	
	@GetMapping("/parentTasks/{parentTaskId}")
	public ParentTask getParentTask(@PathVariable Long parentTaskId){
		logger.info("start getParentTask method>>" + parentTaskId);
		ParentTask parentTask = this.parentTaskService.getParentTaskByParentTaskId(parentTaskId);
		if(parentTask==null) {
			logger.info("ParentTask id not found - " + parentTaskId);
			throw new NotFoundException("ParentTask id not found - " + parentTaskId);
		
		}
		logger.info("end getParentTask method>>");
		return parentTask;
	}
	
	@PostMapping("/parentTasks")
	public ParentTask addParentTask(@RequestBody ParentTask parentTask){
		
		logger.info("start addParentTask method>>");
		if(!this.parentTaskService.addParentTask(parentTask)) {
			logger.info("Cannot add new ParentTask!!!");
			throw new BadRequestException("Cannot add new ParentTask!!!");
		}
		logger.info("end addParentTask method>>");
		return parentTask;
	}
}

