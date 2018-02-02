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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectLevelActivity extends AppCompatActivity {



    private RecyclerViewAdapter mAdapter;
    private RecyclerView mRecyclerView;
    Map<String,Long> mHighscores=new HashMap<String, Long>();
   
    private String []mArrayLevels={"Level1:11","Level2:22","Level3:33","Level4:44","Level5:55","Level6:66",
            "Level7:77","Level8:88","Level9:99","Level10:100","Level11:111"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



            getScores();




        Log.d("Map",mHighscores.toString());


        setContentView(R.layout.activity_select_level);
        mRecyclerView=findViewById(R.id.rw_select_level);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter=new RecyclerViewAdapter(this,mArrayLevels,mHighscores);
        mRecyclerView.setAdapter(mAdapter);
        Log.d("RecyclerView", "created");

     //   Log.d("List",mList.toString());



    }

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

        
        private String[] mArrayLevels;
         private Map<String,Long> mHigscores;
        //private int mNumber;
        private Context mContext;
        public RecyclerViewAdapter(Context mContext,String []mArrayLevels, Map<String,Long> mHighscores)
        {
        //    this.mNumber=mNumber;
            this.mHigscores=mHighscores;
            this.mArrayLevels=mArrayLevels;
          
            this.mContext=mContext;
            Log.d("RecyclerView", "created");
        }
        public void ReplaceData(Map<String,Long> mHighgscores)
        {
            this.mHigscores=mHighscores;
            mAdapter.notifyDataSetChanged();
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            Log.d("RecyclerView", "inflated");
            Context context=parent.getContext();
            LayoutInflater inflater=LayoutInflater.from(context);
            View view=inflater.inflate(R.layout.level_item,parent,false);
            ViewHolder viewHolder=new ViewHolder(view);
            return viewHolder;



        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
       
            //Object score=mHighscores.get("level"+String.valueOf(position+1));
            int level=position+1;
            holder.mLevel.setText(mArrayLevels[position]);
            if(mHigscores.isEmpty())
            {
                holder.mHighscore.setText("Higscore:0");
            }
            else
            {
                Object score=mHighscores.get("level"+String.valueOf(position+1));
                holder.mHighscore.setText("Higscore:"+score.toString());
                holder.mLevelItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(),"level"+String.valueOf(position+1)+" clicked",Toast.LENGTH_SHORT).show();;
                    }
                });
            }

            Log.d("RecyclerView", "onBindViewHolder: called.");

        }

        @Override
        public int getItemCount() {
            //return mList.size();
            return 11;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            ImageView mImageView;
            TextView mLevel;
            TextView mHighscore;
            RelativeLayout mLevelItem;

            public ViewHolder(View itemView) {
                super(itemView);
                Log.d("RecyclerView", "view holder");

                mImageView = itemView.findViewById(R.id.image_view_level);
                mLevel = itemView.findViewById(R.id.textView_level);
                mHighscore = itemView.findViewById(R.id.textView_highscore);
                mLevelItem=itemView.findViewById(R.id.level_item_layout);
            }
        }
    }

    public void getScores()
    {
        String key = FirebaseAuth.getInstance().getUid();
        DatabaseReference myRef= FirebaseDatabase.getInstance().getReference().child("users").child(key);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String string="level3";
               mHighscores=dataSnapshot.getValue(FirebaseScores.class).getScores();
            /*    Log.d("Map",mHighscores.toString());
                Object score=mHighscores.get("level2");
                Log.d("MapLevel3",score.toString());
                score=mHighscores.get("level"+String.valueOf(5));
                Log.d("MapLevel35",score.toString());*/
               mAdapter.ReplaceData(mHighscores);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
      /*  Log.d("Key",key);
        Log.d("Query",myRef.toString());*/

    }

}
