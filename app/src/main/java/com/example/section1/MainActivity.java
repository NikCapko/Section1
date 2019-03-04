package com.example.section1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvLabel;
    private EditText etLogin;
    private EditText etPassword;
    private Button btnSignIn;
    private Button btnSignUn;
    private DemoDataProvider dataProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataProvider = DemoDataProvider.newInstance();
        tvLabel = (TextView) findViewById(R.id.tv_label);
        etLogin = (EditText) findViewById(R.id.et_login);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnSignIn = (Button) findViewById(R.id.btn_sign_in);
        btnSignUn = (Button) findViewById(R.id.btn_sign_up);
        btnSignIn.setOnClickListener(this);
        btnSignUn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_in:
                if (dataProvider != null && dataProvider.getPersonList() != null && !dataProvider.getPersonList().isEmpty()) {
                    String login = etLogin.getText().toString();
                    String password = etPassword.getText().toString();
                    for (Person person :
                            dataProvider.getPersonList()) {
                        if (person != null) {
                            if ((login.equals(person.getLogin()) || login.equals(person.getEmail())) && password.equals(person.getPassword())) {
                                tvLabel.setText(this.getString(R.string.sign_in_success));
                                return;
                            }
                        }
                    }
                    tvLabel.setText(this.getString(R.string.sign_in_error));
                }
                break;
            case R.id.btn_sign_up:
                Intent intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                break;
        }
    }
}
