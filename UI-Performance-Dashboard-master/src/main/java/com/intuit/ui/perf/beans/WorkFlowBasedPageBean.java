package com.intuit.ui.perf.beans;

import com.fasterxml.jackson.annotation.JsonInclude;

public class WorkFlowBasedPageBean {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private long lastruntime;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private long lastrunsi;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private long lastrun_FirstByte;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private long lastrun_DOMCount;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private long shellLoadTime;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private long lastrunrum;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String ref_id;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private long last10run;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private long last10si;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private long last30run;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private long last30si;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private long last30rum;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private long last30_firstByte;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private int last30_domCount;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private long last30_shellLoadTime;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private int count;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private long baseline_si;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private long baseline_loadtime;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private long baseline_build;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private long baseline_rum;

	public long getShellLoadTime() {
		return shellLoadTime;
	}

	public void setShellLoadTime(long shellLoadTime) {
		this.shellLoadTime = shellLoadTime;
	}

	public long getLast30_shellLoadTime() {
		return last30_shellLoadTime;
	}

	public void setLast30_shellLoadTime(long last30_shellLoadTime) {
		this.last30_shellLoadTime = last30_shellLoadTime;
	}

	public long getBaseline_build() {
		return baseline_build;
	}

	public void setBaseline_build(long baseline_build) {
		this.baseline_build = baseline_build;
	}

	public long getLastrun_FirstByte() {
		return lastrun_FirstByte;
	}

	public void setLastrun_FirstByte(long lastrun_FirstByte) {
		this.lastrun_FirstByte = lastrun_FirstByte;
	}

	public long getLastrun_DOMCount() {
		return lastrun_DOMCount;
	}

	public void setLastrun_DOMCount(long lastrun_DOMCount) {
		this.lastrun_DOMCount = lastrun_DOMCount;
	}

	public long getLast30_firstByte() {
		return last30_firstByte;
	}

	public void setLast30_firstByte(long last30_firstByte) {
		this.last30_firstByte = last30_firstByte;
	}

	public int getLast30_domCount() {
		return last30_domCount;
	}

	public void setLast30_domCount(int last30_domCount) {
		this.last30_domCount = last30_domCount;
	}

	public void setBaseline_rum(long baseline_rum) {
		this.baseline_rum = baseline_rum;
	}

	public long getBaseline_si() {
		return baseline_si;
	}

	public void setBaseline_si(long baseline_si) {
		this.baseline_si = baseline_si;
	}

	public long getBaseline_loadtime() {
		return baseline_loadtime;
	}

	public void setBaseline_loadtime(long baseline_loadtime) {
		this.baseline_loadtime = baseline_loadtime;
	}

	public long getBaseline_rum() {
		return baseline_rum;
	}

	public String getRef_id() {
		return ref_id;
	}

	public void setRef_id(String ref_id) {
		this.ref_id = ref_id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public long getLastruntime() {
		return lastruntime;
	}

	public void setLastruntime(long lastruntime) {
		this.lastruntime = lastruntime;
	}

	public long getLastrunsi() {
		return lastrunsi;
	}

	public void setLastrunsi(long lastrunsi) {
		this.lastrunsi = lastrunsi;
	}

	public long getLastrunrum() {
		return lastrunrum;
	}

	public void setLastrunrum(long lastrunrum) {
		this.lastrunrum = lastrunrum;
	}

	public long getLast10run() {
		return last10run;
	}

	public void setLast10run(long last10run) {
		this.last10run = last10run;
	}

	public long getLast10si() {
		return last10si;
	}

	public void setLast10si(long last10si) {
		this.last10si = last10si;
	}

	public long getLast30run() {
		return last30run;
	}

	public void setLast30run(long last30run) {
		this.last30run = last30run;
	}

	public long getLast30si() {
		return last30si;
	}

	public void setLast30si(long last30si) {
		this.last30si = last30si;
	}

	public long getLast30rum() {
		return last30rum;
	}

	public void setLast30rum(long last30rum) {
		this.last30rum = last30rum;
	}

}
