package com.example.user;


import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import com.example.user.http.Http;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Login";

    Button SignIn, SignUp;
    EditText name, pass;

    private SharedPreferences manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Login");
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#000000"));
        actionBar.setBackgroundDrawable(colorDrawable);

        manager = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

//		ActionBar mActionBar = getSupportActionBar();
//		mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
//		
//		actionBar = getSupportActionBar();
//		ColorDrawable colorDrawable = new ColorDrawable(Color.WHITE); 
//		actionBar.setBackgroundDrawable(colorDrawable);

        SignIn = (Button) findViewById(R.id.button1);
        SignUp = (Button) findViewById(R.id.button2);
        name = (EditText) findViewById(R.id.txtUsername);
        pass = (EditText) findViewById(R.id.txtPassword);

        SignIn.setOnClickListener(this);
        SignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:      // Login
                if (name.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "You did not enter username", Toast.LENGTH_SHORT).show();
                } else if (pass.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "You did not enter password", Toast.LENGTH_SHORT).show();
                } else {
                    String url = "http://queuingserver.esy.es/login.php";

                    String username = name.getText().toString();
                    String password = pass.getText().toString();
                    String urlParameter = "name=" + username + "&" + "pass=" + password;

                    String[] params = new String[]{
                            Http.POST,
                            url,
                            urlParameter
                    };
                    new LoginHttp(this, username).execute(params);
                }
                break;
            case R.id.button2:      // Register
                Intent i = new Intent(this, Register.class);
                startActivity(i);
                break;
        }
    }

    private class LoginHttp extends Http {
        private ProgressDialog dialog;
        private Activity context;
        private String username;

        public LoginHttp(Activity context, String username) {
            this.context = context;
            this.username = username;
        }

        @Override
        protected void onPreExecute() {
            String title = "";
            String message = "Authenticating ...";
            dialog = ProgressDialog.show(context, title, message, true);
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            dialog.dismiss();

            try {
                int responseCode = jsonObject.getInt("code");

                if (responseCode == 1) {
                    Editor editor = manager.edit();
                    editor.putString("username", username);
                    editor.apply();

                    Intent i = new Intent(context, MainMenu.class);
                    context.startActivity(i);
                } else {
                    Toast.makeText(context, "Incorrect username or password", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
