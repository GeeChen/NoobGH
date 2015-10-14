package com.example.user;

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

public class Register extends AppCompatActivity{
	
	Button CreateAcc; 
	ActionBar actionBar;
	EditText name, pass, contactNum;
	MenuItem item;

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
		name = (EditText)findViewById(R.id.editText1);
        pass = (EditText)findViewById(R.id.editText2);
        contactNum = (EditText)findViewById(R.id.editText3);

        
		
		CreateAcc.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				JSONParser parser = new JSONParser();
				String url ="http://queuingserver.esy.es/register.php";
				JSONObject obj;
				
				String username = name.getText().toString();
				String password = pass.getText().toString();
				String usertel = contactNum.getText().toString(); 
				
				//if (password.equals(repassword)) {
				//	
				//}
				
				String urlParameter = "name=" + username + "&" + "pass=" + password + "&" + "contactNum=" + usertel;
				
				try {
					obj = parser.makeHttpRequest(url, "POST", urlParameter);
					int responseCode = obj.getInt("code");
					
					if (responseCode == 1)
					{
						Intent i = new Intent(Register.this, Login.class);
						Register.this.startActivity(i);
					}
				
					else
					{
						Toast.makeText(arg0.getContext(), "Please insert correctly" , Toast.LENGTH_SHORT).show();
					}
					
					
					//Toast.makeText(arg0.getContext(), String.valueOf(num), Toast.LENGTH_SHORT).show();
				
				} catch (JSONException e) {
				e.printStackTrace();
				}
			
			}
		});
			
		
	   }
	}		
			
		
