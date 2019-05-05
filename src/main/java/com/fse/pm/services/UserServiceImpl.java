package com.fse.pm.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fse.pm.dao.UserDAO;
import com.fse.pm.entity.User;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserDAO userDAO;
	
	@Override
	public void addUser(User user) {
		userDAO.addUser(user);
	}

	@Override
	public void updateUser(User user) {
		userDAO.updateUser(user);
	}

	@Override
	public void deleteUser(long userId) {
		userDAO.deleteUser(userId);
	}

	@Override
	public List<User> getAllUsers() {
		return userDAO.getAllUsers();
	}

	@Override
	public User getUserByUserId(long userId) {
		return userDAO.getUserByUserId(userId);
	}

	@Override
	public int updateProjectId(Long userId, Long projectId) {
		return userDAO.updateProjectId(userId, projectId);
	}

	@Override
	public int updateTaskId(Long userId, Long taskId) {
		return userDAO.updateTaskId(userId, taskId);
	}

	@Override
	public User getUserByProjectId(Long projectId) {
		return userDAO.getUserByProjectId(projectId);
	}
	@Override
	public User getUserByTaskId(Long taskId) {
		
		return userDAO.getUserByTaskId(taskId);
	}
}
