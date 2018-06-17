package com.udacity.gradle.builditbigger.backend;

import java.util.ArrayList;

/** The object model for the data we are sending through endpoints */
public class MyJokesClass {

    private String myJoke;

    public String getJokes() {
        return myJoke;
    }

    public void setJokes(String jokes) {
        myJoke = jokes;
    }
}