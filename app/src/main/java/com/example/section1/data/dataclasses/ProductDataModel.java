package com.example.section1.data.dataclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductDataModel extends BaseModel {

    @SerializedName("data")
    @Expose
    private List<ProductModel> productModelList;

    public List<ProductModel> getProductModelList() {
        return productModelList;
    }

    public void setProductModelList(List<ProductModel> productModelList) {
        this.productModelList = productModelList;
    }
}
