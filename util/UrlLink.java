package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlLink {

    View method;
    String pattern;
    Matcher matcher;

    public UrlLink(View method, String pattern) {
        this.method = method;
        this.pattern = pattern;
    }

    public Response execute(Request request) {
        return method.handle(request);
    }

    public boolean match(Request request) {
        Pattern pattern = Pattern.compile(this.pattern);
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

    public static String readHtml(String path) {
        try {

            StringBuilder htmlContent = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new FileReader("templates/" + path + ".html"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    htmlContent.append(line);
                }
            } catch (FileNotFoundException e) {
                return "Not Found";
            }
            return htmlContent.toString();
        } catch (Exception e) {
            return "Not Found";
        }
    }

    public static String readStatic(String path) {
        try {
            StringBuilder content = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new FileReader("static" + (path.startsWith("/")?"":"/") + path))) {
                String line;
                while ((line = br.readLine()) != null) {
                    content.append(line);
                }
            } catch (FileNotFoundException e) {
                return "Not Found";
            }
            return content.toString();
        } catch (Exception e) {
            return "Not Found";
        }
    }
}