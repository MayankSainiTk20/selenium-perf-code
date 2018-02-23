package com.intuit.ui.perf.beans;

import com.fasterxml.jackson.annotation.JsonInclude;

public class GitValidationBean {

	private String gitRepo;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String localRepoPath;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Boolean isValid;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String message;

	public String getLocalRepoPath() {
		return localRepoPath;
	}

	public void setLocalRepoPath(String localRepoPath) {
		this.localRepoPath = localRepoPath;
	}

	public String getGitRepo() {
		return gitRepo;
	}

	public void setGitRepo(String gitRepo) {
		this.gitRepo = gitRepo;
	}

	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
