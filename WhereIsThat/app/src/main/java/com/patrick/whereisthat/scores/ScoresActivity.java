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
        adapter.addFragment(new ScoresRVFragment(),"Level1");
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
        adapter.addFragment(new ScoresRVFragment(),"Sprint mode :(");
        viewPager.setAdapter(adapter);
    }
}
