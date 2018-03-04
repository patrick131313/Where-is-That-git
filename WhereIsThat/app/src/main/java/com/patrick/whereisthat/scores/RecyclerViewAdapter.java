package com.patrick.whereisthat.scores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    private String mLastScore="0";

    public RecyclerViewAdapter(List<ScoresRank> ranks)
    {
        this.ranks=ranks;
    }
    public RecyclerViewAdapter(){

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.score_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
     //   holder.mUser.setText(String.valueOf(position));
      //  holder.mScore.setText(String.valueOf(position));
      //  holder.mUser.setText(ranks.get(position).getUser().toString());
       // holder.mScore.setText(ranks.get(position).getScore().toString());
        if(mLastPosition!=0 &&  mLastScore.equals(ranks.get(position).getScore().toString()))
        {
            holder.mScore.setText(String.valueOf(ranks.get(position).getScore().toString()));
            holder.mUser.setText(mLastPosition+"."+ranks.get(position).getUser().toString());
        }
        else
        {
            mLastPosition = ranks.size() - position;
            mLastScore = ranks.get(position).getScore().toString();
            Log.i("Scores", "onBindViewHolder: "+ranks.get(position).getScore().toString());
            holder.mScore.setText(ranks.get(position).getScore().toString());
            holder.mUser.setText(String.valueOf(ranks.size() - position) + "." +ranks.get(position).getUser().toString());
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
        return ranks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mUser;
        TextView mScore;

        public ViewHolder(View itemView) {
            super(itemView);
            mUser = itemView.findViewById(R.id.text_view_username);
            mScore = itemView.findViewById(R.id.text_view_score);
        }
    }
}
