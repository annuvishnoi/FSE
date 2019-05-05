package com.fse.pm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="parent_task")
public class ParentTask {
	
	@Id //PK support
	@GeneratedValue(strategy=GenerationType.IDENTITY) //unique + auto incr
	@Column(name="parent_id")
	private Long parentId;
	
	@Column(name="parent_task")
	private String parentTask;

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getParentTask() {
		return parentTask;
	}

	public void setParentTask(String parentTask) {
		this.parentTask = parentTask;
	}
}
