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
import org.springframework.web.bind.annotation.RestController;

import com.fse.pm.entity.Project;
import com.fse.pm.entity.User;
import com.fse.pm.exceptions.BadRequestException;
import com.fse.pm.exceptions.NotFoundException;
import com.fse.pm.services.ProjectService;
import com.fse.pm.services.UserService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="http://localhost:4200")
public class ProjectController {
	@Autowired
	ProjectService projectService;
	@Autowired
	UserService userService;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/projects")
	public List<Project> getProjects(){
		logger.info("start getProjects method>>");
		List<Project> projects = this.projectService.getAllProjects();
		if(projects==null || projects.isEmpty()) {
			logger.info("Project Records not Found!!!");
			throw new NotFoundException("Project Records not Found!!!");
		}
		for (Project project : projects) {
			User user = userService.getUserByProjectId(project.getProjectId());
			project.setSelectedEmployee(user);
		}
		logger.info("end getProjects method>>");
		return projects;
	}
	
	@GetMapping("/projects/{projectId}")
	public Project getProject(@PathVariable Long projectId){
		logger.info("start getProject method>>" + projectId);
		Project project = this.projectService.getProjectByProjectId(projectId);
		if(project==null) {
			logger.info("Project id not found - " + projectId);
			
			throw new NotFoundException("Project id not found - " + projectId);
		}
		logger.info("end getProject method>>");
		return project;
	}
	@PostMapping("/projects")
	public Project addProject(@RequestBody Project project){
		
		logger.info("start addProject method>>");
		if(project == null) {
			logger.info("Project can not be null!!!");
			throw new BadRequestException("Project can not be null!!!");
		}
		this.projectService.addProject(project);
		if(project.getSelectedEmployee() != null) {
			User selectedEmployee = project.getSelectedEmployee();
			if(selectedEmployee.getUserId() != null) {
				userService.updateProjectId(selectedEmployee.getUserId(), project.getProjectId());
			}
		}
		logger.info("end addProject method>>");
		return project;
	}
	
	@PutMapping("/projects")
	public Project editProject(@RequestBody Project project){
		logger.info("start editProject method>>");
		if(project == null) {
			logger.info("Project can not be null!!!");
			throw new BadRequestException("Project can not be null!!!");
		}
		this.projectService.updateProject(project);
		if(project.getSelectedEmployee() != null) {
			User selectedEmployee = project.getSelectedEmployee();
			if(selectedEmployee.getUserId() != null) {
				userService.updateProjectId(selectedEmployee.getUserId(), project.getProjectId());
			}
		}
		logger.info("end editProject method>>");
		
		return project;
	}
	@DeleteMapping("/projects/{projectId}")
	public boolean deleteProject(@PathVariable Long projectId){
		logger.info("start deleteProject method>>");
		if(projectId == null) {
			logger.info("Project Id can not be null!!");
			throw new BadRequestException("Project Id can not be null!!");
		}
		this.projectService.deleteProject(projectId);
		logger.info("start deleteProject method>>");
		return true;
	
	}
}

