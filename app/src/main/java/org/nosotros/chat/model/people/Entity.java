package org.nosotros.chat.model.people;

import org.nosotros.chat.model.message.Message;

public abstract class Entity {
    private String id;

    protected Entity(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    protected void setId(String id) {
        this.id = id;
    }

    public abstract void sendMessage(Message message);
}
