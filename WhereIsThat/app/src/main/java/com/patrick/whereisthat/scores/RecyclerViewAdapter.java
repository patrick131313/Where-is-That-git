package com.patrick.whereisthat.scores;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import com.patrick.whereisthat.R;
import com.patrick.whereisthat.data.ScoresRank;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Patrick on 3/2/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<ScoresRank> ranks=new ArrayList<ScoresRank>();
    private int mLastPosition=0;
    private int mLoggedUserPosition=0;
    private int mLoggedUserScore=0;
    private String mLastScore="0";
    private String Username="";
    private ViewHolder firstHolder;
    public RecyclerViewAdapter(List<ScoresRank> ranks)
    {
        this.ranks=ranks;
    }
    public RecyclerViewAdapter(){

    }

    public RecyclerViewAdapter(String Username)
    {
        this.Username=Username;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        Context context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        if(viewType==0) {
            view = inflater.inflate(R.layout.userscore_layout, parent, false);
        }
        else {
            view = inflater.inflate(R.layout.score_item, parent, false);
        }

        ViewHolder viewHolder=new ViewHolder(view);
        if(viewType==0)
            firstHolder=viewHolder;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(position !=0 && mLastPosition!=0 &&  mLastScore.equals(ranks.get(position-1).getScore().toString()))
        {
            holder.mScore.setText(String.valueOf(ranks.get(position-1).getScore().toString()));
            holder.mUser.setText(mLastPosition+"."+ranks.get(position-1).getUser().toString());

            if(ranks.get(position-1).getUser().toString().equals(Username))
            {
                firstHolder.mPosition.setText("Rank:"+String.valueOf(mLastPosition));
                firstHolder.mScore.setText("Score:"+ranks.get(position-1).getScore().toString());
                firstHolder.mUser.setText(Username);
                Log.i("UserCheck", "onBindViewHolder: "+String.valueOf(mLoggedUserPosition));
             // Toast.makeText(this,String.valueOf(mLoggedUserPosition),Toast.LENGTH_LONG).show();
            }
        }
        else {
            if (position != 0) {
                mLastPosition = position;
                mLastScore = ranks.get(position-1).getScore().toString();
                Log.i("Scores", "onBindViewHolder: " + ranks.get(position-1).getScore().toString());
                holder.mScore.setText(ranks.get(position-1).getScore().toString());
                holder.mUser.setText(String.valueOf(position) + "." + ranks.get(position-1).getUser().toString());


                if (ranks.get(position-1).getUser().toString().equals(Username)) {

                    firstHolder.mPosition.setText("Rank:"+String.valueOf(position));
                    firstHolder.mScore.setText("Score:"+ranks.get(position-1).getScore().toString());
                    firstHolder.mUser.setText(Username);
                    Log.i("UserCheck", "onBindViewHolder: " + String.valueOf(mLoggedUserPosition));
                }
            }
        }
    }
    public void ReplaceData(List<ScoresRank> ranks)
    {
        this.ranks=ranks;
        this.notifyDataSetChanged();
     //   mRecylerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        //return 15;
        return ranks.size()+1;
    }

    @Override
    public int getItemViewType(int position) {

        if(position==0)
            return 0;
        else
            return 1;
     //   return super.getItemViewType(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mUser;
        TextView mScore;
        TextView mPosition;

        public ViewHolder(View itemView) {
            super(itemView);
            mUser = itemView.findViewById(R.id.text_view_username);
            mScore = itemView.findViewById(R.id.text_view_score);
            mPosition=itemView.findViewById(R.id.text_view_position);
        }
    }
}
