package com.patrick.whereisthat.register;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.patrick.whereisthat.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText mEmail,mUsername,mPassword;
    private Button mCreateAccount;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mEmail=findViewById(R.id.register_email);
        mUsername=findViewById(R.id.register_username);
        mPassword=findViewById(R.id.register_password);
        mCreateAccount=findViewById(R.id.create_account_button);
        mFirebaseAuth = FirebaseAuth.getInstance();


        mCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=mEmail.getText().toString();
                String user=mUsername.getText().toString();
                String password=mPassword.getText().toString();
                Log.d("Email:",email.toString());
                Log.d("Password:",password.toString());
                mFirebaseAuth.createUserWithEmailAndPassword(email,password).
                        addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(getApplication(), "Registration successful", Toast.LENGTH_LONG).show();
                            String userID = mFirebaseAuth.getCurrentUser().getUid();
                        }
                        else
                            Toast.makeText(getApplication(),"Error",Toast.LENGTH_LONG).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });

    }


}

