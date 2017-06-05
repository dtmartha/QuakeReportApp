/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;


import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;
import static android.R.attr.visible;
import static android.R.id.empty;


public class EarthquakeActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<ArrayList<Earthquake>> {
    private static final String URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    EarthquakeAdapter adapter;
    public ArrayList<Earthquake> earthquakes;
    TextView emptyStateTextView;
    ProgressBar progressBar;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        Log.d(LOG_TAG, "Getloadermanager is called");


        earthquakes = new ArrayList<>();


        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);


        // Create a new {@link ArrayAdapter} of earthquakes
        adapter = new EarthquakeAdapter(this, earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);

        emptyStateTextView = (TextView) findViewById(R.id.emptystate);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        earthquakeListView.setEmptyView(emptyStateTextView);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Earthquake earthquake = earthquakes.get(position);

                String url = earthquake.getUrl();
                if (url != null) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                }

            }
        });

        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.isConnected()) {
            getLoaderManager().initLoader(0, null, this);
            Log.d(LOG_TAG, "There's an internet connection");


        } else {
            emptyStateTextView.setText(R.string.No_Internet_connection);
            progressBar.setVisibility(View.GONE);
            Log.d(LOG_TAG, "No Internet connection");

        }


    }


    @Override
    public Loader<ArrayList<Earthquake>> onCreateLoader(int id, Bundle args) {
        Log.d(LOG_TAG, "onCreateLoader is started");
        return new EartquakeLoader(this, URL);
    }


    @Override
    public void onLoadFinished(Loader<ArrayList<Earthquake>> loader, ArrayList<Earthquake> data) {
        Log.d(LOG_TAG, "onLoadFinished is called, update UI");
        emptyStateTextView.setText(R.string.No_Eartquakes_found); //empty state when there's no data to display
        adapter.clear();
        if (data != null && !data.isEmpty()) {
            adapter.addAll(data);
        }

        progressBar.setVisibility(View.GONE);


    }


    @Override
    public void onLoaderReset(Loader<ArrayList<Earthquake>> loader) {
        Log.d(LOG_TAG, "onLoaderReset is called");
        adapter.clear();

    }
}
