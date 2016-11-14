package sg.edu.smu.livelabs.citygangs;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import retrofit2.Callback;
import retrofit2.Response;
import sg.edu.smu.livelabs.citygangs.interfaces.ServerInterface;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sg.edu.smu.livelabs.citygangs.Models.LoginTokenRequest;
import sg.edu.smu.livelabs.citygangs.Models.LoginTokenResponse;

public class LoginActivity extends AppCompatActivity {
    public static final String BASE_URL = "http://is416app.139.59.238.27.nip.io/api/";
    private ServerInterface service;
    private String token;
    private String email;
    private String password;
    private User user;
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
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }


    private void getTokenAndUser(final LoginTokenRequest loginTokenRequest) {
        this.token = null;
        this.user = null;
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

    public void getUser(String e) {
        this.user = null;
        Call<User> userCall = service.getUser(e, "Bearer " + this.token);
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



            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("Login", "OnFailure: " + t.getMessage());
            }
        });

        return;
    }

    public void login(View view) {
        LoginTokenRequest loginTokenRequest = new LoginTokenRequest();
        loginTokenRequest.setEmail(email);
        loginTokenRequest.setPassword(password);

        generateToken(loginTokenRequest);
        getUser(loginTokenRequest.getEmail());

    }

    public void startCreateNewUserActivity(View view){
        startActivity(new Intent(getBaseContext(), CreateNewUserActivity.class));
    }

}
