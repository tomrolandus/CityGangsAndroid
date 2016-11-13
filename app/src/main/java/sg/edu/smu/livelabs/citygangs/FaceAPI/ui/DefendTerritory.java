package sg.edu.smu.livelabs.citygangs.FaceAPI.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

import sg.edu.smu.livelabs.citygangs.R;

public class DefendTerritory extends AppCompatActivity {
    // Flag to indicate the request of the next task to be performed
    private static final int REQUEST_TAKE_PHOTO = 0;
    private static final int REQUEST_SELECT_IMAGE_IN_ALBUM = 1;
    private static int MY_REQUEST_CODE;

    // The URI of photo taken with camera
    private Uri mUriPhotoTaken;

    // When the activity is created, set all the member variables to initial state.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("DEBUG","SelectImageActivity_OnCreate");
        setContentView(R.layout.activity_defend_territory);
    }

    // Save the activity state when it's going to stop.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("ImageUri", mUriPhotoTaken);
    }

    // Recover the saved state when the activity is recreated.
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mUriPhotoTaken = savedInstanceState.getParcelable("ImageUri");
    }

    // Deal with the result of selection of the photos and faces.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {
            case REQUEST_TAKE_PHOTO:
            case REQUEST_SELECT_IMAGE_IN_ALBUM:
                if (resultCode == RESULT_OK) {
                    Uri imageUri;
                    if (data == null || data.getData() == null) {
                        imageUri = mUriPhotoTaken;
                    } else {
                        imageUri = data.getData();
                    }
                    Intent intent = new Intent();
                    intent.setData(imageUri);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("debugPermission", "PERMISSION_GRANTED");
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getPackageManager()) != null) {
                    // Save the photo taken to a temporary file.
                    File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                    try {
                        File file = File.createTempFile("IMG_", ".jpg", storageDir);
                        mUriPhotoTaken = Uri.fromFile(file);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUriPhotoTaken);
                        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
                    } catch (IOException e) {
                        setInfo(e.getMessage());
                    }
                }
            }
            else {
                Log.d("debugPermission", "PERMISSION_DENIED");
                // Your app will not have this permission. Turn off all functions
                // that require this permission or it will force close like your
                // original question
            }
        }
    }

    // When the button of "Take a Photo with Camera" is pressed.
    public void takePhoto(View view) {
        if (checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d("debugPermission", "checkSelfPermission");

            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    MY_REQUEST_CODE);
        }
        else{
            Log.d("debugPermission","checkSeflPermission else");
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(intent.resolveActivity(getPackageManager()) != null) {
                // Save the photo taken to a temporary file.
                File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                try {
                    File file = File.createTempFile("IMG_", ".jpg", storageDir);
                    mUriPhotoTaken = Uri.fromFile(file);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mUriPhotoTaken);
                    startActivityForResult(intent, REQUEST_TAKE_PHOTO);
                } catch (IOException e) {
                    setInfo(e.getMessage());
                }
            }
        }

    }

    // Set the information panel on screen.
    private void setInfo(String info) {
        TextView textView = (TextView) findViewById(R.id.info);
        textView.setText(info);
    }
}
