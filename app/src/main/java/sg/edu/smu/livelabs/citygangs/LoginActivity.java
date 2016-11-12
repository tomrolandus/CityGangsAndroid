package sg.edu.smu.livelabs.citygangs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import retrofit2.Callback;
import retrofit2.Response;
import sg.edu.smu.livelabs.citygangs.interfaces.ServerInterface;

import android.util.Log;
import android.view.View;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sg.edu.smu.livelabs.citygangs.Models.LoginTokenRequest;
import sg.edu.smu.livelabs.citygangs.Models.LoginTokenResponse;

public class LoginActivity extends AppCompatActivity {
    public static final String BASE_URL =   "http://is416app.139.59.238.27.nip.io/api/";
    private ServerInterface service;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
                Log.d("autoLogin","OnResponseToken: " +token);
            }

            @Override
            public void onFailure(Call<LoginTokenResponse> call, Throwable t) {
                Log.d("LoginActivity", "OnFailure: " +t.getMessage());
            }

        });


        Call<User> userCall = service.getUser(email, "Bearer " +token);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                int userStatus = response.code();
                User user = response.body();
                Log.d("autoLogin","onResponse: " +userStatus);
                Log.d("autoLogin","onResponse email call: " +user.getName());


            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

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
}
