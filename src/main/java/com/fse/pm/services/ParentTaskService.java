package com.fse.pm.services;

import java.util.List;

import com.fse.pm.entity.ParentTask;

public interface ParentTaskService {
	public boolean addParentTask(ParentTask parentTask);
	public List<ParentTask> getAllParentTasks();
	public ParentTask getParentTaskByParentTaskId(long parentTaskId);
}
