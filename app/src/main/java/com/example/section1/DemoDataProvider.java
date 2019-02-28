package com.example.section1;

import java.util.ArrayList;
import java.util.List;

public class DemoDataProvider implements IDataProvider {

    private List<Person> personList;

    public DemoDataProvider() {
        personList = new ArrayList<>();
        personList.add(new Person(1, "Alex", "Smit", "alex.smit@gmail.com", "AlexSmit", "qwerty123"));
        personList.add(new Person(2, "Erik", "Lass", "erik.lass@gmail.com", "ErikLass", "12345qwerty"));
    }

    @Override
    public List<Person> getPersonList() {
        return personList;
    }

    @Override
    public void addPerson(Person person) {
        if (personList != null) {
            if (!personList.isEmpty()) {
                person.setId(personList.get(personList.size() - 1).getId() + 1);
            } else {
                person.setId(1);
            }
        } else {
            personList = new ArrayList<>();
            person.setId(1);
        }
        personList.add(person);
    }
}
