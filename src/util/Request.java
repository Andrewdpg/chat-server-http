package util;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

public class Request {

    private String method;
    private String path;
    private String version;
    private String body;
    private Map<String, String> headers = new HashMap<>();
    private String[] params;

    public Request(String rawRequest) {
        String[] lines = rawRequest.split("\r\n");
        String[] requestLine = lines[0].split(" ");

        this.method = requestLine[0];
        this.path = requestLine[1];
        this.version = requestLine[2];

        for (int i = 1; i < lines.length; i++) {
            String[] header = lines[i].split(": ");
            headers.put(header[0], header[1]);
        }
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

    public String getVersion() {
        return version;
    }

    public Map<String, String> getHeaders() {
        return headers;
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

    @Override
    public String toString() {
        return "Request{" +
                "method='" + method + '\'' +
                ", path='" + path + '\'' +
                ", version='" + version + '\'' +
                ", headers=" + headers +
                '}';
    }
}