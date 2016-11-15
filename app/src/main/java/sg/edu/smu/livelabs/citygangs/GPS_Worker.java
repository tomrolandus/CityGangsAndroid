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
import android.support.annotation.Nullable;
import android.widget.TextView;

import java.util.List;

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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Intent i = new Intent("location_update");
                i.putExtra("coordinates",location.getLongitude()+" "+location.getLatitude());
                sendBroadcast(i);


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

//    private void getAllAreas(GetAreasRequest areaResponse) {
//        this.token = null;
//        this.area = null;
//        Call<GetAreasResponse> areaResponseCall = service.getAreas(areaResponse);
//        areaResponseCall.enqueue(new Callback<GetAreasResponse>() {
//            @Override
//            public void onResponse(Call<GetAreasResponse> call, Response<GetAreasResponse> response) {
////                Log.d("DEBUG","onResponse, code: " +response.code());
//                int statusCode = response.code();
//                if (statusCode == 200) {
//                    token = User.getUser().getToken();
//                    Log.d("Login", "getToken token: " + token);
//
//                    Call<Area> areaCall = service.getAreas("Bearer " + token);
//                    areaCall.enqueue(new Callback<Area>() {
//                        @Override
//                        public void onResponse(Call<Area> call, Response<Area> response) {
//                            int userStatus = response.code();
//                            area = response.body();
//                            Log.d("Login", "responseCode: " + userStatus);
//                            Log.d("Login", "Id: " + area.getId());
//                            Log.d("Login", "longitude: " + area.getLongitude());
//                            Log.d("Login", "latitude: " + area.getLatitude());
//                            Log.d("Login", "length: " + area.getLength());
//                            Log.d("Login", "TeamID: " + area.getTeam_id());
//
//
//                        }
//
//                        @Override
//                        public void onFailure(Call<Area> call, Throwable t) {
//                            Log.d("Login", "OnFailure: " + t.getMessage());
//                        }
//                    });
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GetAreasResponse> call, Throwable t) {
//                Log.d("ERROR", "GetToken:OnFailure: " + t.getMessage());
//            }
//        });
//
//    }


   }
