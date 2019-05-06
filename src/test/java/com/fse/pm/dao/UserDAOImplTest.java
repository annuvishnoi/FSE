package com.fse.pm.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
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
import static org.mockito.Mockito.doNothing;

import com.fse.pm.entity.User;

@RunWith(SpringRunner.class)
public class UserDAOImplTest {
	@InjectMocks
	UserDAOImpl userDAOImpl;
	
	@Mock
	EntityManager entityManager;
	
	@Mock
	Session session;
	
	@Mock
	Query query;
	
	@Test
	public void addUser() {
		User user = new User();
		user.setFirstName("First_Check");
		user.setLastName("Last_Check");
		user.setEmployeeId(Long.valueOf(350044));
		user.setUserId(Long.valueOf(2));
		
		when(entityManager.unwrap(Session.class)).thenReturn(session);
		
		session.saveOrUpdate(user);
		verify(session, times(1)).saveOrUpdate(user);
	}
	
	@Test
	public void editUser() {
		User user = new User();
		user.setFirstName("First_Check");
		user.setLastName("Last_Check");
		user.setEmployeeId(Long.valueOf(350044));
		user.setUserId(Long.valueOf(2));
		
		when(entityManager.unwrap(Session.class)).thenReturn(session);
		
		when(session.get(User.class, user.getUserId())).thenReturn(user);
		
		session.merge(user);
		verify(session, times(1)).merge(user);
	}
	
	@Test
	public void deleteUser() {
		User user = new User();
		user.setFirstName("First_Check");
		user.setLastName("Last_Check");
		user.setEmployeeId(Long.valueOf(350044));
		user.setUserId(Long.valueOf(2));
		
		when(entityManager.unwrap(Session.class)).thenReturn(session);
		
		when(session.get(User.class, user.getUserId())).thenReturn(user);
		
		session.delete(user);
		verify(session, times(1)).delete(user);
	}
	@Test
	public void getAllUsers() throws Exception {
		User user = new User();
		user.setFirstName("First_Check");
		user.setLastName("Last_Check");
		user.setEmployeeId(Long.valueOf(350044));
		List<User> users = new ArrayList<User>();
		users.add(user);
		

		when(entityManager.unwrap(Session.class)).thenReturn(session);
		assertNotNull(session);
		
		
		when(session.createQuery("from User",User.class)).thenReturn(query);
		assertNotNull(query);
		
		when(query.getResultList()).thenReturn(users);
		
		List<User> listActual = userDAOImpl.getAllUsers();
		assertNotNull(listActual);
		
		assertTrue(listActual.size() == 1);

	}
	
	@Test
	public void getUserByUserId() throws Exception {
		User user = new User();
		user.setFirstName("First_Check");
		user.setLastName("Last_Check");
		user.setEmployeeId(Long.valueOf(350044));
		user.setUserId(Long.valueOf(2));
		
		when(entityManager.unwrap(Session.class)).thenReturn(session);
		
		when(session.get(User.class, user.getUserId())).thenReturn(user);
		
		User actualObject = userDAOImpl.getUserByUserId(user.getUserId());
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

		
		when(entityManager.unwrap(Session.class)).thenReturn(session);
		
		when(session.createQuery("from User where projectId=" + user.getProjectId())).thenReturn(query);
		assertNotNull(query);
		
		when(query.uniqueResult()).thenReturn(user);
		
		User actualObject = userDAOImpl.getUserByProjectId(user.getProjectId());
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

		when(entityManager.unwrap(Session.class)).thenReturn(session);
		
		when(session.createQuery("from User where taskId=" + user.getTaskId())).thenReturn(query);
		assertNotNull(query);
		
		when(query.uniqueResult()).thenReturn(user);
		
		User actualObject = userDAOImpl.getUserByTaskId(user.getTaskId());
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
		
		when(entityManager.unwrap(Session.class)).thenReturn(session);
		when(session.createQuery("update User set projectId= null where projectId=" + user.getProjectId())).thenReturn(query);
		assertNotNull(query);
		
	//	when(session.createQuery("update User set projectId= null where projectId=" + user.getProjectId())).thenReturn(query);
		when(query.executeUpdate()).thenReturn(1);
		
		when(session.createQuery("update User set projectId = :projectId" +
				" where userId = :userId")).thenReturn(query);
		
		when(query.setParameter("projectId", user.getProjectId())).thenReturn(query);
		when(query.setParameter("userId", user.getUserId())).thenReturn(query);
		
		int count = 1;
		when(query.executeUpdate()).thenReturn(count);
		
				
		int actualCount = userDAOImpl.updateProjectId(user.getUserId(), user.getProjectId());
		assertEquals(count, actualCount);
		
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
		
		when(entityManager.unwrap(Session.class)).thenReturn(session);
		when(session.createQuery("update User set taskId= null where taskId=" + user.getTaskId())).thenReturn(query);
		assertNotNull(query);
		
		//when(session.createQuery("update User set taskId = :taskId where userId = :userId")).thenReturn(query);
		when(query.executeUpdate()).thenReturn(1);
		
		when(session.createQuery("update User set taskId = :taskId where userId = :userId")).thenReturn(query);
		
		when(query.setParameter("taskId", user.getTaskId())).thenReturn(query);
		when(query.setParameter("userId", user.getUserId())).thenReturn(query);
		
		int count = 1;
		when(query.executeUpdate()).thenReturn(count);
		
				
		int actualCount = userDAOImpl.updateTaskId(user.getUserId(), user.getTaskId());
		assertEquals(count, actualCount);
		
	}
	
	/*@Test
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
	}*/
}
