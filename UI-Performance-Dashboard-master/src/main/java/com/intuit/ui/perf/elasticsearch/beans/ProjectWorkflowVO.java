package com.intuit.ui.perf.elasticsearch.beans;

public class ProjectWorkflowVO {
private String env;
private String ref_id;
private String workflow;
private String project;
private String build;
private String run_id;
private String scriptrunningregion;


public String getScriptrunningregion() {
	return scriptrunningregion;
}
public void setScriptrunningregion(String scriptrunningregion) {
	this.scriptrunningregion = scriptrunningregion;
}
public String getRun_id() {
	return run_id;
}
public void setRun_id(String run_id) {
	this.run_id = run_id;
}
public String getEnv() {
	return env;
}
public void setEnv(String env) {
	this.env = env;
}
public String getRef_id() {
	return ref_id;
}
public void setRef_id(String ref_id) {
	this.ref_id = ref_id;
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
public String getBuild() {
	return build;
}
public void setBuild(String build) {
	this.build = build;
}

}
