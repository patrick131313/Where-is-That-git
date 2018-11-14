package com.patrick.whereisthat.level;

        import android.annotation.SuppressLint;
        import android.arch.persistence.room.Room;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.databinding.DataBindingUtil;
        import android.location.Address;
        import android.location.Geocoder;
        import android.location.Location;
        import android.os.AsyncTask;
        import android.os.SystemClock;
        import android.preference.PreferenceManager;
        import android.support.annotation.NonNull;
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

        import com.afollestad.materialdialogs.DialogAction;
        import com.afollestad.materialdialogs.MaterialDialog;
        import com.bumptech.glide.Glide;
        import com.bumptech.glide.request.RequestOptions;
        import com.google.android.gms.maps.CameraUpdateFactory;
        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.OnMapReadyCallback;
        import com.google.android.gms.maps.SupportMapFragment;
        import com.google.android.gms.maps.model.BitmapDescriptorFactory;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.LatLngBounds;
        import com.google.android.gms.maps.model.MapStyleOptions;
        import com.google.android.gms.maps.model.Marker;
        import com.google.android.gms.maps.model.MarkerOptions;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.mapbox.mapboxsdk.Mapbox;
        import com.mapbox.mapboxsdk.camera.CameraPosition;
        import com.mapbox.mapboxsdk.maps.MapView;
        import com.mapbox.mapboxsdk.maps.MapboxMap;
        import com.patrick.whereisthat.R;
        import com.patrick.whereisthat.databinding.ActivityLevelBinding;
        import com.patrick.whereisthat.dialog.DialogLevel;
        import com.patrick.whereisthat.levelsDB.Level;
        import com.patrick.whereisthat.levelsDB.LevelDao;
        import com.patrick.whereisthat.levelsDB.LevelDatabase;
        import com.patrick.whereisthat.levelsDB.Values;
        import com.patrick.whereisthat.selectlevel.SelectLevelActivity;
        import com.patrick.whereisthat.sprint.SprintActivity;

        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LevelActivity extends AppCompatActivity implements com.mapbox.mapboxsdk.maps.OnMapReadyCallback, MapboxMap.OnMapClickListener{
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;
    private GoogleMap mMap;
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
    ActivityLevelBinding mBinding;
    private com.mapbox.mapboxsdk.geometry.LatLng mLatLng;
    private long mScore=0;
    private long mScoreRound;
    private String mHigscore;
    private String mOverall;
    private boolean hint_pressed=false;
    private float mDistance;
    private SharedPreferences sharedPreferences;
    public boolean isFinished=false;
    private boolean mRecord=false;
    DialogLevel mDialog;
    private boolean clickedOne=false;
    private float markerColor;
    private com.mapbox.mapboxsdk.annotations.MarkerOptions mMarker;
    private boolean scoreChange=false;
    LatLngBounds Europe;
    private MapView mapView;
    private MapboxMap mMapBoxMap;


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
 /*   private final Runnable mHidePart2Runnable = new Runnable() {
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
    };*/
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
        Mapbox.getInstance(this, "pk.eyJ1IjoicGF0cmljazEzMTMiLCJhIjoiY2pvNWppOWk1MGJkcDN3cW00a2tjOTVuMCJ9.4HjyRn4wmuFepe1lX5Ojvw");

        mBinding= DataBindingUtil.setContentView(this,R.layout.activity_level);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mLevel=getIntent().getStringExtra(SelectLevelActivity.EXTRA_LEVEL_KEY);
        mHigscore= getIntent().getStringExtra(SelectLevelActivity.EXTRA_HIGHSCORE_KEY);
        mOverall=getIntent().getStringExtra(SelectLevelActivity.EXTRA_OVERALL_KEY);
        new DataTask().execute();

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
      //  mContentView = findViewById(R.id.map);
        mTime=findViewById(R.id.text_view_timer);

      //  SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
      //  mapFragment.getMapAsync((OnMapReadyCallback) this);

        mBinding.buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFinished && clickedOne==false)
                {
                    //
                    StopTimer();
                   new ScoreTask().execute();
                    //mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(54,10.25)));
                    clickedOne=true;
                }
                else
                {
                      Toast.makeText(getApplicationContext(),"Level completed",Toast.LENGTH_LONG).show();
                      mBinding.buttonConfirm.setVisibility(View.INVISIBLE);
                       mBinding.buttonHint.setVisibility(View.INVISIBLE);
                }
            }
        });

        mBinding.imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBinding.imageViewClose.setVisibility(View.INVISIBLE);
                mBinding.imageViewDb.setVisibility(View.INVISIBLE);
                mBinding.textViewWhere.setVisibility(View.INVISIBLE);

            }
        });

        mBinding.buttonHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
                hint_pressed=true;



            }
        });

        mBinding.imageViewDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }
   public void showDialog()
    {
        MaterialDialog dialogHint=new MaterialDialog.Builder(this)
                .title("Hint")
                .content(mBinding.textViewHint.getText().toString())
                .backgroundColorRes(R.color.grey)
                .positiveText("Got it")
                .positiveColor(getResources().getColor(R.color.colorPrimary))
                .show();
        dialogHint.setCancelable(false);
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
     //   mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
   //     mHideHandler.removeCallbacks(mHidePart2Runnable);
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
        mDistance=kmtot/100;
        return kmtot / 100;
    }
    public void score(com.mapbox.mapboxsdk.geometry.LatLng latLng1, com.mapbox.mapboxsdk.geometry.LatLng latLng2)
    {

        float distance = getDistance(latLng1, latLng2);
        long Score=5000-(long)(updateTime*0.1)-(long) distance*2;
        if(hint_pressed)
            Score=Score-1000;
        if(Score<0)
            Score=0;
        mScoreRound=Score;
        mScore=mScore+Score;

    }
    Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updateTime = timeSwapBuff + timeInMilliseconds;
            int secs = (int) (updateTime / 1000);
            int mins = secs / 60;
            secs %= 60;
            int miliseconds = (int) (updateTime % 1000);
            miliseconds = miliseconds / 10;
            if (mins != 0) {
               mTime.setText("Time: " + mins + ":" + String.format("%02d", secs) + ":"
                        + String.format("%02d", miliseconds));
            }
            else
            {
               mTime.setText("Time: "+ String.format("%02d", secs) + ":"
                       + String.format("%02d", miliseconds));
            }

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
    public void UpdateScore()
    {
        long prevHighscore=Long.parseLong(mHigscore);
        if(mScore>Long.parseLong(mHigscore))
        {
            Long overall=mScore-Long.parseLong(mHigscore)+Long.parseLong(mOverall);
            String level = "level" + mLevel;
            String key = FirebaseAuth.getInstance().getUid();
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("users").child(key)
                    .child("scores");
            myRef.child(level).setValue(mScore);
            myRef.child("overall").setValue(overall);
            mRecord=true;
            scoreChange=true;
        }
        if(prevHighscore==0 && mRecord)
            mRecord=false;

    }

    @Override
    public void onMapReady(MapboxMap mapboxMap) {

        com.mapbox.mapboxsdk.geometry.LatLngBounds latLngBounds = new com.mapbox.mapboxsdk.geometry.LatLngBounds.Builder()
                .include(new com.mapbox.mapboxsdk.geometry.LatLng(37, -30))
                .include(new com.mapbox.mapboxsdk.geometry.LatLng(71, 50.5))
                .build();

        com.mapbox.mapboxsdk.geometry.LatLngBounds AUSTRALIA_BOUNDS = new com.mapbox.mapboxsdk.geometry.LatLngBounds.Builder()
                .include(new com.mapbox.mapboxsdk.geometry.LatLng(-9.136343, 109.372126))
                .include(new com.mapbox.mapboxsdk.geometry.LatLng(-44.640488, 158.590484))
                .build();


        CameraPosition position = new CameraPosition.Builder()
                .target(new com.mapbox.mapboxsdk.geometry.LatLng(51.50550, -0.07520)) // Sets the new camera position
                .zoom(10) // Sets the zoom to level 10
                .tilt(20) // Set the camera tilt to 20 degrees
                .build(); // Builds the CameraPosition object from the builder
        mMapBoxMap = mapboxMap;
        //yellow
        // mMapBoxMap.setStyleUrl("mapbox://styles/patrick1313/cjo5rti5u0c182snzl828eeth");

        //fluorescent
        //  mMapBoxMap.setStyleUrl("mapbox://styles/patrick1313/cjo8k5r2n2xna2snzzjkg3rvi");

        //retro
        //   mMapboxMap.setStyleUrl("mapbox://styles/patrick1313/cjo8jpszu0ras2sl5v1y870g9");

        //orange
        //mapboxMap.setStyleUrl("mapbox://styles/patrick1313/cjo8ju7ib0rev2sl53foajjyg");

        //dark
        mMapBoxMap.setStyleUrl("mapbox://styles/patrick1313/cjo8k1p3u1ehk2spiuq3xhmky");
        mMapBoxMap.addOnMapClickListener(this);
        mMapBoxMap.getUiSettings().setCompassEnabled(false);
        mMapBoxMap.getUiSettings().setRotateGesturesEnabled(false);
        mMapBoxMap.setLatLngBoundsForCameraTarget(latLngBounds);
        mMapBoxMap.setMaxZoomPreference(7);
        mMapBoxMap.setMinZoomPreference(3);
    }

    @Override
    public void onMapClick(@NonNull com.mapbox.mapboxsdk.geometry.LatLng latLng) {

        mMarker=new com.mapbox.mapboxsdk.annotations.MarkerOptions()
                .position(new com.mapbox.mapboxsdk.geometry.LatLng(latLng.getLatitude(), latLng.getLongitude()));
   //    mMapBoxMap.clear();
        if (!isFinished && mBinding.imageViewDb.getVisibility()==View.INVISIBLE) {
            mMapBoxMap.addMarker(mMarker);
            mLatLng = latLng;
            if (mBinding.buttonConfirm.getVisibility() == View.INVISIBLE) {
                mBinding.buttonConfirm.setVisibility(View.VISIBLE);
                clickedOne=false;
            }
        }
        else
        {
            Toast.makeText(this,"You must close the image to put marker on map",Toast.LENGTH_LONG).show();
        }

    }


    public class DataTask extends AsyncTask<Void, Void, List<Level>> {

        @Override
        protected List<Level> doInBackground(Void... voids) {
            List<Level> LevelList=new ArrayList<>();
            LevelDatabase db = Room.databaseBuilder(getApplicationContext(), LevelDatabase.class, "levels")
                    .allowMainThreadQueries()
                    .build();
            levelDao = db.levelDao();
            //levelDao.deleteAll();
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
            if(mCurrent!=10) {
                Log.i("mCurrent", String.valueOf(mCurrent));
                return getResources().getIdentifier(levelList.get(mCurrent).getPhoto(), "drawable", getPackageName());

            }
            else
            {
                Log.i("mCurrent", "Return -1");

                return -1;
            }
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            if(mCurrent==0)
            {
                StartTimer();
            }
            if(integer==-1) {
                mBinding.textViewScore.setText("Score:"+String.valueOf(mScore));
                UpdateScore();
            }
            else {
                if(mMapBoxMap!=null) {
                  //  mMapBoxMap.clear();
                }
                mBinding.textViewScore.setText("Score:"+String.valueOf(mScore));
                hint_pressed=false;
                mBinding.textViewRound.setText("Round:"+(mCurrent+1)+"/10");
                mBinding.imageViewDb.setVisibility(View.VISIBLE);
                mBinding.buttonConfirm.setVisibility(View.INVISIBLE);
                mBinding.textViewHint.setText(levelList.get(mCurrent).getHint());

                Log.i("Resource", String.valueOf(integer));
                Glide.with(getApplicationContext())
                        .load(integer)
                        .apply(new RequestOptions().override(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT))
                        .into(mBinding.imageViewDb);

                mBinding.textViewWhere.setVisibility(View.VISIBLE);
                mBinding.imageViewClose.setVisibility(View.VISIBLE);
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
            String map=sharedPreferences.getString("MapPref","4");
            String marker=sharedPreferences.getString("MarkerPref","4");
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
                    mBinding.buttonConfirm.setImageDrawable(getDrawable(R.drawable.ic_check_white));
                    mBinding.textViewScore.setTextColor(getColor(R.color.colorWhite));
                    mBinding.textViewTimer.setTextColor(getColor(R.color.colorWhite));
                    mBinding.textViewWhere.setTextColor(getColor(R.color.colorWhite));
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
            Europe = new LatLngBounds(new LatLng(37, -30), new LatLng(71, 50.5));
            mMap.setMinZoomPreference(4.0f);
            mMap.setMaxZoomPreference(7.0f);
            mMap.setLatLngBoundsForCameraTarget(Europe);
        }
    }
    public class ScoreTask extends AsyncTask<Void,Void,Void>
    {



        @Override
        protected Void doInBackground(Void... voids) {
            //  getCity();
            com.mapbox.mapboxsdk.geometry.LatLng dbLatLng=new com.mapbox.mapboxsdk.geometry.LatLng(Double.parseDouble(levelList.get(mCurrent-1).getLatitude()),Double.parseDouble(levelList.get(mCurrent-1).getLongitude()));
            score(dbLatLng, mLatLng);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

/*            mDialog=new DialogLevel();
            Bundle bundle=new Bundle();
            bundle.putBoolean("Finished",isFinished);
            bundle.putInt("Round",mCurrent);
            bundle.putLong("Time",updateTime);
            bundle.putLong("RoundScore",mScoreRound);
            bundle.putLong("Score",mScore);
            bundle.putFloat("Distance",mDistance);
            mDialog.setArguments(bundle);
            mDialog.setCancelable(false);
            mDialog.show(getSupportFragmentManager(),"aaa");*/
            if(mCurrent==10) {
                mBinding.textViewScore.setText("Score:"+String.valueOf(mScore));
                UpdateScore();
                makeToast();
                 DialogRestart();

                //   finish();
            }
            else {
                new ImageTask().execute();
                StartTimer();
            }


        }
    }
    public void DialogRestart()
    {


        MaterialDialog dialogRestart=new MaterialDialog.Builder(this)
                .title("Restart")
                .content("Do you want to restart?")
                .backgroundColorRes(R.color.grey)
                .positiveText("Yes")
                .negativeText("No")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finishAfterTransition();
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Log.i("OnDestroy", "onDestroy:called ");
                        finishAfterTransition();
                        Intent intent = new Intent(getApplicationContext(), LevelActivity.class);
                        intent.putExtra(SelectLevelActivity.EXTRA_LEVEL_KEY, String.valueOf(mLevel));
                        if(scoreChange)
                            intent.putExtra(SelectLevelActivity.EXTRA_HIGHSCORE_KEY, String.valueOf(mScore));
                        else
                            intent.putExtra(SelectLevelActivity.EXTRA_HIGHSCORE_KEY, String.valueOf(mHigscore));
                        intent.putExtra(SelectLevelActivity.EXTRA_OVERALL_KEY, String.valueOf(mOverall));
                        startActivity(intent);
                    }
                })
                .negativeColor(getResources().getColor(R.color.colorPrimary))
                .positiveColor(getResources().getColor(R.color.colorPrimary))
                .show();
        dialogRestart.setCancelable(false);
    }

    public void makeToast()
    {
        if(mRecord)
            Toast.makeText(getApplicationContext(), "You've beat your level "+mLevel+ " record, now your highscore is "+mScore+" points!",
                    Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(), "You've finished level "+mLevel+ " with "+mScore+" points!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mapView!=null)
        {
            mapView.onDestroy();
        }
        Log.i("Ondestroy called","on destroy");
    }

    @Override
    public void onBackPressed() {
        finish();

        super.onBackPressed();

    }

}
