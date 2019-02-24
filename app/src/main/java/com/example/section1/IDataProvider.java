package com.example.section1;

import java.util.List;

public interface IDataProvider {

    List<Person> getPersonList();

    void addPerson(Person person);
}
