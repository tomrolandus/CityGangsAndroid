package sg.edu.smu.livelabs.citygangs;

import android.content.Intent;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sg.edu.smu.livelabs.citygangs.Models.RegisterTokenRequest;
import sg.edu.smu.livelabs.citygangs.Models.RegisterTokenResponse;
import sg.edu.smu.livelabs.citygangs.interfaces.ServerInterface;

public class CreateNewUserActivity extends AppCompatActivity {
    private String email;
    private String password;
    private String token;
    private String username;
    private User user;
    private int teamdId = 0;
    private String[] arraySpinner;
    public static final String BASE_URL = "http://is416app.139.59.238.27.nip.io/api/";
    private ServerInterface service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_user);


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


        //EDITTEXT change listener
        EditText emailET = (EditText) findViewById(R.id.emailET);
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
        if(this.email != null && this.password != null && this.username != null && this.teamdId != 0) {
            final RegisterTokenRequest registerTokenRequest = new RegisterTokenRequest();
            registerTokenRequest.setEmail(this.email);
            registerTokenRequest.setPassword(this.password);
            registerTokenRequest.setUsername(this.username);
            registerTokenRequest.setTeamId(this.teamdId);

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
                                Log.d("Login", "responseCode: " + userStatus);
                                Log.d("Login", "Name: " + user.getName());
                                Log.d("Login", "username: " + user.getUsername());
                                Log.d("Login", "Password: " + user.getPassword());
                                Log.d("Login", "Email: " + user.getEmail());
                                Log.d("Login", "ID: " + user.getId());
                                Log.d("Login", "TeamID: " + user.getTeamID());
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
}
