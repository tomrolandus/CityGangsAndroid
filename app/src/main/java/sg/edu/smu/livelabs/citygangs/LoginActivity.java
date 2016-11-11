package sg.edu.smu.livelabs.citygangs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import retrofit2.Callback;
import retrofit2.Response;
import sg.edu.smu.livelabs.citygangs.interfaces.LoginInterface;

import android.util.Log;
import android.view.View;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sg.edu.smu.livelabs.citygangs.Models.LoginTokenRequest;
import sg.edu.smu.livelabs.citygangs.Models.LoginTokenResponse;

public class LoginActivity extends AppCompatActivity {
    public static final String BASE_URL =   "http://is416app.139.59.238.27.nip.io/api/";
    private LoginInterface service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(LoginInterface.class);

    }

    public void autoLogin(View view) {

        //here i just give it the hardcoded user
        LoginTokenRequest loginTokenRequest = new LoginTokenRequest();
        loginTokenRequest.setEmail("test@example.com");
        loginTokenRequest.setPassword("testtest");

        Call<LoginTokenResponse> tokenResponseCall = service.getTokenAccess(loginTokenRequest);
        tokenResponseCall.enqueue(new Callback<LoginTokenResponse>() {
            @Override
            public void onResponse(Call<LoginTokenResponse> call, Response<LoginTokenResponse> response) {
                int statusCode = response.code();
                Log.d("LoginActivity","OnResponse: "+statusCode);
            }

            @Override
            public void onFailure(Call<LoginTokenResponse> call, Throwable t) {
                Log.d("LoginActivity", "OnFailure: " +t.getMessage());
            }
        });
    }
}
