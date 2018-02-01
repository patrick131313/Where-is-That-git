package com.patrick.whereisthat.selectlevel;

import android.app.ActionBar;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.patrick.whereisthat.R;
import com.patrick.whereisthat.data.FirebaseScores;
import com.patrick.whereisthat.data.Scores;

import java.util.ArrayList;
import java.util.List;

public class SelectLevelActivity extends AppCompatActivity {



    private RecyclerViewAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private FirebaseAuth mFirebaseAuth;
    private List<Scores> mList=new ArrayList<>();
    private ArrayList<String> mHigscores=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        int i=1;
        while(i!=12)
        {
            Scores mScores=new Scores("Level "+String.valueOf(i),String.valueOf(i));
            mList.add(mScores);
            Log.d("Scores",mScores.toString());
            i++;
        }
        getScores();





        setContentView(R.layout.activity_select_level);
        mRecyclerView=findViewById(R.id.rw_select_level);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter=new RecyclerViewAdapter(this,mList);
        mRecyclerView.setAdapter(mAdapter);
        Log.d("RecyclerView", "created");

        Log.d("List",mList.toString());



    }

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

        private List<Scores> mList;
        //private int mNumber;
        private Context mContext;
        public RecyclerViewAdapter(Context mContext,List<Scores> mList)
        {
        //    this.mNumber=mNumber;
            this.mList=mList;
            this.mContext=mContext;
            Log.d("RecyclerView", "created");
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            Log.d("RecyclerView", "inflated");
            Context context=parent.getContext();
            LayoutInflater inflater=LayoutInflater.from(context);
            View view=inflater.inflate(R.layout.level_item,parent,false);
            ViewHolder viewHolder=new ViewHolder(view);
            return viewHolder;


           /* View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.level_item,parent,false);
            Log.d("RecyclerView", "inflated");
            return new ViewHolder(view);*/
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Scores scores=  mList.get(position);
            int level=position+1;
            holder.mLevel.setText(scores.getmLevel());
            holder.mHighscore.setText("Higscore:"+scores.getmHighscore());
            Log.d("RecyclerView", "onBindViewHolder: called.");

        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            ImageView mImageView;
            TextView mLevel;
            TextView mHighscore;

            public ViewHolder(View itemView) {
                super(itemView);
                Log.d("RecyclerView", "view holder");

                mImageView = itemView.findViewById(R.id.image_view_level);
                mLevel = itemView.findViewById(R.id.textView_level);
                mHighscore = itemView.findViewById(R.id.textView_highscore);
            }
        }
    }

    public void getScores()
    {
        String key = FirebaseAuth.getInstance().getUid();
        DatabaseReference myRef= FirebaseDatabase.getInstance().getReference().child("users").child(key).child("scores");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String string="level3";
                 long level2=dataSnapshot.getValue(FirebaseScores.class).getLevel2();
                 Log.d("Score",String.valueOf(level2));
                //String level1=dataSnapshot.

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.d("Key",key);
        Log.d("Query",myRef.toString());

    }
}
