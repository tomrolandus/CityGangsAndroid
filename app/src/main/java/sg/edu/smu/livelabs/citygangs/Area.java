package sg.edu.smu.livelabs.citygangs;

import android.graphics.Color;

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
    private int[] colors = new int[]{0x50ffff00,0x40ff0000,0x4000ff00,0x400000ff};

    private Polygon poly;

    public ArrayList<LatLng> getLatLngs() {
        return latLngs;
    }

    public List<Area> areas;
    //public List<Area> getAreas() {return areas; }

    public void setLatLngs(ArrayList<LatLng> latLngs) {
        this.latLngs = latLngs;
    }

    private ArrayList<LatLng> latLngs;

    public int getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(int strikeColor) {
        this.strokeColor = strikeColor;
    }

    public int getFillColor() {
        if(fillColor == 0){
            fillColor = colors[id];
        }
        return fillColor;
    }

    public void setFillColor(int fillColor) {
        this.fillColor = fillColor;
    }


    private int fillColor = 0;
    private int strokeColor;


    public Polygon getPoly() {
        return poly;
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

    public Area(int id, LatLng latLng,double length){
        latitude = latLng.latitude;
        longitude = latLng.longitude;
        team_id = id;
        fillColor = colors[id-1];
        strokeColor = Color.BLACK;
        this.length  =length;
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

