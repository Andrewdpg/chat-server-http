package org.nosotros.chat;

import java.util.HashMap;

import org.nosotros.chat.model.Person;

public class PeopleController {

    // people mapped as name : person object
    HashMap<String, Person> people = new HashMap<>();

    public Person getPersonNamed(String name) {
        return people.get(name);
    }

    public Person getPerson(String sessionId) {
        for (Person person : people.values()) {
            if (person.getSessionId().equals(sessionId)) {
                return person;
            }
        }
        return null;
    }

    public boolean personExists(String name) {
        return people.containsKey(name);
    }

    public void addPerson(Person person) {
        people.put(person.getUsername(), person);
    }

    public void removePerson(Person person) {
        people.remove(person.getUsername());
    }

    public void removePerson(String name) {
        people.remove(name);
    }

    public void removePersonBySessionId(String sessionId) {
        for (Person person : people.values()) {
            if (person.getSessionId().equals(sessionId)) {
                people.remove(person.getUsername());
                return;
            }
        }
    }

}
