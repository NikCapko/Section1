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
        if (categoryModelList.isEmpty()) {
            fillCategoryList();
        }
        return categoryModelList;
    }

    private void fillCategoryList() {
        NetworkService.getInstance()
                .getNetworkApi()
                .getCategories()
                .enqueue(new Callback<List<CategoryModel>>() {
                    @Override
                    public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {
                        categoryModelList = response.body();
                    }

                    @Override
                    public void onFailure(Call<List<CategoryModel>> call, Throwable t) {
                    }
                });
    }
}
