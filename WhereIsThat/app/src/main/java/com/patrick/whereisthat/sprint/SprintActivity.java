package com.patrick.whereisthat.sprint;

import android.annotation.SuppressLint;
import android.app.LauncherActivity;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.patrick.whereisthat.R;
import com.patrick.whereisthat.databinding.ActivitySprintBinding;
import com.patrick.whereisthat.dialog.Dialog;
import com.patrick.whereisthat.sprintDB.Cities;
import com.patrick.whereisthat.sprintDB.Sprint;
import com.patrick.whereisthat.sprintDB.SprintDao;
import com.patrick.whereisthat.sprintDB.SprintDatabase;
import com.patrick.whereisthat.sprintDB.SprintValues;

import java.util.ArrayList;
import java.util.List;


public class SprintActivity extends AppCompatActivity implements OnMapReadyCallback, MapboxMap.OnMapClickListener{
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private List<Sprint> sprintList = new ArrayList<Sprint>();
    private SprintDao sprintDao;
    private static final boolean AUTO_HIDE = true;
    private int counter=0;
    ActivitySprintBinding mBinding;
    private long mTimeRemaining;
    private long mTime=120000;
    public CountDownTimer mCountDownTimer;
    private long mScore=0;
    private LatLng mLatLng;
    private SharedPreferences sharedPreferences;
    private long mScoreRound=0;
    private boolean isFinished=false;
    private MaterialDialog dialogCity;
    private boolean clickedOne=false;
    private boolean firstTime=true;
    private MarkerOptions mMarker;
    private MapView mapView;
    private MapboxMap mMapBoxMap;
    private Icon mMarkerIcon;



    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private final Handler mHideHandler = new Handler();
    private View mContentView;

    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
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
        Mapbox.getInstance(this, "pk.eyJ1IjoicGF0cmljazEzMTMiLCJhIjoiY2pvNWppOWk1MGJkcDN3cW00a2tjOTVuMCJ9.4HjyRn4wmuFepe1lX5Ojvw");

        mBinding= DataBindingUtil.setContentView(this,R.layout.activity_sprint);

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.mapView);
        new SprintTask().execute();
      //  new MapTask().execute();

        mBinding.textViewCountdown.setText("2:00:00");
        StartTimer();
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
        if(mapView!=null)
        {
            mapView.onDestroy();
        }
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
   //     mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

   public float getDistance(com.mapbox.mapboxsdk.geometry.LatLng LatLng1, com.mapbox.mapboxsdk.geometry.LatLng LatLng2) {

       double distance = 0;
       Location locationA = new Location("A");
       locationA.setLatitude(LatLng1.getLatitude());
       locationA.setLongitude(LatLng1.getLongitude());
       Location locationB = new Location("B");
       locationB.setLatitude(LatLng2.getLatitude());
       locationB.setLongitude(LatLng2.getLongitude());
       distance = locationA.distanceTo(locationB) * 100;
       int km = ((int) distance) / 1000;
       float kmtot = km;
       return kmtot / 100;
   }
    public void score(com.mapbox.mapboxsdk.geometry.LatLng latLng1, com.mapbox.mapboxsdk.geometry.LatLng latLng2)
    {
        float distance = getDistance(latLng1, latLng2);
        long Score=5000-(long) distance*2;
        mScoreRound=Score;
        mScore=mScore+Score;

    }

    @Override
    public void onMapClick(@NonNull com.mapbox.mapboxsdk.geometry.LatLng latLng) {

     /*   IconFactory iconFactory = IconFactory.getInstance(this);
        Icon icon = iconFactory.fromResource(R.drawable.red_flag);*/
        mMapBoxMap.removeAnnotations();
        mMarker=new MarkerOptions()
                .icon(mMarkerIcon)
                .position(new com.mapbox.mapboxsdk.geometry.LatLng(latLng.getLatitude(), latLng.getLongitude()));


        if(counter!=21 || !isFinished) {
          mMapBoxMap.addMarker(mMarker);
            if (mBinding.buttonConfirmSprint.getVisibility() == View.INVISIBLE) {
                mBinding.buttonConfirmSprint.setVisibility(View.VISIBLE);
                clickedOne=false;
            }
            mLatLng = latLng;
        }
    }

    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        new MapTask().execute();
        LatLngBounds latLngBounds = new com.mapbox.mapboxsdk.geometry.LatLngBounds.Builder()
                .include(new com.mapbox.mapboxsdk.geometry.LatLng(37, -30))
                .include(new com.mapbox.mapboxsdk.geometry.LatLng(71, 50.5))
                .build();

        mMapBoxMap = mapboxMap;

        mMapBoxMap.setStyleUrl("mapbox://styles/patrick1313/cjo8k1p3u1ehk2spiuq3xhmky");
        mMapBoxMap.addOnMapClickListener(this);
        mMapBoxMap.getUiSettings().setCompassEnabled(false);
        mMapBoxMap.getUiSettings().setRotateGesturesEnabled(false);
        mMapBoxMap.setLatLngBoundsForCameraTarget(latLngBounds);
        mMapBoxMap.setMaxZoomPreference(7);
        mMapBoxMap.setMinZoomPreference(3);
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
            sprintDao.deleteAll();
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

    public class MapTask extends AsyncTask<Void, Void, Integer>
    {

        @Override
        protected Integer doInBackground(Void... voids) {
            return 1;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            mMapBoxMap.getUiSettings().setCompassEnabled(false);
            mMapBoxMap.getUiSettings().setRotateGesturesEnabled(false);
            mMapBoxMap.setMaxZoomPreference(7);
            mMapBoxMap.setMinZoomPreference(3);
            String map=sharedPreferences.getString("MapPref","1");

            String marker=sharedPreferences.getString("MarkerPref","4");
            switch (marker)
            {
                case "1":
                    mMarkerIcon=(IconFactory.getInstance(getApplicationContext()).fromResource(R.drawable.ic_marker_green));
                    break;
                case "2":
                    mMarkerIcon=(IconFactory.getInstance(getApplicationContext()).fromResource(R.drawable.ic_marker_magenta));
                    break;
                case "3":
                    mMarkerIcon=(IconFactory.getInstance(getApplicationContext()).fromResource(R.drawable.ic_marker_orange));
                    break;
                case "4":
                    mMarkerIcon=(IconFactory.getInstance(getApplicationContext()).fromResource(R.drawable.ic_marker_red));
                    break;
                case "5":
                    mMarkerIcon=(IconFactory.getInstance(getApplicationContext()).fromResource(R.drawable.ic_marker_blue));
                    break;
                case "6":
                    mMarkerIcon=(IconFactory.getInstance(getApplicationContext()).fromResource(R.drawable.ic_marker_yellow));
                    break;
                default:break;
            }
            switch (map)
            {
                case "1":
                 /*   mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getApplicationContext(), R.raw.map_dark));
                    mBinding.buttonConfirm.setImageDrawable(getDrawable(R.drawable.ic_check_white));
                    mBinding.textViewScore.setTextColor(getColor(R.color.colorWhite));
                    mBinding.textViewTimer.setTextColor(getColor(R.color.colorWhite));
                    mBinding.textViewWhere.setTextColor(getColor(R.color.colorWhite));*/
                    mMapBoxMap.setStyleUrl("mapbox://styles/patrick1313/cjo8jpszu0ras2sl5v1y870g9");
                    break;
                case "2":
                    //  mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getApplicationContext(), R.raw.map_orange));
                    mMapBoxMap.setStyleUrl("mapbox://styles/patrick1313/cjo5rti5u0c182snzl828eeth");
                    break;
                case "3":
                    //   mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getApplicationContext(), R.raw.map_retro));
                    mMapBoxMap.setStyleUrl("mapbox://styles/patrick1313/cjo8k1p3u1ehk2spiuq3xhmky");
                    break;
                case "4":
                    //mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getApplicationContext(), R.raw.map_yellow));
                    mMapBoxMap.setStyleUrl("mapbox://styles/patrick1313/cjo8k5r2n2xna2snzzjkg3rvi");
                    break;
                default:
                    break;

            }
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
                mMapBoxMap.removeAnnotations();
                mBinding.buttonConfirmSprint.setVisibility(View.INVISIBLE);
                mBinding.textViewSprint.setVisibility(View.INVISIBLE);
                mBinding.closeCitySprint.setVisibility(View.INVISIBLE);
                mBinding.twRound.setText("Round: 20/20");
            }
            else {
                   mMapBoxMap.removeAnnotations();
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
            com.mapbox.mapboxsdk.geometry.LatLng dbLatLng=new com.mapbox.mapboxsdk.geometry.LatLng(Double.parseDouble(sprintList.get(counter).getLatitude()),Double.parseDouble(sprintList.get(counter).getLongitude()));

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