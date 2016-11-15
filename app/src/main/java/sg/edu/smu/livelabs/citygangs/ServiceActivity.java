package sg.edu.smu.livelabs.citygangs;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sg.edu.smu.livelabs.citygangs.interfaces.ServerInterface;

public class ServiceActivity extends Activity {

    private Button btn_start, btn_stop;
    private TextView textView;
    private BroadcastReceiver broadcastReceiver;
    private String token;
    private Area area;
    private TextView displayText;
    private ServerInterface service;
    public static final String BASE_URL = "http://is416app.139.59.238.27.nip.io/api/";

    @Override
    protected void onResume() {
        super.onResume();

        if (broadcastReceiver == null) {
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    textView.append("\n" + intent.getExtras().get("coordinates"));
                }
            };
        }
        registerReceiver(broadcastReceiver, new IntentFilter("location_update"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        btn_start = (Button) findViewById(R.id.button);
        btn_stop = (Button) findViewById(R.id.button2);
        textView = (TextView) findViewById(R.id.textView);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(ServerInterface.class);


        getAllAreas();


        if (!runtime_permissions())
            enable_buttons();
    }

    private void enable_buttons() {

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), GPS_Worker.class);
                startService(i);
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), GPS_Worker.class);
                stopService(i);
            }
        });
    }

    private boolean runtime_permissions() {
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 100);

            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                enable_buttons();
            } else {
                runtime_permissions();
            }
        }
    }

    private void getAllAreas() {
        this.token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjEsImlzcyI6Imh0dHA6XC9cL2lzNDE2YXBwLjEzOS41OS4yMzguMjcubmlwLmlvXC9hcGlcL2xvZ2luIiwiaWF0IjoxNDc5MjEyMjI4LCJleHAiOjE0NzkyMTU4MjgsIm5iZiI6MTQ3OTIxMjIyOCwianRpIjoiNGRjN2E3ODFkNDc5M2FlNmE5YmVhZWIwNGZkZWMwMjIifQ.FHA6BCH550UqIPh63ImixNmuaRo1F151cETmE4sJbSU";
//        token = User.getUser().getToken();
        Log.d("DEBUG1","token: " +token);
        Call<List<Area>> areaCall = service.getAreas( "Bearer " + token);
        areaCall.enqueue(new Callback<List<Area>>() {
            @Override
            public void onResponse(Call<List<Area>> call, Response<List<Area>> response) {
                int userStatus = response.code();
                List<Area> areas = response.body();
                Log.d("debug3","areas.size: " +areas.size());
                for(Area a : areas){
                    Log.d("debug3","Longitude: " +a.getLongitude());
                    Log.d("debug3","latitude: " +a.getLatitude());
                }


                displayText = (TextView) findViewById(R.id.textView2);


//                            for(int i = 0; i< areas.size(); i++){
//                                Area area = null;
//                                Log.d("AREA", "responseCode: " + userStatus);
//                                Log.d("AREA", "Id: " + area.getId());
//                                Log.d("AREA", "longitude: " + area.getLongitude());
//                                Log.d("AREA", "latitude: " + area.getLatitude());
//                                Log.d("AREA", "length: " + area.getLength());
//                                Log.d("AREA", "TeamID: " + area.getTeam_id());
//
//                            }


            }

            @Override
            public void onFailure(Call<List<Area>> call, Throwable t) {
                Log.d("Login", "OnFailure: " + t.getMessage());
            }
        });

    }
}