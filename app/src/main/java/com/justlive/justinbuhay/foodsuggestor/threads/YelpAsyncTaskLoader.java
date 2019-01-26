package com.justlive.justinbuhay.foodsuggestor.threads;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.justlive.justinbuhay.foodsuggestor.networking.FetchYelpAPIData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Response;

public class YelpAsyncTaskLoader extends AsyncTaskLoader<List<HashMap<String,String>>> {

    // This class will run in the background of the main thread, serving its way of fetching the yelp api data and parsing the JSON data
    // into a LinkedList of Hashmaps of the places.

    private String term, location;
    private int range;
    private int price;
    Context context;


    public YelpAsyncTaskLoader(@NonNull Context context, String term, String location, int range, int price) {
        super(context);
        this.context = context;
        this.term = term;
        this.location = location;
        this.range = range;
        this.price = price;

    }

    @Nullable
    @Override
    public List<HashMap<String, String>> loadInBackground() {

        String theJSON = getJSONString();

        return parseJSON(theJSON);

    }

    private String getJSONString() {

        try {
            FetchYelpAPIData firstData = new FetchYelpAPIData(context, term, location, range, price);
            Response response = firstData.getResponse();


            String theJSON;
            theJSON = response.body().string();

            return theJSON;


        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Could not work";
    }


    private List<HashMap<String, String>> parseJSON(String theJSON){

        List<HashMap<String, String>> data = new LinkedList<HashMap<String, String>>();

        try {
            JSONObject theObject = new JSONObject(theJSON);

            JSONArray businesses = theObject.getJSONArray("businesses");

            for(int i= 0; i < businesses.length(); i++){
                JSONObject current = businesses.getJSONObject(i);

                String name = current.getString("name");
                String image_url = current.getString("image_url");
                int rating = current.getInt("rating");
                String phone = current.getString("phone");
                String price = current.getString("price");
                String url = current.getString("url");

                JSONObject location = current.getJSONObject("location");
                JSONArray displayAddressArray = location.getJSONArray("display_address");
                String address = "";
                for(int j = 0; j < displayAddressArray.length(); j++){
                    address += displayAddressArray.getString(j) + " ";

                }

                // tmp hash map for single contact
                HashMap<String, String> hashdata = new HashMap<>();

                // adding each child node to HashMap key => value
                hashdata.put("name", name);
                hashdata.put("image_url", image_url);
                hashdata.put("rating", "" + rating);
                hashdata.put("phone", phone);
                hashdata.put("price", price);
                hashdata.put("address", address);
                hashdata.put("url", url);
                Log.e("YelpAsyncTask:", hashdata.get("url"));

                // adding contact to contact list
                data.add(hashdata);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return data;
    }

}
