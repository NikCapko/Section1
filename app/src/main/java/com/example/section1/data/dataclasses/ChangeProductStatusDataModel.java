package com.example.section1.data.dataclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangeProductStatusDataModel extends BaseModel {

    @SerializedName("data")
    @Expose
    private ChangeProductStatusModel changeProductStatusModel;

    public ChangeProductStatusModel getChangeProductStatusModel() {
        return changeProductStatusModel;
    }

    public void setChangeProductStatusModel(ChangeProductStatusModel changeProductStatusModel) {
        this.changeProductStatusModel = changeProductStatusModel;
    }
}
