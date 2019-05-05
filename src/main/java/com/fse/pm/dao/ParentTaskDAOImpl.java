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

import com.fse.pm.entity.ParentTask;

@Repository
@Transactional
public class ParentTaskDAOImpl implements ParentTaskDAO{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private EntityManager entityManager;

	@Override
	public boolean addParentTask(ParentTask parentTask) {
		Session session= this.entityManager.unwrap(Session.class);
		session.saveOrUpdate(parentTask);
		return true;
	}
	@Override
	public List<ParentTask> getAllParentTasks() {
		Session session= this.entityManager.unwrap(Session.class);
		Query<ParentTask> query= session.createQuery("from ParentTask",ParentTask.class); 
		List<ParentTask> parentTasks=query.getResultList();
		
		return parentTasks;
	}

	@Override
	public ParentTask getParentTaskByParentTaskId(long parentTaskId) {
		Session session= this.entityManager.unwrap(Session.class);
		ParentTask parentTask= session.get(ParentTask.class, parentTaskId);
		return parentTask;
	
	}


}
