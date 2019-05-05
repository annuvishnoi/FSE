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

import com.fse.pm.dao.ParentTaskDAO;
import com.fse.pm.entity.ParentTask;

@RunWith(SpringRunner.class)
public class ParentTaskServiceImplTest {
	@InjectMocks
	ParentTaskServiceImpl parentTaskServiceImpl;
	
	@Mock
	ParentTaskDAO parentTaskDAO;
	
	@Test
	public void addParentTask() {
		ParentTask parentTask = new ParentTask();
		parentTask.setParentTask("Test ParentTask");
		parentTask.setParentId(Long.valueOf(20));

		doNothing().when(parentTaskDAO).addParentTask(parentTask);
		parentTaskServiceImpl.addParentTask(parentTask);
		
		verify(parentTaskDAO, times(1)).addParentTask(parentTask);
	}
	
	@Test
	public void getAllParentTasks() throws Exception {
		ParentTask parentTask = new ParentTask();
		parentTask.setParentTask("Test ParentTask");
		parentTask.setParentId(Long.valueOf(20));

		List<ParentTask> parentTasks = new ArrayList<ParentTask>();
		
		parentTasks.add(parentTask);

		given(parentTaskDAO.getAllParentTasks()).willReturn(parentTasks);
		
		List<ParentTask> listActual = parentTaskServiceImpl.getAllParentTasks();
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

		given(parentTaskDAO.getParentTaskByParentTaskId(parentTask.getParentId())).willReturn(parentTask);
		
		ParentTask actualObject = parentTaskServiceImpl.getParentTaskByParentTaskId(parentTask.getParentId());
		assertNotNull(actualObject);
		
		assertTrue(actualObject.getParentTask().equals("Test ParentTask"));

	}
	
}
