package com.example.fredliu.hw3moviedb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private final String url = "http://api.themoviedb.org/3/";
    private static final String API_KEY = "f9b53101441c492be8a7cf5254e331b6";

    private ResponseReceiver receiver;

    ListView moviesList;

    DatabaseManager databaseManager;

    RadioGroup orderby;

    MovieAdapter adapter;
    RadioButton sortTitle;
    RadioButton sortVote;

    Button likeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moviesList = (ListView) findViewById(R.id.moviesList);

        //registering a local broadcast receiver that is activated when "movies_fetched"
        //action happens
        IntentFilter filter = new IntentFilter(ResponseReceiver.ACTION_RESP);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new ResponseReceiver();
        registerReceiver(receiver, filter);

        sortTitle = (RadioButton) findViewById(R.id.sortTitle);
        sortVote = (RadioButton) findViewById(R.id.sortVote);


        orderby = (RadioGroup) findViewById(R.id.orderby);
        orderby.setOnCheckedChangeListener(this);

        databaseManager = new DatabaseManager(this);

        likeList = (Button)findViewById(R.id.likeList);


        // starting an intent service that will fetch the list of movies from the URL below
        String complete_url = url + "movie/now_playing?api_key=" + API_KEY + "&language=en-US&page=1";
        Intent msgIntent = new Intent(this, DownloadJSON.class);
        msgIntent.setAction(DownloadJSON.ACTION_DOWNLOAD);
        msgIntent.putExtra(DownloadJSON.URL, complete_url);
        startService(msgIntent);

        // after IntentService is done we will receive a broadcast telling us that it is time to fetch the list of movies from the db



        databaseManager.open();

        databaseManager.deleteAll();


        List<Movie> arrayOfMovies = databaseManager.getAllRecords();

        // Create the adapter to convert the array to views

        adapter = new MovieAdapter(this, (ArrayList<Movie>) arrayOfMovies);

        // Attach the adapter to a ListView


        moviesList.setAdapter(adapter);

        moviesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {


                if (position < adapter.getCount()) {
                    Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                    intent.putExtra("title", adapter.getItem(position).getTitle());
                    intent.putExtra("popularity", adapter.getItem(position).getPopularity());
                    intent.putExtra("date", adapter.getItem(position).getDate());
                    intent.putExtra("vote", adapter.getItem(position).getRating());
                    intent.putExtra("overview", adapter.getItem(position).getOverview());
                    intent.putExtra("poster", adapter.getItem(position).getPoster());
                    intent.putExtra("like", adapter.getItem(position).getLikeButton());

                    startActivity(intent);
                }
            }
        });



        likeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int n = 0; n < 5; n++) {

                    for (int i = 0; i < adapter.getCount(); i++) {

                        if (adapter.getItem(i).getLikeButton() != "UNLIKE") {

                            adapter.remove(adapter.getItem(i));
                        }
                    }
                }

            }
        });


    }

    // this adapter takes an ArrayList of movies and outputs it into a ListView
    public class MovieAdapter extends ArrayAdapter<Movie> {

        public MovieAdapter(Context context, ArrayList<Movie> movies) {

            super(context, 0, movies);

        }


        //this is where you would add the like button
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {


            // Get the data item for this position

            Movie movie = getItem(position);

            // Check if an existing view is being reused, otherwise inflate the view

            if (convertView == null) {

                //the like button would need to be in single_movie.xml
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list, parent, false);

            }

            // Lookup view for data population

            TextView title = (TextView) convertView.findViewById(R.id.title);

            TextView date = (TextView) convertView.findViewById(R.id.date);

            TextView rating = (TextView) convertView.findViewById(R.id.rating);

            TextView popularity = (TextView) convertView.findViewById(R.id.popularity);

            ImageView image = (ImageView) convertView.findViewById(R.id.image);

            ToggleButton toggleButton = (ToggleButton) convertView.findViewById(R.id.toggleButton);

            // Per each view we fetch info from the corresponding movie and set it
            title.setText(movie.getTitle());

            date.setText(movie.getDate());

            rating.setText(movie.getRating() + "");

            popularity.setText(movie.getPopularity() + "");

            String imgURL = "http://image.tmdb.org/t/p/w185" + movie.getPoster();

            Picasso.with(MainActivity.this).load(imgURL).into(image);

            toggleButton.setOnCheckedChangeListener(null);

            if (adapter.getItem(position).getLikeButton() == "UNLIKE") {
                toggleButton.setChecked(true);
            } else {
                toggleButton.setChecked(false);
                adapter.getItem(position).setLikeButton("LIKE");

            }
//
//            final Bundle bundle2 = getIntent().getExtras();
//            if(bundle2.getString("l").equals("UNLIKE")){
//                toggleButton.setChecked(true);
//            }
//            if(bundle2.getString("l").equals("LIKE")){
//                toggleButton.setChecked(false);
//            }


            toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                String t = adapter.getItem(position).getTitle();
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        // The toggle is enabled

                        adapter.getItem(position).setLikeButton("UNLIKE");
                        Toast.makeText(MainActivity.this, t + " liked", Toast.LENGTH_LONG).show();

                    } else {
                        // The toggle is disabled

                        adapter.getItem(position).setLikeButton("LIKE");
                        Toast.makeText(MainActivity.this, t + " unliked", Toast.LENGTH_LONG).show();

                    }
                }
            });

            // Return the completed view to render on screen

            return convertView;

        }

    }


    // this BroadcastReceiver is waiting for DownloadJSON (IntentService) to issue a broadcast

    public class ResponseReceiver extends BroadcastReceiver {
        public static final String ACTION_RESP =
                "movies_fetched";

        @Override
        public void onReceive(Context context, Intent intent) {
            ;


            adapter.notifyDataSetChanged();
        }
    }

    public void onRefresh(View view) {
        sortTitle.setChecked(false);
        sortVote.setChecked(false);

        databaseManager.open();
        List<Movie> arrayOfMovies = databaseManager.getAllRecords();
        adapter = new MovieAdapter(this, (ArrayList<Movie>) arrayOfMovies);
        moviesList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }




    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

        //Get the sorted result and call updateInfo
        switch (i) {
            case R.id.sortTitle:
                List<Movie> tempSortRecords = databaseManager.getAllRecordsOrderedBy(DBOpenHelper.COLUMN_NAME_TITLE);
                adapter = new MovieAdapter(this, (ArrayList<Movie>) tempSortRecords);
                moviesList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;
            case R.id.sortVote:
                List<Movie> humSortRecords = databaseManager.getAllRecordsOrderedBy(DBOpenHelper.COLUMN_NAME_VOTE);
                adapter = new MovieAdapter(this, (ArrayList<Movie>) humSortRecords);
                moviesList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;

        }
    }

}
