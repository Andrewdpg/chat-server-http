package util;
import java.io.IOException;

public class Response {
    private String version;
    private int statusCode;
    private String statusText;
    private String body;

    public Response(String version, int statusCode, String statusText, String body) {
        this.version = version;
        this.statusCode = statusCode;
        this.statusText = statusText;
        this.body = body;
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

    public byte[] getBytes() throws IOException {
        String response = version + " " + statusCode + " " + statusText + "\r\n\r\n" + body;
        return response.getBytes("UTF-8");
    }

    public byte[] getBytes(String decode) throws IOException {
        String response = version + " " + statusCode + " " + statusText + "\r\n\r\n" + body;
        return response.getBytes(decode);
    }
}