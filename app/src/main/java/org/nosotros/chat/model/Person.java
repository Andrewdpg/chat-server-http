package org.nosotros.chat.model;

import java.util.HashMap;

import org.glassfish.grizzly.http.server.Session;


public class Person {

    private String username;
    private String password;
    private HashMap<String, Session> sessions;
    
    public Person(String username, String password) {
        this.username = username;
        this.password = password;
        sessions = new HashMap<>();
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public HashMap<String, Session> getSessions() {
        return sessions;
    }

    public void setSessions(HashMap<String, Session> sessions) {
        this.sessions = sessions;
    }

    public void addSession(String sessionId, Session session) {
        sessions.put(sessionId, session);
    }

    public void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }

    public Session getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    public String getSessionId() {
        return sessions.keySet().iterator().next();
    }

    public boolean hasSession(String sessionId) {
        return sessions.containsKey(sessionId);
    }

    public boolean hasSession() {
        return !sessions.isEmpty();
    }
}