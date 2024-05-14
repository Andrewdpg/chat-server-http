package org.nosotros.chat;

import java.util.HashMap;

import javax.websocket.Session;

import org.nosotros.chat.model.message.ChatType;
import org.nosotros.chat.model.people.Entity;
import org.nosotros.chat.model.people.Group;
import org.nosotros.chat.model.people.Person;

public class PeopleController {

    // people mapped as username : person object
    private static HashMap<String, Person> people = new HashMap<>();
    private static HashMap<String, Group> Groups = new HashMap<>();

    public static Person getPersonNamed(String username) {
        return people.get(username);
    }

    public static Person getBySessionId(String sessionId) {
        for (Person person : people.values()) {
            if (person.hasSession(sessionId)) {
                return person;
            }
        }
        return null;
    }

    public static boolean personExists(String username) {
        return people.containsKey(username);
    }

    public static void addPerson(Person person) {
        people.put(person.getUsername(), person);
    }

    public static void removePerson(Person person) {
        people.remove(person.getUsername());
    }

    public static void removePerson(String username) {
        people.remove(username);
    }

    public static void removePersonBySessionId(String sessionId) {
        for (Person person : people.values()) {
            if (person.hasSession(sessionId)) {
                people.remove(person.getUsername());
                return;
            }
        }
    }

    public static void removeSession(Person person, Session session){
        person.removeSession(session);
    }

    public static void updateSessionId(String username, String sessionId, Session session){
        Person person = people.get(username);
        person.addSession(sessionId, session);
        System.out.println(people.get(username).getSessions().get(sessionId));
    }

    public static Entity getEntity(String id, ChatType type){
        switch (type){
            case PRIVATE:
                return people.get(id);
            case GROUP:
                return Groups.get(id);
            default:
                return null;
        }
    }

}
