package com.example.section1.ui.basket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.section1.R;
import com.example.section1.data.dataclasses.ProductModel;

import java.util.List;

public class BasketAdapter extends ArrayAdapter<ProductModel> {

    private Context context;
    private int layout;
    private List<ProductModel> productModelList;
    private OnClickListener onClickListener;

    public BasketAdapter(Context context, int layout, List<ProductModel> productModelList) {
        super(context, layout, productModelList);
        this.context = context;
        this.layout = layout;
        this.productModelList = productModelList;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        TextView tvCategoryName = view.findViewById(R.id.tv_product_name);
        ImageView ivProductStatus = view.findViewById(R.id.iv_product_status);
        ProductModel productModel = productModelList.get(position);
        if (productModel != null) {
            tvCategoryName.setText(productModel.getName());
            if (productModel.getStatus() == ProductModel.UNSELECT_STATUS) {
                ivProductStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_add));
            } else if (productModel.getStatus() == ProductModel.SELECT_STATUS) {
                ivProductStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_delete));
            }
            ivProductStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.deleteProduct(productModel.getId());
                    }
                }
            });
            view.setOnClickListener(v -> {
                if (onClickListener != null) {
                    onClickListener.onItemClick(productModel.getId());
                }
            });
        }
        return view;
    }

    public interface OnClickListener {

        void onItemClick(int id);

        void deleteProduct(int id);
    }
}
