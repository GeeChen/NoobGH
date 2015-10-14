package com.example.user;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import com.example.user.http.Http;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Register";
    Button CreateAcc;
    EditText name, pass, contactNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.register);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#74b5df"));
        actionBar.setBackgroundDrawable(colorDrawable);

        actionBar.setTitle("  " + "Register");

        CreateAcc = (Button) findViewById(R.id.button1);
        name = (EditText) findViewById(R.id.editText1);
        pass = (EditText) findViewById(R.id.editText2);
        contactNum = (EditText) findViewById(R.id.editText3);


        CreateAcc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:      // Register
                String url = "http://queuingserver.esy.es/register.php";

                String username = name.getText().toString();
                String password = pass.getText().toString();
                String usertel = contactNum.getText().toString();
                String urlParameter = "name=" + username + "&pass=" + password + "&contactNum=" + usertel;

                String[] params = new String[]{
                        Http.POST,
                        url,
                        urlParameter
                };
                new RegisterHttp(this).execute(params);
                break;
        }
    }

    private class RegisterHttp extends Http {
        private ProgressDialog dialog;
        private Activity context;

        public RegisterHttp(Activity context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            String title = "";
            String message = "Registering ...";
            dialog = ProgressDialog.show(context, title, message, true);
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            dialog.dismiss();

            try {
                int responseCode = jsonObject.getInt("code");

                if (responseCode == 1) {
                    Intent i = new Intent(context, Login.class);
                    context.startActivity(i);
                } else {
                    Toast.makeText(context, "Please insert correctly", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
    }
}