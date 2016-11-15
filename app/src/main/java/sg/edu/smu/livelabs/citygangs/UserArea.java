package sg.edu.smu.livelabs.citygangs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Philip on 2016-11-16.
 */

public class UserArea {

    @SerializedName("user_id")
    @Expose
    private int user_id;

    @SerializedName("area_id")
    @Expose
    private int area_id;

    @SerializedName("distance")
    @Expose
    private double distance;

    public int getUser_id() {
        return user_id;
    }

    public int getArea_id() {
        return area_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDistance() {
        return distance;

    }





}
