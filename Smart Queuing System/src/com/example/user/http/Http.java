package com.example.user.http;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by raphx on 12/10/2015.
 */
public abstract class Http extends AsyncTask<String, Void, JSONObject> {

    private static final String TAG = "Http";
    public static final String GET = "GET";
    public static final String POST = "POST";

    /**
     * Send a HTTP request.
     *
     * @param params A {@link String} array consisting of request parameters.
     *               First parameter is the type of HTTP request.
     *               Second parameter is the URL for the HTTP request.
     *               Subsequent parameters are POST request parameters.
     * @return A JSON array constructed from the server's reply.
     */
    @Override
    protected JSONObject doInBackground(String... params) {
        JSONObject jsonObject = null;
        HttpURLConnection conn = null;

        try {
            String url = params[1];
            URL urlObj = new URL(url);
            conn = (HttpURLConnection) urlObj.openConnection();

            String method = params[0];
            if (method.equals(GET)) {
                conn.setRequestMethod(GET);
            } else if (method.equals(POST)) {
                conn.setRequestMethod(POST);
                //conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setDoOutput(true);

                String urlParameters = params[2];
                //String encodedUrlParameters = URLEncoder.encode(urlParameters, "UTF-8");
                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                dos.writeBytes(urlParameters);
                dos.flush();
                dos.close();
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()
            ));

            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            jsonObject = new JSONObject(response.toString());

        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return jsonObject;
    }
}
