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

import com.fse.pm.entity.Task;
import com.fse.pm.entity.Task;

@RunWith(SpringRunner.class)
public class TaskDAOImplTest {
	@InjectMocks
	TaskDAOImpl taskDAOImpl;
	
	@Mock
	EntityManager entityManager;
	
	@Mock
	Session session;
	
	@Mock
	Query query;
	
	@Test
	public void addTask() {
		Task task = new Task();
		task.setTask("Test Task");
		task.setTaskId(Long.valueOf(20));

		when(entityManager.unwrap(Session.class)).thenReturn(session);
		
		session.saveOrUpdate(task);
		verify(session, times(1)).saveOrUpdate(task);
		
		taskDAOImpl.addTask(task);		
	}
	
	@Test
	public void updateTask() {
		Task task = new Task();
		task.setTask("Test Task");
		task.setTaskId(Long.valueOf(20));

		when(entityManager.unwrap(Session.class)).thenReturn(session);
		assertNotNull(session);
		
		when(session.get(Task.class, task.getTaskId())).thenReturn(task);
		
		session.merge(task);
		verify(session, times(1)).merge(task);
		
		taskDAOImpl.updateTask(task);		
	}
	
	@Test
	public void deleteTask() {
		Task task = new Task();
		task.setTask("Test Task");
		task.setTaskId(Long.valueOf(20));

		List<Task> tasks = new ArrayList<Task>();
		tasks.add(task);

		when(entityManager.unwrap(Session.class)).thenReturn(session);
		assertNotNull(session);
		
		when(session.get(Task.class, task.getTaskId())).thenReturn(task);
		
		session.delete(task);
		verify(session, times(1)).delete(task);
		
		taskDAOImpl.deleteTask(task.getTaskId());		
	}
	@Test
	public void getAllTasks() throws Exception {
		Task task = new Task();
		task.setTask("Test Task");
		task.setTaskId(Long.valueOf(20));

		List<Task> tasks = new ArrayList<Task>();
		tasks.add(task);
		

		when(entityManager.unwrap(Session.class)).thenReturn(session);
		assertNotNull(session);
		
		
		when(session.createQuery("from Task",Task.class)).thenReturn(query);
		assertNotNull(query);
		
		when(query.getResultList()).thenReturn(tasks);
		
		List<Task> listActual = taskDAOImpl.getAllTasks();
		assertNotNull(listActual);
		
		assertTrue(listActual.size() == 1);

	}
	
	@Test
	public void getTaskByTaskId() throws Exception {
		Task task = new Task();
		task.setTask("Test Task");
		task.setTaskId(Long.valueOf(20));

		List<Task> tasks = new ArrayList<Task>();
		tasks.add(task);
		
		when(entityManager.unwrap(Session.class)).thenReturn(session);
		
		when(session.get(Task.class, task.getTaskId())).thenReturn(task);
		
		Task actualObject = taskDAOImpl.getTaskByTaskId(task.getTaskId());
		assertNotNull(actualObject);
		
		assertTrue(actualObject.getTask().equals("Test Task"));

	}
	@Test
	public void updateTaskStatus() {
		Task task = new Task();
		task.setTask("Test Task");
		task.setTaskId(Long.valueOf(20));

		List<Task> tasks = new ArrayList<Task>();
		tasks.add(task);
		

		when(entityManager.unwrap(Session.class)).thenReturn(session);
		assertNotNull(session);
		
		
		when(session.createQuery("update Task set status= :status where taskId=" + task.getTaskId())).thenReturn(query);
		assertNotNull(query);
		
		when(query.setParameter("status", 0)).thenReturn(query);
		assertNotNull(query);
		
		when(query.executeUpdate()).thenReturn(1);
		
		int actualCount = taskDAOImpl.updateTaskStatus(task.getTaskId());
		assertNotNull(actualCount);
		
		assertTrue(actualCount == 1);
	}
}
