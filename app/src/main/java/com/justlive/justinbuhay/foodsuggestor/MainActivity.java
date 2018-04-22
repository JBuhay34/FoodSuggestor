package com.justlive.justinbuhay.foodsuggestor;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.justlive.justinbuhay.foodsuggestor.threads.YelpAsyncTaskLoader;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<HashMap<String,String>>>, View.OnClickListener{

    private String term = "ono";
    private String location = "south san francisco";
    // max is 40,000 meters(25 miles).
    private int range = 10000;
    // max is 4, can also have '1,2,3' to filter within 1-3 price ranges.
    private int price = 1;

    private EditText termsEditText, locationEditText;
    private TextView businessDetailsTextView;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        termsEditText = (EditText) findViewById(R.id.terms_edit_text);
        locationEditText = (EditText) findViewById(R.id.location_edit_text);
        searchButton = (Button) findViewById(R.id.search_button);
        businessDetailsTextView = (TextView) findViewById(R.id.business_details_text_view);

        searchButton.setOnClickListener(this);


    }

    @NonNull
    @Override
    public Loader onCreateLoader(int id, @Nullable Bundle args) {
        return new YelpAsyncTaskLoader(this, term, location, range, price);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<HashMap<String,String>>> loader, List<HashMap<String,String>> data) {
        StringBuilder businessDetails = new StringBuilder();
        for(int i = 0; i < data.size(); i++){

            businessDetails.append(data.get(i).get("name"));
            businessDetails.append(data.get(i).get("phone"));
            businessDetails.append(data.get(i).get("rating"));
            businessDetails.append(data.get(i).get("price"));
            businessDetails.append(data.get(i).get("address"));
            businessDetails.append("\n");

        }

        String details = businessDetails.toString();
        businessDetailsTextView.setText(details);
    }



    @Override
    public void onLoaderReset(@NonNull Loader loader) {

    }

    private static int count = 0;

    @Override
    public void onClick(View view) {
        if(view == searchButton){
            term = termsEditText.getText().toString();
            location = locationEditText.getText().toString();
            Log.e(MainActivity.class.getSimpleName(), term + " " +location);
            if(count == 0) {
                getSupportLoaderManager().initLoader(0, null, this).forceLoad();
                count++;
            }
            getSupportLoaderManager().restartLoader(0, null, this).forceLoad();
        }
    }
}
