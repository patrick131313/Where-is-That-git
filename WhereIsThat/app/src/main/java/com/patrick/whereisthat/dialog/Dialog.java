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

import com.patrick.whereisthat.R;
import com.patrick.whereisthat.sprint.SprintActivity;

import org.w3c.dom.Text;

import javax.security.auth.login.LoginException;

import static android.content.DialogInterface.*;

/**
 * Created by Patrick on 4/19/2018.
 */

public class Dialog extends DialogFragment implements OnDismissListener {

    TextView mRound;
    TextView mDistance;
    TextView mScore;
    TextView mTotal;
    Button mConfirm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NORMAL,R.style.AppTheme);
    }



    @Override
    public void onStart() {
        super.onStart();

        android.app.Dialog dialog=getDialog();

        dialog.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                Log.i("DialogOnDismiss", "onDismiss: ");
            //    mConfirm.callOnClick();
                dismiss();
         //       ((SprintActivity)getActivity()).StartTimer();
          //      ((SprintActivity)getActivity()).mCountDownTimer.start();

            }
        });
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.dialog_layout,container,false);
        mConfirm=view.findViewById(R.id.dialogContinue);
        mRound=view.findViewById(R.id.dialogRound);
        mDistance=view.findViewById(R.id.dialogDistance);
        mScore=view.findViewById(R.id.dialogScore);
        mTotal=view.findViewById(R.id.dialogTotal);
        mRound.setText("Round "+String.valueOf(getArguments().getInt("Round"))+"/20");
        mDistance.setText("Distance: "+String.valueOf(getArguments().getFloat("Distance"))+"km");
        mScore.setText("Score:"+String.valueOf(getArguments().getLong("Score")));
       // if(getArguments().getInt("Round")==1)
      //  {
      //      mTotal.setVisibility(View.INVISIBLE);
      //  }
        {
            mTotal.setText("Total score:" + String.valueOf(getArguments().getLong("Total")));
        }
       // Log.i("ScoreRound", String.valueOf(getArguments().getLong("Score")));

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();

            }
        });
      /* mRound=view.findViewById(R.id.dialog_round);
       mDistance=view.findViewById(R.id.dialog_distance);
        mScore=view.findViewById(R.id.dialog_score);

        mRound.setText("aaaa");
        mDistance.setText("bbb");
        mScore.setText("cccc");*/
        //return super.onCreateView(inflater, container, savedInstanceState);

        return view;
    }


}
