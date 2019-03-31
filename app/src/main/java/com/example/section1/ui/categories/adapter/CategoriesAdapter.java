package com.example.section1.ui.categories.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.section1.R;
import com.example.section1.data.dataclasses.CategoryModel;

import java.util.List;

public class CategoriesAdapter extends ArrayAdapter<CategoryModel> {

    private LayoutInflater inflater;
    private int layout;
    private List<CategoryModel> categoryModelList;

    public CategoriesAdapter(Context context, int resource, List<CategoryModel> categoryModelList) {
        super(context, resource, categoryModelList);
        this.categoryModelList = categoryModelList;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view = inflater.inflate(this.layout, parent, false);
        TextView tvCategoryName = (TextView) view.findViewById(R.id.tv_category_name);

        CategoryModel categoryModel = categoryModelList.get(position);

        tvCategoryName.setText(categoryModel.getCategoryName());

        return view;
    }
}
