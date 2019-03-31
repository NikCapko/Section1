package com.example.section1.data.dataclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CategoryModel implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("categoryName")
    @Expose
    private String categoryName;

    public CategoryModel(String categoryName) {
        this.categoryName = categoryName;
    }

    public CategoryModel(int id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
