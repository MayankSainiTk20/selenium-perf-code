package com.intuit.ui.perf.beans;

public class ResourceListPerfStats {

	private String _index;
	private String _type;
	private long _id;
	private double _score;
	private PerfStats _source;

	public String get_index() {
		return _index;
	}

	public void set_index(String _index) {
		this._index = _index;
	}

	public String get_type() {
		return _type;
	}

	public void set_type(String _type) {
		this._type = _type;
	}

	public long get_id() {
		return _id;
	}

	public void set_id(long _id) {
		this._id = _id;
	}

	public double get_score() {
		return _score;
	}

	public void set_score(double _score) {
		this._score = _score;
	}

	public PerfStats get_source() {
		return _source;
	}

	public void set_source(PerfStats _source) {
		this._source = _source;
	}

}
