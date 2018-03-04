package com.patrick.whereisthat.scores;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v4.app.Fragment;
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

/**
 * Created by Patrick on 3/2/2018.
 */

public class ScoreLevel1 extends Fragment {


    RecyclerView mRecyclerView;
    RecyclerViewAdapter mRecylerViewAdapter;
    LinearLayoutManager mLayoutManager;


    public ScoreLevel1()
    {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View ScoreLevel1=inflater.inflate(R.layout.scores_rv,container,false);
        mLayoutManager=new LinearLayoutManager(getContext());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView=ScoreLevel1.findViewById(R.id.rw_scores);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mRecylerViewAdapter);
        mRecyclerView.setHasFixedSize(true);
        return ScoreLevel1;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecylerViewAdapter=new RecyclerViewAdapter();
         GetScores.getScoresLevel("level1",mRecylerViewAdapter);
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

  /*  public void getScoresLevel(final String level)
    {
        //final List<ScoresRank> scoreList=new ArrayList<ScoresRank>();
        ValueEventListener eventListener;
        final DatabaseReference myRef= FirebaseDatabase.getInstance().getReference();
        Query query=myRef.child("users").orderByChild("scores/"+level);
        query.addListenerForSingleValueEvent(eventListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.i("Snapshot", "onDataChange: "+dataSnapshot.toString());
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {

                    Log.i("Snapshot",snapshot.toString());
                    ScoresRank score=new ScoresRank(snapshot.child("user").getValue().toString(),snapshot.child("scores/"+level).getValue().toString());
                    scoreListLevel1.add(score);

                    Log.i("Snapshot",snapshot.child("scores/"+level).getValue().toString());
                    Log.i("Snapshot",snapshot.child("user").getValue().toString());

                }
                Log.i("List",scoreListLevel1.toString());
                mRecylerViewAdapter.ReplaceData(scoreListLevel1);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }*/


}
