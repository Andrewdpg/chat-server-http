package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlLink {

    public static final String ABSOLUTE_PATH = new File("").getAbsolutePath().replace("\\", "/").concat("/src");

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

    public static String readHtml(String filename) {
        return readStatic("templates/" + filename + ".html");
    }

    public static String readStatic(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(ABSOLUTE_PATH + "/static" + (path.startsWith("/") ? "" : "/") + path))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line);
            }
            return content.toString();
        } catch (Exception e) {
            System.out.println("File not found: " + ABSOLUTE_PATH + "/templates/" + path + ".html");
            return "Not Found";
        }
    }
}