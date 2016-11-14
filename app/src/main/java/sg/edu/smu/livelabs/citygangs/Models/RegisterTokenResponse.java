package sg.edu.smu.livelabs.citygangs.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tomrolandus on 14/11/16.
 */

public class RegisterTokenResponse {
    @SerializedName("token")
    private String token;

    public String getToken(){
        return token;
    }
}
