package com.patrick.whereisthat.scores;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.patrick.whereisthat.R;
import com.patrick.whereisthat.data.GetScores;

/**
 * Created by Patrick on 3/2/2018.
 */

public class ScoreLevel2 extends android.support.v4.app.Fragment {


    RecyclerView mRecyclerView;
    RecyclerViewAdapter mRecylerViewAdapter;
    LinearLayoutManager mLayoutManager;
    String mUser;
    public ScoreLevel2()
    {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View ScoreLevel2=inflater.inflate(R.layout.scores_rv,container,false);
        mLayoutManager=new LinearLayoutManager(getContext());
      //  mLayoutManager.setReverseLayout(true);
     //   mLayoutManager.setStackFromEnd(true);
        mRecyclerView=ScoreLevel2.findViewById(R.id.rw_scores);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mRecylerViewAdapter);
        mRecyclerView.setHasFixedSize(true);
        return ScoreLevel2;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUser=getArguments().getString("Username");
        mRecylerViewAdapter=new RecyclerViewAdapter(mUser);
        mRecylerViewAdapter.TransferAdapter(mRecylerViewAdapter);

        GetScores.getScoresLevel("level2",mRecylerViewAdapter,mUser);
    }
}
