package com.example.fredliu.hw3moviedb;

import android.app.IntentService;
import android.content.Intent;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class DownloadJSON extends IntentService {

    public static final String ACTION_DOWNLOAD = "com.example.fredliu.hw3moviedb.action.download";


    public static final String URL = "url";





    private DatabaseManager manager;

    public DownloadJSON() {
        super("DownloadJSON");
    }



    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_DOWNLOAD.equals(action)) {

                populateDB(
                        download(intent.getStringExtra(URL))

                );
            }
        }
    }


    private String download(String url){


        manager = new DatabaseManager(this.getApplicationContext());

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);

        Response response = null;

        String jsonData = null;

        try {
            response = call.execute();

            if (response.isSuccessful()) {
                jsonData = response.body().string();

            } else {
                jsonData = null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonData; //This is returned to onPostExecute()

    }


    private void populateDB(String jsonData) {

        manager.open();
        JSONObject jsonResponse = null;
        try {

            jsonResponse = new JSONObject(jsonData);

            JSONArray movies = jsonResponse.getJSONArray("results");

            int movie_list_size = movies.length();



            for (int i = 0; i < movie_list_size; i++) {

                JSONObject jsonFilm = movies.getJSONObject(i);



                String dateString = jsonFilm.getString("release_date");
                String title = jsonFilm.getString("title");

                String rating = jsonFilm.getString("vote_average");

                String popularity = jsonFilm.getString("popularity");

                String overview = jsonFilm.getString("overview");

                String poster = jsonFilm.getString("poster_path");



                if (dateString != null && !dateString.equals("null")) {


                    manager.insertMovieInfo(title,dateString,rating,popularity,overview,poster);

                }
            }
            manager.close();

            //broadcasting that it worked

            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction(MainActivity.ResponseReceiver.ACTION_RESP);
            broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
            sendBroadcast(broadcastIntent);
        }
        catch (JSONException e) {

            e.printStackTrace();
        }}


}
