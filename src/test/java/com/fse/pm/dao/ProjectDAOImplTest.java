package com.fse.pm.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.fse.pm.entity.Project;

@RunWith(SpringRunner.class)
public class ProjectDAOImplTest {
	@InjectMocks
	ProjectDAOImpl projectDAOImpl;
	
	@Mock
	EntityManager entityManager;
	
	@Mock
	Session session;
	
	@Mock
	Query query;
	
	@Test
	public void addProject() {
		Project project = new Project();
		project.setProject("Test Project");
		project.setProjectId(Long.valueOf(20));

		when(entityManager.unwrap(Session.class)).thenReturn(session);
		
		session.saveOrUpdate(project);
		verify(session, times(1)).saveOrUpdate(project);
		
		
		projectDAOImpl.addProject(project);
	}
	
	@Test
	public void editProject() {
		Project project = new Project();
		project.setProject("Test Project");
		project.setProjectId(Long.valueOf(20));

		when(entityManager.unwrap(Session.class)).thenReturn(session);
		
		when(session.get(Project.class, project.getProjectId())).thenReturn(project);
		
		session.merge(project);
		verify(session, times(1)).merge(project);
		
		
		projectDAOImpl.updateProject(project);
	}
	
	@Test
	public void deleteProject() {
		Project project = new Project();
		project.setProject("Test Project");
		project.setProjectId(Long.valueOf(20));

		when(entityManager.unwrap(Session.class)).thenReturn(session);
		
		when(session.get(Project.class, project.getProjectId())).thenReturn(project);
		
		session.delete(project);
		verify(session, times(1)).delete(project);
		
		projectDAOImpl.deleteProject(project.getProjectId());
	}
	@Test
	public void getAllProjects() throws Exception {
		Project project = new Project();
		project.setProject("Test Project");
		project.setProjectId(Long.valueOf(20));

		List<Project> projects = new ArrayList<Project>();
		projects.add(project);
		

		when(entityManager.unwrap(Session.class)).thenReturn(session);
		assertNotNull(session);
		
		
		when(session.createQuery("from Project",Project.class)).thenReturn(query);
		assertNotNull(query);
		
		when(query.getResultList()).thenReturn(projects);
		
		List<Project> listActual = projectDAOImpl.getAllProjects();
		assertNotNull(listActual);
		
		assertTrue(listActual.size() == 1);

	}
	
	@Test
	public void getProjectByProjectId() throws Exception {
		Project project = new Project();
		project.setProject("Test Project");
		project.setProjectId(Long.valueOf(20));

		List<Project> projects = new ArrayList<Project>();
		projects.add(project);
		
		when(entityManager.unwrap(Session.class)).thenReturn(session);
		
		when(session.get(Project.class, project.getProjectId())).thenReturn(project);
		
		Project actualObject = projectDAOImpl.getProjectByProjectId(project.getProjectId());
		assertNotNull(actualObject);
		
		assertTrue(actualObject.getProject().equals("Test Project"));

	}
}
