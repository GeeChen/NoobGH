package com.example.user;

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
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainMenu extends AppCompatActivity {

	Button GetTic;
	Button ViewMyTicket;
	Button ViewCurrent;
	Button CancelTicket;
	ActionBar actionBar;
	
	private SharedPreferences manager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setIcon(R.drawable.queue);
		ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#74b5df"));     
		actionBar.setBackgroundDrawable(colorDrawable);
		
		actionBar.setTitle("  " + "Smart Queuing System");
		
		manager = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		GetTic = (Button) findViewById(R.id.button1);
		ViewCurrent = (Button) findViewById(R.id.button2);
		ViewMyTicket = (Button) findViewById(R.id.button3);
		CancelTicket = (Button) findViewById(R.id.button4);
		
		GetTic.setOnClickListener(new View.OnClickListener() 
		{
			
			@Override
			public void onClick(View arg0) 
			{
				JSONParser parser = new JSONParser();
				String url ="http://queuingserver.esy.es/ticket.php";
				JSONObject obj;
				
				String username = manager.getString("username", "");
				
				if (username != "") {
					try 
					{
						String urlParameters = "username=" + username;
						
						obj = parser.makeHttpRequest(url, "POST", urlParameters);
						// String codeResult = obj.getString("code");
						int num = obj.getInt("tic_num");
						
						if (num == -1) {
							Toast.makeText(arg0.getContext(), "You already got a ticket.", Toast.LENGTH_SHORT).show();
						} else {
							Intent i = new Intent(MainMenu.this, GetTicket.class);
							i.putExtra("tic_num", num);
							
							Editor edit = manager.edit();
							edit.putInt("tictic", num);
							edit.commit();
							
							MainMenu.this.startActivity(i);
						}
					
					} 
					catch (Exception e) 
					{
						Toast.makeText(arg0.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
					}
				}
				

			}
		});
		
		ViewMyTicket.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				JSONParser parser = new JSONParser();
				String url ="http://queuingserver.esy.es/view-my-ticket.php";
				JSONObject obj;
				
				String username = manager.getString("username", "");
				
				if (username != "") {
					try 
					{
						String urlParameters = "username=" + username;
						
						obj = parser.makeHttpRequest(url, "POST", urlParameters);
						int codeResult = obj.getInt("code");
						int num = obj.getInt("tic");											
						
						if (codeResult == 1) {
							Intent i = new Intent(MainMenu.this, ViewMyTicket.class);
							i.putExtra("tic", num);
							MainMenu.this.startActivity(i);
							
						} else {
							Intent i = new Intent(MainMenu.this, ViewMyTicket.class);					
							Toast.makeText(getApplicationContext(), "You haven't get a ticket", Toast.LENGTH_SHORT).show();
							MainMenu.this.startActivity(i);
						}
					
					}
					
				catch (Exception e) {
					Toast.makeText(arg0.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
				}
				
			   }
			}
				
		});
				
		
		
		ViewCurrent.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				JSONParser parser = new JSONParser();
				String url ="http://queuingserver.esy.es/get_current_next.php";
				JSONObject obj;
				
				try {
					obj = parser.getJSONFromUrl(url);
					// String codeResult = obj.getString("code");
					int code = obj.getInt("code");
					if(code != -1){
						
						int num = obj.getInt("tic_0");												
						
						Intent i = new Intent(MainMenu.this, DisCurrentTicket.class);
						i.putExtra("tic_num", num);
						
						MainMenu.this.startActivity(i);
					}else {
						
						Toast.makeText(arg0.getContext(), "No Ticket", Toast.LENGTH_SHORT).show();
						
					}
				} catch (Exception e) {
					Toast.makeText(arg0.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
				}
				
					
			}
				
		});
						
	
		
		CancelTicket.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(final View arg0){
				
				final int tictic = manager.getInt("tictic", -1);
				
				if (tictic == -1) {
					Toast.makeText(getApplicationContext(), "You haven't get a ticket", Toast.LENGTH_SHORT).show();
					
				} else {
					new AlertDialog.Builder(MainMenu.this)
			    	   .setMessage("Are you sure you want to cancel your ticket?")
			    	   .setCancelable(false)
			           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			               public void onClick(DialogInterface dialog, int id) {
			            	   
			            	JSONParser parser = new JSONParser();
			   				String url ="http://queuingserver.esy.es/user_cancel.php";
			   				JSONObject obj;
			   				String username = manager.getString("username", "");
			   				String urlParameters = "username=" + username + "&ticnum=" + String.valueOf(tictic);
			   				
			   				try{
			   					
			   					obj=parser.makeHttpRequest(url, "POST", urlParameters);
			   					int num = obj.getInt ("code");
			   					
			   					if(num!=1){
			   						
			   						Toast.makeText(arg0.getContext(), "No Ticket", Toast.LENGTH_SHORT).show();
			   						
			   					} else {
			   						Editor edit = manager.edit();
			   						edit.putInt("tictic", -1);
			   						edit.commit();
			   						
			   						Toast.makeText(arg0.getContext(), "You ticket has been deleted", Toast.LENGTH_SHORT).show();
			   						
			   					}
			   					
			   				}
			   				
			   				catch(Exception e){
			   					Toast.makeText(arg0.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
			   				}
			            	   
			               }
			               
			           }) .setNegativeButton("No", null).show();
				}		
			}
		});
	
	}
		
			
	@Override
	public void onBackPressed() 
	{
	    new AlertDialog.Builder(this)
	    	   .setMessage("Are you sure you want to exit?")
	    	   .setCancelable(false)
	           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	            	   finish();
	               }
	           }) .setNegativeButton("No", null).show();
		    
	}
		
}
