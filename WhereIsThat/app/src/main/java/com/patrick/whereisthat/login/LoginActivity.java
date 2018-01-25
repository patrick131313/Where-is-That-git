package com.patrick.whereisthat.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.patrick.whereisthat.R;
import com.patrick.whereisthat.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void loginClick(View view)
    {

    }
    public void registerClick(View view)
    {
        Intent intent=new Intent(getApplication(), RegisterActivity.class);
        startActivity(intent);
    }
}
