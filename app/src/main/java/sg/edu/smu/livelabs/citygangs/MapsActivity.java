package sg.edu.smu.livelabs.citygangs;

//import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MapsActivity extends AppCompatActivity implements GoogleMap.OnMyLocationButtonClickListener, OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;
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
        //Polygon is what we use to define the areas
        Polygon polygon = mMap.addPolygon(new PolygonOptions()


                .add(new LatLng(1.2896700, 103.8500700),
                        new LatLng(1.2896700, 103.8600700),
                        new LatLng(1.2996700, 103.8500700),
                        new LatLng(1.2896700, 103.8500700))
                //notice the last one == the first one

                //Stroke of the outside of the polygon
                .strokeColor(Color.RED)

                //color uses the ARGB format which is as follows:
                //0xTTRRGGBB where TT is percentage of transparency, RR GG BB hexa values [00;ff]
                .fillColor(0x40001010));


        //Coordinates of Singapore in decimal degrees
        LatLng singapore = new LatLng(1.2896700, 103.8500700);

        //add a marker (the red thing)
        mMap.addMarker(new MarkerOptions().position(singapore).title("Marker in Singapore"));

//        //move the camera to Singapore
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(singapore));
//
//        //The initial ZOOM of the map
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(singapore, 11));

        mMap.setOnMyLocationButtonClickListener(this);
        enableMyLocation();


    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

}
