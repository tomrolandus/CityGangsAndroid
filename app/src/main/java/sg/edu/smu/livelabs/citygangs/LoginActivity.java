package sg.edu.smu.livelabs.citygangs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.contract.CreatePersonResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sg.edu.smu.livelabs.citygangs.FaceAPI.helper.LogHelper;
import sg.edu.smu.livelabs.citygangs.FaceAPI.helper.StorageHelper;
import sg.edu.smu.livelabs.citygangs.FaceAPI.ui.SelectImageActivity;
import sg.edu.smu.livelabs.citygangs.Models.LoginTokenRequest;
import sg.edu.smu.livelabs.citygangs.Models.LoginTokenResponse;
import sg.edu.smu.livelabs.citygangs.interfaces.ServerInterface;

public class LoginActivity extends AppCompatActivity {
    public static final String BASE_URL = "http://is416app.139.59.238.27.nip.io/api/";
    private ServerInterface service;
    private String token;
    private String email;
    private String password;
    private String personGroupId = "a145436e-03b2-4b72-8e47-f58e13ab49c7";
    ProgressDialog progressDialog;
    private String personId;
    private static final int REQUEST_SELECT_IMAGE = 0;

//    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




        //EDITTEXT
        EditText emailET = (EditText) findViewById(R.id.enterEmail);
        emailET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                email = s.toString();
            }
        });

        EditText passwordET = (EditText) findViewById(R.id.enterPassword);
        passwordET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                password = s.toString();
            }
        });


        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(ServerInterface.class);

    }

    /**
     * This method allows you to quickly login using the hardcoded test@example.com
     */
    public void autoLogin(View view) {

        //here i just give it the hardcoded user
        LoginTokenRequest loginTokenRequest = new LoginTokenRequest();
        loginTokenRequest.setEmail("test@example.com");
        loginTokenRequest.setPassword("testtest");

        getTokenAndUser(loginTokenRequest);
//        generateToken(loginTokenRequest);
//        getUser(loginTokenRequest.getEmail());
//        if(user != null){
//            Log.d("DEBUG","goToMain");
//            goToMain();
//        }
//        else{
//            Log.d("DEBUG","User is null");
//        }
    }


    private void startMainActivity() {
        getAllAreas();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }


    private void getTokenAndUser(final LoginTokenRequest loginTokenRequest) {
        this.token = null;
        Call<LoginTokenResponse> tokenResponseCall = service.getTokenAccess(loginTokenRequest);
        tokenResponseCall.enqueue(new Callback<LoginTokenResponse>() {
            @Override
            public void onResponse(Call<LoginTokenResponse> call, Response<LoginTokenResponse> response) {
//                Log.d("DEBUG","onResponse, code: " +response.code());
                int statusCode = response.code();
                if (statusCode == 200) {
                    token = response.body().getToken();
                    Log.d("Login", "getToken token: " + token);
                    Call<User> userCall = service.getUser(loginTokenRequest.getEmail(), "Bearer " + token);
                    userCall.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            int userStatus = response.code();
                            User user = response.body();
                            user.setToken(token);
//                            Log.d("Login", "responseCode: " + userStatus);
//                            Log.d("Login", "Name: " + user.getName());
//                            Log.d("Login", "username: " + user.getUsername());
//                            Log.d("Login", "Password: " + user.getPassword());
//                            Log.d("Login", "Email: " + user.getEmail());
//                            Log.d("Login", "ID: " + user.getId());
//                            Log.d("Login", "TeamID: " + user.getTeamID());
                            MainActivity.setMainUser(user);

                            startMainActivity();

                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Log.d("Login", "OnFailure: " + t.getMessage());
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<LoginTokenResponse> call, Throwable t) {
                Log.d("ERROR", "GetToken:OnFailure: " + t.getMessage());
            }
        });

    }

    private String generateToken(final LoginTokenRequest loginTokenRequest) {
        this.token = null;
        Call<LoginTokenResponse> tokenResponseCall = service.getTokenAccess(loginTokenRequest);
        tokenResponseCall.enqueue(new Callback<LoginTokenResponse>() {
            @Override
            public void onResponse(Call<LoginTokenResponse> call, Response<LoginTokenResponse> response) {
//                Log.d("DEBUG","onResponse, code: " +response.code());
                int statusCode = response.code();
                if (statusCode == 200) {
                    token = response.body().getToken();
                    Log.d("Login", "getToken token: " + token);
                }
            }

            @Override
            public void onFailure(Call<LoginTokenResponse> call, Throwable t) {
                Log.d("ERROR", "GetToken:OnFailure: " + t.getMessage());
            }
        });
        return token;
    }

//    public void getUser(String e) {
//        this.user = null;
//        Call<User> userCall = service.getUser(e, "Bearer " + this.token);
//        userCall.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                int userStatus = response.code();
//                user = response.body();
////                Log.d("Login", "responseCode: " + userStatus);
////                Log.d("Login", "Name: " + user.getName());
////                Log.d("Login", "username: " + user.getUsername());
////                Log.d("Login", "Password: " + user.getPassword());
////                Log.d("Login", "Email: " + user.getEmail());
////                Log.d("Login", "ID: " + user.getId());
////                Log.d("Login", "TeamID: " + user.getTeamID());
//
//
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                Log.d("Login", "OnFailure: " + t.getMessage());
//            }
//        });
//
//        return;
//    }

    public void login(View view) {
        LoginTokenRequest loginTokenRequest = new LoginTokenRequest();
        loginTokenRequest.setEmail(email);
        loginTokenRequest.setPassword(password);

        getTokenAndUser(loginTokenRequest);

    }

    public void startEnterEmailActivity(View view) {
        startActivity(new Intent(getBaseContext(), EnterEmailActivity.class));
    }

    public void addPerson(View view) {

        new AddPersonTask(true).execute(personGroupId);
//        addPerson();

    }

    private void addPerson() {



//        Intent intent = new Intent(this, PersonActivity.class);
//        intent.putExtra("AddNewPerson", true);
//        intent.putExtra("PersonName", "");
//        intent.putExtra("PersonGroupId", personGroupId);
//        startActivity(intent);

    }


    class AddPersonTask extends AsyncTask<String, String, String> {
        // Indicate the next step is to add face in this person, or finish editing this person.
        boolean mAddFace;

        AddPersonTask(boolean addFace) {
            Log.d("DEBUG1", "addFace(boolean addFace");
            Log.d("DEBUG1", "addFace" + addFace);
            mAddFace = addFace;
        }

        @Override
        protected String doInBackground(String... params) {
            // Get an instance of face service client.
            Log.d("DEBUG1", "doInBackground");
            FaceServiceClient faceServiceClient = MainActivity.getFaceServiceClient();
            try {
                publishProgress("Syncing with server to add person...");
                addLog("Request: Creating Person in person group" + params[0]);

                // Start the request to creating person.
                CreatePersonResult createPersonResult = faceServiceClient.createPerson(
                        params[0],
                        "Person name",
                        "User data");
                Log.d("DEBUG1", "params[0]: " + params[0]);
                return createPersonResult.personId.toString();
            } catch (Exception e) {
                publishProgress(e.getMessage());
                addLog(e.getMessage());
                return null;
            }
        }
//
//    @Override
//    protected void onPreExecute() {
//        setUiBeforeBackgroundTask();
//    }
//
//    @Override
//    protected void onProgressUpdate(String... progress) {
//        setUiDuringBackgroundTask(progress[0]);
//    }

        @Override
        protected void onPostExecute(String result) {
//        progressDialog.dismiss();

            if (result != null) {
                addLog("Response: Success. Person " + result + " created.");
                personId = result;
//                setInfo("Successfully Synchronized!");

                if (mAddFace) {
                    addFace();
                } else {
                    Log.d("DEBUG1", "postExecute done and save");
                    doneAndSave();
                }
            }
        }
    }

    // Add a log item.
    private void addLog(String log) {
        LogHelper.addIdentificationLog(log);
    }

//    // Set the information panel on screen.
//    private void setInfo(String info) {
//        TextView textView = (TextView) findViewById(R.id.info);
//        textView.setText(info);
//    }

    private void addFace() {

        Log.d("DEBUG1", "addFace");
//        setInfo("");
        Intent intent = new Intent(this, SelectImageActivity.class);
        startActivityForResult(intent, REQUEST_SELECT_IMAGE);
    }

    private void doneAndSave() {
        Log.d("DEBUG1", "doneAndSave");
//        TextView textWarning = (TextView) findViewById(R.id.info);
//        EditText editTextPersonName = (EditText) findViewById(R.id.edit_person_name);
//        String newPersonName = editTextPersonName.getText().toString();
//        if (newPersonName.equals("")) {
//            textWarning.setText(R.string.person_name_empty_warning_message);
//            return;
//        }

        StorageHelper.setPersonName(personId, email, personGroupId, LoginActivity.this);
        finish();
    }

    public void startTestActivity(View view) {
        startActivity(new Intent(getBaseContext(), TestActivity.class));
    }

    private void getAllAreas() {
        //  this.token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjEsImlzcyI6Imh0dHA6XC9cL2lzNDE2YXBwLjEzOS41OS4yMzguMjcubmlwLmlvXC9hcGlcL2xvZ2luIiwiaWF0IjoxNDc5MjEyMjI4LCJleHAiOjE0NzkyMTU4MjgsIm5iZiI6MTQ3OTIxMjIyOCwianRpIjoiNGRjN2E3ODFkNDc5M2FlNmE5YmVhZWIwNGZkZWMwMjIifQ.FHA6BCH550UqIPh63ImixNmuaRo1F151cETmE4sJbSU";
        token = MainActivity.getMainUser().getToken();
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