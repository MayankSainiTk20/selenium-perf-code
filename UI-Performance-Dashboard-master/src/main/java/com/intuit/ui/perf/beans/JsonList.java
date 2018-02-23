package com.intuit.ui.perf.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

public class JsonList {
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String _index;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String _type;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private long _id;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private double _score;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Source _source;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List sort;

	public List getSort() {
		return sort;
	}

	public void setSort(List sort) {
		this.sort = sort;
	}

	public String get_index() {
		return this._index;
	}

	public void set_index(String _index) {
		this._index = _index;
	}

	public String get_type() {
		return this._type;
	}

	public void set_type(String _type) {
		this._type = _type;
	}

	public Source get_source() {
		return this._source;
	}

	public void set_source(Source _source) {
		this._source = _source;
	}

	public long get_id() {
		return this._id;
	}

	public void set_id(long _id) {
		this._id = _id;
	}

	public double get_score() {
		return this._score;
	}

	public void set_score(double _score) {
		this._score = _score;
	}
}