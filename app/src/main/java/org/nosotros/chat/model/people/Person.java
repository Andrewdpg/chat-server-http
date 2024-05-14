package org.nosotros.chat.model.people;

import java.util.HashMap;

import javax.websocket.Session;

import org.nosotros.chat.model.message.Message;

public class Person extends Entity {

    private String password;
    private HashMap<String, Session> sessions;

    public Person(String username, String password) {
        super(username);
        this.password = password;
        sessions = new HashMap<>();
    }

    public String getUsername() {
        return getId();
    }

    public void setUsername(String username) {
        setId(username);
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

    public void removeSession(Session session) {
        if (session == null) {
            for (String key : sessions.keySet()) {
                if (sessions.get(key) == null) {
                    sessions.remove(key);
                }
            }
            return;
        }

        sessions.values().remove(session);
    }

    public Session getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    public boolean hasSession(String sessionId) {
        return sessions.containsKey(sessionId);
    }

    public boolean hasSession() {
        return !sessions.isEmpty();
    }

    public void sendMessage(Message message) {
        for (Session session : sessions.values()) {
            try {
                if (session == null || !session.isOpen()) {
                    continue;
                }
                System.out.println("Sending message to " + getUsername() + " : " + message.toString());
                session.getBasicRemote().sendText(message.toString());
                System.out.println("Message sent to " + getUsername() + " : " + message.toString());
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }
}