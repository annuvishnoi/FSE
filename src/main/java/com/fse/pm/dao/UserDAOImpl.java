package com.fse.pm.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fse.pm.entity.User;
import com.fse.pm.exceptions.BadRequestException;
import com.fse.pm.exceptions.NotFoundException;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO{
	
	
	@Autowired
	private EntityManager entityManager;

	@Override
	public void addUser(User user) {
		Session session= this.entityManager.unwrap(Session.class);
		session.saveOrUpdate(user);
	}

	@Override
	public void updateUser(User user) {
		if(user == null) {
			throw new BadRequestException("User object is null");
		}
		User userAvailable = getUserByUserId(user.getUserId());
		if(userAvailable == null) {
			throw new NotFoundException("User id not found - " + user.getUserId());
		}
		Session session= this.entityManager.unwrap(Session.class);
		session.merge(user);
	}

	@Override
	public void deleteUser(long userId) {
		User user = getUserByUserId(userId);
		if(user == null) {
			throw new NotFoundException("User id not found - " + userId);
		}
		Session session= this.entityManager.unwrap(Session.class);
		session.delete(user);
	}

	@Override
	public List<User> getAllUsers() {
		Session session= this.entityManager.unwrap(Session.class);
		Query<User> query= session.createQuery("from User",User.class); 
		List<User> users = query.getResultList();
		Set<User> set = new HashSet<User>();
		if(users != null) {
			for (User user : users) {
				set.add(user);
			}
			users.clear();
			users.addAll(set);
		}
		
		return users;

	}
	
	@Override
	public List<User> getAllManagers() {
		Session session= this.entityManager.unwrap(Session.class);
		Query<User> query= session.createQuery("from User where taskId=null",User.class); 
		List<User> users=query.getResultList();
		return users;

	}

	@Override
	public User getUserByUserId(long userId) {
		Session session= this.entityManager.unwrap(Session.class);
		User user= session.get(User.class, userId);
		return user;
	}

	@Override
	public int updateProjectId(Long userId, Long projectId) {
		Session session= this.entityManager.unwrap(Session.class);
		
		String hql = "update User set projectId= null where projectId=" + projectId;
		Query query1 = session.createQuery(hql);
		query1.executeUpdate();
		
		Query query = session.createQuery("update User set projectId = :projectId" +
				" where userId = :userId");
		query.setParameter("projectId", projectId);
		query.setParameter("userId", userId);
		int result = query.executeUpdate();
		return result;
	}

	@Override
	public int updateTaskId(Long userId, Long taskId) {
		Session session= this.entityManager.unwrap(Session.class);
		String hql = "update User set taskId= null where taskId=" + taskId;
		Query query1 = session.createQuery(hql);
		
		query1.executeUpdate();
		
		Query query = session.createQuery("update User set taskId = :taskId" +
				" where userId = :userId");
		query.setParameter("taskId", taskId);
		query.setParameter("userId", userId);
		int result = query.executeUpdate();
		return result;
	}
	
	@Override
	public User getUserByProjectId(Long projectId) {
		String hql = "from User where taskId=null and projectId=" + projectId;
		Session session= this.entityManager.unwrap(Session.class);
		Query query = session.createQuery(hql);
		/*query.setLong("projectId", projectId);
		query.setMaxResults(1);*/
		User user = (User) query.uniqueResult();
		return user;
	}
	@Override
	public User getUserByTaskId(Long taskId) {
		String hql = "from User where taskId=" + taskId;
		Session session= this.entityManager.unwrap(Session.class);
		Query query = session.createQuery(hql);
		User user = (User) query.uniqueResult();
		return user;
	
	}
	public List<User> getUserByEmployeeId(long employeeId) {
		String hql = "from User where employeeId=" + employeeId;
		Session session= this.entityManager.unwrap(Session.class);
		Query query = session.createQuery(hql);
		List<User> users=query.getResultList();
		return users;
	}
}

