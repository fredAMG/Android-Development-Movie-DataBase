package com.example.fredliu.hw3moviedb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

public class Main2Activity extends AppCompatActivity {

    TextView title2;
    TextView popularity2;
    TextView date2;
    TextView vote2;
    TextView overview2;
    TextView position2;
    ImageView image2;

    ToggleButton toggleButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        title2 = (TextView) findViewById(R.id.title2);
        popularity2 = (TextView) findViewById(R.id.popularity2);
        date2 = (TextView) findViewById(R.id.data2);
        vote2 = (TextView) findViewById(R.id.vote2);
        overview2 = (TextView) findViewById(R.id.overview2);
        position2 = (TextView) findViewById(R.id.position2);


        image2 = (ImageView) findViewById(R.id.image2);

        toggleButton2 = (ToggleButton)findViewById(R.id.toggleButton2);


        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            title2.setText(bundle.getString("title"));
            popularity2.setText(bundle.getString("popularity"));
            date2.setText(bundle.getString("date"));
            vote2.setText(bundle.getString("vote"));
            overview2.setText(bundle.getString("overview"));
            String imgURL = "http://image.tmdb.org/t/p/w185" + bundle.getString("poster");
            Picasso.with(this).load(imgURL).into(image2);
        }



        toggleButton2.setOnCheckedChangeListener(null);

        if(bundle.getString("like").equals("UNLIKE")){
            toggleButton2.setChecked(true);
        }
        if(bundle.getString("like").equals("LIKE")){
            toggleButton2.setChecked(false);
        }



        toggleButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

//                Intent intent2 = new Intent(Main2Activity.this,MainActivity.class);


                String t = bundle.getString("title");

                if (isChecked) {
                    // The toggle is enabled

//                    intent2.putExtra("l", toggleButton2.getText().toString());


                    Toast.makeText(Main2Activity.this, t + " liked", Toast.LENGTH_LONG).show();


                } else {
                    // The toggle is disabled

//                    intent2.putExtra("l", toggleButton2.getText().toString());
                    Toast.makeText(Main2Activity.this, t + " unliked", Toast.LENGTH_LONG).show();


                }
            }
        });




    }







}
