package com.patrick.whereisthat;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.patrick.whereisthat.selectlevel.SelectLevelActivity;


public class StartActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMapClickListener {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
       mapFragment.getMapAsync(this);
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
}
