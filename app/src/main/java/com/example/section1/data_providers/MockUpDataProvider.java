package com.example.section1.data_providers;

import com.example.section1.dataclasses.Category;
import com.example.section1.dataclasses.Person;

import java.util.ArrayList;
import java.util.List;

public class MockUpDataProvider implements IDataProvider {

    private static MockUpDataProvider mockUpDataProvider = new MockUpDataProvider();

    private List<Person> personList;
    private List<Category> categoryList;

    private MockUpDataProvider() {
        personList = new ArrayList<>();
        categoryList = new ArrayList<>();
    }

    public static MockUpDataProvider newInstance() {
        if (mockUpDataProvider == null) {
            mockUpDataProvider = new MockUpDataProvider();
        }
        return mockUpDataProvider;
    }

    @Override
    public List<Person> getPersonList() {
        if (personList.isEmpty()) {
            fillPersonList();
        }
        return personList;
    }

    private void fillPersonList() {
        personList.add(new Person(1, "Alex", "Smit", "alex.smit@gmail.com", "AlexSmit", "qwerty123"));
        personList.add(new Person(2, "Erik", "Lass", "erik.lass@gmail.com", "ErikLass", "12345qwerty"));
    }

    @Override
    public void addPerson(Person person) {
        if (!personList.isEmpty()) {
            person.setId(personList.get(personList.size() - 1).getId() + 1);
        } else {
            person.setId(1);
        }
        personList.add(person);
    }

    @Override
    public List<Category> getCategoryList() {
        if (categoryList.isEmpty()) {
            fillCategoryList();
        }
        return categoryList;
    }

    private void fillCategoryList() {
        categoryList.add(new Category(1, "Компьютеры"));
        categoryList.add(new Category(2, "Ноутбуки"));
        categoryList.add(new Category(3, "Планшеты"));
        categoryList.add(new Category(4, "Телефоны"));
    }
}
