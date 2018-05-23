package com.patrick.whereisthat.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.patrick.whereisthat.R;

/**
 * Created by Patrick on 5/22/2018.
 */

public class DialogImage extends DialogFragment implements DialogInterface.OnDismissListener {

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme);
    }

    @Override
    public void onStart() {
        super.onStart();

        android.app.Dialog dialog=getDialog();

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                Log.i("DialogOnDismiss", "onDismiss: ");
                //    mConfirm.callOnClick();
                dismiss();
                //       ((SprintActivity)getActivity()).StartTimer();
                //      ((SprintActivity)getActivity()).mCountDownTimer.start();

            }
        });
   /*     if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }*/
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view=inflater.inflate(R.layout.image_dialog,container,false);
        ImageView mImageView=view.findViewById(R.id.dialogImage);
        TextView mTextView=view.findViewById(R.id.dialogTitle);

        Glide.with(getContext())
                .load(getArguments().getInt("Resource"))
                .apply(new RequestOptions().override(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT))
                .into(mImageView);
        return view;
    }
}
