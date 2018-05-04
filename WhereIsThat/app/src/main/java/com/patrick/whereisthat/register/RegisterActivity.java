package com.patrick.whereisthat.register;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.patrick.whereisthat.R;
import com.patrick.whereisthat.StartActivity;
import com.patrick.whereisthat.login.LoginActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText mEmail,mUsername,mPassword,mRPassword;
    private Button mCreateAccount;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mEmail=findViewById(R.id.register_email);
        mUsername=findViewById(R.id.register_username);
        mPassword=findViewById(R.id.register_password);
        mRPassword=findViewById(R.id.register_repeat_password);
        mCreateAccount=findViewById(R.id.create_account_button);
        mFirebaseAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
                if(mUser!=null){
                    Intent intent = new Intent(getApplication(), StartActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        mCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean register=true;
                final String email = mEmail.getText().toString();
                final String user = mUsername.getText().toString();
                final String password = mPassword.getText().toString();
                final String rpassword=mRPassword.getText().toString();
                Log.d("Email:", email.toString());
                Log.d("Password:", password.toString());
               // if (!isEmailValid(email) || email.equals("") || user.equals("")||(password.equals("")||mPassword.equals("")||(!rpassword.equals(password))))
               //     register=false;
                if(email.equals("") || user.equals("")||(password.equals("")||rpassword.equals("")))
                 {
                     Toast.makeText(getApplicationContext(), "Some fields are not completed", Toast.LENGTH_LONG).show();
                     register = false;
                }
                else
                {
                    if (!isEmailValid(email))
                    {
                        Toast.makeText(getApplicationContext(), "Email adress is not valid", Toast.LENGTH_LONG).show();
                        register = false;
                    }
                    else
                    {
                        if((!rpassword.equals(password)))
                        {
                            Toast.makeText(getApplicationContext(), "Passwords don't match", Toast.LENGTH_LONG).show();

                            register = false;
                        }
                    }
                }
                if(register==true)
                checkIfUserExists(email,user,password);



            }
        });




    }
  void  checkIfUserExists(final String email,final String user,final String password)
    {

        Log.d("Query", email.toString());
        Log.d("Query", user.toString());
        Log.d("Query",password.toString());
        //mFirebaseAuth.signInAnonymously();
       // mFirebaseAuth.signInWithEmailAndPassword("suciu.patrick@gmail.coom","patrick1313");
        DatabaseReference myRef=FirebaseDatabase.getInstance().getReference();
        Log.d("Query",myRef.toString());
                Query query=myRef.child("users").orderByChild("user").equalTo(user);
              query.addListenerForSingleValueEvent(new ValueEventListener() {

                  @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            Log.d("Query", "User exits");
                            toastExits();


                        }
                        else {
                            Log.d("Query", "You can add this user");
                            //toastSuccesful();
                            mFirebaseAuth.createUserWithEmailAndPassword(email, password).
                                    addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                //
                                                String userID = mFirebaseAuth.getCurrentUser().getUid();

                                                DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("users").child(userID);
                                                Map info = new HashMap<>();
                                                info.put("user", user);
                                                info.put("email", email);
                                                Map level=new HashMap<>();
                                                for (int i = 1; i < 12; i++)
                                                    level.put("level" + String.valueOf(i), 0);
                                                level.put("overall",0);
                                                userDb.child("scores").updateChildren(level);
                                                info.put("sprint_mode", 0);
                                                userDb.updateChildren(info);
                                            /*    Intent intent=new Intent(getApplication(), StartActivity.class);
                                                startActivity(intent);*/


                                            } else {
                                              //  Toast.makeText(getApplication(), "Error", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    }
                          ).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("QueryFailure", e.toString());
                                    if(e.toString().equals("com.google.firebase.auth.FirebaseAuthUserCollisionException: The email address is already in use by another account."))
                                        Toast.makeText(getApplication(), "This email address is already in use by another account ", Toast.LENGTH_LONG).show();
                                }
                            });



                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    Log.d("Query",databaseError.toString());
                    }
                });

    }
    public void toastSuccesful()
    {
        Toast.makeText(getApplication(), "Registration successful", Toast.LENGTH_LONG).show();


    }
    public void toastExits()
    {
        Toast.makeText(this,"This user already exists",Toast.LENGTH_LONG).show();


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
    private boolean isEmailValid(String email){
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
        return matcher.find();
    }
}

