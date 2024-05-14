package org.nosotros.chat.model.message;

import org.glassfish.tyrus.spi.WriterInfo.MessageType;
import org.json.JSONObject;
import org.nosotros.chat.PeopleController;
import org.nosotros.chat.model.people.Entity;

public class Message {

    private String message;
    private String sender;
    private Entity receiver;
    private MessageType type;
    private ChatType chatType;

    private Message(String message, String sender, String receiver, MessageType type, ChatType chatType) {
        this.message = message;
        this.sender = sender;
        this.receiver = PeopleController.getEntity(receiver, chatType);
        this.type = type;
        this.chatType = chatType;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public Entity getReceiver() {
        return receiver;
    }

    public void setReceiver(Entity receiver) {
        this.receiver = receiver;
    }

    public ChatType getChatType() {
        return chatType;
    }

    public void setChatType(ChatType chatType) {
        this.chatType = chatType;
    }

    public String toString() {
        JSONObject json = new JSONObject();
        json.put("type", "message");
        json.put("message", this.message);
        json.put("sender", this.sender);
        json.put("receiver", this.receiver.getId());
        json.put("messageType", this.type);
        json.put("chatType", this.chatType);
        return json.toString();
    }

    public static Message fromJson(JSONObject json) {
        return new Message(
                json.getString("message"),
                json.getString("sender"),
                json.getString("receiver"),
                MessageType.valueOf(json.getString("messageType")),
                ChatType.valueOf(json.getString("chatType")));
    }
}