import util.Request;
import util.Response;
import util.UrlLink;

public class Views {
    
    public static Response inicio(Request request){
        if(request.isPost()){
            request.getHeaders().get("Authorization");
        }
        return new Response("HTTP/1.1", 200, "OK", UrlLink.readHtml("index"));
    }
}
