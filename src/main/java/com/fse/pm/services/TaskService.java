package com.fse.pm.services;

import java.util.List;

import com.fse.pm.entity.Task;

public interface TaskService {
	public boolean addTask(Task task);
	public boolean updateTask(Task task);
	public boolean deleteTask(long taskId);
	public List<Task> getAllTasks();
	public Task getTaskByTaskId(long taskId);
	
	public int updateTaskStatus(Long taskId);
	
}