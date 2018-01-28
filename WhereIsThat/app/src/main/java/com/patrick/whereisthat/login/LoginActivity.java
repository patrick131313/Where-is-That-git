package com.patrick.whereisthat.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.patrick.whereisthat.R;
import com.patrick.whereisthat.StartActivity;
import com.patrick.whereisthat.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPassword;
    private Button mLogin;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLogin=findViewById(R.id.login_button);
        mEmail=findViewById(R.id.login_email);
        mPassword=findViewById(R.id.login_password);
        mFirebaseAuth=FirebaseAuth.getInstance();

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=mEmail.getText().toString();
                String password=mPassword.getText().toString();
              mFirebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                          makeToast();
                          goToStartActivity();
                        }
                        else
                        {
                            errorToast();
                        }
                    }
                });
            }
        });

    }

    public void registerClick(View view)
    {
        Intent intent=new Intent(getApplication(), RegisterActivity.class);
        startActivity(intent);
    }
    public void makeToast()
    {
        Toast.makeText(this,"You are logged in",Toast.LENGTH_LONG).show();
    }
    public void errorToast()
    {
        Toast.makeText(this,"Verify your email and your password",Toast.LENGTH_LONG).show();
    }
    public void goToStartActivity()
    {
        Intent intent=new Intent(getApplication(), StartActivity.class);
        startActivity(intent);
    }

}
