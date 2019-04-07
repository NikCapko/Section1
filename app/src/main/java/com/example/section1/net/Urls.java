package com.example.section1.net;

public class Urls {

    public static final String BASE_URL = "http://192.168.57.1:3000";

    public static final String LOGIN = "/auth/login";
    public static final String REGISTRATION = "/auth/registration";
    public static final String GET_CATEGORIES = "/market/categories";
    public static final String GET_PRODUCTS = "/market/categories/{category_id}/products";
    public static final String GET_PRODUCT_DETAIL = "/market/categories/{category_id}/products/{product_id}";
    public static final String BASKET_ADD_PRODUCT = "/market/basket/add";
    public static final String BASKET_REMOVE_PRODUCT = "/market/basket/remove";
    public static final String BASKET_CHECKOUT = "/market/basket/checkout";
}
