package sg.edu.smu.livelabs.citygangs;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        //will run until it is stopped
        Toast.makeText(this, "Service started", Toast.LENGTH_LONG).show();
        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Toast.makeText(this,"Service is Destroyed", Toast.LENGTH_LONG).show();
    }

}
