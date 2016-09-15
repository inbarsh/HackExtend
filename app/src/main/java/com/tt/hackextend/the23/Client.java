package com.tt.hackextend.the23;

import android.util.Log;

import java.io.Closeable;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yotamc on 15-Sep-16.
 */
public class Client {
        static final String mHost = "https://timebank-9fdea.firebaseio.com/";

        public Client() {
        }

        //type is GET or POST
        public HttpURLConnection OpenConnection(String type, URL url) {
            HttpURLConnection urlConnection = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod(type);
                urlConnection.setRequestProperty("Content-Type", "application/json");
                Log.i("Journee", "\"Content-Type\": " + "application/json");
                System.setProperty("http.keepAlive", "false");

            } catch (Exception e) {
                Log.e("Journee", e.getMessage());

            }
            return urlConnection;
        }


        public void closeConnection(HttpURLConnection con) {
            if (con !=null)
                con.disconnect();
        }

        // close input stream and output stram
        protected void close(Closeable c) {
            if (c == null) return;
            try {
                c.close();
            } catch (IOException e) {
                Log.e("Journee", e.getMessage());
            }
        }
    }


