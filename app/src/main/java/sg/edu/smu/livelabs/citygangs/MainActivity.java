package sg.edu.smu.livelabs.citygangs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.drive.query.internal.LogicalFilter;

import sg.edu.smu.livelabs.citygangs.FaceAPI.AddFaceToPersonActivity;


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
        startActivity(new Intent(getBaseContext(), FaceRecogActivityOld.class));
    }

    public void startLoginActivity(View view) {
        startActivity(new Intent(getBaseContext(), LoginActivity.class));
    }

    public void startLogicService(View view) {
        startService(new Intent(getBaseContext(), LogicService.class));
    }

    // Method to stop the service
    public void stopLogicService(View view) {
        stopService(new Intent(getBaseContext(), LogicService.class));
    }

    //ADD FACE TO PERSON ACTIVITY
    public void startAddFaceToPersonActivity(View view) {
        startActivity(new Intent(getBaseContext(), AddFaceToPersonActivity.class));
    }


}