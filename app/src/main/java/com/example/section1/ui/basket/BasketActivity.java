package com.example.section1.ui.basket;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.section1.R;
import com.example.section1.data.dataclasses.BasketDataModel;
import com.example.section1.data.dataclasses.ChangeProductStatusDataModel;
import com.example.section1.data.dataclasses.ProductModel;
import com.example.section1.data.dataclasses.StatusModel;
import com.example.section1.dialogs.ErrorDialog;
import com.example.section1.dialogs.ProgressDialog;
import com.example.section1.net.ErrorData;
import com.example.section1.net.NetworkService;
import com.example.section1.routing.Router;
import com.example.section1.ui.basket.adapter.BasketAdapter;
import com.example.section1.utils.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BasketActivity extends AppCompatActivity {

    @BindView(R.id.lv_product_list)
    ListView lvProductList;
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
    @BindView(R.id.ll_empty_container)
    LinearLayout llEmptyContainer;

    private BasketAdapter basketAdapter;
    private List<ProductModel> productModelList;

    private ErrorDialog errorDialog;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        ButterKnife.bind(this);
        setTitle(R.string.basket_screen_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        makeProductsRequest();
    }

    private void makeProductsRequest() {
        showProgress();
        NetworkService.getInstance()
                .getNetworkApi()
                .basketCheckout()
                .enqueue(new Callback<BasketDataModel>() {
                    @Override
                    public void onResponse(Call<BasketDataModel> call, Response<BasketDataModel> response) {
                        BasketDataModel basketDataModel = response.body();
                        if (basketDataModel != null && basketDataModel.getStatusModel() != null) {
                            StatusModel statusModel = basketDataModel.getStatusModel();
                            if (statusModel.getCode().equals(Constants.RESPONSE_200)) {
                                productModelList = basketDataModel.getProductModelList();
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
                    public void onFailure(Call<BasketDataModel> call, Throwable t) {
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
        onBackPressed();
        return true;
    }

    private void setProductModelList(List<ProductModel> categoryModelList) {
        basketAdapter = new BasketAdapter(getApplicationContext(), R.layout.row_basket_product_item, categoryModelList);
        lvProductList.setAdapter(basketAdapter);
        basketAdapter.setOnClickListener(new BasketAdapter.OnClickListener() {
            @Override
            public void onItemClick(int id) {
                Router.openProductDetailScreen(BasketActivity.this, -1, id);
            }

            @Override
            public void deleteProduct(int id) {
                makeDeleteProductRequest(id);
            }
        });
    }

    private void makeDeleteProductRequest(int productId) {
        showProgressDialog();
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
                                removeProductFromList(productId);
                            }
                        }
                        hideProgressDialog();
                    }

                    @Override
                    public void onFailure(Call<ChangeProductStatusDataModel> call, Throwable t) {
                        hideProgressDialog();
                        ErrorData errorData = new ErrorData(getApplicationContext(), t);
                        showErrorDialog(errorData.getErrorMessage());
                    }
                });
    }

    private void removeProductFromList(int productId) {
        for (int i = 0; i < productModelList.size(); i++) {
            if (productModelList.get(i).getId() == productId) {
                productModelList.remove(i);
                if (productModelList.isEmpty()) {
                    showEmptyList();
                } else {
                    basketAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    private void showErrorDialog(String errorMessage) {
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

    @Override
    public void onBackPressed() {
        setResult(Router.RESULT_BASKET_ACTIVITY);
        finish();
    }

}
