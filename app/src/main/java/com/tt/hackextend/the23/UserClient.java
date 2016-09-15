package com.tt.hackextend.the23;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by yotamc on 15-Sep-16.
 */

public class UserClient extends Client {


    private Context context;
    private User user;

    public UserClient(Context context) {
        super();
        this.context = context;

    }

    public void createUser(User user) {
        try {
            CreateUserTask userTask = new CreateUserTask(user);
            userTask.execute();
        } catch (Exception e) {
            Log.e("app", e.getMessage());
        }
    }


    public void getUsers() {
        List<User> users = null;
        try {
            GetUserTask userTask = new GetUserTask();
            userTask.execute();

        } catch (Exception e) {
            Log.e("Journee", e.getMessage());
        }
    }


    public class CreateUserTask extends AsyncTask<Void, Void, String> {
        private User user;


        public CreateUserTask(User user) {
            this.user = user;
        }


        @Override
        protected String doInBackground(Void... params) {
            String IdInServer = executeCreateUser(user);
            return IdInServer;
        }

        // creates new user in the server
        public String executeCreateUser(User user) {
            HttpURLConnection urlConnection = null;
            String response = null;
            OutputStreamWriter wr = null;
            BufferedInputStream in = null;

            try {
                URL url = new URL("https://timebank-9fdea.firebaseio.com/users.json");
                urlConnection = OpenConnection("POST", url);
                JSONObject jsonUser = new JSONObject();
                jsonUser.put("name", user.name);
                jsonUser.put("base_skill", user.base_skill);
                jsonUser.put("want_skill", user.want_skill);
                jsonUser.put("city", user.city);
                jsonUser.put("phone_number", user.phone_number);
                jsonUser.put("email", user.email);
                wr = new OutputStreamWriter(urlConnection.getOutputStream());
                wr.write(jsonUser.toString());
                wr.flush();
                wr.close();

                // read the response
                in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;
                int responseCode = urlConnection.getResponseCode();

                if (responseCode == 200) {
                    while ((line = r.readLine()) != null) {
                        sb.append(line);
                        // maybe I get a json file??
                        response = sb.toString();
                    }

                    JSONObject json = new JSONObject(response);
                    response = json.getString("name");
                }

                in.close();

            } catch (IOException e) {
                Log.e("Journee", e.getMessage());
                Log.e("Journee", "I got an error", e);

            } catch (JSONException j) {
                Log.e("Journee", j.getMessage());
                Log.e("Journee", "I got an error", j);

            } catch (Exception e) {
                Log.e("Journee", e.getMessage());
                Log.e("Journee", "I got an error", e);

            } finally {
                close(wr);
                close(in);
                closeConnection(urlConnection);
            }
            Log.d("Journee", "//UserClient.executeCreateUser()");
            return response;
        }
    }

    public class GetUserTask extends AsyncTask<Void, Void, List<User>> {

        public GetUserTask() {
        }


        @Override
        protected List<User> doInBackground(Void... params) {
            List<User> users =  executeGetUsers();
            return users;
        }

        //gets all the journals the user involved in
        public List<User> executeGetUsers() {
            HttpURLConnection con = null;
            String response = null;
            OutputStreamWriter wr = null;
            BufferedInputStream in = null;
            URL url = null;
            List<User> users = null;

            try {

                url = new URL("https://timebank-9fdea.firebaseio.com/users.json");
                con = OpenConnection("GET", url);

                // read the response
                in = new BufferedInputStream(con.getInputStream());
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;
                int responseCode = con.getResponseCode();
                Log.d("Journee", "UserClient.executeGetUsers: response code: " + responseCode);
                if (responseCode == 200) {
                    while ((line = r.readLine()) != null) {
                        sb.append(line);
                        // maybe I get a json file??
                        response = sb.toString();
                    }

                    JSONObject jsonObject = new JSONObject(response);
                    users = new ArrayList<User>();
                    Log.i("app", response);
                    Iterator<?> keys = jsonObject.keys();
                    while (keys.hasNext()) {
                        String key = (String) keys.next();
                        User currUser = new User();
                        if (jsonObject.get(key) instanceof JSONObject) {
                            JSONObject jsonUser = (JSONObject) jsonObject.get(key);

                            currUser.name = jsonUser.getString("name");
                            currUser.base_skill = jsonUser.getString("base_skill");
                            currUser.city = jsonUser.getString("city");
                            currUser.email = jsonUser.getString("email");
                            currUser.phone_number = jsonUser.getString("phone_number");
                            currUser.want_skill = jsonUser.getString("want_skill");
                        }
                        users.add(currUser);

                    }
                }

            }catch(IOException e){
                Log.e("Journee", e.getMessage());
                Log.e("Journee", "I got an error", e);

            }catch(JSONException j){
                Log.e("Journee", j.getMessage());
                Log.e("Journee", "I got an error", j);

            }catch(Exception e){
                Log.e("Journee", e.getMessage());
                Log.e("Journee", "I got an error", e);

            }finally{
                close(wr);
                close(in);
                closeConnection(con);
            }
            return users;

        }

        @Override
        protected void onPostExecute(List<User> usersFromServer){
            Search activity = (Search) context;
            activity.setUsers(usersFromServer);
        }
    }
}

