package com.intuit.ui.perf.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "projects")
public class Entity_Project {

	@Id
	private String project_name;
	private String project_git_repo;
	private String project_execution_schedule;
	private String project_admin_email;
	private String project_region;
	private String project_testng_file;


	public String getProject_testng_file() {
		return project_testng_file;
	}

	public void setProject_testng_file(String project_testng_file) {
		this.project_testng_file = project_testng_file;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public String getProject_git_repo() {
		return project_git_repo;
	}

	public void setProject_git_repo(String project_git_repo) {
		this.project_git_repo = project_git_repo;
	}

	public String getProject_execution_schedule() {
		return project_execution_schedule;
	}

	public void setProject_execution_schedule(String project_execution_schedule) {
		this.project_execution_schedule = project_execution_schedule;
	}

	public String getProject_admin_email() {
		return project_admin_email;
	}

	public void setProject_admin_email(String project_admin_email) {
		this.project_admin_email = project_admin_email;
	}

	public String getProject_region() {
		return project_region;
	}

	public void setProject_region(String project_region) {
		this.project_region = project_region;
	}

}
