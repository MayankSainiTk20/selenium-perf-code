package com.intuit.ui.perf.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import com.intuit.ui.perf.elasticsearch.ElasticSearchProcessor;
import com.intuit.ui.perf.elasticsearch.NewElasticSearchParser;
import com.intuit.ui.perf.elasticsearch.beans.IdAndReferenceId;
import com.intuit.ui.perf.elasticsearch.beans.ProjectWorkflowVO;

public class BaseLine {
	public static void main(String args[]) {

		String env = "performance";
		String run_id = "runidQJC22JKLADS3OL8SQ2L68PQS4CUBV8GSK7JL";

		ProjectWorkflowVO provo = fetchWorkflowProjectBuildFromRunId(run_id, env);
 
		ArrayList<IdAndReferenceId> idandrefIdList = fetchListofReferenceidsFromRunId(run_id, env);
		System.out.println(idandrefIdList.size());
		Boolean ifWorkflowExistsinBaseLine = checkIfWorkflowExistsInBaseLineTable(provo.getWorkflow(), env,provo.getScriptrunningregion(),provo.getProject());
		System.out.println("Value of ifWorkflowExistsinBaseLine - " + ifWorkflowExistsinBaseLine);
		if (ifWorkflowExistsinBaseLine) {
			
			ArrayList<IdAndReferenceId> idandrefIdListFromBaseLine = fetchListofReferenceidsFromRunIdForBaseLine(run_id, env,provo.getScriptrunningregion());
			
			
			
			updateWorkflowPagesByRef_id(idandrefIdList,idandrefIdListFromBaseLine, run_id, env);
		} else {
			insertWorkFlowPagesByRef_id(idandrefIdList, run_id, env);
		}
		
		
		

	}

	private static ArrayList<IdAndReferenceId> fetchListofReferenceidsFromRunIdForBaseLine(String run_id, String env,String scriptrunningregion)  {

		ArrayList<IdAndReferenceId> list = new ArrayList<IdAndReferenceId>();
		try {
			ElasticSearchProcessor es = new ElasticSearchProcessor();
			String json = "{\"query\":{\"bool\":{\"must\":[{\"query_string\":{\"default_field\":\"performancemainresources.run_id\",\"query\":\""+run_id+"\"}},{\"query_string\":{\"default_field\":\"performancemainresources.scriptrunningregion\",\"query\":\""+scriptrunningregion+"\"}}],\"must_not\":[],\"should\":[]}},\"from\":0,\"size\":10,\"sort\":[],\"facets\":{}}";
			System.out.println("fetchListofReferenceidsFromRunIdForBaseLine payload " + json );
			
			String response = es.queryElasticSearchBaseLine(json, env);
			System.out.println(response);

			ObjectMapper MainResourceMapper = new ObjectMapper();
			JsonNode mainResourceNode;
			mainResourceNode = MainResourceMapper.readTree(response);
			JsonNode mainResourceResultNode = mainResourceNode.get("hits").get("hits");
			List<JsonNode> ref_id = mainResourceResultNode.findValues("ref");
			List<JsonNode> _id = mainResourceResultNode.findValues("_id");
			int size = ref_id.size();
			for(int i = 0 ; i<size; i++){
				IdAndReferenceId obj = new IdAndReferenceId();
				obj.setRef_id(ref_id.get(i).asText());
				obj.set_id(_id.get(i).asText());
				list.add(obj);
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	

	private static void insertWorkFlowPagesByRef_id(ArrayList<IdAndReferenceId> idandrefIdList, String run_id, String env) {
		int size = idandrefIdList.size();
		NewElasticSearchParser es = new NewElasticSearchParser();
		String indexname = null;
		String type = "performancemainresources";

		if (env.equalsIgnoreCase("production")) {
			indexname = "prodbaselineqboperformancemainresources";
		} else if (env.equalsIgnoreCase("performance")) {
			indexname = "perfbaselineqboperformancemainresources";
		}

		for (int i = 0; i < idandrefIdList.size(); i++) {
			String value = jsongetPageDetailsFromES(idandrefIdList.get(i).getRef_id(), env);
			es.insertSingleDocument(value, randomNumber(), indexname, type);

		}

	}

	public static long randomNumber() {

		long random_num = System.currentTimeMillis();
		return random_num;
	}

	private static String jsongetPageDetailsFromES(String ref_id, String env) {
		String body = null;
		try {
			ElasticSearchProcessor es = new ElasticSearchProcessor();
			String json = "{\"query\":{\"bool\":{\"must\":[{\"query_string\":{\"default_field\":\"performancemainresources.ref\",\"query\":\""
					+ ref_id
					+ "\"}}],\"must_not\":[],\"should\":[]}},\"from\":0,\"size\":10,\"sort\":[],\"facets\":{}}";
			String response = es.queryElasticSearch(json, env);
			System.out.println(response);

			ObjectMapper MainResourceMapper = new ObjectMapper();
			JsonNode mainResourceNode;
			mainResourceNode = MainResourceMapper.readTree(response);
			JsonNode mainResourceResultNode = mainResourceNode.get("hits").get("hits");
			List<JsonNode> abc = mainResourceResultNode.findParents("_source");
			JsonNode xyz = abc.get(0).findValue("_source");
			body = xyz.toString();

			System.out.println("Body for Ref id " + ref_id + " is " + body);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return body;
	}

	private static void updateWorkflowPagesByRef_id(ArrayList<IdAndReferenceId> idandrefIdList, ArrayList<IdAndReferenceId> idandrefIdListFromBaseLine, String run_id, String env) {
		int size = idandrefIdList.size();
		NewElasticSearchParser es = new NewElasticSearchParser();
		String indexname = null;
		String type = "performancemainresources";

		if (env.equalsIgnoreCase("production")) {
			indexname = "prodbaselineqboperformancemainresources";
		} else if (env.equalsIgnoreCase("performance")) {
			indexname = "perfbaselineqboperformancemainresources";
		}

		for (int i = 0; i < idandrefIdList.size(); i++) {
			String newBaseLineValue = jsongetPageDetailsFromES(idandrefIdList.get(i).getRef_id(), env);
			String _idofPageFromBaselineTableUsingProjectWorkFlowAndPage = getIdofPageFromBaselineTableUsingProjectWorkFlowAndPage(idandrefIdList.get(i).getProject(),idandrefIdList.get(i).getWorkflow(),idandrefIdList.get(i).getPage(),env,idandrefIdList.get(i).getScriptrunningregion());
			es.insertSingleDocument(newBaseLineValue, Long.parseLong(_idofPageFromBaselineTableUsingProjectWorkFlowAndPage), indexname, type);

		}

	}

	private static String getIdofPageFromBaselineTableUsingProjectWorkFlowAndPage(String project, String workflow,
			String page, String env,String scriptrunningregion ) {
		String baseline_id = null;
		try {
			ElasticSearchProcessor es = new ElasticSearchProcessor();
			String json = "{\"query\":{\"bool\":{\"must\":[{\"query_string\":{\"default_field\":\"performancemainresources.project\",\"query\":\""+project+"\"}},{\"query_string\":{\"default_field\":\"performancemainresources.workflow\",\"query\":\""+workflow+"\"}},{\"query_string\":{\"default_field\":\"performancemainresources.scriptrunningregion\",\"query\":\""+scriptrunningregion+"\"}},{\"query_string\":{\"default_field\":\"performancemainresources.page\",\"query\":\""+page+"\"}}],\"must_not\":[],\"should\":[]}},\"from\":0,\"size\":10,\"sort\":[],\"facets\":{}}";				
			
			System.out.println("getIdofPageFromBaselineTableUsingProjectWorkFlowAndPage payload" + json);
			String response = es.queryElasticSearchBaseLine(json, env);
			System.out.println(response);

			ObjectMapper MainResourceMapper = new ObjectMapper();
			JsonNode mainResourceNode;
			mainResourceNode = MainResourceMapper.readTree(response);
			JsonNode mainResourceResultNode = mainResourceNode.get("hits").get("hits");
			List<JsonNode> ref_id = mainResourceResultNode.findValues("ref");
			JsonNode _id = mainResourceResultNode.findValue("_id");
		
			baseline_id = _id.asText();
			System.out.println("baseline_id - " + baseline_id);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return baseline_id;
	}
	private static Boolean checkIfWorkflowExistsInBaseLineTable(String workflow, String env,String scriptrunningregion,String project) {

		ElasticSearchProcessor es = new ElasticSearchProcessor();

		String json = "{\"query\":{\"bool\":{\"must\":[{\"query_string\":{\"default_field\":\"performancemainresources.workflow\",\"query\":\""+workflow+"\"}},{\"query_string\":{\"default_field\":\"performancemainresources.scriptrunningregion\",\"query\":\""+scriptrunningregion+"\"}},{\"query_string\":{\"default_field\":\"performancemainresources.project\",\"query\":\""+project+"\"}}],\"must_not\":[],\"should\":[]}},\"from\":0,\"size\":10,\"sort\":[],\"facets\":{}}";		
		System.out.println("checkIfWorkflowExistsInBaseLineTable payload- " + json );
		
		String response = es.queryElasticSearchBaseLine(json, env);
		System.out.println(response);

		if (response.contains(workflow)) {
			return true;
		} else {
			return false;
		}

	}

	private static ArrayList<IdAndReferenceId> fetchListofReferenceidsFromRunId(String run_id, String env) {

		ArrayList<IdAndReferenceId> list = new ArrayList<IdAndReferenceId>();
		try {
			ElasticSearchProcessor es = new ElasticSearchProcessor();
			String json = "{\"query\":{\"bool\":{\"must\":[{\"query_string\":{\"default_field\":\"performancemainresources.run_id\",\"query\":\""
					+ run_id
					+ "\"}}],\"must_not\":[],\"should\":[]}},\"from\":0,\"size\":10,\"sort\":[],\"facets\":{}}";
			String response = es.queryElasticSearch(json, env);
			System.out.println(response);

			ObjectMapper MainResourceMapper = new ObjectMapper();
			JsonNode mainResourceNode;
			mainResourceNode = MainResourceMapper.readTree(response);
			JsonNode mainResourceResultNode = mainResourceNode.get("hits").get("hits");
			List<JsonNode> ref_id = mainResourceResultNode.findValues("ref");
			List<JsonNode> _id = mainResourceResultNode.findValues("_id");
			List<JsonNode> project = mainResourceResultNode.findValues("project");
			List<JsonNode> workflow = mainResourceResultNode.findValues("workflow");
			List<JsonNode> page = mainResourceResultNode.findValues("page");
			List<JsonNode> scriptrunningregion = mainResourceResultNode.findValues("scriptrunningregion");
			int size = ref_id.size();
			for(int i = 0 ; i<size; i++){
				IdAndReferenceId obj = new IdAndReferenceId();
				obj.setRef_id(ref_id.get(i).asText());
				obj.set_id(_id.get(i).asText());
				obj.setProject(project.get(i).asText());
				obj.setWorkflow(workflow.get(i).asText());
				obj.setPage(page.get(i).asText());
				obj.setScriptrunningregion(scriptrunningregion.get(i).asText());
				list.add(obj);
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	private static ProjectWorkflowVO fetchWorkflowProjectBuildFromRunId(String run_id, String env) {
		ProjectWorkflowVO provo = new ProjectWorkflowVO();
		try {
			ElasticSearchProcessor es = new ElasticSearchProcessor();
			String json = "{\"query\":{\"bool\":{\"must\":[{\"query_string\":{\"default_field\":\"performancemainresources.run_id\",\"query\":\""
					+ run_id
					+ "\"}}],\"must_not\":[],\"should\":[]}},\"from\":0,\"size\":10,\"sort\":[],\"facets\":{}}";
			String response = es.queryElasticSearch(json, env);
			System.out.println(response);

			ObjectMapper MainResourceMapper = new ObjectMapper();
			JsonNode mainResourceNode;
			mainResourceNode = MainResourceMapper.readTree(response);
			JsonNode mainResourceResultNode = mainResourceNode.get("hits").get("hits");
			JsonNode build = mainResourceResultNode.findValue("build");
			JsonNode project = mainResourceResultNode.findValue("project");
			JsonNode workflow = mainResourceResultNode.findValue("workflow");
			JsonNode scriptrunningregion = mainResourceResultNode.findValue("scriptrunningregion");
			System.out.println(build);
			System.out.println(project);
			System.out.println(workflow);
			System.out.println(scriptrunningregion);
			provo.setBuild(build.asText());
			provo.setProject(project.asText());
			provo.setWorkflow(workflow.asText());
			provo.setRun_id(run_id);
			provo.setScriptrunningregion(scriptrunningregion.asText());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return provo;
	}

	public void setBaseLine(String env, String run_id) {


		//String env = "performance";
	//	String run_id = "runidQJC22JKLADS3OL8SQ2L68PQS4CUBV8GSK7JL";

		ProjectWorkflowVO provo = fetchWorkflowProjectBuildFromRunId(run_id, env);

		ArrayList<IdAndReferenceId> idandrefIdList = fetchListofReferenceidsFromRunId(run_id, env);
		System.out.println(idandrefIdList.size());
		Boolean ifWorkflowExistsinBaseLine = checkIfWorkflowExistsInBaseLineTable(provo.getWorkflow(), env,provo.getScriptrunningregion(),provo.getProject());
		System.out.println("Value of ifWorkflowExistsinBaseLine - " + ifWorkflowExistsinBaseLine);
		if (ifWorkflowExistsinBaseLine) {
			
			ArrayList<IdAndReferenceId> idandrefIdListFromBaseLine = fetchListofReferenceidsFromRunIdForBaseLine(run_id, env, provo.getScriptrunningregion());
			
			
			
			updateWorkflowPagesByRef_id(idandrefIdList,idandrefIdListFromBaseLine, run_id, env);
		} else {
			insertWorkFlowPagesByRef_id(idandrefIdList, run_id, env);
		}
		
		
		
		
	}
}
