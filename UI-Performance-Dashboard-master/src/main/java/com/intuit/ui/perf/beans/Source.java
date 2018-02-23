package com.intuit.ui.perf.beans;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Source {

	@JsonDeserialize(as = String.class, contentAs = String.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String ref;

	@JsonDeserialize(as = long.class, contentAs = String.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private long runtimestamp;

	@JsonDeserialize(as = String.class, contentAs = String.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String datetime;

	@JsonDeserialize(as = String.class, contentAs = String.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String project;

	@JsonDeserialize(as = String.class, contentAs = String.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String datetimestamp;

	@JsonDeserialize(as = String.class, contentAs = String.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String networkCallCount;

	@JsonDeserialize(as = String.class, contentAs = String.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String page;

	@JsonDeserialize(as = long.class, contentAs = long.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private long loadtime;

	@JsonDeserialize(as = double.class, contentAs = double.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private double FirstPaintTime;

	@JsonDeserialize(as = long.class, contentAs = long.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private long TimeUntilPageLoaded;

	@JsonDeserialize(as = long.class, contentAs = long.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private long responsetime;

	@JsonDeserialize(as = long.class, contentAs = long.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private long shellLoadTime;

	@JsonDeserialize(as = long.class, contentAs = long.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private long ReadyStart;

	@JsonDeserialize(as = long.class, contentAs = long.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private long InitDomTreeTime;

	@JsonDeserialize(as = long.class, contentAs = long.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private long FrontEndTime;

	@JsonDeserialize(as = long.class, contentAs = long.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private long LoadEventTime;

	@JsonDeserialize(as = long.class, contentAs = long.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private long DNSLookupTime;

	@JsonDeserialize(as = long.class, contentAs = long.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private long DomProcessingTime;

	@JsonDeserialize(as = long.class, contentAs = long.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private long RedirectTime;

	@JsonDeserialize(as = long.class, contentAs = long.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private long TCPConnectTime;

	@JsonDeserialize(as = long.class, contentAs = long.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private long DomReadyTime;

	@JsonDeserialize(as = long.class, contentAs = long.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private long AppCacheTime;

	@JsonDeserialize(as = long.class, contentAs = long.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private long UnloadEventTime;

	@JsonDeserialize(as = long.class, contentAs = long.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private long TTFB;

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

	@JsonDeserialize(as = Long.class, contentAs = Long.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long firstByte;

	@JsonDeserialize(as = Integer.class, contentAs = Integer.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer domCount;

	@JsonDeserialize(as = Integer.class, contentAs = Integer.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer jsCount;

	@JsonDeserialize(as = Integer.class, contentAs = Integer.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer cssCount;

	@JsonDeserialize(as = Integer.class, contentAs = Integer.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer imgCount;

	@JsonDeserialize(as = Integer.class, contentAs = Integer.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer imageCount;

	public String getNetworkCallCount() {
		return networkCallCount;
	}

	public void setNetworkCallCount(String networkCallCount) {
		this.networkCallCount = networkCallCount;
	}

	public long getShellLoadTime() {
		return shellLoadTime;
	}

	public void setShellLoadTime(long shellLoadTime) {
		this.shellLoadTime = shellLoadTime;
	}

	public Long getFirstByte() {
		return firstByte;
	}

	@JsonDeserialize(as = String.class, contentAs = String.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String datetimestampk;

	public String getDatetimestamp() {
		return datetimestamp;
	}

	public void setDatetimestamp(String datetimestamp) {
		this.datetimestamp = datetimestamp;
	}

	public String getDatetimestampk() {
		return datetimestampk;
	}

	public void setDatetimestampk(String datetimestampk) {
		this.datetimestampk = datetimestampk;
	}

	public void setFirstByte(Long firstByte) {
		this.firstByte = firstByte;
	}

	public Integer getDomCount() {
		return domCount;
	}

	public void setDomCount(Integer domCount) {
		this.domCount = domCount;
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

	public String getBuild() {
		return build;
	}

	public void setBuild(String build) {
		this.build = build;
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

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public long getRuntimestamp() {
		return runtimestamp;
	}

	public void setRuntimestamp(long runtimestamp) {
		this.runtimestamp = runtimestamp;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public long getLoadtime() {
		return loadtime;
	}

	public void setLoadtime(long loadtime) {
		this.loadtime = loadtime;
	}

	public double getFirstPaintTime() {
		return FirstPaintTime;
	}

	public void setFirstPaintTime(double firstPaintTime) {
		FirstPaintTime = firstPaintTime;
	}

	public long getTimeUntilPageLoaded() {
		return TimeUntilPageLoaded;
	}

	public void setTimeUntilPageLoaded(long timeUntilPageLoaded) {
		TimeUntilPageLoaded = timeUntilPageLoaded;
	}

	public long getResponsetime() {
		return responsetime;
	}

	public void setResponsetime(long responsetime) {
		this.responsetime = responsetime;
	}

	public long getReadyStart() {
		return ReadyStart;
	}

	public void setReadyStart(long readyStart) {
		ReadyStart = readyStart;
	}

	public long getInitDomTreeTime() {
		return InitDomTreeTime;
	}

	public void setInitDomTreeTime(long initDomTreeTime) {
		InitDomTreeTime = initDomTreeTime;
	}

	public long getFrontEndTime() {
		return FrontEndTime;
	}

	public void setFrontEndTime(long frontEndTime) {
		FrontEndTime = frontEndTime;
	}

	public long getLoadEventTime() {
		return LoadEventTime;
	}

	public void setLoadEventTime(long loadEventTime) {
		LoadEventTime = loadEventTime;
	}

	public long getDNSLookupTime() {
		return DNSLookupTime;
	}

	public void setDNSLookupTime(long dNSLookupTime) {
		DNSLookupTime = dNSLookupTime;
	}

	public long getDomProcessingTime() {
		return DomProcessingTime;
	}

	public void setDomProcessingTime(long domProcessingTime) {
		DomProcessingTime = domProcessingTime;
	}

	public long getRedirectTime() {
		return RedirectTime;
	}

	public void setRedirectTime(long redirectTime) {
		RedirectTime = redirectTime;
	}

	public long getTCPConnectTime() {
		return TCPConnectTime;
	}

	public void setTCPConnectTime(long tCPConnectTime) {
		TCPConnectTime = tCPConnectTime;
	}

	public long getDomReadyTime() {
		return DomReadyTime;
	}

	public void setDomReadyTime(long domReadyTime) {
		DomReadyTime = domReadyTime;
	}

	public long getAppCacheTime() {
		return AppCacheTime;
	}

	public void setAppCacheTime(long appCacheTime) {
		AppCacheTime = appCacheTime;
	}

	public long getUnloadEventTime() {
		return UnloadEventTime;
	}

	public void setUnloadEventTime(long unloadEventTime) {
		UnloadEventTime = unloadEventTime;
	}

	public long getTTFB() {
		return TTFB;
	}

	public void setTTFB(long tTFB) {
		TTFB = tTFB;
	}

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

	public Integer getJsCount() {
		return jsCount;
	}

	public void setJsCount(Integer jsCount) {
		this.jsCount = jsCount;
	}

	public Integer getCssCount() {
		return cssCount;
	}

	public void setCssCount(Integer cssCount) {
		this.cssCount = cssCount;
	}

	public Integer getImgCount() {
		return imgCount;
	}

	public void setImgCount(Integer imgCount) {
		this.imgCount = imgCount;
	}

	public Integer getImageCount() {
		return imageCount;
	}

	public void setImageCount(Integer imageCount) {
		this.imageCount = imageCount;
	}

}
