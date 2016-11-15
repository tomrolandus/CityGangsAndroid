package sg.edu.smu.livelabs.citygangs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

public class EnterEmailActivity extends AppCompatActivity {
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_email);

                //EDITTEXT change listener
        EditText emailET = (EditText) findViewById(R.id.emailET);
        emailET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                email = s.toString();
                MainActivity.setEmail(email);
            }
        });
    }

    public void startCreateNewUserActivity(View view){
        if(email != null)
            startActivity(new Intent(getBaseContext(), CreateNewUserActivity.class));
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
