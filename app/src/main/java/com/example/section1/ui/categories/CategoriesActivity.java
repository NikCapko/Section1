package com.example.section1.ui.categories;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.section1.R;
import com.example.section1.data.dataclasses.BaseModel;
import com.example.section1.data.dataclasses.CategoryDataModel;
import com.example.section1.data.dataclasses.CategoryModel;
import com.example.section1.data.dataclasses.StatusModel;
import com.example.section1.dialogs.ErrorDialog;
import com.example.section1.net.ErrorData;
import com.example.section1.net.NetworkService;
import com.example.section1.routing.Router;
import com.example.section1.ui.categories.adapter.CategoriesAdapter;
import com.example.section1.data.data_providers.RestDataProvider;
import com.example.section1.ui.sign_in.SignInActivity;
import com.example.section1.utils.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesActivity extends AppCompatActivity {

    @BindView(R.id.lv_categories_list)
    ListView lvCategoriesList;

    private CategoriesAdapter categoriesAdapter;

    private List<CategoryModel> categoryModelList;
    private ErrorDialog errorDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        ButterKnife.bind(this);
        NetworkService.getInstance()
                .getNetworkApi()
                .getCategories()
                .enqueue(new Callback<CategoryDataModel>() {
                    @Override
                    public void onResponse(Call<CategoryDataModel> call, Response<CategoryDataModel> response) {
                        CategoryDataModel categoryDataModel = response.body();
                        if (categoryDataModel != null && categoryDataModel.getStatusModel() != null) {
                            StatusModel statusModel = categoryDataModel.getStatusModel();
                            if (statusModel.getCode().equals(Constants.RESPONSE_200)) {
                                categoryModelList = categoryDataModel.getCategoryModelList();
                                setCategoryModelList(categoryModelList);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CategoryDataModel> call, Throwable t) {
                        ErrorData errorData = new ErrorData(getApplicationContext(), t);
                        showError(errorData.getErrorMessage());
                    }
                });
    }

    private void setCategoryModelList(List<CategoryModel> categoryModelList) {
        categoriesAdapter = new CategoriesAdapter(this, R.layout.row_categories_item);
        categoriesAdapter.setData(categoryModelList);
        categoriesAdapter.setOnClickListener(categoryId -> Router.openProductListScreen(CategoriesActivity.this, categoryId));
        lvCategoriesList.setAdapter(categoriesAdapter);
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
}
