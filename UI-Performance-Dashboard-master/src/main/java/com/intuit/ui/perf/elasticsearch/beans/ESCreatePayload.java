package com.intuit.ui.perf.elasticsearch.beans;

import java.util.ArrayList;
import java.util.List;

import com.intuit.ui.perf.beans.WorkFlowBean;
import com.intuit.ui.perf.utilities.JSONConversion;

public class ESCreatePayload {


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
	
	public String getESPayload(WorkFlowBean workflow) {
		query = new ESMainQueryVO();
		bool = new ESBoolVO();
		must = new ESMustVO();
		queries = new ArrayList<>();
		queryString1 = new ESQueryStringVO();

		ESQueryVO query1 = new ESQueryVO();
		query1.setDefault_field("performancemainresources.page");
		query1.setQuery(workflow.getPage());

		ESQueryVO query2 = new ESQueryVO();
		query2.setDefault_field("performancemainresources.region");
		query2.setQuery(workflow.getRegion());

		ESQueryVO query3 = new ESQueryVO();
		query3.setDefault_field("performancemainresources.scriptrunningregion");
		query3.setQuery("US");

		queryString1 = new ESQueryStringVO();
		queryString1.setQuery_string(query1);
		queryString2 = new ESQueryStringVO();
		queryString2.setQuery_string(query2);
		queryString3 = new ESQueryStringVO();
		queryString3.setQuery_string(query3);

		queries.add(queryString1);
		queries.add(queryString2);
		queries.add(queryString3);

		must.setMust(queries);

		li = new ArrayList<ESSortVO>();
		sort = new ESSortVO();
		runtime = new ESRunTimeStampVO();
		runtime.setOrder("desc");
		sort.setRuntimestamp(runtime);

		li.add(sort);

		bool.setBool(must);
		query.setQuery(bool);
		query.setSize(workflow.getResources());
		query.setFrom(0);
		query.setSort(li);

		String payload = JSONConversion.object_To_JSON(query);
		System.out.println("GeneratedPayload=======================================" + payload);
		return payload;
	}
}
