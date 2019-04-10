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

    private Context context;
    private int layout;
    private List<CategoryModel> categoryModelList;
    private OnClickListener onClickListener;

    public CategoriesAdapter(Context context, int layout, List<CategoryModel> categoryModelList) {
        super(context, layout, categoryModelList);
        this.context = context;
        this.layout = layout;
        this.categoryModelList = categoryModelList;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        TextView tvCategoryName = view.findViewById(R.id.tv_category_name);
        CategoryModel categoryModel = categoryModelList.get(position);
        if (categoryModel != null) {
            tvCategoryName.setText(categoryModel.getName());
            view.setOnClickListener(v -> {
                if (onClickListener != null) {
                    onClickListener.onItemClick(categoryModel.getId());
                }
            });
        }
        return view;
    }

    public interface OnClickListener {
        void onItemClick(int id);
    }
}
