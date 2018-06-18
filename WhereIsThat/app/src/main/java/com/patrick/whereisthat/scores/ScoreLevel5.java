package com.patrick.whereisthat.scores;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
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

public class ScoreLevel5 extends Fragment {

    RecyclerView mRecyclerView;
    RecyclerViewAdapter mRecylerViewAdapter;
    LinearLayoutManager mLayoutManager;
    String mUser;
    SwipeRefreshLayout mRefreshLayout;
    Context mContext;
    View view;

    public ScoreLevel5()
    {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View ScoreLevel5=inflater.inflate(R.layout.scores_rv,container,false);
        mRefreshLayout=ScoreLevel5.findViewById(R.id.swipeRefresh);
        mLayoutManager=new LinearLayoutManager(getContext());
      //  mLayoutManager.setReverseLayout(true);
     //   mLayoutManager.setStackFromEnd(true);
       mRecyclerView=ScoreLevel5.findViewById(R.id.rw_scores);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mRecylerViewAdapter);
        mRecyclerView.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(getContext(),mLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetScores.getScoresLevel("level5",mRecylerViewAdapter,mUser);
                mRefreshLayout.setRefreshing(false);
            }
        });
        view=ScoreLevel5;
        TransferView(view);
        return ScoreLevel5;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUser=getArguments().getString("Username");
        mRecylerViewAdapter=new RecyclerViewAdapter(mUser,getContext());
        mRecylerViewAdapter.TransferAdapter(mRecylerViewAdapter);

        GetScores.getScoresLevel("level5",mRecylerViewAdapter,mUser);
    }
    public void TransferView(View view)
    {
        mRecylerViewAdapter.TransferView(view);
    }
}
