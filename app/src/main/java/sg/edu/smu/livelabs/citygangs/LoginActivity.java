package sg.edu.smu.livelabs.citygangs;

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
    private TextView loginStatus;

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

        loginStatus = (TextView) findViewById(R.id.logingStatusTV);


        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(ServerInterface.class);

    }

    public void autoLogin(View view) {

        //here i just give it the hardcoded user
        LoginTokenRequest loginTokenRequest = new LoginTokenRequest();
        loginTokenRequest.setEmail("test@example.com");
        loginTokenRequest.setPassword("testtest");
        final String email = "test@example.com";

        Call<LoginTokenResponse> tokenResponseCall = service.getTokenAccess(loginTokenRequest);
        tokenResponseCall.enqueue(new Callback<LoginTokenResponse>() {
            @Override
            public void onResponse(Call<LoginTokenResponse> call, Response<LoginTokenResponse> response) {
                int statusCode = response.code();
                LoginTokenResponse loginTokenResponse = response.body();
                token = loginTokenResponse.getToken();
                Log.d("autoLogin", "Token: " + token);
            }

            @Override
            public void onFailure(Call<LoginTokenResponse> call, Throwable t) {
                Log.d("LoginActivity", "OnFailure: " + t.getMessage());
            }

        });


        Call<User> userCall = service.getUser(email, "Bearer " + token);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                int userStatus = response.code();
                if(userStatus == 200){
                    loginStatus.setText("Connected");
                }
                User user = response.body();
                Log.d("autoLogin", "responseCode: " + userStatus);
                Log.d("autoLogin", "Name: " + user.getName());
                Log.d("autoLogin", "username: " + user.getUsername());
                Log.d("autoLogin", "Password: " + user.getPassword());
                Log.d("autoLogin", "Email: " + user.getEmail());
                Log.d("autoLogin", "ID: " + user.getId());
                Log.d("autoLogin", "TeamID: " + user.getTeamID());


            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });


    }

    public void login(View view) {
        LoginTokenRequest loginTokenRequest = new LoginTokenRequest();
        loginTokenRequest.setEmail(email);
        loginTokenRequest.setPassword(password);

        Call<LoginTokenResponse> tokenResponseCall = service.getTokenAccess(loginTokenRequest);
        tokenResponseCall.enqueue(new Callback<LoginTokenResponse>() {
            @Override
            public void onResponse(Call<LoginTokenResponse> call, Response<LoginTokenResponse> response) {
//                Log.d("DEBUG","onResponse, code: " +response.code());
                int statusCode = response.code();
                if (statusCode == 200) {
                    loginStatus.setText("Connected");


                    Call<User> userCall = service.getUser(email, "Bearer " + token);
                    userCall.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            int userStatus = response.code();
                            User user = response.body();
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
                            Log.d("Login","OnFailure: " +t.getMessage());
                        }
                    });

                } else {
                    loginStatus.setText("Email/PW wrong");
                }
            }

            @Override
            public void onFailure(Call<LoginTokenResponse> call, Throwable t) {
                Log.d("DEBUG", "OnFailure: " + t.getMessage());
            }
        });
//        Log.d("DEBUG", "Login1");
//        Log.d("DEBUG", "email: " + email);
//        Log.d("DEBUG", "Password: " +password);

    }

//        Call<List<User>> allUsersCall = service.getAllUsers();
//        allUsersCall.enqueue(new Callback<List<User>>() {
//            @Override
//            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
//                int allUsersCallStatus = response.code();
//                List<User> allUsers = response.body();
//                Log.d("allUsersCall", "onResponseAllUsers: " +allUsersCallStatus);
//                if(allUsers != null){
//                    Log.d("allUsersCall", "size: " +allUsers.size());
//                }
//                else{
//                    Log.d("allUsersCall", "allUsers is null" );
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<List<User>> call, Throwable t) {
//                Log.d("autologinAllUsers",t.getMessage());
//            }
//
//        });

}
