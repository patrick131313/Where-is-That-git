package com.patrick.whereisthat;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.patrick.whereisthat.login.LoginActivity;
import com.patrick.whereisthat.scores.ScoresActivity;
import com.patrick.whereisthat.selectlevel.SelectLevelActivity;
import com.patrick.whereisthat.settings.SettingsActivity;
import com.patrick.whereisthat.sprint.SprintActivity;

import java.net.InetAddress;
import java.util.Timer;
import java.util.TimerTask;


public class StartActivity extends FragmentActivity {

    private GoogleMap mMap;
    private DrawerLayout mDrawerLayout;
    private FirebaseAuth mFirebaseAuth;
    public static final String EXTRA_USERNAME="USERNAME_KEY";
    private String mUser="";
    private TextView textView;
    private ProgressBar mProgress;
    private Button mSprint;
    private boolean mInit;
    private boolean mInternet;
    private Timer timer;
    private TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mFirebaseAuth = FirebaseAuth.getInstance();
        setUpNavDrawer();
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        mProgress=findViewById(R.id.progress_user);
        mSprint=findViewById(R.id.button_sprint_mode);
        textView=findViewById(R.id.textView_user);
        textView.setVisibility(View.INVISIBLE);
        mSprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mInternet) {
                    if (!mUser.equals("")) {
                        timer.cancel();
                        timerTask.cancel();
                        Intent intent = new Intent(getApplicationContext(), SprintActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "You must be logged in to play, swipe left to login", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
        timerTask.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        newTimerTask();
        timer=new Timer();
        timer.schedule(timerTask, 0,10000);
    }

    public void newTimerTask()
    {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                //CALL YOUR ASSYNC TASK HERE.
                new CheckInternetTask().execute();
            }
        };
    }
    public void startGameClick(View view){

        if(mInternet) {
            if (!mUser.equals("")) {
                timer.cancel();
                timerTask.cancel();
                Intent intent = new Intent(getApplication(), SelectLevelActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "You must be logged in to play, swipe left to login", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_LONG).show();
        }
    }
    public void setUpNavDrawer()
    {
        NavigationView mNavigationView=findViewById(R.id.nav_view);
        if(mNavigationView!=null)
            setUpNavListener(mNavigationView);
    }

    public void setUpNavListener(NavigationView navigationView)
    {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.leaderboard_navigation_menu_item:
                                if(mInternet) {
                                    timer.cancel();
                                    timerTask.cancel();
                                    Intent intent2 = new Intent(getApplicationContext(), ScoresActivity.class);
                                    intent2.putExtra(EXTRA_USERNAME, mUser);
                                    startActivity(intent2);
                                }
                                else
                                    Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_LONG).show();
                                break;
                            case R.id.invite_navigation_menu_item:
                                if(mInternet) {
                                    timer.cancel();
                                    timerTask.cancel();
                                    Intent sendIntent = new Intent();
                                    String Title = "Share";
                                    Intent chooser = Intent.createChooser(sendIntent, Title);
                                    sendIntent.setAction(Intent.ACTION_SEND);
                                    sendIntent.putExtra(Intent.EXTRA_SUBJECT, " 'Where is this?' - game");
                                    sendIntent.putExtra(Intent.EXTRA_TEXT, "Hi, you must try this game! #whereisthis" + System.getProperty("line.separator") + "www.whereisthisapp.com");
                                    sendIntent.setType("text/plain");
                                    if (sendIntent.resolveActivity(getPackageManager()) != null)
                                        startActivity(chooser);
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_LONG).show();
                                }
                                break;
                            case R.id.settings_navigation_menu_item:
                                timer.cancel();
                                timerTask.cancel();
                                Intent intent3=new Intent(getApplicationContext(),SettingsActivity.class);
                                startActivity(intent3);
                                break;
                            case R.id.logout_navigation_menu_item:
                                if(mInternet) {
                                    String title = item.getTitle().toString();
                                    if (title.equals("Logout")) {
                                        FirebaseAuth.getInstance().signOut();
                                        logoutToast();
                                        changeMenuToLogout();
                                        deleteUsername();

                                    } else {
                                        Log.d("Logout", "Logout clicked");
                                        Intent intent = new Intent(getApplication(), LoginActivity.class);
                                        startActivity(intent);
                                        break;
                                    }
                                }
                             else
                                {
                                    Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_LONG).show();
                                }


                            default:
                                break;
                        }

                        return true;
                    }

                });

    };

    public void getUsername(String key)
    {
        DatabaseReference myRef=FirebaseDatabase.getInstance().getReference();
        Query query=myRef.child("users").child(key).child("user");
        Log.d("MyRef",query.toString());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mProgress.setVisibility(View.INVISIBLE);
                setUsername(dataSnapshot.getValue().toString());
                mUser=dataSnapshot.getValue().toString();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void setUsername(String username)
    {
        textView.setText("You are logged in as: "+username);
        textView.setVisibility(View.VISIBLE);
    }
    public void deleteUsername()
    {
        textView.setVisibility(View.VISIBLE);
        textView.setText("You are not logged in");
    }


    public void changeMenuToLogout()
    {
            NavigationView mNavigationView = findViewById(R.id.nav_view);
            Menu menu = mNavigationView.getMenu();
            MenuItem item = menu.findItem(R.id.logout_navigation_menu_item);
            item.setTitle("Login");
            item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_login_24dp));
            mUser = "";
    }
    public void logoutToast()
    {
        Toast.makeText(this,"You are logged out",Toast.LENGTH_LONG).show();
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

    class CheckInternetTask extends AsyncTask<Void, Void, Boolean>
    {

        @Override
        protected Boolean doInBackground(Void... voids) {
            return isInternetAvailable();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean) {
                mInternet=true;
                if (mFirebaseAuth.getCurrentUser() != null) {

                    getUsername(mFirebaseAuth.getUid());

                    mInit = true;
                    Log.d("Login", mFirebaseAuth.getUid());
                } else {
                    mProgress.setVisibility(View.INVISIBLE);
                    mInit = false;
                    changeMenuToLogout();
                    deleteUsername();
                }
            }
            else
            {
                    mInternet=false;
                    mProgress.setVisibility(View.INVISIBLE);
                    textView.setText("No internet connection");
                    textView.setVisibility(View.VISIBLE);
            }
        }
    }








}
