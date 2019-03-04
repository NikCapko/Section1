package com.example.section1;

import java.util.ArrayList;
import java.util.List;

public class DemoDataProvider implements IDataProvider {

    private static DemoDataProvider dataProvider = new DemoDataProvider();

    private List<Person> personList;

    private DemoDataProvider() {
        personList = new ArrayList<>();
        personList.add(new Person(1, "Alex", "Smit", "alex.smit@gmail.com", "AlexSmit", "qwerty123"));
        personList.add(new Person(2, "Erik", "Lass", "erik.lass@gmail.com", "ErikLass", "12345qwerty"));
    }

    public static DemoDataProvider newInstance() {
        if (dataProvider == null) {
            dataProvider = new DemoDataProvider();
        }
        return dataProvider;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
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
