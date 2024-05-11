package org.nosotros.http.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

    public static Response handle(Request request) throws IOException{
        System.out.print("\nRequest: " + request.getPath()+ " - ");
        if(UrlLink.isFile(request.getPath())){
            return readStatic(request.getPath());
        }
        UrlLink url = UrlLink.getUrl(request, Urls.urls);
        if(url != null) return url.execute(request);
        else return new Response("HTTP/1.1", 404, "Not Found", "Not Found");
    }

    public Response execute(Request request) {
        return method.handle(request);
    }

    public boolean match(Request request) {
        Pattern pattern = Pattern.compile(this.pattern+"\\??");
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
        InputStreamReader is;
        if (!App.debug) {
            is = new InputStreamReader(App.class.getResourceAsStream("/static" + (path.startsWith("/") ? "" : "/") + path));
        }else{
            try {
                String absolutePath = new File("").getAbsolutePath().replace("\\", "/").concat("/app/src/main/resources/static" + (path.startsWith("/") ? "" : "/") + path);
                System.out.println("Absolute Path: " + absolutePath);
                is = new FileReader(absolutePath);
            } catch (IOException e) {
                System.out.println(e);
                return new Response("HTTP/1.1", 404, "Not Found", "Not Found");
            }
        }
        try (BufferedReader br = new BufferedReader(is)) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line);
            }
            return new Response("HTTP/1.1", 200, "OK", content.toString());
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