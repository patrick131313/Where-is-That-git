package com.patrick.whereisthat.scores;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.patrick.whereisthat.R;

public class ScoresActivity extends AppCompatActivity {

    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        mSectionsPageAdapter=new SectionsPageAdapter(getSupportFragmentManager());

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
        SectionsPageAdapter adapter=new SectionsPageAdapter(getSupportFragmentManager());
       /* adapter.addFragment(new ScoresRVFragment(),"Level1");
        adapter.addFragment(new ScoresRVFragment(),"Level2");
        adapter.addFragment(new ScoresRVFragment(),"Level3");
        adapter.addFragment(new ScoresRVFragment(),"Level4");
        adapter.addFragment(new ScoresRVFragment(),"Level5");
        adapter.addFragment(new ScoresRVFragment(),"Level6");
        adapter.addFragment(new ScoresRVFragment(),"Level7");
        adapter.addFragment(new ScoresRVFragment(),"Level8");
        adapter.addFragment(new ScoresRVFragment(),"Level9");
        adapter.addFragment(new ScoresRVFragment(),"Level10");
        adapter.addFragment(new ScoresRVFragment(),"Level11");
        adapter.addFragment(new ScoresRVFragment(),"Overall");
        adapter.addFragment(new ScoresRVFragment(),"Sprint mode :(");*/
        adapter.addFragment(new ScoreLevel1(),"Level1");
        adapter.addFragment(new ScoreLevel2(),"Level2");
        adapter.addFragment(new ScoreLevel3(),"Level3");
        adapter.addFragment(new ScoreLevel4(),"Level4");
        adapter.addFragment(new ScoreLevel5(),"Level5");
        adapter.addFragment(new ScoreLevel6(),"Level6");
        adapter.addFragment(new ScoreLevel7(),"Level7");
        adapter.addFragment(new ScoreLevel8(),"Level8");
        adapter.addFragment(new ScoreLevel9(),"Level9");
        adapter.addFragment(new ScoreLevel10(),"Level10");
        adapter.addFragment(new ScoreLevel11(),"Level11");
        adapter.addFragment(new ScoreSprint(),"Sprint Mode");
        viewPager.setAdapter(adapter);
    }
}
