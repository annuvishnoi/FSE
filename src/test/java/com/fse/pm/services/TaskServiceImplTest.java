package com.fse.pm.services;

import static org.junit.Assert.assertEquals;
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

import com.fse.pm.dao.TaskDAO;
import com.fse.pm.entity.Task;

@RunWith(SpringRunner.class)
public class TaskServiceImplTest {
	@InjectMocks
	TaskServiceImpl taskServiceImpl;
	
	@Mock
	TaskDAO taskDAO;
	
	@Test
	public void addTask() {
		Task task = new Task();
		task.setTask("Test Task");
		task.setTaskId(Long.valueOf(20));
		
		doNothing().when(taskDAO).addTask(task);
		taskServiceImpl.addTask(task);
		
		verify(taskDAO, times(1)).addTask(task);
	}
	
	@Test
	public void editTask() {
		Task task = new Task();
		task.setTask("Test Task");
		task.setTaskId(Long.valueOf(20));

		
		doNothing().when(taskDAO).updateTask(task);
		taskServiceImpl.updateTask(task);
		
		verify(taskDAO, times(1)).updateTask(task);
	}
	
	@Test
	public void deleteTask() {
		
		doNothing().when(taskDAO).deleteTask(Long.valueOf(2));
		taskServiceImpl.deleteTask(Long.valueOf(2));
		
		verify(taskDAO, times(1)).deleteTask(Long.valueOf(2));
	}
	@Test
	public void getAllTasks() throws Exception {
		Task task = new Task();
		task.setTask("Test Task");
		task.setTaskId(Long.valueOf(20));

		List<Task> tasks = new ArrayList<Task>();
		tasks.add(task);

		given(taskDAO.getAllTasks()).willReturn(tasks);
		
		List<Task> listActual = taskServiceImpl.getAllTasks();
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

		given(taskDAO.getTaskByTaskId(task.getTaskId())).willReturn(task);
		
		Task actualObject = taskServiceImpl.getTaskByTaskId(task.getTaskId());
		assertNotNull(actualObject);
		
		assertTrue(actualObject.getTask().equals("Test Task"));

	}
	@Test
	public void updateTaskStatus() {
		Task task = new Task();
		task.setTask("Test Task");
		task.setTaskId(Long.valueOf(20));
		
		int count = 1;
		given(taskDAO.updateTaskStatus(task.getTaskId())).willReturn(count);
		int expected = taskServiceImpl.updateTaskStatus(task.getTaskId());
		
		verify(taskDAO, times(1)).updateTaskStatus(task.getTaskId());
		assertEquals(count, expected);
	}
}

