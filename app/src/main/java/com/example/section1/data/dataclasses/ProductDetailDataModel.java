package com.example.section1.data.dataclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductDetailDataModel extends BaseModel {

    @SerializedName("data")
    @Expose
    private ProductModel productModel;

    public ProductModel getProductModel() {
        return productModel;
    }

    public void setProductModel(ProductModel productModel) {
        this.productModel = productModel;
    }
}
