package sg.edu.smu.livelabs.citygangs;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
//import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sg.edu.smu.livelabs.citygangs.interfaces.ServerInterface;

/**
 * Created by Emil on 15/11/16.
 */

public class GPS_Worker extends Service {

    private LocationListener listener;
    private LocationManager locationManager;
    private ServerInterface service;
    private String token;
    private List<Area> areas;
    public TextView displayText;
    public static final String BASE_URL = "http://is416app.139.59.238.27.nip.io/api/";


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
                service = retrofit.create(ServerInterface.class);
                Intent i = new Intent("location_update");
                i.putExtra("coordinates",location.getLongitude()+" "+location.getLatitude());
                sendBroadcast(i);
                updateUserLocation(MainActivity.getMainUser(), location.getLongitude(), location.getLatitude());


            }




            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
            }

            @Override
            public void onProviderEnabled(String s) {
            }

            @Override
            public void onProviderDisabled(String s) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        };

        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        //noinspection MissingPermission
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3000,0,listener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(locationManager != null){
            //noinspection MissingPermission
            locationManager.removeUpdates(listener);
        }
    }

    private void updateUserLocation(User user, double longitude, double latitude) {
        //this.token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjEsImlzcyI6Imh0dHA6XC9cL2lzNDE2YXBwLjEzOS41OS4yMzguMjcubmlwLmlvXC9hcGlcL2xvZ2luIiwiaWF0IjoxNDc5MjEyMjI4LCJleHAiOjE0NzkyMTU4MjgsIm5iZiI6MTQ3OTIxMjIyOCwianRpIjoiNGRjN2E3ODFkNDc5M2FlNmE5YmVhZWIwNGZkZWMwMjIifQ.FHA6BCH550UqIPh63ImixNmuaRo1F151cETmE4sJbSU";
        token = MainActivity.getMainUser().getToken();
        Log.d("DEBUG1","token: " +token);
        user.setLatitude(latitude);
        user.setLongitude(longitude);
        Call<User> userCall = service.updateLocation(user, "Bearer " + token);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                int userStatus = response.code();
                Log.d("DebugUser", "User changed");
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("Login", "OnFailure: " + t.getMessage());
            }
        });
    }
}