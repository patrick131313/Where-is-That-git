package com.patrick.whereisthat.scores;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Filter;
import android.widget.Filterable;
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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements Filterable {

    private List<ScoresRank> ranks=new ArrayList<ScoresRank>();
    private List<ScoresRank> mArray=new ArrayList<ScoresRank>();
    private List<ScoresRank> mArrayFiltered=new ArrayList<ScoresRank>();
    private int mLastPosition=0;
    private int mLoggedUserPosition=0;
    private String mLastScore="0";
    private String Username="";
    private SearchView searchView;
    private RecyclerViewAdapter mAdapter;
    private ScoresRank userLogged;
 /*   public RecyclerViewAdapter(List<ScoresRank> ranks)
    {
        this.mArrayFiltered=ranks;
        this.mArray=ranks;
    }*/
    public RecyclerViewAdapter(){

    }

    public RecyclerViewAdapter(String Username)
    {
        this.Username=Username;


    }
    public void TransferAdapter(RecyclerViewAdapter mAdapter)
    {
        this.mAdapter=mAdapter;
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
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    mAdapter.getFilter().filter(s);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                  mAdapter.getFilter().filter(s);
                    return false;
                }
            });
        }
        else {
            view = inflater.inflate(R.layout.score_item, parent, false);
        }

        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(position!=0) {
           if (mArrayFiltered.size() != 0) {
                holder.mUser.setText(mArrayFiltered.get(position-1).getUser().toString());
                holder.mScore.setText(mArrayFiltered.get(position-1).getScore().toString());
                holder.mPosition.setText(String.valueOf(mArrayFiltered.get(position-1).getPosition()) );
          }
        }
        else
        {
            if(!Username.equals("") &&mArray.size()>0) {
                holder.mUser.setText(mArray.get(mArray.size() - 1).getUser().toString());
                holder.mScore.setText("Score:" + mArray.get(mArray.size() - 1).getScore().toString());
                holder.mPosition.setText("Rank:" + String.valueOf(mArray.get(mArray.size() - 1).getPosition()));
          /*      holder.mUser.setText(userLogged.getUser().toString());
                holder.mScore.setText("Score:" + userLogged.getScore().toString());
                holder.mPosition.setText("Rank:" +userLogged.getPosition());*/
            }


        }
    }
    public void ReplaceData(List<ScoresRank> ranks)
    {
        this.mArrayFiltered=ranks;
        this.mArray=ranks;
        Log.i("ArraySize", String.valueOf(mArray.size()));

       /* if(!Username.equals(""))
        {
            userLogged=new ScoresRank(mArray.get(mArray.size()-1).getUser(),mArray.get(mArray.size()-1).getScore(),mArray.get(mArray.size()-1).getPosition());
            mArray.remove(mArray.size()-1);

            mArrayFiltered.remove((mArrayFiltered.size())-1);


        }
        Log.i("ArraySize", String.valueOf(mArray.size()));*/
        this.notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {

       if(Username.equals(""))
            return mArrayFiltered.size()+1;
        else {

            if(mArrayFiltered.size()==0)
                return 1;
            else {
                if (mArrayFiltered.size() != 0 && mArrayFiltered.size() != mArray.size()) {
                    if(mArrayFiltered.size()==2 && mArrayFiltered.get(0).getUser().equals( mArrayFiltered.get(1).getUser()))
                        return 2;
                    return mArrayFiltered.size() + 1;
                } else {
                    return mArrayFiltered.size();
                }
            }
        }




    }


    @Override
    public int getItemViewType(int position) {

        if(position==0)
            return 0;
        else
            return 1;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mArrayFiltered = mArray;
                } else {
                    List<ScoresRank> arrayList = new ArrayList<ScoresRank>();
                    for (ScoresRank string : mArray) {
                        if (string.getUser().toLowerCase().contains(charString.toLowerCase()) || string.getUser().contains(charSequence)) {
                            arrayList.add(string);
                        }
                    }
     /*               if (!Username.equals("")) {
                        Log.i("FilterUser", Username.toString());
                        arrayList.add(new ScoresRank("1", "11", 0));
                        if (arrayList.get(arrayList.size() - 2).getUser().equals(Username))
                            arrayList.remove(arrayList.size() - 2);*/
                        mArrayFiltered = arrayList;
                 //   }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mArrayFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mArrayFiltered = (List<ScoresRank>) filterResults.values;
               for (ScoresRank string : mArrayFiltered) {
                    Log.i("Filter", string.getUser());


                }
                Log.i("Filter", "----------");


             /*   if(!Username.equals(""))
                {
                    mArrayFiltered.remove(mArrayFiltered.size()-1);
                }*/
                notifyDataSetChanged();
            }
        };
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
