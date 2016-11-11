package sg.edu.smu.livelabs.citygangs;

import android.graphics.Color;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;

import java.util.ArrayList;

/**
 * Created by tomrolandus on 11/11/16.
 */

public class Area {

    private Polygon poly;

    public ArrayList<LatLng> getLatLngs() {
        return latLngs;
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



    public Area(int id, ArrayList<LatLng> c, int f, int s){
        latLngs = c;
        teamID = id;
        fillColor = f;
        strokeColor = s;
    }

    public Polygon getPolygon(){
        return poly;
    }
}
