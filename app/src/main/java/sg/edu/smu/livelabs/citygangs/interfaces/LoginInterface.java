package sg.edu.smu.livelabs.citygangs.interfaces;




import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import sg.edu.smu.livelabs.citygangs.Models.LoginTokenRequest;
import sg.edu.smu.livelabs.citygangs.Models.LoginTokenResponse;

/**
 * Created by tomrolandus on 11/11/16.
 */

public interface LoginInterface {
    @POST("login")
    Call<LoginTokenResponse> getTokenAccess(@Body LoginTokenRequest loginTokenRequest);
}
