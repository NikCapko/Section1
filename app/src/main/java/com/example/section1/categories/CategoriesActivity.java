package com.example.section1.categories;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.section1.R;
import com.example.section1.categories.adapter.CategoriesAdapter;
import com.example.section1.data_providers.MockUpDataProvider;
import com.example.section1.data_providers.RestDataProvider;
import com.example.section1.dataclasses.Category;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoriesActivity extends AppCompatActivity {

    @BindView(R.id.lv_categories_list)
    ListView lvCategoriesList;

    private RestDataProvider dataProvider;
    private CategoriesAdapter categoriesAdapter;

    private List<Category> categoryList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        ButterKnife.bind(this);
        dataProvider = RestDataProvider.newInstance();
        categoryList = dataProvider.getCategoryList();
        setCategoryList(categoryList);
    }

    private void setCategoryList(List<Category> categoryList) {
        categoriesAdapter = new CategoriesAdapter(this, R.layout.row_categories_item, categoryList);
        lvCategoriesList.setAdapter(categoriesAdapter);
        lvCategoriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}
