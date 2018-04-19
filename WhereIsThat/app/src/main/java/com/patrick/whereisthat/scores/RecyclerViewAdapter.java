package com.patrick.whereisthat.scores;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.SearchView;
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
    private String mLastScore="0";
    private String Username="";
    android.support.v7.widget.SearchView searchView;
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
            if(!Username.equals("")) {
                view = inflater.inflate(R.layout.userscore_layout, parent, false);
                searchView = view.findViewById(R.id.search_view_user);
            }
            else
            {
                view = inflater.inflate(R.layout.search_layout, parent, false);
                searchView = view.findViewById(R.id.search_view_user);
            }
        }
        else {
            view = inflater.inflate(R.layout.score_item, parent, false);
        }

        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

      if(position!=0)
      {
          holder.mUser.setText(ranks.get(position-1).getUser().toString());
          holder.mScore.setText(ranks.get(position-1).getScore().toString());
          holder.mPosition.setText(String.valueOf(ranks.get(position-1).getPosition())+" ");
      }
      else
      {
          if(!Username.equals("")) {
              holder.mUser.setText(ranks.get(ranks.size() - 1).getUser().toString());
              holder.mScore.setText("Score:" + ranks.get(ranks.size() - 1).getScore().toString());
              holder.mPosition.setText("Rank:" + String.valueOf(ranks.get(ranks.size() - 1).getPosition()));
          }
      }
    }
    public void ReplaceData(List<ScoresRank> ranks)
    {
        this.ranks=ranks;
        this.notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {

        if(Username.equals(""))
            return ranks.size()+1;
        else
            return ranks.size();
    }

    @Override
    public int getItemViewType(int position) {

        if(position==0)
            return 0;
        else
            return 1;
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
