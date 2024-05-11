package org.nosotros;

import java.io.IOException;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/chat")
public class WebServer {

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Received: " + message);
        // Envía el mensaje a todos los clientes conectados
        for (Session s : session.getOpenSessions()) {
            try {
                s.getBasicRemote().sendText(message);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}