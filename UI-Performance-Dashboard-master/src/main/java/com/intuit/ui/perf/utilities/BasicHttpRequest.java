
package com.intuit.ui.perf.utilities;

public interface BasicHttpRequest {
    public BasicHttpRequest setSchema(String schema);
    public BasicHttpRequest setHostPort(String hostPort);
    public enum RequestMethod { GET, PUT, POST, DELETE, HEAD };
    public BasicHttpRequest setRequestMethod(RequestMethod requestMethod);
    public BasicHttpRequest setPath(String path);
    public BasicHttpRequest setParameters(String[][] parameters);
    public enum EntityFormat { JSON };
    public BasicHttpRequest setEntity(EntityFormat format, String entity);
    public int execute();
    public String getResponse();
    public String getHostPort();
    public void close();
}
