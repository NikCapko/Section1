package com.example.section1.ui.sign_in;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.section1.R;
import com.example.section1.data.dataclasses.BaseModel;
import com.example.section1.data.dataclasses.StatusModel;
import com.example.section1.data.dataclasses.UserLoginModel;
import com.example.section1.dialogs.ErrorDialog;
import com.example.section1.dialogs.ProgressDialog;
import com.example.section1.net.ErrorData;
import com.example.section1.net.NetworkService;
import com.example.section1.routing.Router;
import com.example.section1.ui.categories.CategoriesActivity;
import com.example.section1.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    @BindView(R.id.til_login)
    TextInputLayout tilLogin;
    @BindView(R.id.et_login)
    EditText etLogin;
    @BindView(R.id.til_password)
    TextInputLayout tilPassword;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_sign_in)
    Button btnSignIn;
    @BindView(R.id.btn_sign_up)
    Button btnSignUp;

    private ErrorDialog errorDialog;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        setTitle(R.string.sign_in_screen_title);
    }

    private void makeSignInRequest(UserLoginModel userLoginModel) {
        showProgressDialog();
        NetworkService.getInstance()
                .getNetworkApi()
                .authLogin(userLoginModel)
                .enqueue(new Callback<BaseModel>() {
                    @Override
                    public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                        BaseModel baseModel = response.body();
                        if (baseModel != null && baseModel.getStatusModel() != null) {
                            StatusModel statusModel = baseModel.getStatusModel();
                            if (statusModel.getCode().equals(Constants.RESPONSE_200)) {
                                hideProgressDialog();
                                //cleanFields();
                                Router.openCategoriesScreen(SignInActivity.this);
                                //finish();
                            } else if (statusModel.getCode().equals(Constants.RESPONSE_401)) {
                                tilLogin.setError(getApplicationContext().getString(R.string.sign_in_error));
                                tilPassword.setError(getApplicationContext().getString(R.string.sign_in_error));
                            }
                        }
                        hideProgressDialog();
                    }

                    @Override
                    public void onFailure(Call<BaseModel> call, Throwable t) {
                        hideProgressDialog();
                        ErrorData errorData = new ErrorData(getApplicationContext(), t);
                        showError(errorData.getErrorMessage());
                    }
                });
    }

    @OnClick({R.id.btn_sign_in, R.id.btn_sign_up})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_sign_in:
                tilLogin.setError(null);
                tilPassword.setError(null);
                String login = etLogin.getText().toString();
                String password = etPassword.getText().toString();
                UserLoginModel userLoginModel = new UserLoginModel(login, password);
                makeSignInRequest(userLoginModel);
                break;
            case R.id.btn_sign_up:
                Router.openSignUpScreen(SignInActivity.this);
                break;
        }
    }

    private void cleanFields() {
        etLogin.setText(null);
        etPassword.setText(null);
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
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
        builder.setTitle(getApplicationContext().getString(R.string.attention))
                .setMessage(getApplicationContext().getString(R.string.app_exi_question))
                .setIcon(R.drawable.ic_warning)
                .setCancelable(false)
                .setNegativeButton(getApplicationContext().getString(R.string.no),
                        (dialog, id) -> dialog.cancel())
                .setPositiveButton(getApplicationContext().getString(R.string.yes),
                        (dialog, id) -> finish());
        AlertDialog alert = builder.create();
        alert.show();
    }
}
