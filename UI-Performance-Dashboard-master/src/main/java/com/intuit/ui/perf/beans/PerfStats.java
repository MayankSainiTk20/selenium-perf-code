package com.intuit.ui.perf.beans;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

public class PerfStats {

	@JsonDeserialize(as = String.class, contentAs = String.class)
	private String ref;
	@JsonDeserialize(as = Long.class, contentAs = Long.class)
	private Long RequestTime;

	@JsonDeserialize(as = Long.class, contentAs = Long.class)
	private Long ConcurrentRequests;
	@JsonDeserialize(as = Long.class, contentAs = Long.class)
	private Long DBTime;
	@JsonDeserialize(as = Long.class, contentAs = Long.class)
	private Long DBActions;
	@JsonDeserialize(as = Long.class, contentAs = Long.class)
	private Long XmitTime;
	@JsonDeserialize(as = Long.class, contentAs = Long.class)
	private Long XmitSize;
	@JsonDeserialize(as = Long.class, contentAs = Long.class)
	private Long DBQueries;
	@JsonDeserialize(as = Long.class, contentAs = Long.class)
	private Long DBQueryTime;
	@JsonDeserialize(as = Long.class, contentAs = Long.class)
	private Long DBNextCalls;
	@JsonDeserialize(as = Long.class, contentAs = Long.class)
	private Long DBNextTime;
	@JsonDeserialize(as = Long.class, contentAs = Long.class)
	private Long DBGetTime;
	@JsonDeserialize(as = Long.class, contentAs = Long.class)
	private Long DBPreparedQueries;
	@JsonDeserialize(as = Long.class, contentAs = Long.class)
	private Long DBConnectionCalls;
	@JsonDeserialize(as = Long.class, contentAs = Long.class)
	private Long DBConnectionTime;
	@JsonDeserialize(as = Long.class, contentAs = Long.class)
	private Long CompanyIdT;
	@JsonDeserialize(as = Long.class, contentAs = Long.class)
	private Long AuthIDT;
	@JsonDeserialize(as = Long.class, contentAs = Long.class)
	private Long agentID;
	@JsonDeserialize(as = Long.class, contentAs = Long.class)
	private Long RuntimeFreeMemory;
	@JsonDeserialize(as = String.class, contentAs = String.class)
	private String intuit_tid;
	@JsonDeserialize(as = String.class, contentAs = String.class)
	private String qboBuildNum;
	@JsonDeserialize(as = String.class, contentAs = String.class)
	private String qboVersionNum;
	@JsonDeserialize(as = String.class, contentAs = String.class)
	private String url;
	@JsonDeserialize(as = Long.class, contentAs = Long.class)
	private Long OtherConcurrentThreads;
	@JsonDeserialize(as = String.class, contentAs = String.class)
	private String RefererString;
	@JsonDeserialize(as = String.class, contentAs = String.class)
	private String RequestData;
	@JsonDeserialize(as = String.class, contentAs = String.class)
	private String locale;

	@JsonDeserialize(as = Long.class, contentAs = Long.class)
	private Long NetworkTime;

	public Long getNetworkTime() {
		return NetworkTime;
	}

	public void setNetworkTime(Long networkTime) {
		NetworkTime = networkTime;
	}

	public String getRefererString() {
		return RefererString;
	}

	public void setRefererString(String refererString) {
		RefererString = refererString;
	}

	public String getRequestData() {
		return RequestData;
	}

	public void setRequestData(String requestData) {
		RequestData = requestData;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public Long getOtherConcurrentThreads() {
		return OtherConcurrentThreads;
	}

	public void setOtherConcurrentThreads(Long otherConcurrentThreads) {
		OtherConcurrentThreads = otherConcurrentThreads;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public Long getRequestTime() {
		return RequestTime;
	}

	public void setRequestTime(Long requestTime) {
		RequestTime = requestTime;
	}

	public Long getConcurrentRequests() {
		return ConcurrentRequests;
	}

	public void setConcurrentRequests(Long concurrentRequests) {
		ConcurrentRequests = concurrentRequests;
	}

	public Long getDBTime() {
		return DBTime;
	}

	public void setDBTime(Long dBTime) {
		DBTime = dBTime;
	}

	public Long getDBActions() {
		return DBActions;
	}

	public void setDBActions(Long dBActions) {
		DBActions = dBActions;
	}

	public Long getXmitTime() {
		return XmitTime;
	}

	public void setXmitTime(Long xmitTime) {
		XmitTime = xmitTime;
	}

	public Long getXmitSize() {
		return XmitSize;
	}

	public void setXmitSize(Long xmitSize) {
		XmitSize = xmitSize;
	}

	public Long getDBQueries() {
		return DBQueries;
	}

	public void setDBQueries(Long dBQueries) {
		DBQueries = dBQueries;
	}

	public Long getDBQueryTime() {
		return DBQueryTime;
	}

	public void setDBQueryTime(Long dBQueryTime) {
		DBQueryTime = dBQueryTime;
	}

	public Long getDBNextCalls() {
		return DBNextCalls;
	}

	public void setDBNextCalls(Long dBNextCalls) {
		DBNextCalls = dBNextCalls;
	}

	public Long getDBNextTime() {
		return DBNextTime;
	}

	public void setDBNextTime(Long dBNextTime) {
		DBNextTime = dBNextTime;
	}

	public Long getDBGetTime() {
		return DBGetTime;
	}

	public void setDBGetTime(Long dBGetTime) {
		DBGetTime = dBGetTime;
	}

	public Long getDBPreparedQueries() {
		return DBPreparedQueries;
	}

	public void setDBPreparedQueries(Long dBPreparedQueries) {
		DBPreparedQueries = dBPreparedQueries;
	}

	public Long getDBConnectionCalls() {
		return DBConnectionCalls;
	}

	public void setDBConnectionCalls(Long dBConnectionCalls) {
		DBConnectionCalls = dBConnectionCalls;
	}

	public Long getDBConnectionTime() {
		return DBConnectionTime;
	}

	public void setDBConnectionTime(Long dBConnectionTime) {
		DBConnectionTime = dBConnectionTime;
	}

	public Long getCompanyIdT() {
		return CompanyIdT;
	}

	public void setCompanyIdT(Long companyIdT) {
		CompanyIdT = companyIdT;
	}

	public Long getAuthIDT() {
		return AuthIDT;
	}

	public void setAuthIDT(Long authIDT) {
		AuthIDT = authIDT;
	}

	public Long getAgentID() {
		return agentID;
	}

	public void setAgentID(Long agentID) {
		this.agentID = agentID;
	}

	public Long getRuntimeFreeMemory() {
		return RuntimeFreeMemory;
	}

	public void setRuntimeFreeMemory(Long runtimeFreeMemory) {
		RuntimeFreeMemory = runtimeFreeMemory;
	}

	public String getIntuit_tid() {
		return intuit_tid;
	}

	public void setIntuit_tid(String intuit_tid) {
		this.intuit_tid = intuit_tid;
	}

	public String getQboBuildNum() {
		return qboBuildNum;
	}

	public void setQboBuildNum(String qboBuildNum) {
		this.qboBuildNum = qboBuildNum;
	}

	public String getQboVersionNum() {
		return qboVersionNum;
	}

	public void setQboVersionNum(String qboVersionNum) {
		this.qboVersionNum = qboVersionNum;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
