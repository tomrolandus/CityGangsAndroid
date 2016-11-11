package sg.edu.smu.livelabs.citygangs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by tomrolandus on 11/11/16.
 */

public class ServerResponse {


    public static ServerResponse parseJSON(String response){
        Gson gson = new GsonBuilder().create();
        ServerResponse serverResponse = gson.fromJson(response, ServerResponse.class);
        return serverResponse;
    }
}
