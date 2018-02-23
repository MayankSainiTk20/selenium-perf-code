package com.intuit.ui.perf.beans;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

public class VC_source {
	
	@JsonDeserialize(as = String.class, contentAs = String.class)
	private String ref;

	@JsonDeserialize(as = String.class, contentAs = String.class)
	private String time;

	@JsonDeserialize(as = String.class, contentAs = String.class)
	private String percentagecompletion;
	
	@JsonDeserialize(as = Long.class, contentAs = Long.class)
	private Long timestampinmillisecond;
	
	

	public Long getTimestampinmillisecond() {
		return timestampinmillisecond;
	}

	public void setTimestampinmillisecond(Long timestampinmillisecond) {
		this.timestampinmillisecond = timestampinmillisecond;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getPercentagecompletion() {
		return percentagecompletion;
	}

	public void setPercentagecompletion(String percentagecompletion) {
		this.percentagecompletion = percentagecompletion;
	}
}
