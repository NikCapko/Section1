package com.example.section1.net;

import com.example.section1.data.dataclasses.BaseModel;
import com.example.section1.data.dataclasses.CategoryDataModel;
import com.example.section1.data.dataclasses.UserLoginModel;
import com.example.section1.data.dataclasses.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface INetworkApi {

    @POST(Urls.LOGIN)
    Call<BaseModel> authLogin(@Body UserLoginModel data);

    @POST(Urls.REGISTRATION)
    Call<BaseModel> authRegistration(@Body UserModel data);

    @GET(Urls.GET_CATEGORIES)
    Call<CategoryDataModel> getCategories();
}
