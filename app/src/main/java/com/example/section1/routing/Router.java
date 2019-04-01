package com.example.section1.routing;

import android.content.Context;
import android.content.Intent;

import com.example.section1.ui.basket.BasketActivity;
import com.example.section1.ui.categories.CategoriesActivity;
import com.example.section1.ui.product.detail.ProductDetailActivity;
import com.example.section1.ui.product.list.ProductListActivity;
import com.example.section1.ui.sign_up.SignUpActivity;

public class Router {

    public static void openSignUpScreen(Context context) {
        Intent intent = new Intent(context, SignUpActivity.class);
        context.startActivity(intent);
    }

    public static void openCategoriesScreen(Context context) {
        Intent intent = new Intent(context, CategoriesActivity.class);
        context.startActivity(intent);
    }

    public static void openProducListScreen(Context context) {
        Intent intent = new Intent(context, ProductListActivity.class);
        context.startActivity(intent);
    }

    public static void openProducDetailScreen(Context context) {
        Intent intent = new Intent(context, ProductDetailActivity.class);
        context.startActivity(intent);
    }

    public static void openBasketScreen(Context context) {
        Intent intent = new Intent(context, BasketActivity.class);
        context.startActivity(intent);
    }
}
