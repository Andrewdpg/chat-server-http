package org.nosotros.http.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Response {
    private String version;
    private int statusCode;
    private String statusText;
    private String body;
    private Map<String, String> headers;

    public Response(String version, int statusCode, String statusText, String body) {
        System.out.print(version + " " + statusCode + " " + statusText);
        this.version = version;
        this.statusCode = statusCode;
        this.statusText = statusText;
        this.body = body;
        this.headers = new HashMap<>();
    }

    public String getVersion() {
        return version;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusText() {
        return statusText;
    }

    public String getBody() {
        return body;
    }

    public void addHeader(String key, String value) {
        this.headers.put(key, value);
    }

    public byte[] getBytes() throws IOException {
        StringBuilder response = new StringBuilder();
        response.append(version).append(" ").append(statusCode).append(" ").append(statusText).append("\r\n");
        for (Map.Entry<String, String> header : headers.entrySet()) {
            response.append(header.getKey()).append(": ").append(header.getValue()).append("\r\n");
        }
        response.append("\r\n").append(body);
        return response.toString().getBytes("UTF-8");
    }
}