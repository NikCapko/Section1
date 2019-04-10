package com.example.section1.ui.product.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.section1.R;

import butterknife.ButterKnife;

public class ProductListActivity extends AppCompatActivity {

    public static final String CATEGORY_ID = "ProductListActivity.CATEGORY_ID";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        ButterKnife.bind(this);
        setTitle(R.string.product_list_screen_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
