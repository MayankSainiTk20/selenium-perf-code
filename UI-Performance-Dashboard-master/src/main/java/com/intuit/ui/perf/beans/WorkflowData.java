package com.intuit.ui.perf.beans;

import java.util.List;

public class WorkflowData {

	private String date_of_execution;
	private String workflow;
	private long workflow_execution_time;
	private String build;
	private String region;
	private String run_id;

	public String getRun_id() {
		return run_id;
	}

	public void setRun_id(String run_id) {
		this.run_id = run_id;
	}

	private List<Source> sources;

	public List<Source> getSources() {
		return sources;
	}

	public void setSources(List<Source> sources) {
		this.sources = sources;
	}

	public String getDate_of_execution() {
		return date_of_execution;
	}

	public void setDate_of_execution(String date_of_execution) {
		this.date_of_execution = date_of_execution;
	}

	public String getWorkflow() {
		return workflow;
	}

	public void setWorkflow(String workflow) {
		this.workflow = workflow;
	}

	public long getWorkflow_execution_time() {
		return workflow_execution_time;
	}

	public void setWorkflow_execution_time(long workflow_execution_time) {
		this.workflow_execution_time = workflow_execution_time;
	}

	public String getBuild() {
		return build;
	}

	public void setBuild(String build) {
		this.build = build;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

}