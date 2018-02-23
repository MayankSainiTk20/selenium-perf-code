package com.intuit.ui.perf.elasticsearch.beans;

import java.util.ArrayList;
import java.util.List;

import com.intuit.ui.perf.utilities.JSONConversion;

public class ESCreateVisualCompletionPercentPayload {
	
	ESMainQueryVO query;
	ESBoolVO bool;
	ESMustVO must;
	List<ESQueryStringVO> queries;
	ESQueryStringVO queryString1;
	ESQueryStringVO queryString2;
	ESQueryStringVO queryString3;
	List<ESSortVO> li;
	ESSortVO sort;
	ESRunTimeStampVO runtime;
	
	public String getESPayload(String refID) {
		query = new ESMainQueryVO();
		bool = new ESBoolVO();
		must = new ESMustVO();
		queries = new ArrayList<>();
		queryString1 = new ESQueryStringVO();

		ESQueryVO query1 = new ESQueryVO();
		query1.setDefault_field("vcpercentage.ref");
		query1.setQuery(refID);

		queryString1 = new ESQueryStringVO();
		queryString1.setQuery_string(query1);
		
		queries.add(queryString1);
		
		must.setMust(queries);

		li = new ArrayList<ESSortVO>();
		sort = new ESSortVO();
		runtime = new ESRunTimeStampVO();
		runtime.setOrder("asc");
		sort.setRuntimestamp(runtime);

		li.add(sort);

		bool.setBool(must);
		query.setQuery(bool);
		query.setSize(1000);
		query.setFrom(0);
		query.setSort(li);

		String payload = JSONConversion.object_To_JSON(query).replaceAll("runtimestamp", "time");
		System.out.println("GeneratedPayload=======================================" + payload);
		return payload;
	}
	
	
	
	public String getESSubResourcesPayload(String refID) {
		query = new ESMainQueryVO();
		bool = new ESBoolVO();
		must = new ESMustVO();
		queries = new ArrayList<>();
		queryString1 = new ESQueryStringVO();

		ESQueryVO query1 = new ESQueryVO();
		query1.setDefault_field("performancesubresources.ref");
		query1.setQuery(refID);

		queryString1 = new ESQueryStringVO();
		queryString1.setQuery_string(query1);
		
		queries.add(queryString1);
		
		must.setMust(queries);

		li = new ArrayList<ESSortVO>();

		bool.setBool(must);
		query.setQuery(bool);
		query.setSize(1000);
		query.setFrom(0);
		query.setSort(li);

		String payload = JSONConversion.object_To_JSON(query);
		System.out.println("GeneratedPayload=======================================" + payload);
		return payload;
	}

	
	public String getPerfStatsPayload(String refID) {
		query = new ESMainQueryVO();
		bool = new ESBoolVO();
		must = new ESMustVO();
		queries = new ArrayList<>();
		queryString1 = new ESQueryStringVO();

		ESQueryVO query1 = new ESQueryVO();
		query1.setDefault_field("performanceperfstats.ref");
		query1.setQuery(refID);

		queryString1 = new ESQueryStringVO();
		queryString1.setQuery_string(query1);
		
		queries.add(queryString1);
		
		must.setMust(queries);

		li = new ArrayList<ESSortVO>();

		bool.setBool(must);
		query.setQuery(bool);
		query.setSize(1000);
		query.setFrom(0);
		query.setSort(li);

		String payload = JSONConversion.object_To_JSON(query);
		System.out.println("GeneratedPayload for Perf Stats=======================================" + payload);
		return payload;
	}
	
	
}
