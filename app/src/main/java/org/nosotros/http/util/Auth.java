package org.nosotros.http.util;

import java.util.UUID;

import org.nosotros.chat.PeopleController;
import org.nosotros.chat.model.people.Person;

public class Auth {

    private static String generateSessionId() {
        String sessionId = UUID.randomUUID().toString();

        while (PeopleController.getBySessionId(sessionId) != null) {
            sessionId = UUID.randomUUID().toString();
        }

        return sessionId;
    }
    
    /**
     * Checks if the user is authenticated. If authenticated, it returns the HTML
     * content
     * of the specified URL. If not authenticated, it redirects the user to the
     * login page.
     *
     * @param request The HTTP request object.
     * @param next    The URL to redirect to after successful authentication.
     * @return The response object containing the HTML content or the redirect URL.
     */
    public static Response loginRequired(Request request, String next) {
        if (request.isAuthenticated()) {
            return UrlLink.readHtml(next);
        }
        return UrlLink.redirect("/login");
    }

    /**
     * Authenticates the request and returns a response.
     * If the request is already authenticated, it redirects to the specified next URL.
     * If the request is not authenticated, it checks the username and password from the request form.
     * If the username or password is missing, it redirects to the login page.
     * If the username does not exist, it adds a new person with the provided username and password.
     * If the password matches the person's password, it generates a session ID, adds the session to the person, and returns a response with the session ID and username as cookies.
     * If the password does not match, it redirects to the login page.
     *
     * @param request the request object containing the authentication information
     * @param next the URL to redirect to if the request is already authenticated
     * @return the response object
     */
    public static Response authenticate(Request request, String next) {

        if(request.isAuthenticated()) {
            return UrlLink.redirect(next);
        }

        String username = request.getFormValue("username");
        String password = request.getFormValue("password");

        if(username == null || password == null) {
            return UrlLink.redirect("/login");
        }

        if (!PeopleController.personExists(username)) {
            PeopleController.addPerson(new Person(username, password));
        }
        
        Person person = PeopleController.getPersonNamed(username);
        if (person.getPassword().equals(password)) {
            String sessionId = generateSessionId();
            person.addSession(sessionId, null);
            Response response = UrlLink.redirect(next);
            response.addHeader("Set-Cookie", "cSessionId=" + sessionId + "; Path=/");
            response.addHeader("Set-Cookie", "cUsername=" + username + "; Path=/");
            return response;
        }
        return UrlLink.redirect("/login");
    }
}
