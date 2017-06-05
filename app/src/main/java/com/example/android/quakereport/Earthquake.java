package com.example.android.quakereport;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class Earthquake {
    public double magnitude;
    public String location;
    public long date;
    public String url;


    public Earthquake(double magnitudeEartquake, String locatie, long datum, String link) {
        magnitude = magnitudeEartquake;
        location = locatie;
        date = datum;
        url = link;


    }

    public double getMagnitude() {
        return magnitude;

    }

    public String getLocation() {
        return location;
    }

    public long getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }
}




