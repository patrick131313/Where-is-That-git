package com.patrick.whereisthat.scores;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.patrick.whereisthat.R;
import com.patrick.whereisthat.data.GetScores;
import com.patrick.whereisthat.data.Scores;
import com.patrick.whereisthat.data.ScoresRank;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

/**
 * Created by Patrick on 3/2/2018.
 */

public class ScoreLevel1 extends Fragment {


    RecyclerView mRecyclerView;
    RecyclerViewAdapter mRecylerViewAdapter;
    LinearLayoutManager mLayoutManager;
    String mUser;
    SwipeRefreshLayout mRefreshLayout;

    public ScoreLevel1()
    {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View ScoreLevel1=inflater.inflate(R.layout.scores_rv,container,false);
        mRefreshLayout=ScoreLevel1.findViewById(R.id.swipeRefresh);
        mLayoutManager=new LinearLayoutManager(getContext());
        mRecyclerView=ScoreLevel1.findViewById(R.id.rw_scores);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mRecylerViewAdapter);
        mRecyclerView.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(getContext(),mLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetScores.getScoresLevel("level1",mRecylerViewAdapter,mUser);
                mRefreshLayout.setRefreshing(false);
            }
        });
        return ScoreLevel1;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUser=getArguments().getString("Username");


        mRecylerViewAdapter=new RecyclerViewAdapter(mUser);
        mRecylerViewAdapter.TransferAdapter(mRecylerViewAdapter);

        GetScores.getScoresLevel("level1",mRecylerViewAdapter,mUser);
        Log.i("ScoreLevel1", "onCreate: ");


    }



    void makeToast(DataSnapshot snapshot)
    {
        Toast.makeText(getContext(),snapshot.toString(),Toast.LENGTH_LONG).show();
    }
}
