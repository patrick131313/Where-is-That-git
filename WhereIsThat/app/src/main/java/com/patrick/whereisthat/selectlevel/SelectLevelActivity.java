package com.patrick.whereisthat.selectlevel;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.patrick.whereisthat.level.LevelActivity;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectLevelActivity extends AppCompatActivity {



    private RecyclerViewAdapter mAdapter;
    private RecyclerView mRecyclerView;
    Map<String,Long> mHighscores=new HashMap<String, Long>();
    public static final String EXTRA_LEVEL_KEY="LEVEL_KEY";
    public static final String EXTRA_HIGHSCORE_KEY="HIGHSCORE_KEY";
    public static final String EXTRA_OVERALL_KEY="OVERALL_KEY";
    private int mLastPlayed;
    private long prevLevel;
    private boolean empty;
    private boolean checked;
    private boolean mInternet;

    private String []mArrayLevels={"Level 1","Level 2","Level 3","Level 4","Level 5","Level 6",
            "Level 7","Level 8","Level 9","Level 10","Level 11"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getScores();
        Log.d("Map",mHighscores.toString());
        setContentView(R.layout.activity_select_level);
        android.support.v7.app.ActionBar actionBar=this.getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mRecyclerView=findViewById(R.id.rw_select_level);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this,layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        mAdapter=new RecyclerViewAdapter(this,mArrayLevels,mHighscores);
        mRecyclerView.setAdapter(mAdapter);
        Log.d("RecyclerView", "created");


    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {


        private String[] mArrayLevels;
        private int lastLevelCompleted;
        private Map<String,Long> mHigscores;
        private Context mContext;
        public RecyclerViewAdapter(Context mContext,String []mArrayLevels, Map<String,Long> mHighscores)
        {
            this.mHigscores=mHighscores;
            this.mArrayLevels=mArrayLevels;

            this.mContext=mContext;
            Log.d("RecyclerView", "created");
        }
        public void ReplaceData(Map<String,Long> mHighgscores)
        {
            this.mHigscores=mHighscores;
            mLastPlayed=checkLastLevel();
            mAdapter.notifyDataSetChanged();
        }

        private int checkLastLevel() {

            final int score=(int) (long)mHighscores.get("level"+String.valueOf(1));

            for(int i=1;i<mHighscores.size();i++)
                if((int) (long)mHighscores.get("level"+String.valueOf(i))==0)
                    return i-1;
                return mHighscores.size()-1;
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
            int level=position+1;
            Log.i("Imagechanged",  String.valueOf(position));

            holder.mImageView.setImageResource(getResources().getIdentifier("ic_"+String.valueOf(level), "drawable", getPackageName()));
            holder.mLevel.setText(mArrayLevels[position]);
            if(mHigscores.isEmpty())
            {

                holder.mHighscore.setText("Higscore");
            }
            else
            {
                holder.mProgress.setVisibility(View.INVISIBLE);
                final Object score=mHighscores.get("level"+String.valueOf(position+1));
                holder.mHighscore.setText("Higscore:"+score.toString());
                holder.mLevelItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checked=false;
                        new CheckInternetTask().execute();
                        while (!checked)
                        {
                            Log.i("Checcked", String.valueOf(checked));
                        }
                        Log.i("Checcked", String.valueOf(checked));
                        if(mInternet) {
                            if (position <= mLastPlayed) {
                                Intent intent = new Intent(getApplicationContext(), LevelActivity.class);
                                intent.putExtra(EXTRA_LEVEL_KEY, String.valueOf(position + 1));
                                intent.putExtra(EXTRA_HIGHSCORE_KEY, score.toString());
                                intent.putExtra(EXTRA_OVERALL_KEY, mHighscores.get("overall").toString());
                                startActivity(intent);
                            } else {
                                    Toast.makeText(getApplicationContext(), "You can't play this level now,complete level " + String.valueOf(mLastPlayed+1)
                                            , Toast.LENGTH_LONG).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Check your internet connection",Toast.LENGTH_LONG).show();
                        }
                    }

                });
            }

        }

        @Override
        public int getItemCount() {
            return 11;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private ImageView mImageView;
            private TextView mLevel;
            private TextView mHighscore;
            private RelativeLayout mLevelItem;
            private ProgressBar mProgress;

            private ViewHolder(View itemView) {
                super(itemView);
                Log.d("RecyclerView", "view holder");

                mProgress=itemView.findViewById(R.id.level_progress);
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
                mAdapter.ReplaceData(mHighscores);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            Log.i("CheckConnection", ipAddr.toString());
            return !ipAddr.equals("");

        } catch (Exception e) {
            Log.i("CheckConnection", e.toString());
            return false;
        }
    }

    class CheckInternetTask extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {
           mInternet=isInternetAvailable();
           checked=true;
           return null;
        }


    }

}
