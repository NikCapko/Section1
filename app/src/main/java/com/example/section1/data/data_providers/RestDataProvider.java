package com.example.section1.data.data_providers;

import com.example.section1.data.dataclasses.CategoryModel;
import com.example.section1.net.NetworkService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestDataProvider implements IDataProvider {

    private static RestDataProvider restDataProvider = new RestDataProvider();

    private List<CategoryModel> categoryModelList;

    private RestDataProvider() {
        categoryModelList = new ArrayList<>();
    }

    public static RestDataProvider newInstance() {
        if (restDataProvider == null) {
            restDataProvider = new RestDataProvider();
        }
        return restDataProvider;
    }

    @Override
    public List<CategoryModel> getCategoryModelList() {
        return categoryModelList;
    }

    public void setCategoryModelList(List<CategoryModel> categoryModelList) {
        this.categoryModelList = categoryModelList;
    }
}
