package com.example.section1.ui.sign_up;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.example.section1.R;
import com.example.section1.data.dataclasses.BaseModel;
import com.example.section1.data.dataclasses.StatusModel;
import com.example.section1.data.dataclasses.UserModel;
import com.example.section1.dialogs.ErrorDialog;
import com.example.section1.dialogs.SuccessDialog;
import com.example.section1.net.ErrorData;
import com.example.section1.net.NetworkService;
import com.example.section1.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.til_first_name)
    TextInputLayout tilFirstName;
    @BindView(R.id.et_first_name)
    EditText etFirstName;
    @BindView(R.id.til_last_name)
    TextInputLayout tilLastName;
    @BindView(R.id.et_last_name)
    EditText etLastName;
    @BindView(R.id.til_email)
    TextInputLayout tilEmail;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.til_login)
    TextInputLayout tilLogin;
    @BindView(R.id.et_login)
    EditText etLogin;
    @BindView(R.id.til_password)
    TextInputLayout tilPassword;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.til_repeat_password)
    TextInputLayout tilRepeatPassword;
    @BindView(R.id.et_repeat_password)
    EditText etRepeatPassword;
    @BindView(R.id.btn_sign_up)
    Button btnSignUp;

    private ErrorDialog errorDialog;
    private SuccessDialog successDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_sign_up)
    public void onViewClicked() {

        tilLogin.setError(null);
        tilEmail.setError(null);
        tilPassword.setError(null);
        tilRepeatPassword.setError(null);

        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String email = etEmail.getText().toString();
        String login = etLogin.getText().toString();
        String password = etPassword.getText().toString();
        String repeatPassword = etRepeatPassword.getText().toString();

        if (!firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty() && !login.isEmpty() && !password.isEmpty()
                && !repeatPassword.isEmpty()) {
            if (password.equals(repeatPassword)) {
                UserModel userModel = new UserModel(firstName, lastName, email, login, password);
                NetworkService.getInstance()
                        .getNetworkApi()
                        .authRegistration(userModel)
                        .enqueue(new Callback<BaseModel>() {
                            @Override
                            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                                BaseModel baseModel = response.body();
                                if (baseModel != null && baseModel.getStatusModel() != null) {
                                    StatusModel statusModel = baseModel.getStatusModel();
                                    switch (statusModel.getCode()) {
                                        case Constants.RESPONSE_200:
                                            showSuccess();
                                            break;
                                        case Constants.RESPONSE_310:
                                            tilLogin.setError(getApplicationContext().getString(R.string.sign_up_login_error));
                                            break;
                                        case Constants.RESPONSE_311:
                                            tilEmail.setError(getApplicationContext().getString(R.string.sign_up_email_error));
                                            break;
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<BaseModel> call, Throwable t) {
                                ErrorData errorData = new ErrorData(getApplicationContext(), t);
                                showError(errorData.getErrorMessage());
                            }
                        });
            } else {
                tilPassword.setError(getApplicationContext().getString(R.string.sign_up_password_error));
                tilRepeatPassword.setError(getApplicationContext().getString(R.string.sign_up_password_error));
            }
        }
    }

    private void showSuccess() {
        if (successDialog != null) {
            successDialog = null;
        }
        successDialog = SuccessDialog.newInstance(getApplicationContext().getString(R.string.sign_up_sign_up_success));
        successDialog.setOnResultListener(statusCode -> {
            if (statusCode == Activity.RESULT_OK) {
                if (successDialog != null) {
                    successDialog.dismiss();
                    finish();
                }
            }
        });
        successDialog.show(this.getSupportFragmentManager(), ErrorDialog.TAG);
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
