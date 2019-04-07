package com.example.section1.ui.product.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.section1.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductDetailActivity extends AppCompatActivity {

    public static final String PRODUCT_ID = "ProductDetailActivity.PRODUCT_ID";

    @BindView(R.id.rl_container)
    RelativeLayout rlContainer;
    @BindView(R.id.iv_product_status)
    ImageView ivProductStatus;
    @BindView(R.id.tv_product_name_title)
    TextView tvProductNameTitle;
    @BindView(R.id.tv_product_name)
    TextView tvProductName;
    @BindView(R.id.tv_product_price_title)
    TextView tvProductPriceTitle;
    @BindView(R.id.tv_product_price)
    TextView tvProductPrice;
    @BindView(R.id.tv_product_count_title)
    TextView tvProductCountTitle;
    @BindView(R.id.tv_product_count)
    TextView tvProductCount;
    @BindView(R.id.tv_product_more_detail)
    TextView tvProductMoreDetail;
    @BindView(R.id.pb_load)
    ProgressBar pbLoad;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_product_status, R.id.tv_product_more_detail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_product_status:
                break;
            case R.id.tv_product_more_detail:
                break;
        }
    }
}
