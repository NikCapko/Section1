package com.example.section1.routing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.section1.ui.basket.BasketActivity;
import com.example.section1.ui.categories.CategoriesActivity;
import com.example.section1.ui.product.detail.ProductDetailActivity;
import com.example.section1.ui.product.list.ProductListActivity;
import com.example.section1.ui.sign_up.SignUpActivity;

public class Router {

    public static void openSignUpScreen(Activity activity) {
        Intent intent = new Intent(activity, SignUpActivity.class);
        activity.startActivity(intent);
    }

    public static void openCategoriesScreen(Activity activity) {
        Intent intent = new Intent(activity, CategoriesActivity.class);
        activity.startActivity(intent);
    }

    public static void openProductListScreen(Activity activity, int categoryId) {
        Intent intent = new Intent(activity, ProductListActivity.class);
        intent.putExtra(ProductListActivity.CATEGORY_ID, categoryId);
        activity.startActivity(intent);
    }

    public static void openProductDetailScreen(Activity activity, int productId) {
        Intent intent = new Intent(activity, ProductDetailActivity.class);
        intent.putExtra(ProductDetailActivity.PRODUCT_ID, productId);
        activity.startActivity(intent);
    }

    public static void openBasketScreen(Activity activity) {
        Intent intent = new Intent(activity, BasketActivity.class);
        activity.startActivity(intent);
    }
}
