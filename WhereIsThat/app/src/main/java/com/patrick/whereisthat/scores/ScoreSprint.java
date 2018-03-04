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
 * Created by Patrick on 3/4/2018.
 */

public class ScoreSprint extends Fragment {

    RecyclerView mRecyclerView;
    RecyclerViewAdapter mRecylerViewAdapter;
    LinearLayoutManager mLayoutManager;
    public ScoreSprint()
    {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View ScoreSprint=inflater.inflate(R.layout.scores_rv,container,false);
        mLayoutManager=new LinearLayoutManager(getContext());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView=ScoreSprint.findViewById(R.id.rw_scores);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mRecylerViewAdapter);
        mRecyclerView.setHasFixedSize(true);
        return ScoreSprint;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecylerViewAdapter=new RecyclerViewAdapter();
        GetScores.getSprintScores(mRecylerViewAdapter);
    }
}