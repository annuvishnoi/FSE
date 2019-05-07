package com.fse.pm.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="project") 
public class Project {
	
	@Id //PK support
	@GeneratedValue(strategy=GenerationType.IDENTITY) //unique + auto incr
	@Column(name="project_id")
	private Long projectId;
	
	@Column(name="project")
	private String project;
	
	@Column(name="startdate")
	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	@Column(name="enddate")
	@Temporal(TemporalType.DATE)
	private Date endDate;
	
	@Column(name="priority")
	private Integer priority;
	
	@OneToMany(mappedBy="project", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private Set<Task> tasks = new HashSet<Task>();

	@Transient
	private User selectedEmployee;
	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		
		this.endDate = endDate;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	
	}

	public Set<Task> getTasks() {
		return tasks;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}

	public User getSelectedEmployee() {
		return selectedEmployee;
	}

	public void setSelectedEmployee(User selectedEmployee) {
		this.selectedEmployee = selectedEmployee;
	}
}
