package com.example.section1.routing;

import android.app.Activity;
import android.content.Intent;

import com.example.section1.ui.categories.CategoriesActivity;
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
}
