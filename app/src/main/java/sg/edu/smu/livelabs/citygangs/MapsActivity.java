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
//import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

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
        ArrayList<LatLng> latLngs;
        MapResources tmp = new MapResources();
        Log.d("MAP","tmp.getAreas.size(): " +tmp.getAreas().size());
        for(Area area : tmp.getAreas()){
            Log.d("MAP","tmp.getAreas");
            latLngs = createSquare(new LatLng(area.getLatitude(),area.getLongitude()), area.getLength());
            PolygonOptions tmpOptions = new PolygonOptions();

            for(LatLng latLng : latLngs){
                Log.d("MAP","for latLng");
                tmpOptions.add(new LatLng(latLng.latitude, latLng.longitude));
            tmpOptions.strokeColor(area.getStrokeColor());
                Log.d("MAP","strokeColor: "+area.getStrokeColor());
            tmpOptions.fillColor(area.getFillColor());
                Log.d("MAP","fillColor: " +area.getFillColor());
            Polygon poly = mMap.addPolygon(tmpOptions);
            }
//            poly.setClickable(true);
//            mMap.setOnPolygonClickListener(GoogleMap.OnPolygonClickListener);
        }



        mMap.setOnMyLocationButtonClickListener(this);
        enableMyLocation();


    }

    public ArrayList<LatLng> createSquare(LatLng iLatLng, double length){
        Log.d("MAP","createSquare");
        ArrayList<LatLng> latLgns = new ArrayList<LatLng>();
        latLgns.add(iLatLng);
        latLgns.add(new LatLng(iLatLng.latitude, iLatLng.longitude + length));
        latLgns.add(new LatLng(iLatLng.latitude + length, iLatLng.longitude + length));
        latLgns.add(new LatLng(iLatLng.latitude + length, iLatLng.longitude));
        return latLgns;
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

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,
                                            int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            mPermissionDenied = false;
        }
    }


}
