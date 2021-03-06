package com.example.user;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ViewMyTicket extends AppCompatActivity {

    TextView ViewMyTicket;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewmyticket);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.queue);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#74b5df"));
        actionBar.setBackgroundDrawable(colorDrawable);

        actionBar.setTitle("  " + "Smart Queuing System");

        ViewMyTicket = (TextView) findViewById(R.id.txtViewMyTicket);

        //Get the bundle
        Bundle bundle = getIntent().getExtras();

        //Extract the data
        int num = bundle.getInt("tic");

        ViewMyTicket.setText(String.valueOf(num));
    }
}