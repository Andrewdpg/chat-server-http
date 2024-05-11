package org.nosotros.http.config;

import org.nosotros.http.util.Request;
import org.nosotros.http.util.Response;
import org.nosotros.http.util.UrlLink;

public class Views {
    
    public static Response inicio(Request request){
        if(request.isPost()){
            return UrlLink.redirect("/nose");
        }
        
        return UrlLink.readHtml("index");
    }

    public static Response chat(Request request){
        if(request.isPost()){
            return UrlLink.redirect("/chat");
        }
        
        return UrlLink.readHtml("chat");
    }
}
