package com.patrick.whereisthat.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.patrick.whereisthat.R;
import com.patrick.whereisthat.StartActivity;
import com.patrick.whereisthat.dialog.DialogResetPassword;
import com.patrick.whereisthat.register.RegisterActivity;

import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPassword;
    private Button mLogin;
    private FirebaseAuth mFirebaseAuth;
    private TextView resetPassword;
    private Button mRegister;
    private Boolean login = true;
    private boolean clickedOne=false;


    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
                if (mUser != null) {
                    Intent intent = new Intent(getApplication(), StartActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };
        mLogin = findViewById(R.id.login_button);
        mEmail = findViewById(R.id.login_email);
        mPassword = findViewById(R.id.login_password);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mRegister = findViewById(R.id.register_button);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CheckInternetTask().execute();
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        resetPassword = findViewById(R.id.tv_forgot_password);
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogResetPassword dialog = new DialogResetPassword();
                dialog.setCancelable(false);
                dialog.show(getSupportFragmentManager(), "dialog_password_reset");
            }
        });

    }


    public void loginToast() {
        Toast.makeText(this, "You are logged in", Toast.LENGTH_SHORT).show();
    }

    public void errorToast() {
        Toast.makeText(this, "Verify your email and your password", Toast.LENGTH_LONG).show();
    }
    public void fieldToast()
    {
        Toast.makeText(this, "You must complete all fields", Toast.LENGTH_LONG).show();
    }
    public void emailToast()
    {
        Toast.makeText(this, "Email adress is not valid", Toast.LENGTH_LONG).show();
    }



    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    private boolean isEmailValid(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            Log.i("CheckConnection", ipAddr.toString());
            return !ipAddr.equals("");

        } catch (Exception e) {
            Log.i("CheckConnection", e.toString());
            return false;
        }
    }

    class CheckInternetTask extends AsyncTask<Void, Void, Boolean>
    {

        @Override
        protected Boolean doInBackground(Void... voids) {
            return isInternetAvailable();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
         //   super.onPostExecute(aBoolean);
            if(aBoolean)
            {
                login = true;
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                if (email.equals("") || password.equals("")) {
                    fieldToast();
                    login = false;
                } else {
                    if (!isEmailValid(email)) {
                        emailToast();
                        login = false;
                    }
                }
                if (login == true) {
                    mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                loginToast();
                            } else {
                                errorToast();
                            }
                        }
                    });
                }
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Check your internet connection",Toast.LENGTH_LONG).show();
            }
        }
    }
}
