package com.fse.pm.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.fse.pm.dao.ProjectDAO;
import com.fse.pm.entity.Project;

@RunWith(SpringRunner.class)
public class ProjectServiceImplTest {
	@InjectMocks
	ProjectServiceImpl projectServiceImpl;
	
	@Mock
	ProjectDAO projectDAO;
	
	@Test
	public void addProject() {
		Project project = new Project();
		project.setProject("Test Project");
		project.setProjectId(Long.valueOf(20));
		
		doNothing().when(projectDAO).addProject(project);
		projectServiceImpl.addProject(project);
		
		verify(projectDAO, times(1)).addProject(project);
	}
	
	@Test
	public void editProject() {
		Project project = new Project();
		project.setProject("Test Project");
		project.setProjectId(Long.valueOf(20));
		
		doNothing().when(projectDAO).updateProject(project);
		projectServiceImpl.updateProject(project);
		
		verify(projectDAO, times(1)).updateProject(project);
	}
	
	@Test
	public void deleteProject() {
		
		doNothing().when(projectDAO).deleteProject(Long.valueOf(2));
		projectServiceImpl.deleteProject(Long.valueOf(2));
		
		verify(projectDAO, times(1)).deleteProject(Long.valueOf(2));
	}
	@Test
	public void getAllProjects() throws Exception {
		Project project = new Project();
		project.setProject("Test Project");
		project.setProjectId(Long.valueOf(20));

		List<Project> projects = new ArrayList<Project>();
		projects.add(project);

		given(projectDAO.getAllProjects()).willReturn(projects);
		
		List<Project> listActual = projectServiceImpl.getAllProjects();
		assertNotNull(listActual);
		
		assertTrue(listActual.size() == 1);

	}
	
	@Test
	public void getProjectByProjectId() throws Exception {
		Project project = new Project();
		project.setProject("Test Project");
		project.setProjectId(Long.valueOf(20));

		given(projectDAO.getProjectByProjectId(project.getProjectId())).willReturn(project);
		
		Project actualObject = projectServiceImpl.getProjectByProjectId(project.getProjectId());
		assertNotNull(actualObject);
		
		assertTrue(actualObject.getProject().equals("Test Project"));

	}
	
}
