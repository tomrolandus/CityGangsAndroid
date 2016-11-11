package sg.edu.smu.livelabs.citygangs;

/**
 * Created by tomrolandus on 11/11/16.
 */

import retrofit2.Retrofit;


public class ServerClient {

    public static final String BASE_URL =   "is416app.139.59.238.27.nip.io/api/login";

    public static <S> S createService(Class<S> serviceClass, String username, String password){
        Retrofit builder = new Retrofit.Builder().baseUrl(BASE_URL).build();
    }
}
