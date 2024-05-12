package org.nosotros.http.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Response {
    private String version;
    private int statusCode;
    private String statusText;
    private byte[] data;
    private boolean isBinary;

    private Map<String, String> headers;

    public Response(String version, int statusCode, String statusText, byte[] data) {
        System.out.print(version + " " + statusCode + " " + statusText);
        this.version = version;
        this.statusCode = statusCode;
        this.statusText = statusText;
        this.data = data;
        this.headers = new HashMap<>();
        this.isBinary = true;
    }

    public Response(String version, int statusCode, String statusText, byte[] data, boolean isBinary) {
        this(version, statusCode, statusText, data);
        this.isBinary = isBinary;
    }

    public Response(String version, int statusCode, String statusText, String body) {
        this(version, statusCode, statusText, body.getBytes());
        this.isBinary = false;
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

    public byte[] getData() {
        return data;
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
        response.append("\r\n");
        if (isBinary) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(response.toString().getBytes("UTF-8"));
            baos.write(data);
            return baos.toByteArray();
        } else {
            response.append(new String(data, "UTF-8"));
            return response.toString().getBytes("UTF-8");
        }
    }
}