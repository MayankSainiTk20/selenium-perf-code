package com.intuit.ui.perf.elasticsearch.beans;

import java.util.List;

public class ESMustVO {

	private List<ESQueryStringVO> must;

	public List<ESQueryStringVO> getMust() {
		return must;
	}

	public void setMust(List<ESQueryStringVO> must) {
		this.must = must;
	}
}
