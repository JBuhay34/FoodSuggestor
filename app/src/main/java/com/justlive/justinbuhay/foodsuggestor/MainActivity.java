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

import com.justlive.justinbuhay.foodsuggestor.threads.YelpAsyncTaskLoader;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<HashMap<String,String>>>{

    String term = "ono";
    String location = "south san francisco";
    // max is 40,000 meters(25 miles).
    int range = 10000;
    // max is 4, can also have '1,2,3' to filter within 1-3 price ranges.
    int price = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportLoaderManager().initLoader(0, null, this).forceLoad();


    }

    @NonNull
    @Override
    public Loader onCreateLoader(int id, @Nullable Bundle args) {
        return new YelpAsyncTaskLoader(this, term, location, range, price);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<HashMap<String,String>>> loader, List<HashMap<String,String>> data) {
        for(int i = 0; i < data.size(); i++){
            Log.e(MainActivity.class.getSimpleName(), data.get(i).get("name"));
            Log.e(MainActivity.class.getSimpleName(), data.get(i).get("phone"));
            Log.e(MainActivity.class.getSimpleName(), data.get(i).get("rating"));
            Log.e(MainActivity.class.getSimpleName(), data.get(i).get("price"));
            Log.e(MainActivity.class.getSimpleName(), data.get(i).get("address"));

        }
    }



    @Override
    public void onLoaderReset(@NonNull Loader loader) {

    }
}
