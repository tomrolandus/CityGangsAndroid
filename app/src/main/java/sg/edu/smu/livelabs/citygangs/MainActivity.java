package sg.edu.smu.livelabs.citygangs;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.FaceServiceRestClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sg.edu.smu.livelabs.citygangs.FaceAPI.helper.StorageHelper;
import sg.edu.smu.livelabs.citygangs.FaceAPI.persongroupmanagement.PersonActivity;
import sg.edu.smu.livelabs.citygangs.FaceAPI.ui.FaceRecogActivity;
import sg.edu.smu.livelabs.citygangs.interfaces.ServerInterface;

public class MainActivity extends AppCompatActivity {

    String msg = "Android : ";
    private TextView userMain;
    private static FaceServiceClient sFaceServiceClient;
    private static String email;
    public static final String BASE_URL = "http://is416app.139.59.238.27.nip.io/api/";
    private static ServerInterface service;
    private static User mainUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(ServerInterface.class);

        //CREATE THE FACESERVICE CLIENT
        sFaceServiceClient = new FaceServiceRestClient(getString(R.string.subscription_key));

        if (getString(R.string.subscription_key).startsWith("Please")) {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.add_subscription_key_tip_title))
                    .setMessage(getString(R.string.add_subscription_key_tip))
                    .setCancelable(false)
                    .show();
        }

        //CHECK IF USER CONNECTED
        if (mainUser == null)
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

    // Method to start the service activity
    public void startServiceActivity(View view) {
        startActivity(new Intent(getBaseContext(), ServiceActivity.class));
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

    public static FaceServiceClient getFaceServiceClient() {
        return sFaceServiceClient;
    }


    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        MainActivity.email = email;
    }

    public static ServerInterface getService() {
        return service;
    }

    public static User getMainUser() {
        return mainUser;
    }

    public static void setMainUser(User mainUser) {
        MainActivity.mainUser = mainUser;
    }
}

