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

import com.fse.pm.entity.ParentTask;

@RunWith(SpringRunner.class)
public class ParentTaskDAOImplTest {
	@InjectMocks
	ParentTaskDAOImpl parentTaskDAOImpl;
	
	@Mock
	EntityManager entityManager;
	
	@Mock
	Session session;
	
	@Mock
	Query query;
	
	@Test
	public void addParentTask() {
		ParentTask parentTask = new ParentTask();
		parentTask.setParentTask("Test ParentTask");
		parentTask.setParentId(Long.valueOf(20));

		List<ParentTask> parentTasks = new ArrayList<ParentTask>();
		
		parentTasks.add(parentTask);


		when(entityManager.unwrap(Session.class)).thenReturn(session);
		
		session.saveOrUpdate(parentTask);
		verify(session, times(1)).saveOrUpdate(parentTask);
	}
	@Test
	public void getAllParentTasks() throws Exception {
		ParentTask parentTask = new ParentTask();
		parentTask.setParentTask("Test ParentTask");
		parentTask.setParentId(Long.valueOf(20));

		List<ParentTask> parentTasks = new ArrayList<ParentTask>();
		
		parentTasks.add(parentTask);


		when(entityManager.unwrap(Session.class)).thenReturn(session);
		assertNotNull(session);
		
		
		when(session.createQuery("from ParentTask",ParentTask.class)).thenReturn(query);
		assertNotNull(query);
		
		when(query.getResultList()).thenReturn(parentTasks);
		
		List<ParentTask> listActual = parentTaskDAOImpl.getAllParentTasks();
		assertNotNull(listActual);
		
		assertTrue(listActual.size() == 1);

	}
	
	@Test
	public void getParentTaskByParentTaskId() throws Exception {
		ParentTask parentTask = new ParentTask();
		parentTask.setParentTask("Test ParentTask");
		parentTask.setParentId(Long.valueOf(20));

		List<ParentTask> parentTasks = new ArrayList<ParentTask>();
		
		parentTasks.add(parentTask);

		
		when(entityManager.unwrap(Session.class)).thenReturn(session);
		
		when(session.get(ParentTask.class, parentTask.getParentId())).thenReturn(parentTask);
		
		ParentTask actualObject = parentTaskDAOImpl.getParentTaskByParentTaskId(parentTask.getParentId());
		assertNotNull(actualObject);
		
		assertTrue(actualObject.getParentTask().equals("Test ParentTask"));

	}
}
