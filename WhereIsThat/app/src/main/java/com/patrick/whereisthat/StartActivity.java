package com.patrick.whereisthat;

import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.patrick.whereisthat.data.Scores;
import com.patrick.whereisthat.login.LoginActivity;
import com.patrick.whereisthat.scores.ScoresActivity;
import com.patrick.whereisthat.selectlevel.SelectLevelActivity;
import com.patrick.whereisthat.settings.SettingsActivity;
import com.patrick.whereisthat.sprint.SprintActivity;

import java.util.List;


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
                if(!mUser.equals("")) {
                        Intent intent = new Intent(getApplicationContext(), SprintActivity.class);
                        startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"You must be logged to play",Toast.LENGTH_LONG).show();
                }
            }
        });
        if (mFirebaseAuth.getCurrentUser() != null) {
            getUsername(mFirebaseAuth.getUid());
            mInit=true;
            Log.d("Login", mFirebaseAuth.getUid());
        }
        else
        {
            mProgress.setVisibility(View.INVISIBLE);
            mInit=false;
            changeMenuToLogout();
            deleteUsername();
        }
    }
    public void startGameClick(View view){

        if(!mUser.equals("")) {
            Intent intent = new Intent(getApplication(), SelectLevelActivity.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"You must be logged to play",Toast.LENGTH_LONG).show();
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
                        Intent intent2=new Intent(getApplicationContext(), ScoresActivity.class);
                        intent2.putExtra(EXTRA_USERNAME,mUser);
                        startActivity(intent2);
                        break;
                        case R.id.invite_navigation_menu_item:
                            Intent sendIntent = new Intent();
                            sendIntent.setAction(Intent.ACTION_SEND);
                            sendIntent.putExtra(Intent.EXTRA_SUBJECT,"Where is this game");
                            sendIntent.putExtra(Intent.EXTRA_TEXT, "Hi,you must try this game! #whereisthis"+System.getProperty("line.separator")+"www.whereisthis.com");
                            sendIntent.setType("text/plain");
                            startActivity(sendIntent);
                        break;
                    case R.id.settings_navigation_menu_item:
                        Intent intent3=new Intent(getApplicationContext(),SettingsActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.logout_navigation_menu_item:
                       String title=item.getTitle().toString();

                        if(title.equals("Logout"))
                        {
                            FirebaseAuth.getInstance().signOut();
                            logoutToast();
                            changeMenuToLogout();
                            deleteUsername();

                        }
                        else {
                            Log.d("Logout", "Logout clicked");
                            Intent intent = new Intent(getApplication(), LoginActivity.class);
                            startActivity(intent);
                            break;
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
        NavigationView mNavigationView=findViewById(R.id.nav_view);
        Menu menu=mNavigationView.getMenu();
        MenuItem item=menu.findItem(R.id.logout_navigation_menu_item);
        item.setTitle("Login");
        item.setIcon(ContextCompat.getDrawable(this,R.drawable.ic_login_24dp));
        mUser="";
    }
    public void logoutToast()
    {
        Toast.makeText(this,"You are logged out",Toast.LENGTH_LONG).show();
    }






}
