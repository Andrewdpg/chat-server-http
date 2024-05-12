package org.nosotros.http.config;

import org.nosotros.http.util.Auth;
import org.nosotros.http.util.Request;
import org.nosotros.http.util.Response;
import org.nosotros.http.util.UrlLink;

public class Views {

    public static Response chat(Request request) {
        if (request.isPost()) {
            return UrlLink.redirect("/chat");
        }

        return Auth.loginRequired(request, "chat");
    }

    public static Response login(Request request) {
        if (request.isAuthenticated()) {
            return UrlLink.redirect("/chat");
        }

        if (request.isPost()) {
            return Auth.authenticate(request,"/chat");
        }

        return UrlLink.readHtml("login");
    }
}
