
import java.io.IOException;
import java.util.ArrayList;

import util.Request;
import util.Response;
import util.UrlLink;

public class Urls {

    public static final ArrayList<UrlLink> urls = new ArrayList<UrlLink>(){{
        add(new UrlLink(Views::inicio, "/home"));
    }};

    public static Response handle(Request request) throws IOException{
        if(UrlLink.isFile(request.getPath())){
            return new Response("HTTP/1.1", 200, "OK", UrlLink.readStatic(request.getPath()));
        }
        UrlLink url = UrlLink.getUrl(request, urls);
        if(url != null) return url.execute(request);
        else return new Response("HTTP/1.1", 404, "Not Found", "Not Found");
    }

}

