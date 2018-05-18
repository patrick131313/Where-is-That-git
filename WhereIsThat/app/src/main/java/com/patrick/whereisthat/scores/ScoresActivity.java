package com.patrick.whereisthat.scores;

import android.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.patrick.whereisthat.R;
import com.patrick.whereisthat.StartActivity;

public class ScoresActivity extends AppCompatActivity {

    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    private String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSectionsPageAdapter=new SectionsPageAdapter(getSupportFragmentManager());
        user=getIntent().getStringExtra(StartActivity.EXTRA_USERNAME);
        //Toast.makeText(getApplicationContext(),user,Toast.LENGTH_LONG).show();
        mViewPager=findViewById(R.id.view_pager_scores);

        setupViewPager(mViewPager);

        TabLayout tabLayout=findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
      //  tabLayout.computeScroll();
      //  tabLayout.isNestedScrollingEnabled(true)
    }

    private void setupViewPager(ViewPager viewPager)
    {
        Bundle bundle = new Bundle();
        bundle.putString("Username",user);
        ScoreLevel1 level1=new ScoreLevel1();
        level1.setArguments(bundle);
        SectionsPageAdapter adapter=new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(level1,"Level1");

        ScoreLevel2 level2=new ScoreLevel2();
        level2.setArguments(bundle);
        adapter.addFragment(level2,"Level2");

        ScoreLevel3 level3=new ScoreLevel3();
        level3.setArguments(bundle);
        adapter.addFragment(level3,"Level3");

        ScoreLevel4 level4=new ScoreLevel4();
        level4.setArguments(bundle);
        adapter.addFragment(level4,"Level4");

        ScoreLevel5 level5=new ScoreLevel5();
        level5.setArguments(bundle);
        adapter.addFragment(level5,"Level5");

        ScoreLevel6 level6=new ScoreLevel6();
        level6.setArguments(bundle);
        adapter.addFragment(level6,"Level6");

        ScoreLevel7 level7=new ScoreLevel7();
        level7.setArguments(bundle);
        adapter.addFragment(level7,"Level7");

        ScoreLevel8 level8=new ScoreLevel8();
        level8.setArguments(bundle);
        adapter.addFragment(level8,"Level8");

        ScoreLevel9 level9=new ScoreLevel9();
        level9.setArguments(bundle);
        adapter.addFragment(level9,"Level9");

        ScoreLevel10 level10=new ScoreLevel10();
        level10.setArguments(bundle);
        adapter.addFragment(level10,"Level10");

        ScoreLevel11 level11=new ScoreLevel11();
        level11.setArguments(bundle);
        adapter.addFragment(level11,"Level11");

        Overall overall=new Overall();
        overall.setArguments(bundle);
        adapter.addFragment(overall,"Overall");

        ScoreSprint scoreSprint=new ScoreSprint();
        scoreSprint.setArguments(bundle);
        adapter.addFragment(scoreSprint,"Sprint mode");

       /* adapter.addFragment(new ScoreLevel3(),"Level3");
        adapter.addFragment(new ScoreLevel4(),"Level4");
        adapter.addFragment(new ScoreLevel5(),"Level5");
        adapter.addFragment(new ScoreLevel6(),"Level6");
        adapter.addFragment(new ScoreLevel7(),"Level7");
        adapter.addFragment(new ScoreLevel8(),"Level8");
        adapter.addFragment(new ScoreLevel9(),"Level9");
        adapter.addFragment(new ScoreLevel10(),"Level10");
        adapter.addFragment(new ScoreLevel11(),"Level11");
        adapter.addFragment(new Overall(),"Overall");
        adapter.addFragment(new ScoreSprint(),"Sprint Mode");*/
        viewPager.setAdapter(adapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
