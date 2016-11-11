package sg.edu.smu.livelabs.citygangs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import okhttp3.OkHttpClient;


public class MainActivity extends AppCompatActivity {

    String msg = "Android : ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Method to start the maps activity
    public void startMapsActivity(View view) {
        startActivity(new Intent(getBaseContext(), MapsActivity.class));
    }

    // Method to start the Face recognition activity
    public void startFaceRecogActivity(View view) {
        startActivity(new Intent(getBaseContext(), FaceRecogActivity.class));
    }

    public void startLoginActivity(View view) {
        startActivity(new Intent(getBaseContext(), LoginActivity.class));
    }


}