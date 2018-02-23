package com.intuit.ui.perf.beans;

import java.util.List;

public class VC_jsonList {
	private String _index;
	private String _type;
	private long _id;
	private double _score;
	private VC_source _source;
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

	public VC_source get_source() {
		return _source;
	}

	public void set_source(VC_source _source) {
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