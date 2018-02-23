package com.intuit.ui.perf.services;

import javax.ws.rs.core.MediaType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.CollectionType;
import org.codehaus.jackson.map.type.TypeFactory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.intuit.ui.perf.beans.BaselineVO;
import com.intuit.ui.perf.beans.JsonList;
import com.intuit.ui.perf.beans.ResourceList;
import com.intuit.ui.perf.beans.ResourceListPerfStats;
import com.intuit.ui.perf.beans.Source;
import com.intuit.ui.perf.beans.TPbean;
import com.intuit.ui.perf.beans.VC_jsonList;
import com.intuit.ui.perf.beans.WorkFlowBasedPageBean;
import com.intuit.ui.perf.beans.WorkFlowBean;
import com.intuit.ui.perf.beans.WorkflowData;
import com.intuit.ui.perf.beans.WorkflowVO;
import com.intuit.ui.perf.elasticsearch.ElasticSearchProcessor;
import com.intuit.ui.perf.elasticsearch.beans.ESCreateVisualCompletionPercentPayload;

@JsonIgnoreProperties(ignoreUnknown = true)
@Path("project")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProjectResources {

	/************************************************************************************************************
	 * DESCRIPTION Webservice to get all Project
	 * 
	 * @param workflow
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 ************************************************************************************************************/
	@POST
	@Path("projects")
	public Response getAllProjects(WorkFlowBean workflow) throws JsonProcessingException, IOException {
		System.out.println("Querying all projects...........");
		List<String> projects = new ArrayList<String>();
		ElasticSearchProcessor esSearchParser = new ElasticSearchProcessor();
		String env = workflow.getEnv();
		String query = "{\"query\":{\"bool\":{\"must\":[{\"match_all\":{}}]}},\"from\":0,\"size\":1000,\"aggs\" : {\"langs\" : {\"terms\" : {\"field\" : \"project\" ,\"size\" : 1000 }}}}";

		String result = esSearchParser.queryElasticSearch(query, env);

		System.out.println("Result of Query Execution=====" + result);

		ObjectMapper MainResourceMapper = new ObjectMapper();
		JsonNode mainResourceNode = MainResourceMapper.readTree(result);
		JsonNode mainResourceResultNode = mainResourceNode.get("aggregations").get("langs").get("buckets");
		List<JsonNode> regs = mainResourceResultNode.findValues("key");
		for (JsonNode reg : regs) {
			projects.add(reg.asText());
		}
		//GenericEntity<List<String>> entity = new GenericEntity<List<String>>(projects) {};
		
		return Response.ok(projects).build();
	}

	/************************************************************************************************************
	 * DESCRIPTION Webservice to get all Builds
	 * 
	 * @param workflow
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 ************************************************************************************************************/
	@POST
	@Path("builds")
	public Response getBuilds(WorkFlowBean workflow) throws JsonProcessingException, IOException {
		System.out.println("Querying all projects...........");
		Set<String> builds = new HashSet();
		ElasticSearchProcessor esSearchParser = new ElasticSearchProcessor();
		String env = workflow.getEnv();
		String query = "{\"query\":{\"bool\":{\"must\":[{\"query_string\":{\"default_field\":\"performancemainresources.project\",\"query\":\""
				+ workflow.getProject()
				+ "\"}}],\"must_not\":[],\"should\":[]}},\"from\":0,\"size\":1000,\"sort\":[],\"facets\":{}}";

		String result = esSearchParser.queryElasticSearch(query, env);

		System.out.println("Result of Query Execution=====" + result);

		ObjectMapper MainResourceMapper = new ObjectMapper();
		JsonNode mainResourceNode = MainResourceMapper.readTree(result);
		JsonNode mainResourceResultNode = mainResourceNode.get("hits").get("hits");
		List<JsonNode> regs = mainResourceResultNode.findValues("build");
		for (JsonNode reg : regs) {
			builds.add(reg.asText());
		}
		return Response.ok(builds).build();
	}

	/************************************************************************************************************
	 * DESCRIPTION Webservice to get all Countries
	 * 
	 * @param workflow
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 ************************************************************************************************************/
	@POST
	@Path("regions")
	public Response getCountries(WorkFlowBean workflow) throws JsonProcessingException, IOException {
		System.out.println("Querying all projects...........");
		Set<String> regions = new HashSet();
		ElasticSearchProcessor esSearchParser = new ElasticSearchProcessor();
		String env = workflow.getEnv();
		String query = "{\"query\":{\"bool\":{\"must\":[{\"query_string\":{\"default_field\":\"performancemainresources.build\",\"query\":\""
				+ workflow.getProject()
				+ "\"}}],\"must_not\":[],\"should\":[]}},\"from\":0,\"size\":1000,\"sort\":[],\"facets\":{}}";

		String result = esSearchParser.queryElasticSearch(query, env);

		System.out.println("Result of Query Execution=====" + result);

		ObjectMapper MainResourceMapper = new ObjectMapper();
		JsonNode mainResourceNode = MainResourceMapper.readTree(result);
		JsonNode mainResourceResultNode = mainResourceNode.get("hits").get("hits");
		List<JsonNode> regs = mainResourceResultNode.findValues("scriptrunningregion");
		for (JsonNode reg : regs) {
			regions.add(reg.asText());
		}
		return Response.ok(regions).build();
	}

	/************************************************************************************************************
	 * DESCRIPTION Webservice to get all workflows based on selected project
	 * 
	 * @param workflow
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 ************************************************************************************************************/

	@POST
	@Path("workflows")
	public Response getWorkflowsBasedOnProject(WorkFlowBean workflow) throws JsonProcessingException, IOException {
		List<String> pages = new ArrayList<String>();
		ElasticSearchProcessor esSearchParser = new ElasticSearchProcessor();
		String env = workflow.getEnv();
		String query = "{\"query\":{\"bool\":{\"must\":[{\"query_string\": {\"default_field\": \"performancemainresources.project\","
				+ "\"query\": \"" + workflow.getProject()
				+ "\"}}, {\"query_string\": {\"default_field\": \"performancemainresources.build\",\"query\": \""
				+ workflow.getBuild()
				+ "\"}}],\"must_not\":[],\"should\":[]}},\"from\":0,\"size\":1000,\"sort\":[],\"facets\":{}}";

		// +
		// "[{\"query_string\":{\"default_field\":\"performancemainresources.project\",\"query\":\""
		// + workflow.getProject()
		// +
		// "\"}}],\"must_not\":[],\"should\":[]}},\"from\":0,\"size\":1000,\"sort\":[],\"facets\":{}}";
		String result = esSearchParser.queryElasticSearch(query, env);

		System.out.println("Result of Query Execution=====" + result);

		ObjectMapper MainResourceMapper = new ObjectMapper();
		JsonNode mainResourceNode = MainResourceMapper.readTree(result);
		JsonNode mainResourceResultNode = mainResourceNode.get("hits").get("hits");

		Set<JsonNode> regs = new HashSet<JsonNode>(mainResourceResultNode.findValues("workflow"));

		for (JsonNode reg : regs) {
			pages.add(reg.asText());
		}
		return Response.ok(pages).build();
	}

	/************************************************************************************************************
	 * DESCRIPTION Webservice to get all pages based on selected workflow
	 * 
	 * @param workflow
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 ************************************************************************************************************/

	@POST
	@Path("pages")
	public Response getPageResources(WorkFlowBean workflow) throws JsonProcessingException, IOException {
		ElasticSearchProcessor esSearchParser = new ElasticSearchProcessor();
		String env = workflow.getEnv();
		String wflow = workflow.getWorkflow();
		String payload;
		if (workflow.getRegion() == null) {
			payload = "{\"query\":{\"bool\":{\"must\":[{\"query_string\":{\"default_field\":\"performancemainresources.project\",\"query\":\""
					+ workflow.getProject()
					+ "\"}},{\"query_string\":{\"default_field\":\"performancemainresources.workflow\",\"query\":\""
					+ wflow + "\"}}"
					+ ",{\"query_string\": {\"default_field\": \"performancemainresources.build\",\"query\": \""
					+ workflow.getBuild() + "\"}}"
					+ "],\"must_not\":[],\"should\":[]}},\"from\":0,\"size\":1000,\"sort\":[],\"facets\":{}}";
		} else {
			payload = "{\"query\":{\"bool\":{\"must\":[{\"query_string\":{\"default_field\":\"performancemainresources.project\",\"query\":\""
					+ workflow.getProject()
					+ "\"}},{\"query_string\":{\"default_field\":\"performancemainresources.workflow\",\"query\":\""
					+ wflow + "\"}}"
					+ ",{\"query_string\": {\"default_field\": \"performancemainresources.build\",\"query\": \""
					+ workflow.getBuild() + "\"}}"
					+ ",{\"query_string\": {\"default_field\": \"performancemainresources.scriptrunningregion\",\"query\": \""
					+ workflow.getRegion() + "\"}}"
					+ "],\"must_not\":[],\"should\":[]}},\"from\":0,\"size\":1000,\"sort\":[],\"facets\":{}}";
		}

		System.out.println("Average Payload Request =============>" + payload);
		String result = esSearchParser.queryElasticSearch(payload, env);
		System.out.println("Average Payload response =============>" + result);

		ObjectMapper MainResourceMapper = new ObjectMapper();
		JsonNode mainResourceNode = MainResourceMapper.readTree(result);
		JsonNode mainResourceResultNode = mainResourceNode.get("hits").get("hits");

		HashMap<String, WorkFlowBasedPageBean> wfData = new HashMap<>();

		List<JsonNode> abc = mainResourceResultNode.findParents("_source");

		WorkFlowBasedPageBean page = null;
		for (JsonNode ab : abc) {

			if (wfData.containsKey(ab.findValue("page").asText())) {
				WorkFlowBasedPageBean pagewf = wfData.get(ab.findValue("page").asText());

				if (pagewf.getCount() > 30) {
					break;
				}
				pagewf.setLast30run((pagewf.getLast30run() + ab.findValue("loadtime").asLong()));
				pagewf.setLast30si((pagewf.getLast30si() + ab.findValue("speedindex").asLong()));
				pagewf.setLast30rum((pagewf.getLast30rum() + ab.findValue("rum").asLong()));
				pagewf.setLast30_domCount((pagewf.getLast30_domCount() + ab.findValue("domCount").asInt()));
				pagewf.setLast30_firstByte((pagewf.getLast30_firstByte() + ab.findValue("firstByte").asLong()));

				pagewf.setLast30_shellLoadTime(
						(pagewf.getLast30_shellLoadTime() + ab.findValue("shellLoadTime").asLong()));

				pagewf.setCount(pagewf.getCount() + 1);
				wfData.put(ab.findValue("page").asText(), pagewf);
			} else {
				page = new WorkFlowBasedPageBean();
				long loadtime = ab.findValue("loadtime").asLong();
				long speedindex = ab.findValue("speedindex").asLong();
				long rum = ab.findValue("rum").asLong();
				long firstByte = ab.findValue("firstByte").asLong();
				int domCount = ab.findValue("domCount").asInt();
				long shellLoadTime = 0;
				String ref_id = "";
				if (ab.findValue("shellLoadTime") != null) {
					shellLoadTime = ab.findValue("shellLoadTime").asLong();
					ref_id = ab.findValue("ref").asText();
				}

				System.out.println(ab.findValue("page") + "------" + loadtime);
				page.setLastruntime(loadtime);
				page.setLastrunsi(speedindex);
				page.setLastrunrum(rum);
				page.setRef_id(ref_id);
				page.setLastrun_FirstByte(firstByte);
				page.setLastrun_DOMCount(domCount);
				page.setShellLoadTime(shellLoadTime);

				page.setLast30_domCount(domCount);
				page.setLast30_firstByte(firstByte);
				page.setLast30run(loadtime);
				page.setLast30si(speedindex);
				page.setLast30rum(rum);
				page.setLast30_shellLoadTime(shellLoadTime);
				page.setCount(1);
				wfData.put(ab.findValue("page").asText(), page);
			}
		}

		// Baseline data
		String payload_baseline = "{\"query\":{\"bool\":{\"must\":[{\"query_string\":{\"default_field\":\"performancemainresources.project\",\"query\":\""
				+ workflow.getProject()
				+ "\"}},{\"query_string\":{\"default_field\":\"performancemainresources.workflow\",\"query\":\"" + wflow
				+ "\"}}"
				// +
				// ",{\"query_string\": {\"default_field\":
				// \"performancemainresources.build\",\"query\": \""+
				// workflow.getBuild()
				// +"\"}}"
				+ "],\"must_not\":[],\"should\":[]}},\"from\":0,\"size\":1000,\"sort\":[],\"facets\":{}}";

		System.out.println("Baseline Payload request =============>" + payload_baseline);
		String baselinedata = esSearchParser.queryElasticSearchBaseline(payload_baseline, env);
		System.out.println("Baseline Payload response =============>" + baselinedata);
		JsonNode baselineResourceNode = MainResourceMapper.readTree(baselinedata);
		JsonNode baselineResultNode = baselineResourceNode.get("hits").get("hits");
		List<JsonNode> baselinevalues = baselineResultNode.findValues("_source");

		for (JsonNode blv : baselinevalues) {

			String pagev = blv.get("page").asText();
			System.out.println(pagev);
			System.out.println(wfData.containsKey(pagev));

			if (wfData.containsKey(pagev)) {
				WorkFlowBasedPageBean wf = wfData.get(pagev);
				wf.setBaseline_loadtime(blv.findValue("loadtime").asLong());
				wf.setBaseline_si(blv.findValue("speedindex").asLong());
				wf.setBaseline_build(blv.findValue("build").asInt());
				wf.setBaseline_rum(blv.findValue("rum").asLong());
			}
		}

		return Response.ok(wfData).build();
	}

	/************************************************************************************************************
	 * DESCRIPTION Webservice to get TP Data for respective workflow breaking
	 * onto page level
	 * 
	 * @param workflow
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 ************************************************************************************************************/

	@POST
	@Path("tpdata")
	public Response getTPData(WorkFlowBean workflow) throws JsonProcessingException, IOException {
		ElasticSearchProcessor esSearchParser = new ElasticSearchProcessor();
		String env = workflow.getEnv();
		String field = null;

		String payload = "{\"query\":{\"bool\":{\"must\":[{\"query_string\":{\"default_field\":\"performancemainresources.page\",\"query\":\""
				+ workflow.getPage()
				+ "\"}},{\"query_string\":{\"default_field\":\"performancemainresources.region\",\"query\":\"US\"}},{\"query_string\":{\"default_field\":\"performancemainresources.scriptrunningregion\",\"query\":\"US\"}}]}},\"from\":0,\"size\":1000,\"aggs\" : {\"load_time_percentile\" : {\"percentiles\" : {\"field\" : \"loadtime\" }}},\"sort\":[{\"runtimestamp\":{\"order\":\"desc\"}}]}";

		String result = esSearchParser.queryElasticSearch(payload, env);

		System.out.println("Result of Query Execution=====" + result);

		ObjectMapper MainResourceMapper = new ObjectMapper();
		JsonNode mainResourceNode = MainResourceMapper.readTree(result);
		JsonNode mainResourceResultNode = mainResourceNode.get("aggregations").get("load_time_percentile")
				.get("values");

		ObjectMapper mainResourceObjectMapper = new ObjectMapper();
		TPbean tp = mainResourceObjectMapper.convertValue(mainResourceResultNode, TPbean.class);

		return Response.ok(tp).build();
	}

	@POST
	@Path("resources")
	public Response getResourcesOfPage(WorkFlowBean workflow) throws JsonProcessingException, IOException {
		ElasticSearchProcessor esSearchParser = new ElasticSearchProcessor();
		String env = workflow.getEnv();
		String page = null;

		if (env.equalsIgnoreCase("production")) {
			page = "Performance.QBO.Prod.UI." + workflow.getPage();
		} else if (env.equalsIgnoreCase("performance")) {
			page = "Performance.QBO.Perf.UI." + workflow.getPage();
		}else{
			page = "Performance.QBO.Perf.UI." + workflow.getPage();
		}

		String payload = "{\"query\": {\"bool\": {\"must\": [{\"query_string\": {\"default_field\": \"performancemainresources.project\",\"query\": \""
				+ workflow.getProject()
				+ "\"}}, {\"query_string\": {\"default_field\": \"performancemainresources.workflow\",\"query\": \""
				+ workflow.getWorkflow()
				+ "\"}}, {\"query_string\": {\"default_field\": \"performancemainresources.page\",\"query\": \"" + page
				+ "\"}}],\"must_not\": [],\"should\": []}},\"from\": 0,\"size\": 1000,\"sort\": [],\"facets\": {}}";

		String result = esSearchParser.queryElasticSearch(payload, env);

		ObjectMapper MainResourceMapper = new ObjectMapper();
		JsonNode mainResourceNode = MainResourceMapper.readTree(result);
		JsonNode mainResourceResultNode = mainResourceNode.get("hits").get("hits");

		ObjectMapper mainResourceObjectMapper = new ObjectMapper();
		TypeFactory mainResourceTypeFactory = mainResourceObjectMapper.getTypeFactory();

		CollectionType mainResourceCollectionType = mainResourceTypeFactory.constructCollectionType(List.class,
				JsonList.class);
		List<JsonList> mainResourceJsonList = (List<JsonList>) mainResourceObjectMapper
				.readValue(mainResourceResultNode, mainResourceCollectionType);

		return Response.ok(mainResourceJsonList).build();
	}

	@POST
	@Path("vcpercent")
	public Response getvisualcompletionpercentageData(WorkflowVO workflowvo)
			throws JsonProcessingException, IOException {
		ElasticSearchProcessor esSearchParser = new ElasticSearchProcessor();

		ESCreateVisualCompletionPercentPayload esObject = new ESCreateVisualCompletionPercentPayload();
		String payload = esObject.getESPayload(workflowvo.getRef_id());

		// String result = esSearchParser.queryElasticSearch(payload,
		// workflowvo.getEnv());
		String result = esSearchParser.queryVCPercentage(payload, workflowvo.getEnv());
		System.out.println("********************************" + result);

		ObjectMapper MainResourceMapper = new ObjectMapper();
		JsonNode mainResourceNode = MainResourceMapper.readTree(result);
		JsonNode mainResourceResultNode = mainResourceNode.get("hits").get("hits");

		ObjectMapper mainResourceObjectMapper = new ObjectMapper();
		TypeFactory mainResourceTypeFactory = mainResourceObjectMapper.getTypeFactory();

		CollectionType mainResourceCollectionType = mainResourceTypeFactory.constructCollectionType(List.class,
				VC_jsonList.class);
		List<JsonList> mainResourceJsonList = (List<JsonList>) mainResourceObjectMapper
				.readValue(mainResourceResultNode, mainResourceCollectionType);

		return Response.ok(mainResourceJsonList).build();
	}

	@POST
	@Path("subresources")
	public Response getPageSubResources(WorkflowVO workflowvo) throws JsonProcessingException, IOException {
		ElasticSearchProcessor esSearchParser = new ElasticSearchProcessor();

		ESCreateVisualCompletionPercentPayload esObject = new ESCreateVisualCompletionPercentPayload();
		String payload = esObject.getESSubResourcesPayload(workflowvo.getRef_id());

		String result = esSearchParser.queryElasticSearchforSubResources(payload, workflowvo.getEnv());

		ObjectMapper MainResourceMapper = new ObjectMapper();
		JsonNode mainResourceNode = MainResourceMapper.readTree(result);
		JsonNode mainResourceResultNode = mainResourceNode.get("hits").get("hits");

		ObjectMapper mainResourceObjectMapper = new ObjectMapper();
		TypeFactory mainResourceTypeFactory = mainResourceObjectMapper.getTypeFactory();

		CollectionType mainResourceCollectionType = mainResourceTypeFactory.constructCollectionType(List.class,
				ResourceList.class);
		List<JsonList> mainResourceJsonList = (List<JsonList>) mainResourceObjectMapper
				.readValue(mainResourceResultNode, mainResourceCollectionType);

		return Response.ok(mainResourceJsonList).build();
	}

	@POST
	@Path("perfstats")
	public Response getPerfStats(WorkflowVO workflowvo) throws JsonProcessingException, IOException {
		ElasticSearchProcessor esSearchParser = new ElasticSearchProcessor();

		ESCreateVisualCompletionPercentPayload esObject = new ESCreateVisualCompletionPercentPayload();
		String payload = esObject.getPerfStatsPayload(workflowvo.getRef_id());

		String result = esSearchParser.queryElasticSearchforPerfStats(payload, workflowvo.getEnv());

		ObjectMapper MainResourceMapper = new ObjectMapper();
		JsonNode mainResourceNode = MainResourceMapper.readTree(result);
		JsonNode mainResourceResultNode = mainResourceNode.get("hits").get("hits");

		ObjectMapper mainResourceObjectMapper = new ObjectMapper();
		TypeFactory mainResourceTypeFactory = mainResourceObjectMapper.getTypeFactory();

		CollectionType mainResourceCollectionType = mainResourceTypeFactory.constructCollectionType(List.class,
				ResourceListPerfStats.class);
		List<JsonList> mainResourceJsonList = (List<JsonList>) mainResourceObjectMapper
				.readValue(mainResourceResultNode, mainResourceCollectionType);

		return Response.ok(mainResourceJsonList).build();
	}

	@POST
	@Path("baselineservice")
	public Response setBaseLine(BaselineVO baselinevo) throws JsonProcessingException, IOException {

		System.out.println("In setBaseLine Method");
		System.out.println("env + " + baselinevo.getEnv());
		System.out.println("env + " + baselinevo.getRun_id());

		BaseLine baseline = new BaseLine();
		baseline.setBaseLine(baselinevo.getEnv(), baselinevo.getRun_id());
		return Response.ok("{\"result\":\"success\"}").build();
	}

	/************************************************************************************************************
	 * DESCRIPTION Webservice to get all workflows based on selected project
	 * 
	 * @param workflow
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 ************************************************************************************************************/

	@POST
	@Path("allworkflows")
	public Response getAllWorkflows(WorkFlowBean workflow) throws JsonProcessingException, IOException {
		List<String> pages = new ArrayList<String>();
		ElasticSearchProcessor esSearchParser = new ElasticSearchProcessor();
		String env = workflow.getEnv();
		String query = "{" + "\"query\": {" + "\"bool\": {" + "\"must\": [{" + "\"query_string\": {"
				+ "\"default_field\": \"performancemainresources.project\"," + "\"query\": \"" + workflow.getProject()
				+ "\"" + "}" + "}, {" + "\"query_string\": {"
				+ "\"default_field\": \"performancemainresources.workflow\"," + "\"query\": \"" + workflow.getWorkflow()
				+ "\"" + "}}]," + "\"must_not\": []," + "\"should\": []}}," + "\"from\": 0," + "\"size\": 1000,"
				+ "\"sort\": []," + "\"facets\": {}}";

		// "{\"query\":{\"bool\":{\"must\":[{\"query_string\":{\"default_field\":\"performancemainresources.project\",\"query\":\""
		// + workflow.getProject()
		// +
		// "\"}}],\"must_not\":[],\"should\":[]}},\"from\":0,\"size\":30,\"sort\":[],\"facets\":{}}";
		String result = esSearchParser.queryElasticSearch(query, "performance");

		System.out.println("Result of Query Execution=====" + result);

		ObjectMapper MainResourceMapper = new ObjectMapper();
		JsonNode mainResourceNode = MainResourceMapper.readTree(result);
		JsonNode mainResourceResultNode = mainResourceNode.get("hits").get("hits");

		Map<String, WorkflowData> workFlows = new HashMap<>();

		List<JsonNode> vals = mainResourceResultNode.findValues("_source");

		List<Source> sources;
		Source source;

		for (JsonNode val : vals) {

			if (workFlows.containsKey(val.findValue("run_id").asText())) {
				source = new Source();

				source.setPage(val.findValue("page").asText());
				source.setLoadtime(val.findValue("loadtime").asLong());
				source.setSpeedindex((float) val.findValue("speedindex").asDouble());
				source.setRum(val.findValue("rum").asLong());
				source.setFirstByte(val.findValue("firstByte").asLong());
				source.setDomCount(val.findValue("domCount").asInt());
				source.setRun_id(val.findValue("run_id").asText());

				WorkflowData wfo = workFlows.get(val.findValue("run_id").asText());
				wfo.getSources().add(source);
				wfo.setWorkflow_execution_time(wfo.getWorkflow_execution_time() + val.findValue("loadtime").asLong());
			} else {
				WorkflowData workflowData = new WorkflowData();
				sources = new ArrayList<>();
				source = new Source();

				source.setPage(val.findValue("page").asText());
				source.setLoadtime(val.findValue("loadtime").asLong());
				source.setSpeedindex((float) val.findValue("speedindex").asDouble());
				source.setRum(val.findValue("rum").asLong());
				source.setFirstByte(val.findValue("firstByte").asLong());
				source.setDomCount(val.findValue("domCount").asInt());
				source.setRun_id(val.findValue("run_id").asText());
				sources.add(source);

				workflowData.setSources(sources);
				workflowData.setDate_of_execution(val.findValue("datetime").asText());
				workflowData.setBuild(val.findValue("build").asText());
				workflowData.setRegion(val.findValue("region").asText());
				workflowData.setRun_id(val.findValue("run_id").asText());
				workflowData.setWorkflow_execution_time(val.findValue("loadtime").asLong());
				workflowData.setWorkflow(val.findValue("workflow").asText());

				workFlows.put(val.findValue("run_id").asText(), workflowData);
			}
		}

		return Response.ok(workFlows).build();
	}

	/*
	 * @POST
	 * 
	 * @Path("workflowbaseline") public Response
	 * getWorkflowBaseLineData(WorkFlowBean workflow) throws
	 * JsonProcessingException, IOException { ElasticSearchProcessor
	 * esSearchParser = new ElasticSearchProcessor(); String env =
	 * workflow.getEnv(); String query =
	 * "{\"query\":{\"bool\":{\"must\":[{\"query_string\":{\"default_field\":\"performancemainresources.project\",\"query\":\""
	 * + workflow.getProject() +
	 * "\"}}],\"must_not\":[],\"should\":[]}},\"from\":0,\"size\":1000,\"sort\":[],\"facets\":{}}"
	 * ; String result = esSearchParser.queryElasticSearchBaseline(query, env);
	 * 
	 * System.out.println("Result of Query Execution=====" + result);
	 * 
	 * ObjectMapper MainResourceMapper = new ObjectMapper(); JsonNode
	 * mainResourceNode = MainResourceMapper.readTree(result); JsonNode
	 * mainResourceResultNode = mainResourceNode.get("hits").get("hits");
	 * 
	 * List<JsonNode> values = mainResourceResultNode.findValues("_source");
	 * 
	 * return Response.ok(values).build(); }
	 */
}