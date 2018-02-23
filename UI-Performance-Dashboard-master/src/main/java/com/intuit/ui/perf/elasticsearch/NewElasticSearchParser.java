package com.intuit.ui.perf.elasticsearch;

import java.io.File;
import java.text.ParseException;
import java.util.Random;

import com.intuit.ui.perf.utilities.BasicHttpRequest;
import com.intuit.ui.perf.utilities.BasicHttpRequestApache;
import com.intuit.ui.perf.utilities.BasicHttpRequest.EntityFormat;
import com.intuit.ui.perf.utilities.BasicHttpRequest.RequestMethod;

public class NewElasticSearchParser {

	private String index = "intuitmarket1";
	private String type = "performance1";
	private BasicHttpRequest request;
	private long startTime = System.currentTimeMillis();
	private int documentCount = 0;
	private File documentFile;
	private File queryFile;
	private File settingsFile;
	private File mappingsFile;

	public NewElasticSearchParser() {
		System.out.print("Start NewElasticSearchParser");
		request = new BasicHttpRequestApache();
		System.out.print("Start NewElasticSearchParser 2");
		request.setSchema("http").setHostPort("pppdc9prd743.corp.intuit.net:9200");

		System.out.print("End NewElasticSearchParser");
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

	private void refresh() {
		request.setRequestMethod(RequestMethod.POST).setEntity(EntityFormat.JSON, "").setPath("/_flush").execute();
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// ignore
		}
		request.setRequestMethod(RequestMethod.POST).setEntity(EntityFormat.JSON, "").setPath("/_refresh").execute();
	}

	public void insertSingleDocument(String json, long n, String indexname, String type) {

		try {

			String path = "/" + indexname + "/" + type + "/" + n;
			System.out.println("---------------------JSON Start--------------------------------");

			json = json.replaceAll(",}", "}");
			System.out.println(json);
			System.out.println("path - " +path);
			System.out.println("-------------------------JSON Stop--------------------------------");

			request.setRequestMethod(RequestMethod.PUT).setEntity(EntityFormat.JSON, json.toString()).setPath(path);
			int rc = request.execute();
			String response = request.getResponse();

			if (rc != 200 || !response.contains("\"errors\":false")) {
				System.out.println("Invalid bulk response rc=" + rc + " response=" + response);
			}

			// refresh();
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public static void main(String[] args) throws ParseException {
		NewElasticSearchParser esSearchParser = new NewElasticSearchParser();
		String st = "{\"Ref\":\"QZ700HNC-XQ6R-0T6Q-J3R7-KE80ZDL14E6E\",\"RunTimeStamp\":\"1425120242\",\"fetchStart\":3585.05699993111,\"redirectStart\":0,\"redirectEnd\":0,\"requestStart\":0,\"secureConnectionStart\":0,\"responseEnd\":4726.18900006637,\"initiatorType\":\"link\",\"startTime\":3585.05699993111,\"domainLookupEnd\":0,\"duration\":1141.13200013526,\"entryType\":resource,\"responseStart\":0,\"connectEnd\":0,\"name\":\"https://staging-intuitmarket.intuitcdn.net/ui/css/AllCrescendoCSS.ashx?v=4.05\",\"connectStart\":0,\"domainLookupStart\":0}";

		// esSearchParser.insertSingleDocument(st,12);

	}

}
