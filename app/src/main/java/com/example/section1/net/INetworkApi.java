package com.example.section1.net;

import com.example.section1.data.dataclasses.CategoryModel;
import com.example.section1.data.dataclasses.ResultModel;
import com.example.section1.data.dataclasses.UserLoginModel;
import com.example.section1.data.dataclasses.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface INetworkApi {

    @POST("/auth/login")
    Call<ResultModel> authLogin(@Body UserLoginModel data);

    @POST("/auth/registration")
    Call<ResultModel> authRegistration(@Body UserModel data);

    @POST("/market/categories")
    Call<List<CategoryModel>> getCategories();
}
