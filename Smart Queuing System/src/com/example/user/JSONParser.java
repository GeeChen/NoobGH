package com.example.user;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONParser {

    // constructor
    public JSONParser() {
 
    }
    
    public JSONObject getJSONFromUrl(String temp_url) throws JSONException {
        HttpURLConnection conn = null;
        String json = "";
        try {

           URL url = new URL(temp_url);
           conn = (HttpURLConnection)url.openConnection();
           conn.setRequestMethod("POST");
           conn.setDoInput(true);
           conn.setDoOutput(true);
           conn.connect();

           BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

           StringBuilder sb = new StringBuilder();
           String line = null;
           while ((line = reader.readLine()) != null) {
               sb.append(line + "\n");
           }
           reader.close();
           json = sb.toString();

        }catch (Exception e) {

            System.out.println("getJSONFromUrl Exception Error :"+e);
        }

       finally {

           if (conn != null) {

               conn.disconnect();
           }
       }

       // try parse the string to a JSON object
//           try {
//
//               jObj = new JSONObject(json);
//
//           } catch (JSONException e) {
//
//               Log.e("JSON Parser", "getJSONFromUrl Error parsing data " + e.toString());
//           }
       //return jObj;

       return new JSONObject(json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1));
   }
    
    
    public JSONObject makeHttpRequest(String temp_url, String method, String urlParameters) throws JSONException
    {
        HttpURLConnection conn = null;
        String json = "";

        try{  
               URL url = new URL(temp_url);
               conn = (HttpURLConnection)url.openConnection();
               //conn.setReadTimeout(10000);
               //conn.setConnectTimeout(15000);
               conn.setRequestMethod(method); 
               conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
               conn.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
               conn.setRequestProperty("Content-Language", "en-US");
               conn.setUseCaches (false);
               conn.setDoInput(true);
               conn.setDoOutput(true);
             
               //Send request
               DataOutputStream wr = new DataOutputStream (conn.getOutputStream ());
               wr.writeBytes (URLEncoder.encode(urlParameters, "UTF-8"));
               wr.flush ();
               wr.close ();
             
               //Get Response 
               BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
               String line;
               StringBuilder sb = new StringBuilder();
                 
               while((line = reader.readLine()) != null) 
               {
            	   sb.append(line + "\n");
               }
               
               reader.close();
               json = sb.toString();
               Log.d("TAG", json);
        }catch (Exception e) 
        {
        	System.out.println("makeHttpRequest Error:"+e.toString());
        	
        }finally 
        {
        	if (conn != null) 
        	{
        		conn.disconnect();
        	}
        }   

        return new JSONObject(json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1));
    }

}
