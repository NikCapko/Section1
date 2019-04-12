package com.example.section1.ui.product.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.section1.R;
import com.example.section1.data.dataclasses.ChangeProductStatusDataModel;
import com.example.section1.data.dataclasses.ChangeProductStatusModel;
import com.example.section1.data.dataclasses.ProductDetailDataModel;
import com.example.section1.data.dataclasses.ProductModel;
import com.example.section1.data.dataclasses.StatusModel;
import com.example.section1.dialogs.ErrorDialog;
import com.example.section1.dialogs.ProgressDialog;
import com.example.section1.net.ErrorData;
import com.example.section1.net.NetworkService;
import com.example.section1.routing.Router;
import com.example.section1.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {

    public static final String CATEGORY_ID = "ProductDetailActivity.CATEGORY_ID";
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
    @BindView(R.id.pb_load)
    ProgressBar pbLoad;
    @BindView(R.id.tv_error_title)
    TextView tvErrorTitle;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.btn_repeat)
    Button btnRepeat;
    @BindView(R.id.ll_error_container)
    LinearLayout llErrorContainer;

    private int categoryId;
    private int productId;

    private ErrorDialog errorDialog;
    private ProgressDialog progressDialog;

    private ProductModel productModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);
        setTitle(R.string.product_detail_screen_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (getIntent().getExtras() != null) {
            categoryId = getIntent().getExtras().getInt(CATEGORY_ID);
            productId = getIntent().getExtras().getInt(PRODUCT_ID);
            makeProductRequest();
        } else {
            showErrorProgress(getApplicationContext().getString(R.string.message_universal_error));
            btnRepeat.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
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
                Router.openBasketScreen(ProductDetailActivity.this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void makeProductRequest() {
        showProgress();
        NetworkService.getInstance()
                .getNetworkApi()
                .getProductDetail(String.valueOf(categoryId), String.valueOf(productId))
                .enqueue(new Callback<ProductDetailDataModel>() {
                    @Override
                    public void onResponse(Call<ProductDetailDataModel> call, Response<ProductDetailDataModel> response) {
                        ProductDetailDataModel productDetailDataModel = response.body();
                        if (productDetailDataModel != null && productDetailDataModel.getStatusModel() != null) {
                            StatusModel statusModel = productDetailDataModel.getStatusModel();
                            if (statusModel.getCode().equals(Constants.RESPONSE_200)) {
                                if (productDetailDataModel.getProductModel() != null) {
                                    productModel = productDetailDataModel.getProductModel();
                                    tvProductName.setText(productModel.getName());
                                    tvProductPrice.setText(productModel.getPrice());
                                    tvProductCount.setText(String.valueOf(productModel.getCount()));
                                    if (productModel.getCount() != 0) {
                                        if (productModel.getStatus() == ProductModel.UNSELECT_STATUS) {
                                            ivProductStatus.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_add));
                                        } else if (productModel.getStatus() == ProductModel.SELECT_STATUS) {
                                            ivProductStatus.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_delete));
                                        }
                                    } else {
                                        ivProductStatus.setVisibility(View.GONE);
                                    }
                                }
                                completeProgress();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ProductDetailDataModel> call, Throwable t) {
                        ErrorData errorData = new ErrorData(getApplicationContext(), t);
                        showErrorProgress(errorData.getErrorMessage());
                    }
                });
    }

    @OnClick({R.id.iv_product_status, R.id.btn_repeat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_product_status:
                makeChangeStatusProductRequest();
                break;
            case R.id.btn_repeat:
                makeProductRequest();
                break;
        }
    }

    private void makeChangeStatusProductRequest() {
        showProgressDialog();
        if (productModel.getStatus() == ProductModel.UNSELECT_STATUS) {
            NetworkService
                    .getInstance()
                    .getNetworkApi()
                    .addProduct(String.valueOf(productId))
                    .enqueue(new Callback<ChangeProductStatusDataModel>() {
                        @Override
                        public void onResponse(Call<ChangeProductStatusDataModel> call, Response<ChangeProductStatusDataModel> response) {
                            ChangeProductStatusDataModel changeProductStatusDataModel = response.body();
                            if (changeProductStatusDataModel != null && changeProductStatusDataModel.getStatusModel() != null) {
                                StatusModel statusModel = changeProductStatusDataModel.getStatusModel();
                                if (statusModel.getCode().equals(Constants.RESPONSE_200)) {
                                    if (changeProductStatusDataModel.getChangeProductStatusModel() != null) {
                                        ChangeProductStatusModel changeProductStatusModel = changeProductStatusDataModel.getChangeProductStatusModel();
                                        productModel.setStatus(changeProductStatusModel.getStatus());
                                        if (changeProductStatusModel.getStatus() == ProductModel.SELECT_STATUS) {
                                            ivProductStatus.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_delete));
                                        } else {
                                            ivProductStatus.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_add));
                                        }
                                    }
                                }
                            }
                            hideProgressDialog();
                        }

                        @Override
                        public void onFailure(Call<ChangeProductStatusDataModel> call, Throwable t) {
                            hideProgressDialog();
                            ErrorData errorData = new ErrorData(getApplicationContext(), t);
                            showError(errorData.getErrorMessage());
                        }
                    });
        } else {
            NetworkService
                    .getInstance()
                    .getNetworkApi()
                    .removeProduct(String.valueOf(productId))
                    .enqueue(new Callback<ChangeProductStatusDataModel>() {
                        @Override
                        public void onResponse(Call<ChangeProductStatusDataModel> call, Response<ChangeProductStatusDataModel> response) {
                            ChangeProductStatusDataModel changeProductStatusDataModel = response.body();
                            if (changeProductStatusDataModel != null && changeProductStatusDataModel.getStatusModel() != null) {
                                StatusModel statusModel = changeProductStatusDataModel.getStatusModel();
                                if (statusModel.getCode().equals(Constants.RESPONSE_200)) {
                                    if (changeProductStatusDataModel.getChangeProductStatusModel() != null) {
                                        ChangeProductStatusModel changeProductStatusModel = changeProductStatusDataModel.getChangeProductStatusModel();
                                        productModel.setStatus(changeProductStatusModel.getStatus());
                                        if (changeProductStatusModel.getStatus() == ProductModel.SELECT_STATUS) {
                                            ivProductStatus.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_delete));
                                        } else {
                                            ivProductStatus.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_add));
                                        }
                                    }
                                }
                            }
                            hideProgressDialog();
                        }

                        @Override
                        public void onFailure(Call<ChangeProductStatusDataModel> call, Throwable t) {
                            hideProgressDialog();
                            ErrorData errorData = new ErrorData(getApplicationContext(), t);
                            showError(errorData.getErrorMessage());
                        }
                    });
        }
    }

    private void showProgress() {
        rlContainer.setVisibility(View.GONE);
        pbLoad.setVisibility(View.VISIBLE);
        llErrorContainer.setVisibility(View.GONE);
    }

    private void completeProgress() {
        rlContainer.setVisibility(View.VISIBLE);
        pbLoad.setVisibility(View.GONE);
        llErrorContainer.setVisibility(View.GONE);
    }

    private void showErrorProgress(String errorMessage) {
        rlContainer.setVisibility(View.GONE);
        pbLoad.setVisibility(View.GONE);
        llErrorContainer.setVisibility(View.VISIBLE);
        tvDescription.setText(errorMessage);
    }

    private void showError(String errorMessage) {
        if (errorDialog != null) {
            errorDialog = null;
        }
        errorDialog = ErrorDialog.newInstance(errorMessage);
        errorDialog.setOnResultListener(statusCode -> {
            if (statusCode == Activity.RESULT_OK) {
                if (errorDialog != null) {
                    errorDialog.dismiss();
                }
            }
        });
        errorDialog.show(this.getSupportFragmentManager(), ErrorDialog.TAG);
    }

    private void showProgressDialog() {
        if (progressDialog != null) {
            progressDialog = null;
        }
        progressDialog = ProgressDialog.newInstance();
        progressDialog.show(this.getSupportFragmentManager(), ProgressDialog.TAG);
    }

    private void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Router.START_BASKET_ACTIVITY) {
            if (resultCode == Router.RESULT_BASKET_ACTIVITY) {
                makeProductRequest();
            }
        }
    }
}
