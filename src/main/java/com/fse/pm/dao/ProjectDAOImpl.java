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

import com.fse.pm.entity.Project;
import com.fse.pm.exceptions.BadRequestException;
import com.fse.pm.exceptions.NotFoundException;

@Repository
@Transactional
public class ProjectDAOImpl implements ProjectDAO{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private EntityManager entityManager;

	@Override
	public boolean addProject(Project project) {
		Session session= this.entityManager.unwrap(Session.class);
		session.saveOrUpdate(project);
		return true;
	}

	@Override
	public boolean updateProject(Project project) {
		if(project == null) {
			logger.info("Project object is null");
			throw new BadRequestException("Project object is null");
		}
		Project projectAvailable = getProjectByProjectId(project.getProjectId());
		if(projectAvailable == null) {
			logger.info("Project id not found - " + project.getProjectId());
			throw new NotFoundException("Project id not found - " + project.getProjectId());
		}
		Session session= this.entityManager.unwrap(Session.class);
		session.merge(project);
		return true;
	}

	@Override
	public boolean deleteProject(long projectId) {
		Project project = getProjectByProjectId(projectId);
		if(project == null) {
			logger.info("Project id not found - " + projectId);
			throw new NotFoundException("Project id not found - " + projectId);
		}
		Session session= this.entityManager.unwrap(Session.class);
		session.delete(project);
		return true;
	}

	@Override
	public List<Project> getAllProjects() {
		Session session= this.entityManager.unwrap(Session.class);
		Query<Project> query= session.createQuery("from Project",Project.class); 
		List<Project> projects=query.getResultList();
		return projects;

	}

	@Override
	public Project getProjectByProjectId(long projectId) {
		Session session= this.entityManager.unwrap(Session.class);
		Project project= session.get(Project.class, projectId);
		return project;
	}
	
}
