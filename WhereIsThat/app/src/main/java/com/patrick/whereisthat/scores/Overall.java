package com.patrick.whereisthat.scores;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public Overall()
    {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View Overall=inflater.inflate(R.layout.scores_rv,container,false);
        mLayoutManager=new LinearLayoutManager(getContext());
     //   mLayoutManager.setReverseLayout(true);
     //   mLayoutManager.setStackFromEnd(true);
        mRecyclerView=Overall.findViewById(R.id.rw_scores);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mRecylerViewAdapter);
        mRecyclerView.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(getContext(),mLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        return Overall;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUser=getArguments().getString("Username");
        mRecylerViewAdapter=new RecyclerViewAdapter(mUser);
        mRecylerViewAdapter.TransferAdapter(mRecylerViewAdapter);
        GetScores.getScoresLevel("overall",mRecylerViewAdapter,mUser);
        // getScoresLevel("level3");
//        scoreListLevel1=GetScores.getScoresLevel("level1");
        //    scoreListLevel1.add(new ScoresRank("aaaa","111"));
        //      Log.i("OnCreateScoreList", scoreListLevel1.toString());
        //    mRecylerViewAdapter=new RecyclerViewAdapter(scoreListLevel1);
        Log.i("ScoreLevel1", "onCreate: ");
        //  new ScoresAsync().execute();


    }



    void makeToast(DataSnapshot snapshot)
    {
        Toast.makeText(getContext(),snapshot.toString(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
