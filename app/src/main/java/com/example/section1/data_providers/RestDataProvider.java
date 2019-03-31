package com.example.section1.data_providers;

import com.example.section1.dataclasses.Category;
import com.example.section1.dataclasses.Person;
import com.example.section1.requests.CategoryListTask;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class RestDataProvider implements IDataProvider {

    private static RestDataProvider restDataProvider = new RestDataProvider();

    private List<Person> personList;
    private List<Category> categoryList;

    private RestDataProvider() {
        personList = new ArrayList<>();
        categoryList = new ArrayList<>();
    }

    public static RestDataProvider newInstance() {
        if (restDataProvider == null) {
            restDataProvider = new RestDataProvider();
        }
        return restDataProvider;
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
        try {
            categoryList = new CategoryListTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
