package sg.edu.smu.livelabs.citygangs;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.contract.CreatePersonResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sg.edu.smu.livelabs.citygangs.FaceAPI.persongroupmanagement.AddFaceToPersonActivity2;
import sg.edu.smu.livelabs.citygangs.FaceAPI.ui.SelectImageActivity;
import sg.edu.smu.livelabs.citygangs.Models.RegisterTokenRequest;
import sg.edu.smu.livelabs.citygangs.Models.RegisterTokenResponse;
import sg.edu.smu.livelabs.citygangs.interfaces.ServerInterface;

public class CreateNewUserActivity extends AppCompatActivity {
//    private String email;
    private String password;
    private String token;
    private String username;
    private User user;
    private int teamdId = 0;
    private String[] arraySpinner;
    public static final String BASE_URL = "http://is416app.139.59.238.27.nip.io/api/";
    private ServerInterface service;
//    private static String personGroupId = "a145436e-03b2-4b72-8e47-f58e13ab49c7";
    private static String personId;
    private static final int REQUEST_SELECT_IMAGE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_user);
        new AddPersonTask(true).execute(getString(R.string.personGroupId));


        //Get access to Retrofit
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(ServerInterface.class);


        //HANDLES THE SPINNER
        this.arraySpinner = new String[]{
                "Ninjas", "Thugs", "OG's", "Los Pepes"};
        Spinner s = (Spinner) findViewById(R.id.teamSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        s.setAdapter(adapter);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                teamdId = position +1;
                Log.d("CreateNewUser","teamId: " +teamdId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


//        //EDITTEXT change listener
//        EditText emailET = (EditText) findViewById(R.id.emailET);
//        emailET.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                email = s.toString();
//            }
//        });

        EditText passwordET = (EditText) findViewById(R.id.passwordET);
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

        EditText usernameET = (EditText) findViewById(R.id.usernameET);
        usernameET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                username = s.toString();
            }
        });
    }



    public void createNewUser(View view){

        if(this.password != null && this.username != null && this.teamdId != 0) {
            final RegisterTokenRequest registerTokenRequest = new RegisterTokenRequest();
            registerTokenRequest.setEmail(MainActivity.getEmail());
            registerTokenRequest.setPassword(this.password);
            registerTokenRequest.setUsername(this.username);
            registerTokenRequest.setTeam_id(this.teamdId);
            registerTokenRequest.setGroup_id(getString(R.string.personGroupId));
            registerTokenRequest.setFace_id(personId);

            Call<RegisterTokenResponse> registerTokenResponseCall = service.registerToken(registerTokenRequest);
            registerTokenResponseCall.enqueue(new Callback<RegisterTokenResponse>() {
                @Override
                public void onResponse(Call<RegisterTokenResponse> call, Response<RegisterTokenResponse> response) {
                    int code = response.code();
                    if(code == 200){//SUCCESS
                        token = response.body().getToken();

                        Call<User> userCall = service.getUser(registerTokenRequest.getEmail(), "Bearer " + token);
                        userCall.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                int userStatus = response.code();
                                user = response.body();
//                                Log.d("Login", "responseCode: " + userStatus);
//                                Log.d("Login", "Name: " + user.getName());
//                                Log.d("Login", "username: " + user.getUsername());
//                                Log.d("Login", "Password: " + user.getPassword());
//                                Log.d("Login", "Email: " + user.getEmail());
//                                Log.d("Login", "ID: " + user.getId());
//                                Log.d("Login", "TeamID: " + user.getTeamID());
                                User.setUser(user);
                                startMainActivity();

                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                Log.d("Login", "OnFailure: " + t.getMessage());
                            }
                        });
                    }
                    else{
                        Log.d("CreateNewUser","code != 200");
                    }
                }

                @Override
                public void onFailure(Call<RegisterTokenResponse> call, Throwable t) {

                }
            });
        }
    }


    private void startMainActivity() {
        startActivity(new Intent(CreateNewUserActivity.this, MainActivity.class));
        finish();
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
            Log.d("DEBUG4","IT gets here");
            try{
                Log.d("DEBUG4","try");
                publishProgress("Syncing with server to add person...");
//                addLog("Request: Creating Person in person group" + params[0]);

                // Start the request to creating person.
                CreatePersonResult createPersonResult = faceServiceClient.createPerson(
                        params[0],
                        MainActivity.getEmail(),
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

//    public static String getPersonGroupId() {
//        return personGroupId;
//    }
}
