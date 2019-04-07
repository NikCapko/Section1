package com.example.section1.data.dataclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryDataModel extends BaseModel {

    @SerializedName("data")
    @Expose
    private List<CategoryModel> categoryModelList;

    public List<CategoryModel> getCategoryModelList() {
        return categoryModelList;
    }

    public void setCategoryModelList(List<CategoryModel> categoryModelList) {
        this.categoryModelList = categoryModelList;
    }
}
