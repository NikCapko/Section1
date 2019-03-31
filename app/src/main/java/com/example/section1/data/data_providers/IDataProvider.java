package com.example.section1.data.data_providers;

import com.example.section1.data.dataclasses.CategoryModel;

import java.util.List;

public interface IDataProvider {
    List<CategoryModel> getCategoryModelList();
}
