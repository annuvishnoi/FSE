package com.fse.pm.dao;

import java.util.List;

import com.fse.pm.entity.Project;

public interface ProjectDAO {
	public boolean addProject(Project project);
	public boolean updateProject(Project project);
	public boolean deleteProject(long projectId);
	public List<Project> getAllProjects();
	public Project getProjectByProjectId(long projectId);

}
