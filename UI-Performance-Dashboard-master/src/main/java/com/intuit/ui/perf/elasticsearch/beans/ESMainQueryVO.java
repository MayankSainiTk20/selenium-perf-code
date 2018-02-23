package com.intuit.ui.perf.elasticsearch.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ESMainQueryVO {
	
	private ESBoolVO query;
	private int from;
	private int size;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<ESSortVO> sort;
	
	public ESBoolVO getQuery() {
		return query;
	}

	public void setQuery(ESBoolVO query) {
		this.query = query;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<ESSortVO> getSort() {
		return sort;
	}

	public void setSort(List<ESSortVO> sort) {
		this.sort = sort;
	}
}

