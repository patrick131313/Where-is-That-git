package com.patrick.whereisthat.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.patrick.whereisthat.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Patrick on 5/1/2018.
 */

public class DialogResetPassword extends DialogFragment {

    private static final String TAG = "PasswordResetDialog";
    private EditText mEmail;
    private Context mContext;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_resetpassword, container, false);
        mEmail = view.findViewById(R.id.email_password_reset);
        mContext = getActivity();
        TextView cancelDialog=view.findViewById(R.id.dialogCancel);
        TextView confirmDialog = view.findViewById(R.id.dialogConfirm);
        confirmDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty(mEmail.getText().toString()))
                {
                    Toast.makeText(mContext, "You must enter your email adress",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (!isEmailValid(mEmail.getText().toString())) {
                        Toast.makeText(mContext, "Email adress is not valid", Toast.LENGTH_LONG).show();

                    }
                }
                if(!isEmpty(mEmail.getText().toString()) && isEmailValid(mEmail.getText().toString()) ){
                    Log.d(TAG, "onClick: attempting to send reset link to: " + mEmail.getText().toString());
                    sendPasswordResetEmail(mEmail.getText().toString());
                    getDialog().dismiss();
                }

            }
        });
        cancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        return view;
    }
    public void sendPasswordResetEmail(String email){
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: Password Reset Email sent.");
                            Toast.makeText(mContext, "Email was sent",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Log.d(TAG, "onComplete: No user associated with this email.");
                            Toast.makeText(mContext, "No User is Associated with this Email",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private boolean isEmpty(String string)
    {
        return string.equals("");
    }

    private boolean isEmailValid(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }
}
