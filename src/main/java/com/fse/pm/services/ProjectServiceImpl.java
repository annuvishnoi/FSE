package com.fse.pm.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fse.pm.dao.ProjectDAO;
import com.fse.pm.entity.Project;

@Service
public class ProjectServiceImpl implements ProjectService{
	
	@Autowired
	ProjectDAO projectDAO;
	
	@Override
	public boolean addProject(Project project) {
		return projectDAO.addProject(project);
	}

	@Override
	public boolean updateProject(Project project) {
		return projectDAO.updateProject(project);
	}

	@Override
	public boolean deleteProject(long projectId) {
		return projectDAO.deleteProject(projectId);
	}

	@Override
	public List<Project> getAllProjects() {
		return projectDAO.getAllProjects();
	}

	@Override
	public Project getProjectByProjectId(long projectId) {
		return projectDAO.getProjectByProjectId(projectId);
	}
}
