package sg.edu.smu.livelabs.citygangs;

import android.os.AsyncTask;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;

import com.microsoft.projectoxford.face.FaceServiceClient;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.contract.CreatePersonResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by tomrolandus on 10/11/16.
 */
public class PersonActivity extends AppCompatActivity{
    boolean addNewPerson;
    String personId;
    String personGroupId;
    String oldPersonName;

    private static final int REQUEST_SELECT_IMAGE = 0;

    //background task of adding a person to person group
    class AddPersonTask extends AsyncTask<String, String, String>{

        // Progress dialog popped up when communicating with server.
        ProgressDialog progressDialog;
        boolean mAddFace;
        AddPersonTask (boolean addFace){
            mAddFace = addFace;
        }
        @Override
        protected String doInBackground(String... params) {
            FaceServiceClient faceServiceClient = Tools.getFaceServiceClient();
            try {
                publishProgress("Syncing with the server to add person...");


                //start the request to create a person
                CreatePersonResult createPersonResult = faceServiceClient.createPerson(
                        params[0],
                        getString(R.string.user_provided_person_name),
                        getString(R.string.user_provided_description_data));
                return createPersonResult.personId.toString();
            } catch (Exception e){
                publishProgress(e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPreExecute(){
            setUiBeforeBackgroundTask();
        }

        @Override
        protected void onPostExecute(String result){
            progressDialog.dismiss();
            if(result != null){
                personId = result;

                if(mAddFace){
                    addFace();
                }
                else{
                    doneAndSave();
                }
            }
        }

        private void setUiBeforeBackgroundTask() {
            progressDialog.show();
        }



    }

    private void addFace(){
        Intent intent = new Intent(this,SelectImageActivity.class);
        startActivityForResult(intent, REQUEST_SELECT_IMAGE);
    }


    public void doneAndSave(View view) {
        if (personId == null) {
            new AddPersonTask(false).execute(personGroupId);
        } else {
            doneAndSave();
        }
    }


    private void doneAndSave() {
        TextView textWarning = (TextView)findViewById(R.id.info);
//        EditText editTextPersonName = (EditText)findViewById(R.id.edit_person_name);
//        String newPersonName = editTextPersonName.getText().toString();
//        if (newPersonName.equals("")) {
//            textWarning.setText(R.string.person_name_empty_warning_message);
//            return;
//        }
//
//        StorageHelper.setPersonName(personId, newPersonName, personGroupId, PersonActivity.this);

        finish();
    }
}
