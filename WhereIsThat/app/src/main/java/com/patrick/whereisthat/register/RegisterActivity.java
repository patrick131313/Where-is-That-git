package com.patrick.whereisthat.register;

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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.patrick.whereisthat.R;

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


        mCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email=mEmail.getText().toString();
                final String user=mUsername.getText().toString();
                final String password=mPassword.getText().toString();
                Log.d("Email:",email.toString());
                Log.d("Password:",password.toString());
                checkIfUserExists(user);
              mFirebaseAuth.createUserWithEmailAndPassword(email,password).
                        addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(getApplication(), "Registration successful", Toast.LENGTH_LONG).show();
                            String userID = mFirebaseAuth.getCurrentUser().getUid();

                           DatabaseReference userDb= FirebaseDatabase.getInstance().getReference().child("users").child(userID);
                            Map info=new HashMap<>();
                            info.put("user",user);
                            info.put("email",email);
                            for(int i=1;i<12;i++)
                                info.put("level"+String.valueOf(i),0);
                            info.put("sprint_mode",0);
                            userDb.updateChildren(info);



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
    boolean checkIfUserExists(String user)
    {
        DatabaseReference myRef=FirebaseDatabase.getInstance().getReference();
        Query query=myRef.child("users").orderByChild("user").equalTo(user);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    Log.d("Query","User exits");
                else
                    Log.d("Query","You cand add this user");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return false;
    }


}

