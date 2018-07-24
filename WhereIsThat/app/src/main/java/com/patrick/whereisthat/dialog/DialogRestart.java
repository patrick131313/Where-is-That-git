package com.patrick.whereisthat.dialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.patrick.whereisthat.R;
import com.patrick.whereisthat.sprint.SprintActivity;

/**
 * Created by Patrick on 5/6/2018.
 */

public class DialogRestart extends DialogFragment implements DialogInterface.OnDismissListener {

    public Button mYes,mNo;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_restart, container, false);

        mYes=view.findViewById(R.id.restart_yes);
        mNo=view.findViewById(R.id.restart_no);
        mYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Crash", "onClick: ");

            //    dismiss();
                //((SprintActivity)getActivity()).mCountDownTimer.cancel();
             //   Log.i("OnDestroy", "onDestroy:called ");
            //    ((SprintActivity)getActivity()).finish();
            //    Intent intent = new Intent(getContext(), SprintActivity.class);
            //    startActivity(intent);
            }
        });
        mNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                ((SprintActivity)getActivity()).finish();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        android.app.Dialog dialog=getDialog();
        dialog.setCanceledOnTouchOutside(false);
    }
}
