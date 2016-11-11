package sg.edu.smu.livelabs.citygangs;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telecom.Call;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import static com.microsoft.projectoxford.face.common.RequestMethod.GET;

public class ServerService extends Service {
    public ServerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


//    public static serverResponse parseJSON(String response) {
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        Gson Gson = gsonBuilder.create();
//
//    }
}
