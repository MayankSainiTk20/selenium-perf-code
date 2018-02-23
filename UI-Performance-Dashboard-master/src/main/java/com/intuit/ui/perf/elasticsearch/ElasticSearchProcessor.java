package com.intuit.ui.perf.elasticsearch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Random;

import com.intuit.ui.perf.utilities.BasicHttpRequest;
import com.intuit.ui.perf.utilities.BasicHttpRequestApache;
import com.intuit.ui.perf.utilities.BasicHttpRequest.EntityFormat;
import com.intuit.ui.perf.utilities.BasicHttpRequest.RequestMethod;

public class ElasticSearchProcessor {

	private String index = "intuitmarket1";
	private String type = "performance1";
	private BasicHttpRequest request;
	private long startTime = System.currentTimeMillis();
	private int documentCount = 0;
	private File documentFile;
	private File queryFile;
	private File settingsFile;
	private File mappingsFile;

	public ElasticSearchProcessor() {
		request = new BasicHttpRequestApache();
		request.setSchema("http").setHostPort("pppdc9prd743.corp.intuit.net:9200");

	}

	public void createIndex(BasicHttpRequest indexRequest, String index) {
		String slashIndex = "/" + index + "/performance1";
		indexRequest.setRequestMethod(RequestMethod.PUT).setPath(slashIndex).setEntity(EntityFormat.JSON, "");
		int rc = indexRequest.execute();
		String response = indexRequest.getResponse();
		if (rc != 200 && rc != 404) {
			System.out.println("Invalid response returned for add index rc=" + rc + " response=" + response);
		}

	}

	public int randomNumber() {
		Random rand = new Random();
		int random_num = rand.nextInt(1000000);
		return random_num;
	}

	public void insertSingleDocument(String json, int random) {
		String type = "performance1";

		String index = "intuitmarket1";

		// createIndex(request, index);
		System.out.println("Inside insertDocuments");
		System.out.println("Inside insertDocuments");
		String path = "/" + index + "/" + type + "/" + random;
		System.out.println("path - " + path);
		System.out.println("json - " + json);
		request.setRequestMethod(RequestMethod.PUT).setEntity(EntityFormat.JSON, json.toString()).setPath(path);
		int rc = request.execute();
		String response = request.getResponse();
		System.out.println(rc);
		if (rc != 200 || !response.contains("\"errors\":false")) {
			System.out.println("Invalid  response rc=" + rc);
		}
		refresh();
	}

	public void insertDocuments() {
		createIndex(request, index);

		// DirectoryReader listFilesUtil = new DirectoryReader();
		//
		// final String directoryLinuxMac ="/Users/ssinghthind/sam/_har";
		//
		// //Windows directory example
		// //final String directoryWindows ="C://test";
		//
		// ArrayList<String> ar = listFilesUtil.listFiles(directoryLinuxMac);
		//
		// int size = ar.size();
		//
		// for(int i = 0 ; i < size;i++){
		// String document = readFile(directoryLinuxMac+"/"+ar.get(i));
		// int random = randomNumber();
		// insertSingleDocument(document,random);
		// System.out.println("document - " + document);
		// gettimings(document);
	}

	private void gettimings(String json) {

		if (json.contains(
				"\"url\": \"https://intuitmarket.intuitcdn.net/Stylify/assets/img/harmony-marketing/icons-white.png\"")) {
			System.out.print("true");

		}

	}

	private String readFile(String filepath) {
		StringBuilder sb = new StringBuilder();
		FileReader fr = null;
		String document = null;
		System.out.println(filepath);

		try {
			fr = new FileReader(filepath);

		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}

		BufferedReader br = new BufferedReader(fr);

		try {
			while ((document = br.readLine()) != null) {

				sb.append(document);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// System.out.println("sb- " + );
		return sb.toString();

	}

	private boolean isAnIndex(String index) {
		request.setRequestMethod(RequestMethod.HEAD).setPath("/" + index);
		int rc = request.execute();
		return rc == 200;
	}

	private void refresh() {
		request.setRequestMethod(RequestMethod.POST).setEntity(EntityFormat.JSON, "").setPath("/_flush").execute();
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// ignore
		}
		request.setRequestMethod(RequestMethod.POST).setEntity(EntityFormat.JSON, "").setPath("/_refresh").execute();
	}

	public void getProcessor() {

		try {
			System.out.println("loading");

			insertDocuments();

			/*
			 * 
			 * System.out.println("Processing");
			 * //System.out.println("Searching"); //String path = "/" + index +
			 * "/" + type +"/"+"_search"; String path=
			 * "/intuitmarket/performance/_search"; String newsearchjson = "";
			 * //logger.info(newsearchjson);
			 * //request.setRequestMethod(RequestMethod
			 * .POST).setEntity(EntityFormat.JSON,
			 * newsearchjson.toString()).setPath(path);
			 * //request.setRequestMethod
			 * (RequestMethod.POST).setEntity(EntityFormat.JSON,
			 * newsearchjson.toString()).setPath(path);
			 * request.setRequestMethod(RequestMethod.GET).setPath(path); int rc
			 * = request.execute(); System.out.println(rc); String response =
			 * request.getResponse(); //int startIndex =
			 * response.indexOf("\"hits\":{\"total\":") + 16; //int endIndex =
			 * response.indexOf(",\"max_score\":", startIndex); //String
			 * foundCount = response.substring(startIndex, endIndex); //
			 * logger.info("foundCount - " + foundCount);
			 * //System.out.println(response); //System.out.print(response); if
			 * (rc != 200 || !response.contains("\"errors\":false")) { //
			 * System.out.println("Invalid bulk response rc=" + rc +
			 * " response=" + response); }
			 */

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public String queryElasticSearch(String json, String env) {
		String type = "_search";
		String index = null;

		if (env.equalsIgnoreCase("production")) {
			index = "prodqboperformancemainresources";
		} else if (env.equalsIgnoreCase("performance")) {
			index = "perfqboperformancemainresources";
		} else if (env.equalsIgnoreCase("1ClickCluster")) {
			index = "1clickqboperformancemainresources";
		}else if (env.equalsIgnoreCase("E2E")) {
			index = "e2eqboperformancemainresources";
		}else if (env.equalsIgnoreCase("silver-develop")) {
			index = "silver-developqboperformancemainresources";
		}else if (env.equalsIgnoreCase("silver-release")) {
			index = "silver-releaseqboperformancemainresources";
		}
		else {
			index = env+"qboperformancemainresources";
		}
		// createIndex(request, index);
		System.out.println("Searching Elastic ");
		String path = "/" + index + "/" + type;
		System.out.println("path - " + path);
		System.out.println("json - " + json);
		request.setRequestMethod(RequestMethod.POST).setEntity(EntityFormat.JSON, json.toString()).setPath(path);
		int rc = request.execute();
		String response = request.getResponse();
		System.out.println(rc);
		// if (rc != 200 || !response.contains("\"errors\":false")) {
		// System.out.println("Invalid response rc=" + response);
		// }
		refresh();
		return response;
	}

	public String queryElasticSearchBaseline(String json, String env) {
		String type = "_search";
		String index = null;

		if (env.equalsIgnoreCase("production")) {
			index = "prodbaselineqboperformancemainresources";
		} else if (env.equalsIgnoreCase("performance")) {
			index = "perfbaselineqboperformancemainresources";
		} else if (env.equalsIgnoreCase("1ClickCluster")) {
			index = "1clickqboperformancemainresources";
		} else if (env.equalsIgnoreCase("E2E")) {
			index = "e2eqboperformancemainresources";
		}else if (env.equalsIgnoreCase("silver-develop")) {
			index = "silver-developqboperformancemainresources";
		}else if (env.equalsIgnoreCase("silver-release")) {
			index = "silver-releaseqboperformancemainresources";
		}
		else {
			index = env+"qboperformancemainresources";
		}
		// createIndex(request, index);
		System.out.println("Searching Elastic ");
		String path = "/" + index + "/" + type;
		System.out.println("path - " + path);
		System.out.println("json - " + json);
		request.setRequestMethod(RequestMethod.POST).setEntity(EntityFormat.JSON, json.toString()).setPath(path);
		int rc = request.execute();
		String response = request.getResponse();
		System.out.println(rc);
		refresh();
		return response;
	}

	public String queryVCPercentage(String json, String env) {
		String type = "_search";
		String index = null;

		if (env.equalsIgnoreCase("production")) {
			index = "prodqboperformancevisualcompletionpercentage";
		} else if (env.equalsIgnoreCase("performance")) {
			index = "perfqboperformancevisualcompletionpercentage";
		}else if (env.equalsIgnoreCase("1ClickCluster")) {
			index = "1clickqboperformancevisualcompletionpercentage";
		}else if (env.equalsIgnoreCase("E2E")) {
			index = "e2eqboperformancevisualcompletionpercentage";
		}else if (env.equalsIgnoreCase("silver-develop")) {
			index = "silver-developqboperformancevisualcompletionpercentage";
		}else if (env.equalsIgnoreCase("silver-release")) {
			index = "silver-releaseqboperformancevisualcompletionpercentage";
		}
		else {
			index = env+"qboperformancevisualcompletionpercentage";
		}
		// createIndex(request, index);
		System.out.println("Searching Elastic ");
		String path = "/" + index + "/" + type;
		System.out.println("path - " + path);
		System.out.println("json - " + json);
		request.setRequestMethod(RequestMethod.POST).setEntity(EntityFormat.JSON, json.toString()).setPath(path);
		int rc = request.execute();
		String response = request.getResponse();
		System.out.println(rc);
		// if (rc != 200 || !response.contains("\"errors\":false")) {
		// System.out.println("Invalid response rc=" + response);
		// }
		refresh();
		return response;
	}

	public String queryElasticSearchforSubResources(String json, String env) {
		String type = "_search";
		String index = null;

		if (env.equalsIgnoreCase("production")) {
			index = "prodqboperformancesubresources";
		} else if (env.equalsIgnoreCase("performance")) {
			index = "perfqboperformancesubresources";
		}else if (env.equalsIgnoreCase("1ClickCluster")) {
			index = "1clickqboperformancesubresources";
		}else if (env.equalsIgnoreCase("E2E")) {
			index = "e2eqboperformancesubresources";
		}else if (env.equalsIgnoreCase("silver-develop")) {
			index = "silver-developqboperformancesubresources";
		}else if (env.equalsIgnoreCase("silver-release")) {
			index = "silver-releaseqboperformancesubresources";
		}
		else {
			index = env+"qboperformancesubresources";
		}
		//else
		// createIndex(request, index);
		System.out.println("Searching Elastic ");
		String path = "/" + index + "/" + type;
		System.out.println("path - " + path);
		System.out.println("json - " + json);
		request.setRequestMethod(RequestMethod.POST).setEntity(EntityFormat.JSON, json.toString()).setPath(path);
		int rc = request.execute();
		String response = request.getResponse();
		System.out.println(rc);
		// if (rc != 200 || !response.contains("\"errors\":false")) {
		// System.out.println("Invalid response rc=" + response);
		// }
		refresh();
		return response;
	}

	public String queryElasticSearchBaseLine(String json, String env) {
		String type = "_search";
		String index = null;

		if (env.equalsIgnoreCase("production")) {
			index = "prodbaselineqboperformancemainresources";
		} else if (env.equalsIgnoreCase("performance")) {
			index = "perfbaselineqboperformancemainresources";
		}
		else if (env.equalsIgnoreCase("performance")) {
			index = "1clickbaselineqboperformancemainresources";
		}else if (env.equalsIgnoreCase("performance")) {
			index = "e2ebaselineqboperformancemainresources";
		}else if (env.equalsIgnoreCase("silver-develop")) {
			index = "silver-developbaselineqboperformancemainresources";
		}else if (env.equalsIgnoreCase("silver-release")) {
			index = "silver-releasebaselineqboperformancemainresources";
		}
		else {
			index = env+"baselineqboperformancemainresources";
		}
		// createIndex(request, index);
		System.out.println("Searching Elastic ");
		String path = "/" + index + "/" + type;
		System.out.println("path - " + path);
		System.out.println("json - " + json);
		request.setRequestMethod(RequestMethod.POST).setEntity(EntityFormat.JSON, json.toString()).setPath(path);
		int rc = request.execute();
		String response = request.getResponse();
		System.out.println(rc);
		// if (rc != 200 || !response.contains("\"errors\":false")) {
		// System.out.println("Invalid response rc=" + response);
		// }
		refresh();
		return response;
	}

	public String queryElasticSearchforPerfStats(String json, String env) {
		String type = "_search";
		String index = null;

		if (env.equalsIgnoreCase("production")) {
			index = "prodqboperformanceperfstats";
		} else if (env.equalsIgnoreCase("performance")) {
			index = "perfqboperformanceperfstats";
		}else if (env.equalsIgnoreCase("performance")) {
			index = "1clickqboperformanceperfstats";
		}else if (env.equalsIgnoreCase("performance")) {
			index = "e2eqboperformanceperfstats";
		}else if (env.equalsIgnoreCase("silver-develop")) {
			index = "silver-developqboperformanceperfstats";
		}else if (env.equalsIgnoreCase("silver-release")) {
			index = "silver-releaseqboperformanceperfstats";
		}
		else {
			index = env+"qboperformanceperfstats";
		}
		// createIndex(request, index);
		System.out.println("Searching Elastic ");
		String path = "/" + index + "/" + type;
		System.out.println("path - " + path);
		System.out.println("json - " + json);
		request.setRequestMethod(RequestMethod.POST).setEntity(EntityFormat.JSON, json.toString()).setPath(path);
		int rc = request.execute();
		String response = request.getResponse();
		System.out.println(rc);
		// if (rc != 200 || !response.contains("\"errors\":false")) {
		// System.out.println("Invalid response rc=" + response);
		// }
		refresh();
		return response;
	}

}
