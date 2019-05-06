package com.fse.pm.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fse.pm.entity.Task;
import com.fse.pm.exceptions.BadRequestException;
import com.fse.pm.exceptions.NotFoundException;

@Repository
@Transactional
public class TaskDAOImpl implements TaskDAO{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private EntityManager entityManager;

	@Override
	public void addTask(Task task) {
		if(task == null) {
			logger.info("Task object is null");
			throw new BadRequestException("Task object is null");
		}
		task.setProject(task.getSelectedProject());
		Session session= this.entityManager.unwrap(Session.class);
		session.saveOrUpdate(task);
	}

	@Override
	public void updateTask(Task task) {
		if(task == null) {
			logger.info("Task object is null");
			throw new BadRequestException("Task object is null");
		} else {
			Task taskAvailable = getTaskByTaskId(task.getTaskId());
			if(taskAvailable == null) {
				logger.info("Task id not found - " + task.getTaskId());
				throw new NotFoundException("Task id not found - " + task.getTaskId());
			
			}
			task.setProject(task.getSelectedProject());
			Session session= this.entityManager.unwrap(Session.class);
			session.merge(task);
		}
		
	}

	@Override
	public void deleteTask(long taskId) {
		Task task = getTaskByTaskId(taskId);
		if(task == null) {
			logger.info("Task id not found - " + taskId);
			throw new NotFoundException("Task id not found - " + taskId);
		}
		Session session= this.entityManager.unwrap(Session.class);
		session.delete(task);
	}

	@Override
	public List<Task> getAllTasks() {
		Session session= this.entityManager.unwrap(Session.class);
		Query<Task> query= session.createQuery("from Task",Task.class); 
		List<Task> tasks=query.getResultList();
		
		return tasks;
	}

	@Override
	public Task getTaskByTaskId(long taskId) {
		Session session= this.entityManager.unwrap(Session.class);
		Task task= session.get(Task.class, taskId);
		return task;
	
	}
	
	@Override
	public int updateTaskStatus(Long taskId) {
		Session session= this.entityManager.unwrap(Session.class);
		String hql = "update Task set status= :status where taskId=" + taskId;
		Query query = session.createQuery(hql);
		query.setParameter("status", 0);
		int result = query.executeUpdate();
		return result;
	}
}
