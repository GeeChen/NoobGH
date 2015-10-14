package com.example.user;


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

public class Login extends AppCompatActivity {
	
	Button SignIn;
	Button SignUp;
	ActionBar actionBar;
	EditText name, pass;
	
	private static final String TAG = "Login";
	
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
		name = (EditText)findViewById(R.id.txtUsername);
        pass = (EditText)findViewById(R.id.txtPassword);
        
		SignIn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
															
				// TODO Auto-generated method stub
				JSONParser parser = new JSONParser();
				String url ="http://queuingserver.esy.es/login.php";
				JSONObject obj;
				
				if(name.getText().toString().matches(""))
				{
					Toast.makeText(getApplicationContext(), "You did not enter username", Toast.LENGTH_SHORT).show();
				}
				else if(pass.getText().toString().matches(""))
				{
					Toast.makeText(getApplicationContext(), "You did not enter password", Toast.LENGTH_SHORT).show();
				} else {
					String username = name.getText().toString();
					String password = pass.getText().toString();
					
					String urlParameter = "name=" + username + "&" + "pass=" + password;																		
					
					try 
					{
						obj = parser.makeHttpRequest(url, "POST", urlParameter);
						// String codeResult = obj.getString("code");
							int responseCode = obj.getInt("code");
							
							if (responseCode == 1)
							{
								Editor editor = manager.edit();
								editor.putString("username", username);
								editor.commit();
								
								Intent i = new Intent(Login.this, MainMenu.class);
								Login.this.startActivity(i);
								//Login.this.finish();
								
								
								
							}
						
							else
							{
								Toast.makeText(arg0.getContext(), "Incorrect username or password" , Toast.LENGTH_SHORT).show();
							}

					
					} 
					catch (JSONException e) 
					{
						e.printStackTrace();
					}
				}
			}			
		});

		SignUp.setOnClickListener(new View.OnClickListener() {
		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent i = new Intent(Login.this, Register.class);
				Login.this.startActivity(i);
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		
	   
		
	    return true;
	}
	
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
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
