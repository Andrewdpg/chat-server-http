package org.nosotros;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONObject;
import org.nosotros.chat.model.Person;

@ServerEndpoint("/chat")
public class ChatSocket {

    Person person;

    @OnMessage
    public void onMessage(String message, Session session) {
        JSONObject json = new JSONObject(message);

        // Si el mensaje es una actualización de sesión
        if (json.getString("type").equals("sessionUpdate")) {
            
            String username = json.getString("username");

            
            System.out.println("Username: " + username);
            // Actualiza la sesión del usuario
            // Aquí debes implementar la lógica para actualizar la sesión del usuario en tu
            // lista de objetos Persona
        }
    }
}

/*
 * 
 * 
    private static final String PREFIJO_PARTICIPANTE = "Invitado # ";
    
    private static final AtomicInteger idConexionI = new AtomicInteger(0);
    
    private static final Set<ConexionClienteChat> conexiones = new CopyOnWriteArraySet<>();
    
    private final String nickname;
    private Session sessionWebsocket;
    
    public ConexionClienteChat(){
        nickname = PREFIJO_PARTICIPANTE + idConexionI.getAndIncrement();
    }
    
    @OnOpen
    public void iniciarConexion(Session session){
        this.sessionWebsocket = session;
        conexiones.add(this);
        String message = String.format("* El %s %s", nickname , "se ha unido al chat");
        publicarGlobalmente(message);
    }
    
    @OnClose
    public void terminarConexion(){
        conexiones.remove(this);
        String message = String.format("* %s %s", nickname, "se ha desconectado");
        publicarGlobalmente(message);
    }
    
    
    @OnMessage
    public void atenderMensaje(String message){
        String mensajeConId = String.format("* %s: %s", nickname, message);
        publicarGlobalmente(mensajeConId);
    }
    
    
    @OnError
    public void onError(Throwable t) throws Throwable{
        System.out.println("Chat Error : " + t.toString());
    }
    
    public static void publicarGlobalmente(String msg) {
        for (ConexionClienteChat conexionI : conexiones) {
            try {
                synchronized (conexionI) {
                    if (conexionI.sessionWebsocket.isOpen()) {
                        conexionI.sessionWebsocket.getBasicRemote().sendText(msg);
                    }
                }
            } catch (IOException e) {
            }
        }
    }
    
 * 
 */