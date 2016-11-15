package sg.edu.smu.livelabs.citygangs.interfaces;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import sg.edu.smu.livelabs.citygangs.Area;
import sg.edu.smu.livelabs.citygangs.Models.LoginTokenRequest;
import sg.edu.smu.livelabs.citygangs.Models.LoginTokenResponse;
import sg.edu.smu.livelabs.citygangs.Models.RegisterTokenRequest;
import sg.edu.smu.livelabs.citygangs.Models.RegisterTokenResponse;
import sg.edu.smu.livelabs.citygangs.User;
import sg.edu.smu.livelabs.citygangs.UserArea;

/**
 * Created by tomrolandus on 11/11/16.
 */

public interface ServerInterface {
    @POST("login")
    Call<LoginTokenResponse> getTokenAccess(@Body LoginTokenRequest loginTokenRequest);

    @POST("register")
    Call<RegisterTokenResponse> registerToken(@Body RegisterTokenRequest registerTokenRequest);

    @Headers("Accept: application/json")
    @GET("user/{email}")
    Call<User> getUser(@Path("email") String email, @Header("Authorization") String authorization);

    @Headers("Accept: application/json")
    @GET("users/face/{face_id}")
    Call<User> getUserByFaceId(@Path("face_id") String faceId, @Header("Authorization") String authorization);

    @GET("areas/{id}")
    Call<Area> getArea(@Path("id") int id, @Header("Authorization") String authorization);

    @Headers("Accept: application/json")
    @GET("areas")
    Call<List<Area>> getAreas( @Header("Authorization") String authorization);

    @Headers("Accept: application/json")
    @POST("user/update")
    Call<User> updateLocation(@Body User user,  @Header("Authorization") String authorization);

    @Headers("Accept: application/json")
    @POST("user_areas/add/distance")
    Call<UserArea> addDistance(@Body UserArea userArea, @Header("Authorization") String authorization);

    @Headers("Accept: application/json")
    @GET("user_areas/{user_id}/{area_id}")
    Call<UserArea> findByUserIdAndAreaId(@Path("user_id") int user_id,@Path("area_id") int area_id, @Header("Authorization") String authorization);

    @Headers("Accept: application/json")
    @POST("user_areas/add")
    Call<UserArea> addUserArea(@Body UserArea userArea, @Header("Authorization") String authorization);

    //    @Headers({"Accept: application/json",
//            "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjEsImlzcyI6Imh0dHA6XC9cL2lzNDE2YXBwLjEzOS41OS4yMzguMjcubmlwLmlvXC9hcGlcL2xvZ2luIiwiaWF0IjoxNDc4OTI5MDUyLCJleHAiOjE0Nzg5MzI2NTIsIm5iZiI6MTQ3ODkyOTA1MiwianRpIjoiZmU5MDFjMDRkZWY2M2M3ODRjMzY1ZjVhNmM4YTUxNmYifQ.nrz5daYC-VED8Z37lOFCZJgwAJcyCAFPJHg7EEXKr48"})
//    @GET("users")
//    Call<List<User>> getAllUsers();
}