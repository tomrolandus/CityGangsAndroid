package sg.edu.smu.livelabs.citygangs.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tomrolandus on 11/11/16.
 */

public class LoginTokenResponse {
    @SerializedName("token")
    private String token;

    public String getToken(){
        return token;
    }
}


