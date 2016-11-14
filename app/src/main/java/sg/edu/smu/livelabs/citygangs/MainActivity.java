package sg.edu.smu.livelabs.citygangs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import sg.edu.smu.livelabs.citygangs.FaceAPI.helper.StorageHelper;
import sg.edu.smu.livelabs.citygangs.FaceAPI.persongroupmanagement.PersonActivity;
import sg.edu.smu.livelabs.citygangs.FaceAPI.ui.FaceRecogActivity;


public class MainActivity extends AppCompatActivity {

    String msg = "Android : ";
    private TextView userMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (User.getUser() == null)
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

    }

    public void startPreferencesActivity(View view) {
        startActivity(new Intent(getBaseContext(), PreferencesActivity.class));
    }

    public void test(View view) {
        User user = User.getUser();
        String personId = user.getFaceId();
        String personGroupId = user.getGroupId();
//        String personId = "6a3a65d8-2beb-4a57-bc24-e3079c803fbc";
//        String personGroupId = "a145436e-03b2-4b72-8e47-f58e13ab49c7";
        String personName = StorageHelper.getPersonName(
                personId, personGroupId, MainActivity.this);

        Intent intent = new Intent(MainActivity.this, PersonActivity.class);
        intent.putExtra("AddNewPerson", false);
        intent.putExtra("PersonName", personName);
        intent.putExtra("PersonId", personId);
        intent.putExtra("PersonGroupId", personGroupId);
        startActivity(intent);
    }
}
