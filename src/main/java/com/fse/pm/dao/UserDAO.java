package com.fse.pm.dao;

import java.util.List;

import com.fse.pm.entity.User;

public interface UserDAO {
	public void addUser(User user);
	public void updateUser(User user);
	public int updateProjectId(Long userId, Long projectId);
	public int updateTaskId(Long userId, Long taskId);
	public void deleteUser(long userId);
	
	public List<User> getAllUsers();
	public User getUserByUserId(long userId);
	public User getUserByProjectId(Long projectId);
	public User getUserByTaskId(Long taskId);
}
