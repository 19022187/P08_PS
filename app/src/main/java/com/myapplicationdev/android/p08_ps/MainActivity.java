package com.myapplicationdev.android.p08_ps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    Button btn1, btn2, btn3;
    Spinner spn;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment)
                fm.findFragmentById(R.id.map);

        LatLng pos_sg = new LatLng(1.357107, 103.8194992);
        LatLng pos_north = new LatLng(1.4557396747917046, 103.81709518756111);
        LatLng pos_central = new LatLng(1.2977112509351951, 103.84775395113755);
        LatLng pos_east = new LatLng(1.3508312166232623, 103.93355146257583);

        mapFragment.getMapAsync(new OnMapReadyCallback(){
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;

                map.moveCamera(CameraUpdateFactory.newLatLngZoom(pos_sg,
                        11));

                UiSettings ui = map.getUiSettings();
                ui.setCompassEnabled(true);
                ui.setZoomControlsEnabled(true);

                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION);

                if (permissionCheck == PermissionChecker.PERMISSION_GRANTED) {
                    map.setMyLocationEnabled(true);
                } else {
                    Log.e("GMap - Permission", "GPS access has not been granted");
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                }

                Marker north = map.addMarker(new
                        MarkerOptions()
                        .position(pos_north)
                        .title("HQ - North")
                        .snippet("Block 333, Admiralty Ave 3, 765654  \n" +
                                "Operating hours: 10am-5pm\n" +
                                "Tel:65433456")
                        .icon(BitmapDescriptorFactory.fromResource(android.R.drawable.star_big_on)));

                Marker central = map.addMarker(new
                        MarkerOptions()
                        .position(pos_central)
                        .title("Central")
                        .snippet("Block 3A, Orchard Ave 3, 134542  \n" +
                                "Operating hours: 11am-8pm\n" +
                                "Tel:67788652")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                Marker east = map.addMarker(new
                        MarkerOptions()
                        .position(pos_east)
                        .title("East")
                        .snippet("Block 555, Tampines Ave 3, 287788 \n" +
                                "Operating hours: 9am-5pm\n" +
                                "Tel:66776677")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        String msg = "";
                        msg = marker.getTitle();
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });
            }
        });

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        spn = findViewById(R.id.spinner);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (map != null){
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(pos_north,
                            12));
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (map != null){
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(pos_central,
                            12));
                }
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (map != null){
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(pos_east,
                            12));
                }
            }
        });

        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(pos_sg,
                            11));
                }
                else if (position == 1){
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(pos_north,
                            12));
                }
                else if (position == 2){
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(pos_central,
                            12));
                }
                else {
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(pos_east,
                            12));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}