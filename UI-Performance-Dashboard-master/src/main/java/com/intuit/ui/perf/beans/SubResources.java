package com.intuit.ui.perf.beans;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

import com.fasterxml.jackson.annotation.JsonInclude;

public class SubResources {

	@JsonDeserialize(as = String.class, contentAs = String.class)
	private String ref;
	@JsonDeserialize(as = Long.class, contentAs = Long.class)
	private Long runtimestamp;
	@JsonDeserialize(as = String.class, contentAs = String.class)
	private String datetime;
	@JsonDeserialize(as = Double.class, contentAs = Double.class)
	private Double fetchStart;
	@JsonDeserialize(as = Double.class, contentAs = Double.class)
	private Double redirectStart;
	@JsonDeserialize(as = Double.class, contentAs = Double.class)
	private Double redirectEnd;
	@JsonDeserialize(as = Double.class, contentAs = Double.class)
	private Double requestStart;
	@JsonDeserialize(as = Double.class, contentAs = Double.class)
	private Double secureConnectionStart;
	@JsonDeserialize(as = Double.class, contentAs = Double.class)
	private Double responseEnd;
	@JsonDeserialize(as = String.class, contentAs = String.class)
	private String initiatorType;
	@JsonDeserialize(as = Double.class, contentAs = Double.class)
	private Double startTime;
	@JsonDeserialize(as = Double.class, contentAs = Double.class)
	private Double domainLookupEnd;
	@JsonDeserialize(as = Double.class, contentAs = Double.class)
	private Double duration;
	@JsonDeserialize(as = Double.class, contentAs = Double.class)
	private Double responseStart;
	@JsonDeserialize(as = Double.class, contentAs = Double.class)
	private Double connectEnd;
	@JsonDeserialize(as = String.class, contentAs = String.class)
	private String entryType;
	@JsonDeserialize(as = Double.class, contentAs = Double.class)
	private Double connectStart;
	@JsonDeserialize(as = String.class, contentAs = String.class)
	private String name;
	@JsonDeserialize(as = Double.class, contentAs = Double.class)
	private Double domainLookupStart;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonDeserialize(as = String.class, contentAs = String.class)
	private String project;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonDeserialize(as = String.class, contentAs = String.class)
	private String loadtime;

	@JsonDeserialize(as = String.class, contentAs = String.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String region;

	@JsonDeserialize(as = Float.class, contentAs = Float.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Float speedindex;

	@JsonDeserialize(as = String.class, contentAs = String.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String scriptrunningregion;

	@JsonDeserialize(as = String.class, contentAs = String.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String workflow;

	@JsonDeserialize(as = String.class, contentAs = String.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String build;

	@JsonDeserialize(as = long.class, contentAs = long.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private long rum;

	@JsonDeserialize(as = String.class, contentAs = String.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String run_id;

	@JsonDeserialize(as = String.class, contentAs = String.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String page;

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Float getSpeedindex() {
		return speedindex;
	}

	public void setSpeedindex(Float speedindex) {
		this.speedindex = speedindex;
	}

	public String getScriptrunningregion() {
		return scriptrunningregion;
	}

	public void setScriptrunningregion(String scriptrunningregion) {
		this.scriptrunningregion = scriptrunningregion;
	}

	public String getWorkflow() {
		return workflow;
	}

	public void setWorkflow(String workflow) {
		this.workflow = workflow;
	}

	public String getBuild() {
		return build;
	}

	public void setBuild(String build) {
		this.build = build;
	}

	public long getRum() {
		return rum;
	}

	public void setRum(long rum) {
		this.rum = rum;
	}

	public String getRun_id() {
		return run_id;
	}

	public void setRun_id(String run_id) {
		this.run_id = run_id;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getLoadtime() {
		return loadtime;
	}

	public void setLoadtime(String loadtime) {
		this.loadtime = loadtime;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public Long getRuntimestamp() {
		return runtimestamp;
	}

	public void setRuntimestamp(Long runtimestamp) {
		this.runtimestamp = runtimestamp;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public Double getFetchStart() {
		return fetchStart;
	}

	public void setFetchStart(Double fetchStart) {
		this.fetchStart = fetchStart;
	}

	public Double getRedirectStart() {
		return redirectStart;
	}

	public void setRedirectStart(Double redirectStart) {
		this.redirectStart = redirectStart;
	}

	public Double getRedirectEnd() {
		return redirectEnd;
	}

	public void setRedirectEnd(Double redirectEnd) {
		this.redirectEnd = redirectEnd;
	}

	public Double getRequestStart() {
		return requestStart;
	}

	public void setRequestStart(Double requestStart) {
		this.requestStart = requestStart;
	}

	public Double getSecureConnectionStart() {
		return secureConnectionStart;
	}

	public void setSecureConnectionStart(Double secureConnectionStart) {
		this.secureConnectionStart = secureConnectionStart;
	}

	public Double getResponseEnd() {
		return responseEnd;
	}

	public void setResponseEnd(Double responseEnd) {
		this.responseEnd = responseEnd;
	}

	public String getInitiatorType() {
		return initiatorType;
	}

	public void setInitiatorType(String initiatorType) {
		this.initiatorType = initiatorType;
	}

	public Double getStartTime() {
		return startTime;
	}

	public void setStartTime(Double startTime) {
		this.startTime = startTime;
	}

	public Double getDomainLookupEnd() {
		return domainLookupEnd;
	}

	public void setDomainLookupEnd(Double domainLookupEnd) {
		this.domainLookupEnd = domainLookupEnd;
	}

	public Double getDuration() {
		return duration;
	}

	public void setDuration(Double duration) {
		this.duration = duration;
	}

	public Double getResponseStart() {
		return responseStart;
	}

	public void setResponseStart(Double responseStart) {
		this.responseStart = responseStart;
	}

	public Double getConnectEnd() {
		return connectEnd;
	}

	public void setConnectEnd(Double connectEnd) {
		this.connectEnd = connectEnd;
	}

	public String getEntryType() {
		return entryType;
	}

	public void setEntryType(String entryType) {
		this.entryType = entryType;
	}

	public Double getConnectStart() {
		return connectStart;
	}

	public void setConnectStart(Double connectStart) {
		this.connectStart = connectStart;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getDomainLookupStart() {
		return domainLookupStart;
	}

	public void setDomainLookupStart(Double domainLookupStart) {
		this.domainLookupStart = domainLookupStart;
	}

}
