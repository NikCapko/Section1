package com.example.section1;

import java.util.ArrayList;
import java.util.List;

public class DemoDataProvider implements IDataProvider {

    private List<Person> personList;

    public DemoDataProvider() {
        personList = new ArrayList<>();
        personList.add(new Person("1", "Alex", "Smit", "alex.smit@gmail.com", "AlexSmit", "qwerty123"));
        personList.add(new Person("1", "Erik", "Lass", "erik.lass@gmail.com", "ErikLass", "12345qwerty"));
    }

    @Override
    public List<Person> getPersonList() {
        return personList;
    }
}
