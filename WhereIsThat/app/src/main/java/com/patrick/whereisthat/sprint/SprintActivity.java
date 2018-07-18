package com.patrick.whereisthat.sprint;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.patrick.whereisthat.R;
import com.patrick.whereisthat.StartActivity;
import com.patrick.whereisthat.databinding.ActivityLevelBinding;
import com.patrick.whereisthat.databinding.ActivitySprintBinding;
import com.patrick.whereisthat.dialog.Dialog;
import com.patrick.whereisthat.dialog.DialogRestart;
import com.patrick.whereisthat.sprintDB.Cities;
import com.patrick.whereisthat.sprintDB.Sprint;
import com.patrick.whereisthat.sprintDB.SprintDao;
import com.patrick.whereisthat.sprintDB.SprintDatabase;
import com.patrick.whereisthat.sprintDB.SprintValues;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.prefs.Preferences;

import javax.security.auth.login.LoginException;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SprintActivity extends AppCompatActivity implements OnMapReadyCallback,GoogleMap.OnMapClickListener {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private List<Sprint> sprintList = new ArrayList<Sprint>();
    private SprintDao sprintDao;
    private static final boolean AUTO_HIDE = true;
    private GoogleMap mMap;
    private int nr_cities=0;
    private int counter=0;
    public AsyncTask mTask;
    private List<Cities> mCities=new ArrayList<Cities>();
    List<LatLng> latLngs = new ArrayList<LatLng>();
    boolean isFirst=true;
    ActivitySprintBinding mBinding;
    boolean isReady=false;
    private long mTimeRemaining;
    private long mTime=120000;
    public CountDownTimer mCountDownTimer;
    private String mCurrent="DB";
    private long mScore=0;
    private LatLng mLatLng;
    private boolean onDestroyCalled=false;
    private SharedPreferences sharedPreferences;
    private float mDistance;
    private long mScoreRound=0;
    private boolean isFinished=false;
    private Dialog mDialog;
    private MaterialDialog dialogCity;
    private boolean clickedOne=false;
    private float markerColor;
    private boolean firstTime=true;
    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
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
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding= DataBindingUtil.setContentView(this,R.layout.activity_sprint);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_sprint);
        mapFragment.getMapAsync((OnMapReadyCallback) this);
        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.map_sprint);
        new SprintTask().execute();
        new MapTask().execute();

        mBinding.textViewCountdown.setText("2:00:00");
        StartTimer();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mBinding.closeCitySprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBinding.closeCitySprint.setVisibility(View.INVISIBLE);
                mBinding.textViewSprint.setVisibility(View.INVISIBLE);
            }
        });
        mBinding.buttonConfirmSprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (clickedOne == false) {
                    if (counter == 20 || isFinished) {
                        Toast.makeText(getApplicationContext(), "Sprint completed", Toast.LENGTH_LONG).show();
                        mCountDownTimer.onFinish();
                    } else {
                        new ScoreTask().execute();
                        new NextCityTask().execute();
                    }
                }
                clickedOne=true;
            }
        });


        // Set up the user interaction to manually show or hide the system UI.

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
    }
    public void StartTimer()
    {
        mCountDownTimer=new CountDownTimer(mTime, 1) {

            public void onTick(long millisUntilFinished) {

                int secs = (int)(millisUntilFinished/1000);
                int mins = secs/60;
                secs%=60;
                int miliseconds = (int)(millisUntilFinished%1000);
                miliseconds=miliseconds/10;
                if(mins==0) {
                    mBinding.textViewCountdown.setText("Time: "+ String.format("%02d", secs) + ":"
                            + String.format("%02d", miliseconds));
                }
                else
                {     mBinding.textViewCountdown.setText("Time: " + mins + ":" + String.format("%02d", secs) + ":"
                        + String.format("%02d", miliseconds));

                }
                mTimeRemaining=millisUntilFinished;

            }


            public void onFinish() {

                Log.i("OnFinish", "Game finished");
                Log.i("Score", "On finish"+String.valueOf(mScore));
                CheckScore();
                mBinding.textViewCountdown.setText("Time: 00:00");
                isFinished=true;
                DialogRestart();
            }


        };
    }

    public void DialogRestart()
    {

        if(dialogCity!=null)
        {
            dialogCity.dismiss();
        }
        MaterialDialog dialogRestart=new MaterialDialog.Builder(this)
                .title("Restart")
                .content("Do you want to restart?")
                .backgroundColorRes(R.color.grey)
                .positiveText("Yes")
                .negativeText("No")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finish();
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        mCountDownTimer.cancel();
                        Log.i("OnDestroy", "onDestroy:called ");
                        if(mTask.getStatus()== AsyncTask.Status.RUNNING) {
                            mTask.cancel(false);
                        }
                        finish();
                        Intent intent = new Intent(getApplicationContext(), SprintActivity.class);
                        startActivity(intent);
                    }
                })
                .negativeColor(getResources().getColor(R.color.colorPrimary))
                .positiveColor(getResources().getColor(R.color.colorPrimary))
                .show();
        dialogRestart.setCancelable(false);
    }
    @Override
    protected void onResume() {
        super.onResume();


    }

    public void CheckScore()
    {
        String key = FirebaseAuth.getInstance().getUid();
        Log.i("Score", "CheckScore"+String.valueOf(mScore));
        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("users").child(key).child("sprint_mode");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (firstTime) {
                    Log.i("Score", "OnDataChange" + String.valueOf(mScore));
                    Log.i("CheckScore", "onDataChange record: " + dataSnapshot.getValue().toString());
                    String current_score = dataSnapshot.getValue().toString();
                    if (Long.parseLong(current_score) < mScore) {
                        myRef.setValue(mScore);
                        Toast.makeText(getApplicationContext(), "You've beat your sprint mode record, now your highscore is " + mScore + " points!", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "You've finished sprint mode with " + mScore + " points!", Toast.LENGTH_LONG).show();

                    }
                    firstTime=false;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCountDownTimer.cancel();
        Log.i("OnDestroy", "onDestroy:called ");
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
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
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
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.getUiSettings().setRotateGesturesEnabled(false);
        mMap.setOnMapClickListener(this);
    }
    @Override
    public void onMapClick(LatLng latLng) {


        mMap.clear();
        if(counter!=21 || !isFinished) {
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latLng.latitude, latLng.longitude))
                    .draggable(true)
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(markerColor)));
            if (mBinding.buttonConfirmSprint.getVisibility() == View.INVISIBLE) {
                mBinding.buttonConfirmSprint.setVisibility(View.VISIBLE);
                clickedOne=false;
            }
            mLatLng = latLng;
        }
     
    }

    public float getDistance(LatLng LatLng1, LatLng LatLng2) {
        double distance = 0;
        Location locationA = new Location("A");
        locationA.setLatitude(LatLng1.latitude);
        locationA.setLongitude(LatLng1.longitude);
        Location locationB = new Location("B");
        locationB.setLatitude(LatLng2.latitude);
        locationB.setLongitude(LatLng2.longitude);
        distance = locationA.distanceTo(locationB) * 100;
        int km = ((int) distance) / 1000;
        float kmtot = km;
        mDistance=kmtot/100;
        return kmtot / 100;
    }
    public void score(LatLng latLng1,LatLng latLng2)
    {
        float distance = getDistance(latLng1, latLng2);
        long Score=5000-(long) distance*2;
        mScoreRound=Score;
        mScore=mScore+Score;


    }


    public Boolean ContainsCyrillic(String s) {
        for (int i = 0; i < s.length(); i++)
            if (Character.UnicodeBlock.of(s.charAt(i)).equals(Character.UnicodeBlock.CYRILLIC))
                return true;
        return false;
    }



    public class SprintTask extends AsyncTask <Void,Void,List<Sprint>> //select din baza de date
    {

        @Override
        protected List<Sprint> doInBackground(Void... voids) {
            List<Sprint> SprintList=new ArrayList<>();
            SprintDatabase db = Room.databaseBuilder(getApplicationContext(), SprintDatabase.class, "sprint")
                    .allowMainThreadQueries()
                    .build();
            sprintDao = db.sprintDao();
            if (db.sprintDao().lines() == 0) {
                SprintValues values = new SprintValues();
                sprintList = values.getValues();
                Log.i("aaa", "onCreate: db: " + sprintList.toString());
                Log.i("aaa", "onCreate: db: " + values.getValues().toString());
                sprintDao.insertAll(sprintList);
            }
            SprintList=db.sprintDao().getSprint21();
            return SprintList;
        }

        @Override
        protected void onPostExecute(List<Sprint> sprints) {
            super.onPostExecute(sprints);
            Log.i("aaaa", "onCreate: db: " +"test");

            sprintList = sprints;
            Log.i("aaaa",String.valueOf(sprintDao.lines()));
            for (int i = 0; i < sprintList.size(); i++) {
                Log.i("aaaa", "onCreate: db: " + sprintList.get(i).getCity());
            }
            mBinding.closeCitySprint.setVisibility(View.VISIBLE);
            mCountDownTimer.start();
            DialogCity(sprintList.get(0).getCity());
        }
    }
    public class MapTask extends AsyncTask<Void, Void, Integer> //map style etc
    {

        @Override
        protected Integer doInBackground(Void... voids) {


            return 1;

        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            mMap.getUiSettings().setMapToolbarEnabled(false);
            String map=sharedPreferences.getString("MapPref","4");
            String marker=sharedPreferences.getString("MarkerPref","4");
                Log.i("MarkerSettings", marker);
            switch (marker)
            {
                case "1":
                    markerColor=BitmapDescriptorFactory.HUE_GREEN;
                    break;
                case "2":
                    markerColor=BitmapDescriptorFactory.HUE_MAGENTA;
                    break;
                case "3":
                    markerColor=BitmapDescriptorFactory.HUE_ORANGE;
                    break;
                case "4":
                    markerColor=BitmapDescriptorFactory.HUE_RED;
                    break;
                case "5":
                    markerColor=BitmapDescriptorFactory.HUE_VIOLET;
                    break;
                case "6":
                    markerColor=BitmapDescriptorFactory.HUE_YELLOW;
                    break;
                default:break;
            }
            switch (map)
            {
                case "1":
                    mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getApplicationContext(), R.raw.map_dark));
                    mBinding.buttonConfirmSprint.setImageDrawable(getDrawable(R.drawable.ic_check_white));
                    mBinding.textViewSprintHs.setTextColor(getColor(R.color.colorWhite));
                    mBinding.twRound.setTextColor(getColor(R.color.colorWhite));
                    mBinding.textViewCountdown.setTextColor(getColor(R.color.colorWhite));
                    break;
                case "2":
                    mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getApplicationContext(), R.raw.map_orange));
                    break;
                case "3":
                    mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getApplicationContext(), R.raw.map_retro));
                    break;
                case "4":
                    mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getApplicationContext(), R.raw.map_yellow));
                    break;

                default:break;

            }

            LatLngBounds Europe = new LatLngBounds(new LatLng(37, -30), new LatLng(71, 50.5));
            mMap.setMinZoomPreference(4.0f);
            mMap.setMaxZoomPreference(7.0f);
            mMap.setLatLngBoundsForCameraTarget(Europe);

        }
    }


    public class NextCityTask extends AsyncTask<Void,Void,String> //trecere la orasul urmator
    {

        @Override
        protected String doInBackground(Void... voids) {
            return sprintList.get(counter).getCity();
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("Counter", String.valueOf(counter));
            if(counter==20)
            {
                mCountDownTimer.cancel();
                Log.i("Score", "Before"+String.valueOf(mScore));
                mScore=mScore+mTimeRemaining/10;
                Log.i("Score", "After"+String.valueOf(mScore));
                mBinding.textViewSprintHs.setText("Score: "+String.valueOf(mScore));
                mCountDownTimer.onFinish();
                mMap.clear();
                mBinding.buttonConfirmSprint.setVisibility(View.INVISIBLE);
                mBinding.textViewSprint.setVisibility(View.INVISIBLE);
                mBinding.closeCitySprint.setVisibility(View.INVISIBLE);
                mBinding.twRound.setText("Round: 20/20");
            }
            else {
                    mMap.clear();
                    DialogCity(s);
                    mBinding.twRound.setText("Round: "+String.valueOf(counter + 1) + "/20");
                    mBinding.textViewSprint.setVisibility(View.VISIBLE);
                    mBinding.closeCitySprint.setVisibility(View.VISIBLE);
                    mBinding.buttonConfirmSprint.setVisibility(View.INVISIBLE);

            }
        }
    }
    public class ScoreTask extends AsyncTask<Void,Void,Void>
    {



        @Override
        protected Void doInBackground(Void... voids) {
         LatLng dbLatLng=new LatLng(Double.parseDouble(sprintList.get(counter).getLatitude()),Double.parseDouble(sprintList.get(counter).getLongitude()));
         score(dbLatLng,mLatLng);
         counter++;
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mBinding.textViewSprintHs.setText("Score: "+String.valueOf(mScore));

        }
    }


    public void DialogCity(String s)
    {
        dialogCity=new MaterialDialog.Builder(this)
                .title("Where is "+ s+" ?")
                .backgroundColorRes(R.color.grey)
                .positiveText("Continue")
                .positiveColor(getResources().getColor(R.color.colorPrimary))
                .show();
        dialogCity.setCancelable(false);
    }

}