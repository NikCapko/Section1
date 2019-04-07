package com.example.section1.net;

import com.example.section1.data.dataclasses.BaseModel;
import com.example.section1.data.dataclasses.BasketDataModel;
import com.example.section1.data.dataclasses.CategoryDataModel;
import com.example.section1.data.dataclasses.ProductDataModel;
import com.example.section1.data.dataclasses.ProductDetailDataModel;
import com.example.section1.data.dataclasses.UserLoginModel;
import com.example.section1.data.dataclasses.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface INetworkApi {

    @POST(Urls.LOGIN)
    Call<BaseModel> authLogin(@Body UserLoginModel data);

    @POST(Urls.REGISTRATION)
    Call<BaseModel> authRegistration(@Body UserModel data);

    @GET(Urls.GET_CATEGORIES)
    Call<CategoryDataModel> getCategories();

    @GET(Urls.GET_PRODUCTS)
    Call<ProductDataModel> getProducts(@Path(RequestFields.CATEGORY_ID) String categoryId);

    @GET(Urls.GET_PRODUCT_DETAIL)
    Call<ProductDetailDataModel> getProductDetail(@Path(RequestFields.CATEGORY_ID) String categoryId, @Path(RequestFields.PRODUCT_ID) String productId);

    @GET(Urls.BASKET_ADD_PRODUCT)
    Call<BaseModel> addProduct(@Query(RequestFields.BASKET_PRODUCT_ID) String productId);

    @GET(Urls.BASKET_REMOVE_PRODUCT)
    Call<BaseModel> removeProduct(@Query(RequestFields.BASKET_PRODUCT_ID) String productId);

    @GET(Urls.BASKET_CHECKOUT)
    Call<BasketDataModel> basketCheckout();

}
