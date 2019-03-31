package com.example.section1.ui.categories;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.section1.R;
import com.example.section1.data.dataclasses.CategoryModel;
import com.example.section1.ui.categories.adapter.CategoriesAdapter;
import com.example.section1.data.data_providers.RestDataProvider;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoriesActivity extends AppCompatActivity {

    @BindView(R.id.lv_categories_list)
    ListView lvCategoriesList;

    private RestDataProvider dataProvider;
    private CategoriesAdapter categoriesAdapter;

    private List<CategoryModel> categoryModelList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        ButterKnife.bind(this);
        dataProvider = RestDataProvider.newInstance();
        categoryModelList = dataProvider.getCategoryModelList();
        setCategoryModelList(categoryModelList);
    }

    private void setCategoryModelList(List<CategoryModel> categoryModelList) {
        categoriesAdapter = new CategoriesAdapter(this, R.layout.row_categories_item, categoryModelList);
        lvCategoriesList.setAdapter(categoriesAdapter);
        lvCategoriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}
