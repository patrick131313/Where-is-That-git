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

import javax.security.auth.login.LoginException;

/**
 * Created by Patrick on 3/3/2018.
 */

public class GetScores {


   /* public static void getScoresLevel(final String level, final RecyclerViewAdapter mRecylerViewAdapter) {
        final List<ScoresRank> scoreList = new ArrayList<ScoresRank>();
        Query query;
        ValueEventListener eventListener;
        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

        query = myRef.child("users").orderByChild("scores/" + level);
        query.addListenerForSingleValueEvent(eventListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Log.i("Snapshot", "onDataChange: "+dataSnapshot.toString());
                int total = (int) dataSnapshot.getChildrenCount();
                     Log.i("Snapshot", "onDataChange:"+String.valueOf(total));
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {

                    Log.i("Snapshot",snapshot.toString());
                    ScoresRank score=new ScoresRank(snapshot.child("user").getValue().toString(),snapshot.child("scores/"+level).getValue().toString());
                    ///     if(score.getUser().equals("patrick1313"))



                    scoreList.add(score);

                    Log.i("Snapshot",snapshot.child("scores/"+level).getValue().toString());
                    Log.i("Snapshot",snapshot.child("user").getValue().toString());
                    Log.i("ScoreList",scoreList.toString());
                }
                final List<ScoresRank> finalList = new ArrayList<ScoresRank>(scoreList);
                Collections.reverse(finalList);
                int mLastPosition=0;
                String mLastScore="-1";
                int i=0;
                for(ScoresRank rank:finalList)
                {
                 if(mLastPosition!=0 && rank.getScore().equals((mLastScore)))
                    {
                        finalList.get(i).setUser(String.valueOf(mLastPosition)+"."+rank.getUser());
                        i++;
                    }
                    else
                    {
                        Log.i("ForRank", "onDataChange: "+String.valueOf(i));

                        mLastPosition=i+1;
                        mLastScore=rank.getScore();
                        finalList.get(i).setUser(String.valueOf(i+1)+"."+rank.getUser());
                        i++;
                    }

                }

                mRecylerViewAdapter.ReplaceData(finalList);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        query.removeEventListener(eventListener);

    }*/

    public static void getScoresLevel(final String level, final RecyclerViewAdapter mRecylerViewAdapter, final String username) {
        final List<ScoresRank> scoreList = new ArrayList<ScoresRank>();
        Query query;
        ValueEventListener eventListener;
        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

        query = myRef.child("users").orderByChild("scores/" + level);
        query.addListenerForSingleValueEvent(eventListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Log.i("Snapshot", "onDataChange: "+dataSnapshot.toString());
                int total = (int) dataSnapshot.getChildrenCount();
                Log.i("Snapshot", "onDataChange:"+String.valueOf(total));
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {

                    Log.i("Snapshot",snapshot.toString());
                    ScoresRank score=new ScoresRank(snapshot.child("user").getValue().toString(),snapshot.child("scores/"+level).getValue().toString(),0);
                    ///     if(score.getUser().equals("patrick1313"))



                    scoreList.add(score);
                    scoreList.add(score);
                    scoreList.add(score);
                    scoreList.add(score);
                    scoreList.add(score);


                    Log.i("Snapshot",snapshot.child("scores/"+level).getValue().toString());
                    Log.i("Snapshot",snapshot.child("user").getValue().toString());
                    Log.i("ScoreList",scoreList.toString());
                }
                final List<ScoresRank> finalList = new ArrayList<ScoresRank>(scoreList);
                Collections.reverse(finalList);
                int mLastPosition=0;
                String mLastScore="-1";
                int i=0;
                int userLogged=finalList.size();
                ScoresRank scoresRank=new ScoresRank("0","0",0);
                boolean found=false;
                Log.i("Adaugat", "onDataChange: "+username);
                for(ScoresRank rank:finalList)
                {
                    if(mLastPosition!=0 && rank.getScore().equals((mLastScore)))
                    {
                        if(username.equals(rank.getUser()) && username!="") {
                            found=true;
                            scoresRank=new ScoresRank(rank.getUser(),rank.getScore(),mLastPosition);
                      //      finalList.add(rank);
                          Log.i("Adaugat", "onDataChange: "+username);
                        }
                       // finalList.get(i).setUser(String.valueOf(mLastPosition)+"."+rank.getUser());
                        finalList.get(i).setPosition(mLastPosition);
                        i++;

                    }
                    else
                    {
                        Log.i("ForRank", "onDataChange: "+String.valueOf(i));

                        if(username.equals(rank.getUser()) && username!="") {
                          //  finalList.add(rank);
                            found=true;
                            scoresRank=new ScoresRank(rank.getUser(),rank.getScore(),i+1);
                            Log.i("Adaugat", "onDataChange: "+username);
                        }
                        mLastPosition=i+1;
                        mLastScore=rank.getScore();
                       // finalList.get(i).setUser(String.valueOf(i+1)+"."+rank.getUser());
                        finalList.get(i).setPosition(i+1);
                        i++;

                    }

                }
                if(found) {
                    finalList.add(scoresRank);
                }
                mRecylerViewAdapter.ReplaceData(finalList);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        query.removeEventListener(eventListener);

    }

    public static void getSprintScores(final RecyclerViewAdapter mRecylerViewAdapter,final String username) {
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
                    ScoresRank score=new ScoresRank(snapshot.child("user").getValue().toString(),snapshot.child("sprint_mode").getValue().toString(),0);
                    scoreList.add(score);


                }
               final List<ScoresRank> finalList = new ArrayList<ScoresRank>(scoreList);
                Collections.reverse(finalList);
                int mLastPosition=0;
                String mLastScore="-1";
                int i=0;
                int userLogged=finalList.size();
                ScoresRank scoresRank=new ScoresRank("0","0",0);
                boolean found=false;
                for(ScoresRank rank:finalList)
                {
                    if(mLastPosition!=0 && rank.getScore().equals((mLastScore)))
                    {
                        if(username.equals(rank.getUser()) && username!="") {
                            found=true;
                            scoresRank=new ScoresRank(rank.getUser(),rank.getScore(),mLastPosition);
                            //      finalList.add(rank);
                            Log.i("Adaugat", "onDataChange: "+username);
                        }
                       // finalList.get(i).setUser(String.valueOf(mLastPosition)+"."+rank.getUser());
                        finalList.get(i).setPosition(mLastPosition);
                        i++;
                    }
                    else
                    {
                        Log.i("ForRank", "onDataChange: "+String.valueOf(i));

                        if(username.equals(rank.getUser()) && username!="") {
                            //  finalList.add(rank);
                            found=true;
                            scoresRank=new ScoresRank(rank.getUser(),rank.getScore(),i+1);
                            Log.i("Adaugat", "onDataChange: "+username);
                        }
                        mLastPosition=i+1;
                        mLastScore=rank.getScore();
                      //  finalList.get(i).setUser(String.valueOf(i+1)+"."+rank.getUser());
                        finalList.get(i).setPosition(i+1);
                        i++;
                    }

                }
                if(found) {
                    finalList.add(scoresRank);
                }
                mRecylerViewAdapter.ReplaceData(finalList);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        query.removeEventListener(eventListener);

    }

}
