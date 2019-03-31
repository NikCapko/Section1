package com.example.section1.data_providers;

import com.example.section1.dataclasses.Category;
import com.example.section1.dataclasses.Person;

import java.util.List;

public interface IDataProvider {

    List<Person> getPersonList();

    void addPerson(Person person);

    List<Category> getCategoryList();
}
