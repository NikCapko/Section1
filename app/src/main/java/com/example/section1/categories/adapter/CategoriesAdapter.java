package com.example.section1.categories.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.section1.R;
import com.example.section1.dataclasses.Category;

import java.util.List;

public class CategoriesAdapter extends ArrayAdapter<Category> {

    private LayoutInflater inflater;
    private int layout;
    private List<Category> categoryList;

    public CategoriesAdapter(Context context, int resource, List<Category> categoryList) {
        super(context, resource, categoryList);
        this.categoryList = categoryList;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view = inflater.inflate(this.layout, parent, false);
        TextView tvCategoryName = (TextView) view.findViewById(R.id.tv_category_name);

        Category category = categoryList.get(position);

        tvCategoryName.setText(category.getName());

        return view;
    }
}
