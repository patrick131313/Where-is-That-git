package com.patrick.whereisthat.data;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.patrick.whereisthat.scores.RecyclerViewAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Patrick on 3/3/2018.
 */

public class GetScores {

  // private List<ScoresRank> list=new ArrayList<ScoresRank>();
    public static void getScoresLevel(final String level, final RecyclerViewAdapter mRecylerViewAdapter) {
        final List<ScoresRank> scoreList = new ArrayList<ScoresRank>();
        Query query;
        ValueEventListener eventListener;
        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

             query = myRef.child("users").orderByChild("scores/" + level);
        query.addListenerForSingleValueEvent(eventListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.i("Snapshot", "onDataChange: "+dataSnapshot.toString());
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {

                    Log.i("Snapshot",snapshot.toString());
                    ScoresRank score=new ScoresRank(snapshot.child("user").getValue().toString(),snapshot.child("scores/"+level).getValue().toString());
                    scoreList.add(score);

                    Log.i("Snapshot",snapshot.child("scores/"+level).getValue().toString());
                    Log.i("Snapshot",snapshot.child("user").getValue().toString());
                    Log.i("ScoreList",scoreList.toString());
                }
                final List<ScoresRank> finalList = new ArrayList<ScoresRank>(scoreList);
                Collections.reverse(finalList);
                mRecylerViewAdapter.ReplaceData(finalList);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        query.removeEventListener(eventListener);

    }

    public static void getSprintScores(final RecyclerViewAdapter mRecylerViewAdapter) {
        final List<ScoresRank> scoreList = new ArrayList<ScoresRank>();
        Query query;
        ValueEventListener eventListener;
        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

        query = myRef.child("users").orderByChild("sprint_mode");
        query.addListenerForSingleValueEvent(eventListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.i("Snapshot", "onDataChange: "+dataSnapshot.toString());
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {

                    Log.i("Snapshot",snapshot.toString());
                    ScoresRank score=new ScoresRank(snapshot.child("user").getValue().toString(),snapshot.child("sprint_mode").getValue().toString());
                    scoreList.add(score);


                }
               final List<ScoresRank> finalList = new ArrayList<ScoresRank>(scoreList);
                Collections.reverse(finalList);
                mRecylerViewAdapter.ReplaceData(finalList);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        query.removeEventListener(eventListener);

    }

}
