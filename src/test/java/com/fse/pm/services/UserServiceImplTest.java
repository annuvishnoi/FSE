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

import com.fse.pm.dao.UserDAO;
import com.fse.pm.entity.User;

@RunWith(SpringRunner.class)
public class UserServiceImplTest {
	@InjectMocks
	UserServiceImpl userServiceImpl;
	
	@Mock
	UserDAO userDAO;
	
	@Test
	public void addUser() {
		User user = new User();
		user.setFirstName("First_Check");
		user.setLastName("Last_Check");
		user.setEmployeeId(Long.valueOf(350044));
		user.setUserId(Long.valueOf(2));
		
		doNothing().when(userDAO).addUser(user);
		userServiceImpl.addUser(user);
		
		verify(userDAO, times(1)).addUser(user);
	}
	
	@Test
	public void editUser() {
		User user = new User();
		user.setFirstName("First_Check");
		user.setLastName("Last_Check");
		user.setEmployeeId(Long.valueOf(350044));
		user.setUserId(Long.valueOf(2));
		
		doNothing().when(userDAO).updateUser(user);
		userServiceImpl.updateUser(user);
		
		verify(userDAO, times(1)).updateUser(user);
	}
	
	@Test
	public void deleteUser() {
		
		doNothing().when(userDAO).deleteUser(Long.valueOf(2));
		userServiceImpl.deleteUser(Long.valueOf(2));
		
		verify(userDAO, times(1)).deleteUser(Long.valueOf(2));
	}
	@Test
	public void getAllUsers() throws Exception {
		User user = new User();
		user.setFirstName("First_Check");
		user.setLastName("Last_Check");
		user.setEmployeeId(Long.valueOf(350044));
		List<User> users = new ArrayList<User>();
		users.add(user);

		given(userDAO.getAllUsers()).willReturn(users);
		
		List<User> listActual = userServiceImpl.getAllUsers();
		assertNotNull(listActual);
		
		assertTrue(listActual.size() == 1);

	}
	
	@Test
	public void getUserByUserId() throws Exception {
		User user = new User();
		user.setFirstName("First_Check");
		user.setLastName("Last_Check");
		user.setEmployeeId(Long.valueOf(350044));

		given(userDAO.getUserByUserId(Long.valueOf(350044))).willReturn(user);
		
		User actualObject = userServiceImpl.getUserByUserId(Long.valueOf(350044));
		assertNotNull(actualObject);
		
		assertTrue(actualObject.getFirstName().equals("First_Check"));

	}
	@Test
	public void getUserByProjectId() throws Exception {
		User user = new User();
		user.setFirstName("First_Check");
		user.setLastName("Last_Check");
		user.setEmployeeId(Long.valueOf(350044));
		user.setProjectId(Long.valueOf(600));

		given(userDAO.getUserByProjectId(Long.valueOf(600))).willReturn(user);
		
		User actualObject = userServiceImpl.getUserByProjectId(Long.valueOf(600));
		assertNotNull(actualObject);
		
		assertTrue(actualObject.getFirstName().equals("First_Check"));

	}
	@Test
	public void getUserByTaskId() throws Exception {
		User user = new User();
		user.setFirstName("First_Check");
		user.setLastName("Last_Check");
		user.setEmployeeId(Long.valueOf(350044));
		user.setProjectId(Long.valueOf(600));
		user.setTaskId(Long.valueOf(2));

		given(userDAO.getUserByTaskId(user.getTaskId())).willReturn(user);
		
		User actualObject = userServiceImpl.getUserByTaskId(user.getTaskId());
		assertNotNull(actualObject);
		
		assertTrue(actualObject.getFirstName().equals("First_Check"));
	}
	@Test
	public void updateProjectId() {
		User user = new User();
		user.setFirstName("First_Check");
		user.setLastName("Last_Check");
		user.setEmployeeId(Long.valueOf(350044));
		user.setUserId(Long.valueOf(2));
		user.setProjectId(Long.valueOf(600));
		user.setTaskId(Long.valueOf(2));
		
		int count = 1;
		given(userDAO.updateProjectId(user.getUserId(), user.getProjectId())).willReturn(count);
		int expected = userServiceImpl.updateProjectId(user.getUserId(), user.getProjectId());
		
		verify(userDAO, times(1)).updateProjectId(user.getUserId(), user.getProjectId());
		assertEquals(count, expected);
	}
	
	@Test
	public void updateTaskId() {
		User user = new User();
		user.setFirstName("First_Check");
		user.setLastName("Last_Check");
		user.setEmployeeId(Long.valueOf(350044));
		user.setUserId(Long.valueOf(2));
		user.setProjectId(Long.valueOf(600));
		user.setTaskId(Long.valueOf(2));
		
		int count = 1;
		given(userDAO.updateTaskId(user.getUserId(), user.getTaskId())).willReturn(count);
		int expected = userServiceImpl.updateTaskId(user.getUserId(), user.getTaskId());
		
		verify(userDAO, times(1)).updateTaskId(user.getUserId(), user.getTaskId());
		assertEquals(count, expected);
	}
}
