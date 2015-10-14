package com.example.user;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.user.http.Http;
import org.json.JSONObject;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainMenu";

    Button GetTic;
    Button ViewMyTicket;
    Button ViewCurrent;
    Button CancelTicket;

    private SharedPreferences manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        GetTic.setOnClickListener(this);
        ViewMyTicket.setOnClickListener(this);
        ViewCurrent.setOnClickListener(this);
        CancelTicket.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String username = "";
        String url = "";
        String urlParameters = "";
        String[] params = null;

        switch (v.getId()) {
            case R.id.button1:      // Get ticket
                username = manager.getString("username", "");

                if (!username.equals("")) {
                    url = "http://queuingserver.esy.es/ticket.php";
                    urlParameters = "username=" + username;

                    params = new String[] {
                            Http.POST,
                            url,
                            urlParameters
                    };
                    new GetTicketHttp(this).execute(params);
                }
                break;

            case R.id.button2:      // View current ticket
                url = "http://queuingserver.esy.es/get_current_next.php";
                params = new String[] {
                        Http.GET,
                        url
                };
                new ViewCurrentTicketHttp(this).execute(params);
                break;

            case R.id.button3:      // View my ticket
                username = manager.getString("username", "");

                if (!username.equals("")) {
                    url = "http://queuingserver.esy.es/view-my-ticket.php";
                    urlParameters = "username=" + username;

                    params = new String[] {
                            Http.POST,
                            url,
                            urlParameters
                    };
                    new ViewMyTicketHttp(this).execute(params);
                }
                break;

            case R.id.button4:      // Cancel ticket
                final int tictic = manager.getInt("tictic", -1);

                if (tictic == -1) {
                    Toast.makeText(this, "You haven't get a ticket", Toast.LENGTH_SHORT).show();

                } else {
                    new AlertDialog.Builder(this)
                            .setMessage("Are you sure you want to cancel your ticket?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    String url = "http://queuingserver.esy.es/user_cancel.php";
                                    String username = manager.getString("username", "");
                                    String urlParameters = "username=" + username + "&ticnum=" + String.valueOf(tictic);

                                    String[] params = new String[] {
                                            Http.POST,
                                            url,
                                            urlParameters
                                    };
                                    new CancelTicketHttp(MainMenu.this).execute(params);
                                }
                            }).setNegativeButton("No", null).show();
                }
                break;
        }
    }

    private class GetTicketHttp extends Http {
        private ProgressDialog dialog;
        private Activity context;

        public GetTicketHttp(Activity context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            String title = "";
            String message = "Getting ticket ...";
            dialog = ProgressDialog.show(context, title, message, true);
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            dialog.dismiss();

            try {
                int num = jsonObject.getInt("tic_num");

                if (num == -1) {
                    Toast.makeText(context, "You already got a ticket.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(context, GetTicket.class);
                    i.putExtra("tic_num", num);

                    Editor edit = manager.edit();
                    edit.putInt("tictic", num);
                    edit.apply();

                    context.startActivity(i);
                }
            } catch (Exception e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
    }

    private class ViewCurrentTicketHttp extends Http {
        private ProgressDialog dialog;
        private Activity context;

        public ViewCurrentTicketHttp(Activity context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            String title = "";
            String message = "Retrieving current ticket ...";
            dialog = ProgressDialog.show(context, title, message, true);
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            dialog.dismiss();

            try {
                int codeResult = jsonObject.getInt("code");

                if (codeResult != -1) {
                    int num = jsonObject.getInt("tic_0");
                    Intent i = new Intent(context, DisCurrentTicket.class);
                    i.putExtra("tic_num", num);
                    context.startActivity(i);
                } else {
                    Toast.makeText(context, "No Ticket", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
    }

    private class ViewMyTicketHttp extends Http {
        private ProgressDialog dialog;
        private Activity context;

        public ViewMyTicketHttp(Activity context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            String title = "";
            String message = "Retrieving your ticket ...";
            dialog = ProgressDialog.show(context, title, message, true);
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            dialog.dismiss();

            try {
                int codeResult = jsonObject.getInt("code");

                if (codeResult == 1) {
                    int num = jsonObject.getInt("tic");
                    Intent i = new Intent(context, ViewMyTicket.class);
                    i.putExtra("tic", num);
                    context.startActivity(i);
                } else {
                    Toast.makeText(context, "You haven't get a ticket", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
    }

    private class CancelTicketHttp extends Http {
        private ProgressDialog dialog;
        private Activity context;

        public CancelTicketHttp(Activity context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            String title = "";
            String message = "Cancelling ticket ...";
            dialog = ProgressDialog.show(context, title, message, true);
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            dialog.dismiss();

            try {
                int codeResult = jsonObject.getInt("code");

                if (codeResult != 1) {
                    Toast.makeText(context, "No Ticket", Toast.LENGTH_SHORT).show();
                } else {
                    Editor edit = manager.edit();
                    edit.putInt("tictic", -1);
                    edit.apply();

                    Toast.makeText(context, "You ticket has been deleted", Toast.LENGTH_SHORT).show();
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
                }).setNegativeButton("No", null).show();
    }
}
