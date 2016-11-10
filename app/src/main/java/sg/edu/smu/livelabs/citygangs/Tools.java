package sg.edu.smu.livelabs.citygangs;

import android.app.Application;
import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.FaceServiceRestClient;
import com.microsoft.projectoxford.face.contract.Face;


/**
 * Created by tomrolandus on 10/11/16.
 */
public class Tools extends Application {
    private static FaceServiceClient sFaceServiceClient;
    @Override
    public void onCreate(){
        super.onCreate();
        sFaceServiceClient = new FaceServiceRestClient(getString(R.string.face_licence));
    }

    public static FaceServiceClient getFaceServiceClient(){
        return sFaceServiceClient;
    }
}
