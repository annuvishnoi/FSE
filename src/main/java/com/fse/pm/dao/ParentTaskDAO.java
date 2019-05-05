package com.fse.pm.dao;

import java.util.List;

import com.fse.pm.entity.ParentTask;

public interface ParentTaskDAO {
	public boolean addParentTask(ParentTask parentParentTask);
	public List<ParentTask> getAllParentTasks();
	public ParentTask getParentTaskByParentTaskId(long parentParentTaskId);
}
