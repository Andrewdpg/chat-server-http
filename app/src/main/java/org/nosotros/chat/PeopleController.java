package org.nosotros.chat;

import java.util.HashMap;

import org.nosotros.chat.model.Person;

public class PeopleController {

    // people mapped as username : person object
    private static HashMap<String, Person> people = new HashMap<>();

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

    

}
