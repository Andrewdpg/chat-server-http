package org.nosotros.http.util;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import org.nosotros.chat.PeopleController;

public class Request {

    private String method;
    private String path;
    private Map<String, String> headers = new HashMap<>();
    private String[] params;
    private String body;
    private Map<String, String> form = new HashMap<>();
    private Map<String, String> cookies = new HashMap<>();


    public Request(InputStream inputStream) throws IOException {
        final List<String> metadataLines = new ArrayList<>();

        final StringBuilder lineBuilder = new StringBuilder();
        int b;
        boolean wasNewLine = false;

        while ((b = inputStream.read()) >= 0) {
            if (b == '\r') {
                int next = inputStream.read();
                if (next == '\n') {
                    if (wasNewLine) {
                        break;
                    }
                    wasNewLine = true;
                    metadataLines.add(lineBuilder.toString());
                    lineBuilder.setLength(0);
                }
            } else {
                lineBuilder.append((char) b);
                wasNewLine = false;
            }
        }

        final String firstLine = metadataLines.get(0);
        this.method = firstLine.split("\\s+")[0];
        this.path = firstLine.split("\\s+")[1];

        for (int i = 1; i < metadataLines.size(); i++) {
            String headerLine = metadataLines.get(i);
            if (headerLine.trim().isEmpty()) {
                break;
            }

            String key = headerLine.split(":\\s")[0];
            String value = headerLine.split(":\\s")[1];

            if (key.equals("Cookie")) {
                setCooKies(value);
                continue;
            }

            headers.put(key, value);
        }

        if (headers.containsKey("Content-Length")) {
            readBody(inputStream);
        }
    }

    public void readBody(InputStream inputStream) throws IOException {
        int remaining = Integer.parseInt(headers.get("Content-Length"));
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        final byte[] buff = new byte[2048];

        while (remaining > 0) {
            int read = inputStream.read(buff, 0, Math.min(remaining, buff.length));
            os.write(buff, 0, read);
            remaining -= read;
        }

        this.body = os.toString();
        if(this.body.contains("&")){
            String[] pairs = this.body.split("&");
            for(String pair : pairs){
                String[] keyValue = pair.split("=");
                form.put(keyValue[0], keyValue[1]);
            }
        }
    }

    private void setCooKies(String cookie) {
        String[] cookies = cookie.split(";");
        for (String c : cookies) {
            String[] keyValue = c.split("=");
            this.cookies.put(keyValue[0].trim(), keyValue[1].trim());
        }
    }

    public String getFormValue(String key){
        return form.get(key);
    }

    public void setParams(Matcher matcher){
        params = new String[matcher.groupCount()];
        for(int i=0; i<matcher.groupCount(); i++){
            params[i] = matcher.group(i+1);
        }
    }

    public String[] getParams(){
        return params;
    }

    public void setBody(String body){
        this.body=body;
    }

    public String getBody(){
        return body;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public String getCookie(String key) {
        return cookies.get(key);
    }

    public boolean isGet() {
        return method.equals("GET");
    }

    public boolean isPost() {
        return method.equals("POST");
    }

    public boolean isPut() {
        return method.equals("PUT");
    }

    public boolean isDelete() {
        return method.equals("DELETE");
    }

    public boolean isHead() {
        return method.equals("HEAD");
    }

    public boolean isOptions() {
        return method.equals("OPTIONS");
    }

    public boolean isTrace() {
        return method.equals("TRACE");
    }

    public boolean isConnect() {
        return method.equals("CONNECT");
    }

    public boolean isPatch() {
        return method.equals("PATCH");
    }

    public boolean isAuthenticated(){
        return cookies.containsKey("cSessionId") && cookies.containsKey("cUsername") && PeopleController.personExists(cookies.get("cUsername"));
    }

    @Override
    public String toString() {
        return "Request{" +
                "method='" + method + '\'' +
                ", path='" + path + '\'' +
                ", headers=" + headers +
                '}';
    }
}