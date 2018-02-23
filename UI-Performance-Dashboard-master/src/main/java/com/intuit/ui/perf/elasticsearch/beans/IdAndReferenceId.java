package com.intuit.ui.perf.elasticsearch.beans;

public class IdAndReferenceId {
	private String _id;
	private String ref_id;
	private String workflow;
	private String page;
	private String project;
	private String scriptrunningregion;
	
	

	public String getScriptrunningregion() {
		return scriptrunningregion;
	}

	public void setScriptrunningregion(String scriptrunningregion) {
		this.scriptrunningregion = scriptrunningregion;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getWorkflow() {
		return workflow;
	}

	public void setWorkflow(String workflow) {
		this.workflow = workflow;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getRef_id() {
		return ref_id;
	}

	public void setRef_id(String ref_id) {
		this.ref_id = ref_id;
	}

}
