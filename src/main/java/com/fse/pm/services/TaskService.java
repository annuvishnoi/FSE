package com.fse.pm.services;

import java.util.List;

import com.fse.pm.entity.Task;

public interface TaskService {
	public void addTask(Task task);
	public void updateTask(Task task);
	public void deleteTask(long taskId);
	public List<Task> getAllTasks();
	public Task getTaskByTaskId(long taskId);
	
	public int updateTaskStatus(Long taskId);
	
}