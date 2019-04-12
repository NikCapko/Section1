package com.example.section1.ui.categories;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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
import com.example.section1.data.dataclasses.CategoryDataModel;
import com.example.section1.data.dataclasses.CategoryModel;
import com.example.section1.data.dataclasses.StatusModel;
import com.example.section1.net.ErrorData;
import com.example.section1.net.NetworkService;
import com.example.section1.routing.Router;
import com.example.section1.ui.categories.adapter.CategoriesAdapter;
import com.example.section1.utils.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesActivity extends AppCompatActivity {

    @BindView(R.id.lv_categories_list)
    ListView lvCategoriesList;
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

    private CategoriesAdapter categoriesAdapter;

    private List<CategoryModel> categoryModelList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        ButterKnife.bind(this);
        setTitle(R.string.category_screen_title);
        makeCategoryRequest();
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
                Router.openBasketScreen(CategoriesActivity.this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void makeCategoryRequest() {
        showProgress();
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
                                completeProgress();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CategoryDataModel> call, Throwable t) {
                        ErrorData errorData = new ErrorData(getApplicationContext(), t);
                        showErrorProgress(errorData.getErrorMessage());
                    }
                });
    }

    private void setCategoryModelList(List<CategoryModel> categoryModelList) {
        categoriesAdapter = new CategoriesAdapter(getApplicationContext(), R.layout.row_categories_item, categoryModelList);
        lvCategoriesList.setAdapter(categoriesAdapter);
        categoriesAdapter.setOnClickListener(categoryId -> Router.openProductListScreen(CategoriesActivity.this, categoryId));
    }

    private void showProgress() {
        lvCategoriesList.setVisibility(View.GONE);
        pbLoad.setVisibility(View.VISIBLE);
        llErrorContainer.setVisibility(View.GONE);
    }

    private void completeProgress() {
        lvCategoriesList.setVisibility(View.VISIBLE);
        pbLoad.setVisibility(View.GONE);
        llErrorContainer.setVisibility(View.GONE);
    }

    private void showErrorProgress(String errorMessage) {
        lvCategoriesList.setVisibility(View.GONE);
        pbLoad.setVisibility(View.GONE);
        llErrorContainer.setVisibility(View.VISIBLE);
        tvDescription.setText(errorMessage);
    }

    @OnClick(R.id.btn_repeat)
    public void onViewClicked() {
        makeCategoryRequest();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CategoriesActivity.this);
        builder.setTitle("Внимание!")
                .setMessage("Вы действительно хотите выйти из приложения ?")
                .setIcon(R.drawable.ic_warning)
                .setCancelable(false)
                .setNegativeButton("НЕТ",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                .setPositiveButton("ДА",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
