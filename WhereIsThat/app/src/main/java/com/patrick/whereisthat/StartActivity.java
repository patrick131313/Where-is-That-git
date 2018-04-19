package com.patrick.whereisthat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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


public class StartActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private DrawerLayout mDrawerLayout;
    private FirebaseAuth mFirebaseAuth;
    public static final String EXTRA_USERNAME="USERNAME_KEY";
    private String mUser="";
    private Button mSprint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         mFirebaseAuth = FirebaseAuth.getInstance();

        if (mFirebaseAuth.getCurrentUser() != null) {
            getUsername(mFirebaseAuth.getUid());
            Log.d("Login", mFirebaseAuth.getUid());
            setContentView(R.layout.activity_start);
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            setUpNavDrawer();
            mSprint=findViewById(R.id.button_sprint_mode);
            mSprint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getApplicationContext(),SprintActivity.class);
                    startActivity(intent);
                }
            });





        }
        else
        {
            Log.d("Login","not logged in");
            Intent intent=new Intent(getApplication(),LoginActivity.class);
            startActivity(intent);
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        // Add a marker in Sydney and move the camera
        LatLng home = new LatLng(45.759282, 21.210157);
        CameraPosition cp = CameraPosition.builder()
                .target(home)
                .zoom(8)
                .bearing(0)
                .tilt(0) //sau tilt 45
                .build();
      //  mMap.addMarker(new MarkerOptions().position(home).title("Marker in Sydney"));
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cp), 1, null);
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this,R.raw.map_start_style));
        mMap.getUiSettings().setMapToolbarEnabled(false);
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }
    public void startGameClick(View view){

        Intent intent=new Intent(getApplication(),SelectLevelActivity.class);
        startActivity(intent);

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
                        break;
                    case R.id.settings_navigation_menu_item:
                        Intent intent3=new Intent(getApplicationContext(),SettingsActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.logout_navigation_menu_item:
                       String title=item.getTitle().toString();
                        if(title.equals("Logout"))
                        {
                        //    mFirebaseAuth.signOut();
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
                //String userName=dataSnapshot.getValue().toString();
               // Log.d("MyRef",userName);
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
        TextView textView=findViewById(R.id.textView_user);
        textView.setText("You are logged in as:"+username);
    }
    public void deleteUsername()
    {
        TextView textView=findViewById(R.id.textView_user);
        textView.setText("You must log in");
    }


    public void changeMenuToLogout()
    {
        NavigationView mNavigationView=findViewById(R.id.nav_view);
        Menu menu=mNavigationView.getMenu();
        MenuItem item=menu.findItem(R.id.logout_navigation_menu_item);
        item.setTitle("Login");
        item.setIcon(ContextCompat.getDrawable(this,R.drawable.ic_login_24dp));
        mUser="";
       /* item.setTitle("Tezd");
        item.setIcon(ContextCompat.getDrawable(this,R.drawable.ic_share_24dp));*/
    }
    public void logoutToast()
    {
        Toast.makeText(this,"You are logged out",Toast.LENGTH_LONG).show();
    }






}
