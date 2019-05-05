package com.fse.pm.services;

import java.util.List;

import com.fse.pm.entity.Project;

public interface ProjectService {
	public void addProject(Project project);
	public void updateProject(Project project);
	public void deleteProject(long projectId);
	public List<Project> getAllProjects();
	public Project getProjectByProjectId(long projectId);
	
}