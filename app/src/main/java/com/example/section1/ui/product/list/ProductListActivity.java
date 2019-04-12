package com.example.section1.ui.product.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.section1.R;
import com.example.section1.data.dataclasses.ProductDataModel;
import com.example.section1.data.dataclasses.ProductModel;
import com.example.section1.data.dataclasses.StatusModel;
import com.example.section1.net.ErrorData;
import com.example.section1.net.NetworkService;
import com.example.section1.routing.Router;
import com.example.section1.ui.product.list.adapter.ProductListAdapter;
import com.example.section1.utils.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListActivity extends AppCompatActivity {

    public static final String CATEGORY_ID = "ProductListActivity.CATEGORY_ID";

    @BindView(R.id.lv_product_list)
    ListView lvProductList;
    @BindView(R.id.tv_error_title)
    TextView tvErrorTitle;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.btn_repeat)
    Button btnRepeat;
    @BindView(R.id.ll_error_container)
    LinearLayout llErrorContainer;
    @BindView(R.id.ll_empty_container)
    LinearLayout llEmptyContainer;
    @BindView(R.id.pb_load)
    ProgressBar pbLoad;

    private ProductListAdapter productListAdapter;
    private List<ProductModel> productModelList;
    private int categoryId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        ButterKnife.bind(this);
        setTitle(R.string.product_list_screen_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (getIntent().getExtras() != null) {
            categoryId = getIntent().getExtras().getInt(CATEGORY_ID);
            makeProductsRequest();
        } else {
            showErrorProgress(getApplicationContext().getString(R.string.message_universal_error));
            btnRepeat.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.basket_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.basket:
                Router.openBasketScreen(ProductListActivity.this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void makeProductsRequest() {
        showProgress();
        NetworkService.getInstance()
                .getNetworkApi()
                .getProducts(String.valueOf(categoryId))
                .enqueue(new Callback<ProductDataModel>() {
                    @Override
                    public void onResponse(Call<ProductDataModel> call, Response<ProductDataModel> response) {
                        ProductDataModel productDataModel = response.body();
                        if (productDataModel != null && productDataModel.getStatusModel() != null) {
                            StatusModel statusModel = productDataModel.getStatusModel();
                            if (statusModel.getCode().equals(Constants.RESPONSE_200)) {
                                productModelList = productDataModel.getProductModelList();
                                if (productModelList.isEmpty()) {
                                    showEmptyList();
                                } else {
                                    setProductModelList(productModelList);
                                }
                                completeProgress();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ProductDataModel> call, Throwable t) {
                        ErrorData errorData = new ErrorData(getApplicationContext(), t);
                        showErrorProgress(errorData.getErrorMessage());
                    }
                });
    }

    private void showEmptyList() {
        lvProductList.setVisibility(View.GONE);
        llEmptyContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void setProductModelList(List<ProductModel> categoryModelList) {
        productListAdapter = new ProductListAdapter(getApplicationContext(), R.layout.row_product_item, categoryModelList);
        lvProductList.setAdapter(productListAdapter);
        productListAdapter.setOnClickListener(productId -> Router.openProductDetailScreen(ProductListActivity.this, categoryId, productId));
    }

    private void showProgress() {
        lvProductList.setVisibility(View.GONE);
        pbLoad.setVisibility(View.VISIBLE);
        llErrorContainer.setVisibility(View.GONE);
    }

    private void completeProgress() {
        lvProductList.setVisibility(View.VISIBLE);
        pbLoad.setVisibility(View.GONE);
        llErrorContainer.setVisibility(View.GONE);
    }

    private void showErrorProgress(String errorMessage) {
        lvProductList.setVisibility(View.GONE);
        pbLoad.setVisibility(View.GONE);
        llErrorContainer.setVisibility(View.VISIBLE);
        tvDescription.setText(errorMessage);
    }

    @OnClick(R.id.btn_repeat)
    public void onViewClicked() {
        makeProductsRequest();
    }
}
