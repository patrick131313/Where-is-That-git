package com.patrick.whereisthat.scores;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class ScoreLevel3 extends Fragment {

    RecyclerView mRecyclerView;
    RecyclerViewAdapter mRecylerViewAdapter;
    LinearLayoutManager mLayoutManager;
    String mUser;

    public ScoreLevel3()
    {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View ScoreLevel3=inflater.inflate(R.layout.scores_rv,container,false);
        mLayoutManager=new LinearLayoutManager(getContext());
        //mLayoutManager.setReverseLayout(true);
      //  mLayoutManager.setStackFromEnd(true);
        mRecyclerView=ScoreLevel3.findViewById(R.id.rw_scores);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mRecylerViewAdapter);
        mRecyclerView.setHasFixedSize(true);
        return ScoreLevel3;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUser=getArguments().getString("Username");
        mRecylerViewAdapter=new RecyclerViewAdapter(mUser);
        GetScores.getScoresLevel("level3",mRecylerViewAdapter,mUser);
    }
}
