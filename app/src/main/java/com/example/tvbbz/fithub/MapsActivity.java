package com.example.tvbbz.fithub;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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

        // Add a marker in Mandeville and move the camera
        LatLng mandeville = new LatLng(18.040504, -77.510772);
        mMap.addMarker(new MarkerOptions().position(mandeville).title("Marker in Mandeville"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mandeville));

        // Add a marker in HWT and move the camera
        LatLng hwt = new LatLng(18.015655, -76.795822);
        mMap.addMarker(new MarkerOptions().position(hwt).title("Marker in Half Way Tree"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(hwt));

        // Add a marker in New Kingston and move the camera
        LatLng nkgn = new LatLng(18.006919, -76.789528);
        mMap.addMarker(new MarkerOptions().position(nkgn).title("Marker in New Kingston"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(nkgn));

        // Add a marker in Hope Road and move the camera
        LatLng platinum = new LatLng(18.020368, -76.769731);
        mMap.addMarker(new MarkerOptions().position(platinum).title("Marker in Hope Road"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(platinum));

        // Add a marker in Manor Park and move the camera
        LatLng mp = new LatLng(18.048309, -76.794816);
        mMap.addMarker(new MarkerOptions().position(mp).title("Marker in Manor Park"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mp));

        // Add a marker in Mobay and move the camera
        LatLng mobay = new LatLng(18.454722, -77.927067);
        mMap.addMarker(new MarkerOptions().position(mobay).title("Marker in Mobay"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mobay));

    }
}
