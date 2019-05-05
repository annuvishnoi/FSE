package com.fse.pm.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="task")
public class Task {
	
	@Id //PK support
	@GeneratedValue(strategy=GenerationType.IDENTITY) //unique + auto incr
	@Column(name="task_id")
	private Long taskId;
	
	@Column(name="task")
	private String task;
	
	@Column(name="startdate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startDate;
	
	@Column(name="enddate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endDate;
	
	@Column(name="priority")
	private Integer priority;
	
	@Column(name="status")
	private Integer status;
	
	/*@Column(name="parent_id")
	private Long parentId;*/
	
	@ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name="parent_id", nullable=true)
	private ParentTask selectedParentTask; 
	
	
	//@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name="project_id", nullable=false)
	private Project project;

	@Transient
	private Project selectedProject;
	
	@Transient
	private User selectedEmployee;
	

	public Long getTaskId() {
		return taskId;
	}


	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}


	public String getTask() {
		return task;
	}


	public void setTask(String task) {
		this.task = task;
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


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	/*public Long getParentId() {
		return parentId;
	}


	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}*/
	

	@JsonIgnore
	public Project getProject() {
		return project;
	}

	
	public ParentTask getSelectedParentTask() {
		return selectedParentTask;
	}


	public void setSelectedParentTask(ParentTask selectedParentTask) {
		this.selectedParentTask = selectedParentTask;
	}


	@JsonIgnore
	public void setProject(Project project) {
		this.project = project;
	}


	public Project getSelectedProject() {
		return selectedProject;
	}

	public void setSelectedProject(Project selectedProject) {
		this.selectedProject = selectedProject;
	}


	public Long getProjectId() {
		
		if(project != null) {
			return project.getProjectId();
		}
		return null;
	}


	public User getSelectedEmployee() {
		return selectedEmployee;
	}


	public void setSelectedEmployee(User selectedEmployee) {
		this.selectedEmployee = selectedEmployee;
	}
	
	
}
