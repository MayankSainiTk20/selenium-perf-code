package com.intuit.ui.perf.utilities;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.inject.Inject;

public class BasicHttpRequestApache implements BasicHttpRequest {
	private String schema = null;
	private String hostPort =null;
	private RequestMethod requestMethod;
	private String path = null;
	private BasicNameValuePair[] parameters = null;
	private String entity = null;
	private URI uri = null;
	private final CloseableHttpClient client;
	private String responseString;

	@Inject
	public BasicHttpRequestApache() {
		System.out.print("Inside BasicHttpRequestApache");
		client = HttpClients.createDefault();
		System.out.print("End BasicHttpRequestApache");
	}

	public BasicHttpRequest setSchema(String schema) {
		if (this.schema != null) {
			throw new IllegalStateException("Schema already set");
		}
		this.schema = schema;
		return this;
	}

	
	public BasicHttpRequest setHostPort(String hostPort) {
		if (this.hostPort != null) {
			throw new IllegalStateException("Host and Port already set");
		}
		this.hostPort = hostPort;
		return this;
	}

	
	public String getHostPort(){
		return hostPort;
	}

	
	public BasicHttpRequest setRequestMethod(RequestMethod requestMethod) {
		this.requestMethod = requestMethod;
		return this;
	}

	
	public BasicHttpRequest setPath(String path) {
		this.path = path;
		uri = null;
		return this;
	}

	
		
	public BasicHttpRequest setParameters(String[][] parameters) {
		if (parameters == null) {
			this.parameters = null;
		} else {
			BasicNameValuePair[] parms = new BasicNameValuePair[parameters.length];
			int i = 0;
			for (String[] param : parameters) {
				if (param.length != 2) {
					throw new IllegalArgumentException("Parameter[" + i + "] must all be arrays of length 2");
				}
				String trimmedName;
				if (param[0] == null || (trimmedName = param[0].trim()).equals("")) {
					throw new IllegalArgumentException("Parameter[" + i + "][0] must not be null or blank");
				}
				parms[i++] = new BasicNameValuePair(trimmedName, param[1] == null ? "" : param[1].trim());
			}
			this.parameters = parms;
		}
		uri = null;
		return this;
	}

	
	public BasicHttpRequest setEntity(EntityFormat format, String entity) {
		if (format != EntityFormat.JSON) {
			throw new IllegalArgumentException("Format must be JSON");
		}
		this.entity = entity;
		return this;
	}

	
	public int execute() {
		if (this.schema == null) {
			throw new IllegalStateException("Schema must be set before execute");
		}
		if (this.hostPort == null) {
			throw new IllegalStateException("Host and Port must be set before execute");
		}
		if (requestMethod == null) {
			throw new IllegalStateException("Request method must be set before execute");
		}
		if (path == null) {
			throw new IllegalStateException("Path must be set before execute");
		}
		if (uri == null) {
			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
			
			if (parameters != null) {
				for (BasicNameValuePair nameValue : parameters) {
					nameValuePair.add(nameValue);
				}
			}
			try {
				URIBuilder uriBuilder = new URIBuilder().setScheme("http").setHost(hostPort).setPath(path);
				System.out.println("URL - " + uriBuilder);
				if (nameValuePair.size() > 0) {
					uriBuilder.setParameters(nameValuePair);
					
				}
				uri = uriBuilder.build();
				System.out.println(uri);
			} catch (URISyntaxException e) {
				return -1;
			}
		}
		final HttpUriRequest httpRequest;
		System.out.println(requestMethod);
		switch(requestMethod) {
		case GET:
			httpRequest = new HttpGet(uri);
			break;
		case PUT:
			httpRequest = new HttpPut(uri);
			break;
		case POST:
			httpRequest = new HttpPost(uri);
			break;
		case HEAD:
			httpRequest = new HttpHead(uri);
			break;
		case DELETE:
			httpRequest = new HttpDelete(uri);
			break;
		default:
			throw new IllegalStateException("request method is not valid");
		}
		if (HttpEntityEnclosingRequestBase.class.isAssignableFrom(httpRequest.getClass())) {
			if (entity == null) {
			}
			((HttpEntityEnclosingRequestBase)httpRequest).setEntity(
					new StringEntity(entity, ContentType.create("text/plain", "UTF-8")));
		}
		CloseableHttpResponse response;
		try {
			response = client.execute(httpRequest);
			final StatusLine status = response.getStatusLine();
			int rc = -4;
			if (status != null) {
				rc = status.getStatusCode();
				if (requestMethod == RequestMethod.HEAD) {
					responseString = "";
				} else {
					HttpEntity entity = response.getEntity();
					responseString = EntityUtils.toString(entity);
				}
			}
			return rc;
		} catch (ClientProtocolException e) {
			return -2;
		} catch (IOException e) {
			return -3;
		}
	}

	
	public String getResponse() {
		return responseString;
	}

	
	public void close() {
		try {
			client.close();
		} catch (IOException e) {
			// ignore
		}
	}
}
