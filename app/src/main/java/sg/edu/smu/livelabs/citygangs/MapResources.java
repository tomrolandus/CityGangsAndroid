package sg.edu.smu.livelabs.citygangs;

import android.graphics.Color;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.microsoft.projectoxford.face.FaceServiceClient;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sg.edu.smu.livelabs.citygangs.Models.AddAreaRequest;
import sg.edu.smu.livelabs.citygangs.Models.AddAreaResponse;
import sg.edu.smu.livelabs.citygangs.interfaces.ServerInterface;
//TODO ADVANCED change to different shapes of polygons
//TODO ADVANCED implement ray-casting algorithm to check whether in polygon
//TODO implement method to check whether inside square
/**
 * Created by tomrolandus on 11/11/16.
 */
//latitude: up and down
// longitude: left and right

public class MapResources {
    public static final String BASE_URL = "http://is416app.139.59.238.27.nip.io/api/";
    private ServerInterface service;
    private List<Area> areas;
    public MapResources() {
//        createAreas();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(ServerInterface.class);
//        addAreasToDatabase();
    }

//    public List<Area> getAreas() {
//        return areas;
////    }

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
        double squareSize = 0.01; // size of each square
        areas = new ArrayList<Area>();
        LatLng iLatLng = new LatLng(1.2696700, 103.7950700); // Latitude and Longitude of bottom left corner of bottom left square
//        ArrayList<LatLng> tmpLatLgns = new ArrayList<LatLng>();
        Area tmpArea;
        LatLng latLng;
        int counter = 0;
        //MAKES THE SQUARES ON THE MAP
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 8; j++) {
//                Log.d("MAP","forLoop");
                int rand = ThreadLocalRandom.current().nextInt(1, 4 + 1);
                latLng = new LatLng(iLatLng.latitude + squareSize * i,iLatLng.longitude + squareSize * j);
                Log.d("MAP4","RANDOM: "+rand);
                tmpArea = new Area(rand, latLng, squareSize);
                areas.add(tmpArea);
            }
            counter++;

        }

    }

    public void addAreasToDatabase(){
//        Area area = areas.get(0);
//        AddAreaRequest addAreaRequest = new AddAreaRequest();
//            addAreaRequest.setLatitude(area.getLatitude());
//            addAreaRequest.setLongitude(area.getLongitude());
//            addAreaRequest.setTeam_id(area.getTeam_id());
//            addAreaRequest.setLength(area.getLength());
//        Log.d("MAP3","latitude: "+addAreaRequest.getLatitute());
//        Log.d("MAP3","longitude: "+addAreaRequest.getLongitude());
//        Log.d("MAP3","team_id: "+addAreaRequest.getTeam_id());
//        Log.d("MAP3","length: "+addAreaRequest.getLength());
//        Call<AddAreaResponse> addAreaResponseCall = service.addAreaArguments(addAreaRequest, "Bearer " +MainActivity.getMainUser().getToken());
//        addAreaResponseCall.enqueue(new Callback<AddAreaResponse>() {
//            @Override
//            public void onResponse(Call<AddAreaResponse> call, Response<AddAreaResponse> response) {
//                Log.d("MAP4","On success code: "+response.code());
//            }
//
//            @Override
//            public void onFailure(Call<AddAreaResponse> call, Throwable t) {
//
//            }
//        });
//        Log.d("MAP4","Token: "+MainActivity.getMainUser().getToken());
        Log.d("MAP6","areas.size: "+areas.size());
        for(int i = 0; i < areas.size(); i++){
//            if(i%10 == 0){
//                Log.d("MAP6","i%10");
//                for (int j = 0 ; j < 10000; j++){
//Log.d("something","somethign");
//                }
//            }
            AddAreaRequest addAreaRequest = new AddAreaRequest();
            addAreaRequest.setLatitude(areas.get(i).getLatitude());
            addAreaRequest.setLongitude(areas.get(i).getLongitude());
            addAreaRequest.setTeam_id(areas.get(i).getTeam_id());
            addAreaRequest.setLength(areas.get(i).getLength());
//            Log.d("MAP6","latitude: "+addAreaRequest.getLatitute());
//        Log.d("MAP6","longitude: "+addAreaRequest.getLongitude());
//        Log.d("MAP6","team_id: "+addAreaRequest.getTeam_id());
//        Log.d("MAP6","length: "+addAreaRequest.getLength());
            Call<AddAreaResponse> addAreaResponseCall = service.addAreaArguments(addAreaRequest,"Bearer " +MainActivity.getMainUser().getToken());
            addAreaResponseCall.enqueue(new Callback<AddAreaResponse>() {
                @Override
                public void onResponse(Call<AddAreaResponse> call, Response<AddAreaResponse> response) {
//                    Log.d("MAP6","onsuccess code: "+response.code());
                }

                @Override
                public void onFailure(Call<AddAreaResponse> call, Throwable t) {

                }
            });
//
////            service.addArea(a, "Bearer " + MainActivity.getMainUser().getToken(), new Callback<Boolean>() {
////                @Override
////                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
////                    Log.d("MAP4","code: " +response.code());
////                }
////
////                @Override
////                public void onFailure(Call<Boolean> call, Throwable t) {
////                    Log.d("MAP4","onFailure: " +t.getMessage());
////                }
////            });
        }
    }

    public List<Area> getAreas(){

        final Call<List<Area>> getAreasCall = service.getAreas("Bearer "+MainActivity.getMainUser().getToken());
        getAreasCall.enqueue(new Callback<List<Area>>() {
            @Override
            public void onResponse(Call<List<Area>> call, Response<List<Area>> response) {
                Log.d("getAreas","code: "+response.code());
                areas = response.body();
                Log.d("getAreas","areas.size: " +areas.size());
            }

            @Override
            public void onFailure(Call<List<Area>> call, Throwable t) {

            }
        });
        return areas;
    }


}
