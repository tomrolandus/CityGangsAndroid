package sg.edu.smu.livelabs.citygangs;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tomrolandus on 11/11/16.
 */

public class Area {

    private Polygon poly;

    public ArrayList<LatLng> getLatLngs() {
        return latLngs;
    }

    public static List<Area> areas;

    public static List<Area> getAreas() {
        return areas;
    }

    public static void setAreas(List<Area> areas) {
        Area.areas = areas;
    }

    public void setLatLngs(ArrayList<LatLng> latLngs) {
        this.latLngs = latLngs;
    }

    private ArrayList<LatLng> latLngs;
    private int teamID;

    public int getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(int strikeColor) {
        this.strokeColor = strikeColor;
    }

    public int getFillColor() {
        return fillColor;
    }

    public void setFillColor(int fillColor) {
        this.fillColor = fillColor;
    }


    private int fillColor;
    private int strokeColor;

    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    public Polygon getPoly() {
        return poly;
    }

    public int getTeamID() {
        return teamID;
    }

    public int getId() {
        return id;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLength() {
        return length;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setId(int id) {
        this.id = id;

    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public Area(int id, ArrayList<LatLng> c, int f, int s){
        latLngs = c;
        teamID = id;
        fillColor = f;
        strokeColor = s;
    }

    public Polygon getPolygon(){
        return poly;
    }

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("longitude")
    @Expose
    private double longitude;

    @SerializedName("latitude")
    @Expose
    private double latitude;

    @SerializedName("length")
    @Expose
    private double length;

    @SerializedName("team_id")
    @Expose
    private int team_id;
}

