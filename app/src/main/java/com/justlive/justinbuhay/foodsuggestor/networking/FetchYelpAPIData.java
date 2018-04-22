package com.justlive.justinbuhay.foodsuggestor.networking;

import android.content.Context;
import android.util.Log;

import com.justlive.justinbuhay.foodsuggestor.R;

import java.io.IOException;
import java.net.URL;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FetchYelpAPIData {

    // This class will obtain the JSON String from the Yelp API.
    // Using okhttp3 to help authorize the token from Yelp.

    private Response response;
    private Context context;
    private String key;

    public FetchYelpAPIData(Context context, String term, String location, int range, int price){
        this.context = context;
        key = context.getString(R.string.yelp_api_key);

        OkHttpClient client = new OkHttpClient();

        URL url = new HttpUrl.Builder()
                .scheme("https")
                .host("api.yelp.com")
                .addPathSegments("v3/businesses/search")
                .addQueryParameter("term", term)
                .addQueryParameter("location", location)
                .addQueryParameter("radius", range + "")
                .addQueryParameter("price", price + "")
                .build().url();

        StringBuilder theURL= new StringBuilder(url.toString());


        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials");
        Request request = new Request.Builder()
                .url(theURL.toString())
                .get()
                .addHeader("Authorization", "Bearer " + key)
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Postman-Token", "37bdb8cb-0c48-40da-97ac-e1c8ddea2c97")
                .build();


        {
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Response getResponse() {
        return response;
    }
}
