package com.example.section1.ui.sign_up;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.section1.R;
import com.example.section1.data.dataclasses.ResultModel;
import com.example.section1.data.dataclasses.UserModel;
import com.example.section1.net.NetworkService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.et_first_name)
    EditText etFirstName;
    @BindView(R.id.et_last_name)
    EditText etLastName;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_login)
    EditText etLogin;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_repeat_password)
    EditText etRepeatPassword;
    @BindView(R.id.btn_sign_up)
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_sign_up)
    public void onViewClicked() {
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String email = etEmail.getText().toString();
        String login = etLogin.getText().toString();
        String password = etPassword.getText().toString();
        String repeatPassword = etRepeatPassword.getText().toString();

        if (!firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty() && !login.isEmpty() && !password.isEmpty()
                && !repeatPassword.isEmpty() && password.equals(repeatPassword)) {
            UserModel userModel = new UserModel(firstName, lastName, email, login, password);
            NetworkService.getInstance()
                    .getNetworkApi()
                    .authRegistration(userModel)
                    .enqueue(new Callback<ResultModel>() {
                        @Override
                        public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                            ResultModel resultModel = response.body();
                            if (resultModel != null) {
                                if (resultModel.isSuccess()) {
                                    //tvLabel.setText(R.string.sign_up_sign_up_success);
                                } else {
                                    //tvLabel.setText(R.string.sign_up_sign_up_error);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResultModel> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), R.string.http_error, Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }
}
