package com.intuit.ui.perf.beans;

import com.fasterxml.jackson.annotation.JsonInclude;

public class WorkFlowBean {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String project;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String page;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String region;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private int resources;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String env;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String workflow;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String build;
	
	

	
	public String getBuild() {
		return build;
	}

	public void setBuild(String build) {
		this.build = build;
	}

	public String getWorkflow() {
		return workflow;
	}

	public void setWorkflow(String workflow) {
		this.workflow = workflow;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public int getResources() {
		return resources;
	}

	public void setResources(int resources) {
		this.resources = resources;
	}

}
