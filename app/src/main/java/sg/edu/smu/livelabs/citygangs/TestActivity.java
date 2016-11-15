package sg.edu.smu.livelabs.citygangs;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.contract.CreatePersonResult;
import com.microsoft.projectoxford.face.rest.ClientException;

import java.io.IOException;

import sg.edu.smu.livelabs.citygangs.FaceAPI.persongroupmanagement.AddFaceToPersonActivity;
import sg.edu.smu.livelabs.citygangs.FaceAPI.persongroupmanagement.AddFaceToPersonActivity2;
import sg.edu.smu.livelabs.citygangs.FaceAPI.ui.SelectImageActivity;

public  class TestActivity extends AppCompatActivity {
//    private static String personGroupId = "a145436e-03b2-4b72-8e47-f58e13ab49c7";
    private static String personId;
    private String email = "Michel";
    private static final int REQUEST_SELECT_IMAGE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        new AddPersonTask(true).execute(getString(R.string.personGroupId));



    }



    private void addFace() {

        Log.d("DEBUG4","addFace");
//        setInfo("");
        Intent intent = new Intent(this, SelectImageActivity.class);
        startActivityForResult(intent, REQUEST_SELECT_IMAGE);
    }

    class AddPersonTask extends AsyncTask<String, String, String> {
        // Indicate the next step is to add face in this person, or finish editing this person.
        boolean mAddFace;
        AddPersonTask (boolean addFace) {
            mAddFace = addFace;
        }

        @Override
        protected String doInBackground(String... params) {
            Log.d("DEBUG4","doInBackground");
            Log.d("DEBUG4","mAddFace= " +mAddFace);
            // Get an instance of face service client.
            FaceServiceClient faceServiceClient = MainActivity.getFaceServiceClient();
            try{
                publishProgress("Syncing with server to add person...");
//                addLog("Request: Creating Person in person group" + params[0]);

                // Start the request to creating person.
                CreatePersonResult createPersonResult = faceServiceClient.createPerson(
                        params[0],
                        email,
                        getString(R.string.user_provided_description_data));
                Log.d("DEBUG4","params[0]= " +params[0]);
                Log.d("DEBUG4","createPersonResult.personId.toString()= " +createPersonResult.personId.toString());
                personId = createPersonResult.personId.toString();
                Log.d("DEBUG4","personId: " +personId);
                return createPersonResult.personId.toString();
            } catch (Exception e) {
                publishProgress(e.getMessage());
                return null;
            }
        }




        @Override
        protected void onPostExecute(String result) {

            if (result != null) {

                if (mAddFace) {
                    addFace();
                } else {
                    finish();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("DEBUG2","onActivityResult(...): "+requestCode);
        switch (requestCode)
        {
            case REQUEST_SELECT_IMAGE:
                Log.d("DEBUG2", "REQUEST_SELECT_IMAGE");
                if (resultCode == RESULT_OK) {
                    Uri uriImagePicked = data.getData();
                    Intent intent = new Intent(this, AddFaceToPersonActivity2.class);
                    intent.putExtra("PersonId", personId);
                    intent.putExtra("PersonGroupId", getString(R.string.personGroupId));
                    intent.putExtra("ImageUriStr", uriImagePicked.toString());
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }

    public static String getPersonId() {
        return personId;
    }
//
//    public static String getPersonGroupId() {
//        return personGroupId;
//    }
}

