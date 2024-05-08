package config;

import util.Request;
import util.Response;
import util.UrlLink;

public class Views {
    
    public static Response inicio(Request request){
        if(request.isPost()){
            return UrlLink.redirect("/nose");
        }
        
        return new Response("HTTP/1.1", 200, "OK", UrlLink.readHtml("index"));
    }

    public static Response nose(Request request){
        return new Response("HTTP/1.1", 200, "OK", UrlLink.readHtml("hola"));
    }
}
