package com.patrick.whereisthat.scores;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.patrick.whereisthat.R;
import com.patrick.whereisthat.data.GetScores;

/**
 * Created by Patrick on 3/20/2018.
 */

public class Overall extends Fragment {


    RecyclerView mRecyclerView;
    RecyclerViewAdapter mRecylerViewAdapter;
    LinearLayoutManager mLayoutManager;
    String mUser;
    SwipeRefreshLayout mRefreshLayout;
    Context mContext;
    View view;
    int counter=0;
    ProgressBar bar;



    public Overall()
    {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View Overall=inflater.inflate(R.layout.scores_rv,container,false);
        counter++;
        bar=Overall.findViewById(R.id.rw_bar);
        if(counter>1)
        {
            bar.setVisibility(View.INVISIBLE);
        }
        mRefreshLayout=Overall.findViewById(R.id.swipeRefresh);
        mLayoutManager=new LinearLayoutManager(getContext());
        mRecyclerView=Overall.findViewById(R.id.rw_scores);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mRecylerViewAdapter);
        mRecyclerView.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(getContext(),mLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetScores.getScoresLevel("overall",mRecylerViewAdapter,mUser);
                mRefreshLayout.setRefreshing(false);
            }
        });
        view=Overall;
        TransferView(view);
        return Overall;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUser=getArguments().getString("Username");
        mRecylerViewAdapter=new RecyclerViewAdapter(mUser,getContext());
        mRecylerViewAdapter.TransferAdapter(mRecylerViewAdapter);
        GetScores.getScoresLevel("overall",mRecylerViewAdapter,mUser);

        Log.i("ScoreLevel1", "onCreate: ");


    }
    public void TransferView(View view)
    {
        mRecylerViewAdapter.TransferView(view);
    }



    void makeToast(DataSnapshot snapshot)
    {
        Toast.makeText(getContext(),snapshot.toString(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy()
    {

        super.onDestroy();
    }
}
