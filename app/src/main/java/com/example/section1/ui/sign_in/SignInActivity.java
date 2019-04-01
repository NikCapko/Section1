package com.example.section1.ui.sign_in;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.section1.R;
import com.example.section1.data.dataclasses.ResultModel;
import com.example.section1.data.dataclasses.UserLoginModel;
import com.example.section1.net.NetworkService;
import com.example.section1.ui.sign_up.SignUpActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    @BindView(R.id.et_login)
    EditText etLogin;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_sign_in)
    Button btnSignIn;
    @BindView(R.id.btn_sign_up)
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_sign_in, R.id.btn_sign_up})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_sign_in:
                String login = etLogin.getText().toString();
                String password = etPassword.getText().toString();
                UserLoginModel userLoginModel = new UserLoginModel(login, password);
                NetworkService.getInstance()
                        .getNetworkApi()
                        .authLogin(userLoginModel)
                        .enqueue(new Callback<ResultModel>() {
                            @Override
                            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                                ResultModel resultModel = response.body();
                                if (resultModel != null) {
                                    if (resultModel.isSuccess()) {
                                        //tvLabel.setText(getApplicationContext().getString(R.string.sign_in_success));
                                        //Intent intent = new Intent(SignInActivity.this, CategoriesActivity.class);
                                        //startActivity(intent);
                                    } else {
                                        //tvLabel.setText(getApplicationContext().getString(R.string.sign_in_error));
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ResultModel> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), R.string.http_error, Toast.LENGTH_LONG).show();
                            }
                        });
                break;
            case R.id.btn_sign_up:
                Intent intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                break;
        }
    }
}
