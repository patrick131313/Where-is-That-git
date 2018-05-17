package com.patrick.whereisthat.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.patrick.whereisthat.R;
import com.patrick.whereisthat.level.LevelActivity;

/**
 * Created by Patrick on 4/30/2018.
 */

public class DialogLevel extends DialogFragment implements DialogInterface.OnDismissListener {

    TextView mRound;
    TextView mDistance;
    TextView mScore;
    TextView mTotal;
    TextView mTime;
    long updateTime;
    int secs,mins,miliseconds;
    Button mConfirm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme);

    }



    @Override
    public void onStart() {
        super.onStart();

        android.app.Dialog dialog=getDialog();

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                Log.i("DialogOnDismiss", "onDismiss: ");
                if(getArguments().getInt("Round")==10)
                {
                    dismiss();
                    ((LevelActivity)getActivity()).makeToast();
                    ((LevelActivity)getActivity()).finish();


                }
                else {
                    //    mConfirm.callOnClick();
                    dismiss();
                }
                //       ((SprintActivity)getActivity()).StartTimer();
                //      ((SprintActivity)getActivity()).mCountDownTimer.start();

            }
        });
   /*    if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }*/
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.dialog_level,container,false);
       mConfirm=view.findViewById(R.id.dialogContinue);

        mRound=view.findViewById(R.id.dialogRound);
        mTime=view.findViewById(R.id.dialogTime);
        mScore=view.findViewById(R.id.dialogScore);
        mTotal=view.findViewById(R.id.dialogTotal);
        mDistance=view.findViewById(R.id.dialogDistance);

        updateTime=getArguments().getLong("Time");
        int secs = (int)(updateTime/1000);
        int mins = secs/60;
        secs%=60;
        int miliseconds = (int)(updateTime%1000);
        miliseconds=miliseconds/10;
        if(mins==0) {
            mTime.setText("Time: " + String.format("%02d", secs) + ":"
                    + String.format("%02d", miliseconds));
        }
        else
        {
            mTime.setText("Time: " + mins + ":" + String.format("%02d", secs) + ":"
                    + String.format("%02d", miliseconds));
        }

        mScore.setText("Score:"+String.valueOf(getArguments().getLong("RoundScore")));
        mRound.setText("Round "+String.valueOf(getArguments().getInt("Round"))+"/10");
        mTotal.setText("Score:"+String.valueOf(getArguments().getLong("Score")));
        mDistance.setText("Distance: "+String.valueOf(getArguments().getFloat("Distance"))+"km");
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                ((LevelActivity)getActivity()).StartTimer();

            }
        });
     

        return view;
    }
}
