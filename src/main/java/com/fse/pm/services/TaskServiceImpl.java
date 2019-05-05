package com.fse.pm.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fse.pm.dao.TaskDAO;
import com.fse.pm.entity.Task;

@Service
public class TaskServiceImpl implements TaskService{
	
	@Autowired
	TaskDAO taskDAO;
	
	@Override
	public void addTask(Task task) {
		taskDAO.addTask(task);
	}

	@Override
	public void updateTask(Task task) {
		taskDAO.updateTask(task);
	}

	@Override
	public void deleteTask(long taskId) {
		taskDAO.deleteTask(taskId);
	}

	@Override
	public List<Task> getAllTasks() {
		List<Task> tasks = taskDAO.getAllTasks();
		/*if(tasks != null ) {
			for (Task task : tasks) {
				if(task != null) {
					task.setSelectedProject(task.getProject());
				}
			}
		}*/
		return tasks;
	}
	@Override
	public Task getTaskByTaskId(long taskId) {
		return taskDAO.getTaskByTaskId(taskId);
	}
	
	@Override
	public int updateTaskStatus(Long taskId) {
		return taskDAO.updateTaskStatus(taskId);
	}
}
