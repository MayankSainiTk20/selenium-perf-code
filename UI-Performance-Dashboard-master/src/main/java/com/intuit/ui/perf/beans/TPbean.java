package com.intuit.ui.perf.beans;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import com.fasterxml.jackson.annotation.JsonInclude;

public class TPbean {

	@JsonProperty("1.0")
	@JsonDeserialize(as = String.class, contentAs = String.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String one;

	@JsonProperty("5.0")
	@JsonDeserialize(as = String.class, contentAs = String.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String five;

	@JsonProperty("25.0")
	@JsonDeserialize(as = String.class, contentAs = String.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String twentyfive;

	@JsonProperty("50.0")
	@JsonDeserialize(as = String.class, contentAs = String.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String fifty;

	@JsonProperty("75.0")
	@JsonDeserialize(as = String.class, contentAs = String.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String seventyfive;

	@JsonProperty("95.0")
	@JsonDeserialize(as = String.class, contentAs = String.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String nintyfive;

	@JsonProperty("99.0")
	@JsonDeserialize(as = String.class, contentAs = String.class)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String nintynine;

	public String getOne() {
		return one;
	}

	public void setOne(String one) {
		this.one = one;
	}

	public String getFive() {
		return five;
	}

	public void setFive(String five) {
		this.five = five;
	}

	public String getTwentyfive() {
		return twentyfive;
	}

	public void setTwentyfive(String twentyfive) {
		this.twentyfive = twentyfive;
	}

	public String getFifty() {
		return fifty;
	}

	public void setFifty(String fifty) {
		this.fifty = fifty;
	}

	public String getSeventyfive() {
		return seventyfive;
	}

	public void setSeventyfive(String seventyfive) {
		this.seventyfive = seventyfive;
	}

	public String getNintyfive() {
		return nintyfive;
	}

	public void setNintyfive(String nintyfive) {
		this.nintyfive = nintyfive;
	}

	public String getNintynine() {
		return nintynine;
	}

	public void setNintynine(String nintynine) {
		this.nintynine = nintynine;
	}

}
