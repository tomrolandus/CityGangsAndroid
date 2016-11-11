package sg.edu.smu.livelabs.citygangs;

import android.graphics.Color;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;

/**
 * Created by tomrolandus on 11/11/16.
 */
//latitude: up and down
// longitude: left and right

public class MapResources {
    private ArrayList<Area> areas;

    public MapResources() {
        createAreas();
    }

    public ArrayList<Area> getAreas() {
        return areas;
    }

    public void createAreas() {
        double squareSize = 0.005;
        areas = new ArrayList<Area>();
        LatLng iLatLng = new LatLng(1.2696700, 103.8100700);
        ArrayList<LatLng> tmpLatLgns = new ArrayList<LatLng>();
        ;
        Area tmpArea;

        //MAKES THE SQUARES ON THE MAP
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 10; j++) {
                if((i >= 11 && j == 0) || (i >= 12 && j == 1) || (i >= 13 && j ==2)) continue; // TO GET NICE EDGE ON BOTTOM RIGHT OF MAP
                tmpLatLgns = new ArrayList<LatLng>();
                tmpLatLgns.add(new LatLng(iLatLng.latitude + squareSize * j, iLatLng.longitude + squareSize * i));
                tmpLatLgns.add(new LatLng(tmpLatLgns.get(0).latitude, tmpLatLgns.get(0).longitude + squareSize));
                tmpLatLgns.add(new LatLng(tmpLatLgns.get(1).latitude + squareSize, tmpLatLgns.get(1).longitude));
                tmpLatLgns.add(new LatLng(tmpLatLgns.get(2).latitude, tmpLatLgns.get(0).longitude));
                tmpArea = new Area(1, tmpLatLgns, 0x40001010, Color.RED); // CREATES THE AREAS, FOR NOW ALL FROM SAME TEAM
                areas.add(tmpArea);
            }

        }

    }


}
