package com.example.section1.requests;

import android.os.AsyncTask;

import com.example.section1.dataclasses.Category;
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

public class CategoryListTask extends AsyncTask<String, Void, List<Category>> {

    @Override
    protected List<Category> doInBackground(String... strings) {

        List<Category> categoryList = new ArrayList<>();
        try {
            categoryList = getCategoryList();
        } catch (IOException ex) {
        }

        return categoryList;
    }

    private List<Category> getCategoryList() throws IOException {
        BufferedReader reader = null;
        try {
            URL url = new URL("http://192.168.56.1:8080/categories");
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.connect();
            reader = new BufferedReader(new InputStreamReader(c.getInputStream()));
            String resp = reader.readLine();
            Gson gson = new Gson();
            JsonObject resultObj = gson.fromJson(resp, JsonObject.class);
            JsonArray jsonList = resultObj.get("data").getAsJsonArray();
            Type listType = new TypeToken<List<Category>>() {
            }.getType();
            return gson.fromJson(jsonList, listType);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
}
