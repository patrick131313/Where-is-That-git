package com.patrick.whereisthat.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.patrick.whereisthat.R;

/**
 * Created by Patrick on 4/19/2018.
 */

public class Dialog extends DialogFragment {

    TextView mRound;
    TextView mDistance;
    TextView mScore;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.dialog_layout,container,false);
        mRound=view.findViewById(R.id.dialog_round);
        mDistance=view.findViewById(R.id.dialog_distance);
        mScore=view.findViewById(R.id.dialog_score);

        mRound.setText("aaaa");
        mDistance.setText("bbb");
        mScore.setText("cccc");
        //return super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }
}
