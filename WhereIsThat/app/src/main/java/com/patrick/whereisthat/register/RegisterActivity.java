package com.patrick.whereisthat.register;

import android.content.Intent;
import android.provider.ContactsContract;
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
                final String email = mEmail.getText().toString();
                final String user = mUsername.getText().toString();
                final String password = mPassword.getText().toString();
                Log.d("Email:", email.toString());
                Log.d("Password:", password.toString());


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
                            Log.d("Query", "You cand add this user");
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


                                            } else
                                                Toast.makeText(getApplication(), "Error", Toast.LENGTH_LONG).show();

                                        }
                                    }
                          ).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("Query", e.toString());                                }
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
}

