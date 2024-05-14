package org.nosotros.chat.model.people;

import java.util.HashSet;
import java.util.Set;

import org.nosotros.chat.model.message.Message;

public class Group extends Entity {

    Set<Person> members = new HashSet<>();

    public Group(String chatID) {
        super(chatID);
    }

    public boolean personExists(String name) {
        for (Person person : members) {
            if (person.getUsername().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void addMember(Person person) {
        if (personExists(person.getUsername())) {
            throw new IllegalArgumentException("Member already in group");
        }
        members.add(person);
    }

    public void removeMember(Person person) {
        members.remove(person);
    }

    public Set<Person> getMembers() {
        return members;
    }

    public String getChatID() {
        return getId();
    }

    public void setChatID(String chatID) {
        setId(chatID);
    }

    public void sendMessage(Message message) {
        for (Person member : members) {
            if (!member.getUsername().equals(message.getSender())) {
                member.sendMessage(message);
            }
        }
    }

    @Override
    public String toString() {
        return getId();
    }

    /*
     * public void sendAudio(byte[] audioData, String sender) {
     * for (Person member : members) {
     * if (!member.getName().equals(sender)) {
     * member.sendAudio(audioData);
     * }
     * }
     * }
     */
}
