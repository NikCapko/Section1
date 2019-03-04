package com.example.section1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private DemoDataProvider dataProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        dataProvider = DemoDataProvider.newInstance();
    }
}
