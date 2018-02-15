package com.patrick.whereisthat.level;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.patrick.whereisthat.R;
import com.patrick.whereisthat.levelsDB.Level;
import com.patrick.whereisthat.levelsDB.LevelDao;
import com.patrick.whereisthat.levelsDB.LevelDatabase;
import com.patrick.whereisthat.levelsDB.Values;
import com.patrick.whereisthat.selectlevel.SelectLevelActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LevelActivity extends AppCompatActivity implements  OnMapReadyCallback,GoogleMap.OnMapClickListener {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;
    private GoogleMap mMap;
    private GoogleMap mDmmyMap;
    private TextView mTime;
    long startTime = 0L;
    long timeInMilliseconds=0L;
    long timeSwapBuff=0L;
    long updateTime=0L;
    Handler customHandler = new Handler();
    String TAG="LevelActivity";
    private List<Level> levelList = new ArrayList<>();
    private LevelDao levelDao;
    String mLevel;
    private ImageView mImageView;
    private Button mConfirm;
    int mCurrent=0;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 1;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 1;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_level);
        mImageView=findViewById(R.id.image_view_db);
        mConfirm=findViewById(R.id.button_confirm);


        mLevel=getIntent().getStringExtra(SelectLevelActivity.EXTRA_LEVEL_KEY);
        new DataTask().execute();

        //
        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.map);
        mTime=findViewById(R.id.text_view_timer);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync((OnMapReadyCallback) this);
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ImageTask().execute();

                /*Glide.with(getApplicationContext())
                        .load(getResources().getIdentifier(levelList.get(mCurrent).getPhoto(), "drawable", getPackageName()))
                        .into(mImageView);
                mCurrent++;*/
            }
        });



    }

    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setOnMapClickListener(this);
      /* LatLng home = new LatLng(48.139699, 15.943359);
        CameraPosition cp = CameraPosition.builder()
                .target(home)
                .zoom(3)
                .bearing(0)
                .tilt(0) //sau tilt 45
                .build();*/
        //mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cp), 5000, null);
        //     mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cp), 1, null);
      /*  mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this,R.raw.map_test));
        LatLngBounds Europe = new LatLngBounds(  new LatLng(37, -30), new LatLng(71, 50.5));
        mMap.setMinZoomPreference(4.0f);
        mMap.setMaxZoomPreference(7.0f);
        mMap.setLatLngBoundsForCameraTarget(Europe);*/
      new MapTask().execute();



    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void hide() {

        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    @Override
    public void onMapClick(LatLng latLng) {

        mMap.clear();
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latLng.latitude, latLng.longitude))
                .title("Problem"));




    }
    Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updateTime = timeSwapBuff+timeInMilliseconds;
            int secs = (int)(updateTime/1000);
            int mins = secs/60;
            secs%=60;
            int miliseconds = (int)(updateTime%1000);
            miliseconds=miliseconds/10;
            mTime.setText(""+mins+":"+String.format("%02d",secs)+":"
                    +String.format("%02d",miliseconds));
            customHandler.postDelayed(this,0);
        }
    };
    public void StartTimer()
    {
        timeSwapBuff=0;
        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread,0);
    }
    public void StopTimer()
    {
        timeSwapBuff+=timeInMilliseconds;
        customHandler.removeCallbacks(updateTimerThread);
    }
    public void ContinueTimer()
    {
        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread,0);
    }


    public class DataTask extends AsyncTask<Void, Void, List<Level>> {

        @Override
        protected List<Level> doInBackground(Void... voids) {
            List<Level> LevelList=new ArrayList<>();
            LevelDatabase db = Room.databaseBuilder(getApplicationContext(), LevelDatabase.class, "levels")
                    .allowMainThreadQueries()
                    .build();
            levelDao = db.levelDao();
            // levelDao.deleteAll();
            if (db.levelDao().lines() == 0) {
                Values values = new Values();
                levelList = values.getValues();
                Log.i(TAG, "onCreate: db: " + levelList.toString());
                Log.i(TAG, "onCreate: db: " + values.getValues().toString());
                levelDao.insertAll(levelList);

            }
            switch (mLevel) {
                case "1":
                    LevelList= db.levelDao().getLevel1();
                    break;
                case "2":
                    LevelList = db.levelDao().getLevel2();
                    break;
                case "3":
                    LevelList = db.levelDao().getLevel3();
                    break;
                case "4":
                    LevelList = db.levelDao().getLevel4();
                    break;
                case "5":
                    LevelList = db.levelDao().getLevel5();
                    break;
                case "6":
                    LevelList = db.levelDao().getLevel6();
                    break;
                case "7":
                    LevelList = db.levelDao().getLevel7();
                    break;
                case "8":
                    LevelList = db.levelDao().getLevel8();
                    break;
                case "9":
                    LevelList = db.levelDao().getLevel9();
                    break;
                case "10":
                    LevelList = db.levelDao().getLevel10();
                    break;
                case "11":
                    LevelList = db.levelDao().getLevel11();
                    break;
            }
            return LevelList;
        }

        @Override
        protected void onPostExecute(List<Level> levels) {
            super.onPostExecute(levels);
            Log.i(TAG, "onCreate: db: " +"test");

            levelList = levels;
            Log.i(TAG,String.valueOf(levelDao.lines()));
            for (int i = 0; i < 10; i++) {
                Log.i(TAG, "onCreate: db: " + levelList.get(i).getPhoto().toString());


            }
            new ImageTask().execute();
        }
    }


    public class ImageTask extends AsyncTask<Void, Void,Integer> {
        @Override
        protected Integer doInBackground(Void... voids) {
            if(mCurrent!=10)
            return getResources().getIdentifier(levelList.get(mCurrent).getPhoto(), "drawable", getPackageName());
            else
                return -1;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            StartTimer();
            if(integer==-1) {
                mImageView.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(),"Level completed",Toast.LENGTH_LONG).show();
                mCurrent=0;
            }
            else {
                Glide.with(getApplicationContext())
                        .load(integer)
                        .into(mImageView);
                mImageView.setVisibility(View.VISIBLE);
                mCurrent++;
                Log.i(TAG, "onPostExecute: " + String.valueOf(mCurrent));
            }
        }
    }

    public class MapTask extends AsyncTask<Void, Void, Integer>
    {


        @Override
        protected Integer doInBackground(Void... voids) {


            return 1;

        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            mMap.getUiSettings().setMapToolbarEnabled(false);
            mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getApplicationContext(), R.raw.map_test));
            LatLngBounds Europe = new LatLngBounds(new LatLng(37, -30), new LatLng(71, 50.5));
            mMap.setMinZoomPreference(4.0f);
            mMap.setMaxZoomPreference(7.0f);
            mMap.setLatLngBoundsForCameraTarget(Europe);
        }
    }
}


