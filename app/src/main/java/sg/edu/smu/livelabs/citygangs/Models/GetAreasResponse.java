package sg.edu.smu.livelabs.citygangs.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Emil on 15/11/16.
 */

public class GetAreasResponse {

    @SerializedName("id")
    private int id;
    @SerializedName("longitude")
    private double longitude;
    @SerializedName("latitude")
    private double latitude;
    @SerializedName("length")
    private double length;
    @SerializedName("team_id")
    private int team_id;


    public int getAreas(){
        return id;
    }
}
