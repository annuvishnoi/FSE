package com.fse.pm.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fse.pm.dao.ParentTaskDAO;
import com.fse.pm.entity.ParentTask;

@Service
public class ParentTaskServiceImpl implements ParentTaskService{
	
	@Autowired
	ParentTaskDAO parentTaskDAO;
	
	@Override
	public boolean addParentTask(ParentTask parentTask) {
		return parentTaskDAO.addParentTask(parentTask);
	}

	@Override
	public List<ParentTask> getAllParentTasks() {
		return parentTaskDAO.getAllParentTasks();
	}

	@Override
	public ParentTask getParentTaskByParentTaskId(long parentTaskId) {
		return parentTaskDAO.getParentTaskByParentTaskId(parentTaskId);
	}
}
