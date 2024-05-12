package org.nosotros.http.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URLConnection;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.nosotros.App;
import org.nosotros.http.config.Urls;

public class UrlLink {

    View method;
    String pattern;
    Matcher matcher;

    public UrlLink(View method, String pattern) {
        this.method = method;
        this.pattern = pattern;
    }

    public static Response handle(Request request) throws IOException {
        System.out.print("\nRequest: " + request.getPath() + " - ");
        if (UrlLink.isFile(request.getPath())) {
            return readStatic(request.getPath());
        }
        UrlLink url = UrlLink.getUrl(request, Urls.urls);
        if (url != null)
            return url.execute(request);
        else
            return new Response("HTTP/1.1", 404, "Not Found", "Not Found");
    }

    public Response execute(Request request) {
        return method.handle(request);
    }

    public boolean match(Request request) {
        Pattern pattern = Pattern.compile(this.pattern + "\\??");
        matcher = pattern.matcher(request.getPath());
        if (matcher.matches()) {
            request.setParams(matcher);
        }
        return matcher.matches();
    }

    public static UrlLink getUrl(Request request, ArrayList<UrlLink> urls) {
        for (UrlLink url : urls) {
            try {
                if (url.match(request)) {
                    return url;
                }
            } catch (Exception e) {
            }
        }
        return null;
    }

    public static boolean isFile(String path) {
        return path.contains(".");
    }

    public static Response readHtml(String filename) {
        return readStatic("templates/" + filename + ".html");
    }

    public static Response readStatic(String path) {
        String fileName;
        if (!App.debug) {
            try {
                fileName = App.class.getResource("/static" + (path.startsWith("/") ? "" : "/") + path).toURI().toString();
            } catch (URISyntaxException e) {
                return new Response("HTTP/1.1", 404, "Not Found", "Not Found");
            }
        } else {
            fileName = new File("").getAbsolutePath().replace("\\", "/")
                    .concat("/app/src/main/resources/static" + (path.startsWith("/") ? "" : "/") + path);
        }
        try {
            InputStream is = new FileInputStream(Paths.get(fileName).toFile());
            String mimeType = URLConnection.guessContentTypeFromName(fileName);
            boolean isBinary = !(mimeType != null && mimeType.startsWith("text/"));
            byte[] content = is.readAllBytes();
            is.close();
            return new Response("HTTP/1.1", 200, "OK", content, isBinary);
        } catch (Exception e) {
            return new Response("HTTP/1.1", 404, "Not Found", "Not Found");
        }
    }

    public static Response redirect(String path) {
        Response response = new Response("HTTP/1.1", 303, "See Other", "");
        response.addHeader("Location", path);
        return response;
    }
}