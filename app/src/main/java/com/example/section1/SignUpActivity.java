package com.example.section1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
import android.support.v7.app.AppCompatActivity;

    DemoDataProvider dataProvider;

    TextView tvLabel;
    EditText etFirstName;
    EditText etLastName;
    EditText etEmail;
    EditText etLogin;
    EditText etPassword;
    EditText etRepeatPassword;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        dataProvider = DemoDataProvider.newInstance();
        tvLabel = (TextView) findViewById(R.id.tv_label);
        etFirstName = (EditText) findViewById(R.id.et_first_name);
        etLastName = (EditText) findViewById(R.id.et_last_name);
        etEmail = (EditText) findViewById(R.id.et_email);
        etLogin = (EditText) findViewById(R.id.et_login);
        etPassword = (EditText) findViewById(R.id.et_password);
        etRepeatPassword = (EditText) findViewById(R.id.et_repeat_password);
        btnSignUp = (Button) findViewById(R.id.btn_sign_up);

        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String email = etEmail.getText().toString();
        String login = etLogin.getText().toString();
        String password = etPassword.getText().toString();
        String repeatPassword = etRepeatPassword.getText().toString();

        if (!firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty() && !login.isEmpty() && !password.isEmpty()
                && !repeatPassword.isEmpty() && password.equals(repeatPassword)) {
            Person person = new Person(firstName, lastName, email, login, password);
            dataProvider.addPerson(person);
            tvLabel.setText(R.string.sign_up_sign_up_success);
        } else {
            tvLabel.setText(R.string.sign_up_sign_up_error);
        }
    }
}
