package com.example.section1.data.dataclasses;

import java.io.Serializable;

public class ResultModel implements Serializable {

    private boolean isSuccess;

    public ResultModel(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
