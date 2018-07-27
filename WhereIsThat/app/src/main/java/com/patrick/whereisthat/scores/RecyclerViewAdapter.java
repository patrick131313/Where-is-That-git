package com.patrick.whereisthat.scores;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ProgressBar;
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
    private Context mContext;
    private ProgressBar bar;
    private View mView=null;
    public RecyclerViewAdapter(){

    }

    public RecyclerViewAdapter(String Username,Context mContext)
    {
        this.Username=Username;
        this.mContext=mContext;
    }

    public void MakeToast()
    {
        Toast.makeText(mContext,"No user found",Toast.LENGTH_LONG).show();
    }
    public void TransferAdapter(RecyclerViewAdapter mAdapter)
    {
        this.mAdapter=mAdapter;
    }
    public void TransferView(View mView)
    {
        this.mView=mView;
        if(this.mView==null)
            Log.i("ViewTest", "Null");
        else
            Log.i("ViewTest", "Not null");
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
                    if(s.equals(""))
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
                holder.mUser.setText(Html.fromHtml("Logged user:"+"<b>"+mArray.get(mArray.size() - 1).getUser().toString()+"</b>"));
                holder.mScore.setText(Html.fromHtml("Points:" + "<b>"+mArray.get(mArray.size() - 1).getScore().toString()+"</b>"));
                holder.mPosition.setText(Html.fromHtml("Rank:" +"<b>"+ String.valueOf(mArray.get(mArray.size() - 1).getPosition())+"</b>"));
            }


        }
    }
    public void ReplaceData(List<ScoresRank> ranks)
    {
        this.mArrayFiltered=ranks;
        this.mArray=ranks;
        Log.i("ArraySize", String.valueOf(mArray.size()));
       // bar.setVisibility(View.INVISIBLE);
        if(mView!=null)
        {
            ProgressBar bar=mView.findViewById(R.id.rw_bar);
            bar.setVisibility(View.INVISIBLE);
        }
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
                        mArrayFiltered = arrayList;
                    if(mArrayFiltered.size()>0) {
                        if (mArrayFiltered.get(mArrayFiltered.size() - 1).getUser().equals(Username) && mArrayFiltered.size() > 1)
                            mArrayFiltered.remove(mArrayFiltered.remove(mArrayFiltered.size() - 1));
                    }
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
               if(mArrayFiltered.size()!=0)
                notifyDataSetChanged();
               else {
                   mArrayFiltered=mArray;
                   notifyDataSetChanged();
                   MakeToast();
               }

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
