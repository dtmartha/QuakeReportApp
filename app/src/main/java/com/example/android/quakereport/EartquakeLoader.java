package com.example.android.quakereport;

import android.content.Context;
import android.content.AsyncTaskLoader;
import android.util.Log;


import java.util.ArrayList;

/**
 * Created by gebruiker on 30-5-2017.
 */

public class EartquakeLoader extends AsyncTaskLoader<ArrayList<Earthquake>> {
    public String url;
    private static final String LOG_TAG = "MyActivity";


    public EartquakeLoader(Context context, String eUrl) {
        super(context);
        url = eUrl;
    }

    @Override
    protected void onStartLoading() {
        Log.d(LOG_TAG, "onStartLoading is called");
        forceLoad();
    }


    @Override
    public ArrayList<Earthquake> loadInBackground() {
        Log.d(LOG_TAG, "loadInBackground is called");
        if (url == null) {
            return null;
        } else {
            ArrayList<Earthquake> result = QueryUtils.fetchEarthquakeData(url);
            return result;
        }


    }


}
