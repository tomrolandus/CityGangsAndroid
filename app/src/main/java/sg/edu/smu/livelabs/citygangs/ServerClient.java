package sg.edu.smu.livelabs.citygangs;

/**
 * Created by tomrolandus on 11/11/16.
 */

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ServerClient {

    public static final String BASE_URL =   "is416app.139.59.238.27.nip.io/api";
    private static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
