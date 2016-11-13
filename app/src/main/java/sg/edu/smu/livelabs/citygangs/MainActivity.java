package sg.edu.smu.livelabs.citygangs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import sg.edu.smu.livelabs.citygangs.FaceAPI.ui.FaceRecogActivity;




public class MainActivity extends AppCompatActivity {

    String msg = "Android : ";
    private TextView userMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userMain = (TextView) findViewById(R.id.userMainTV);
        if(User.getUser() == null)
            startActivity(new Intent(getBaseContext(), LoginActivity.class));

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

    public void startLogicService(View view) {
        startService(new Intent(getBaseContext(), LogicService.class));
    }

    // Method to stop the service
    public void stopLogicService(View view) {
        stopService(new Intent(getBaseContext(), LogicService.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        User tmp = User.getUser();
        if(tmp!=null) {
            userMain.setText(User.getUser().getName());
        }
        else
            userMain.setText("null");
    }
}