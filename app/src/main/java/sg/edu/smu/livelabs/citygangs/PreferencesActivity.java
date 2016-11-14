package sg.edu.smu.livelabs.citygangs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import sg.edu.smu.livelabs.citygangs.FaceAPI.persongroupmanagement.PersonActivity;
import sg.edu.smu.livelabs.citygangs.FaceAPI.persongroupmanagement.PersonGroupActivity;

public class PreferencesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
    }
}
