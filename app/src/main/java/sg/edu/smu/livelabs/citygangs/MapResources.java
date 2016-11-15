package sg.edu.smu.livelabs.citygangs;

import android.graphics.Color;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import java.util.concurrent.ThreadLocalRandom;

import java.util.ArrayList;
//TODO ADVANCED change to different shapes of polygons
//TODO ADVANCED implement ray-casting algorithm to check whether in polygon
//TODO implement method to check whether inside square
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

//    public void createAreas() {
//        double squareSize = 0.02; // size of each square
//        areas = new ArrayList<Area>();
//        LatLng iLatLng = new LatLng(1.2696700, 103.7950700); // Latitude and Longitude of bottom left corner of bottom left square
//        ArrayList<LatLng> tmpLatLgns = new ArrayList<LatLng>();
//        ;
//        Area tmpArea;
//        int counter = 0;
//        //MAKES THE SQUARES ON THE MAP
//        for (int i = 0; i < 4; i++) {
//            for (int j = 0; j < 4; j++) {
////                if((i >= 11 && j == 0) || (i >= 12 && j == 1) || (i >= 13 && j ==2)) continue; // TO GET NICE EDGE ON BOTTOM RIGHT OF MAP
//                tmpLatLgns = new ArrayList<LatLng>();
//                tmpLatLgns.add(new LatLng(iLatLng.latitude + squareSize * j, iLatLng.longitude + squareSize * i));
//                tmpLatLgns.add(new LatLng(tmpLatLgns.get(0).latitude, tmpLatLgns.get(0).longitude + squareSize));
//                tmpLatLgns.add(new LatLng(tmpLatLgns.get(1).latitude + squareSize, tmpLatLgns.get(1).longitude));
//                tmpLatLgns.add(new LatLng(tmpLatLgns.get(2).latitude, tmpLatLgns.get(0).longitude));
//                tmpArea = new Area(1, tmpLatLgns, 0x40001010, Color.RED); // CREATES THE AREAS, FOR NOW ALL FROM SAME TEAM
//                areas.add(tmpArea);
//            }
//            counter++;
//
//        }
//
//    }

    public void createAreas() {
        double squareSize = 0.005; // size of each square
        areas = new ArrayList<Area>();
        LatLng iLatLng = new LatLng(1.2696700, 103.7950700); // Latitude and Longitude of bottom left corner of bottom left square
//        ArrayList<LatLng> tmpLatLgns = new ArrayList<LatLng>();
        Area tmpArea;
        LatLng latLng;
        int counter = 0;
        //MAKES THE SQUARES ON THE MAP
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                Log.d("MAP","forLoop");
                int rand = ThreadLocalRandom.current().nextInt(1, 4 + 1);
                latLng = new LatLng(iLatLng.latitude + squareSize * i,iLatLng.longitude + squareSize * j);
                tmpArea = new Area(rand, latLng);
                areas.add(tmpArea);
            }
            counter++;

        }

    }


}
