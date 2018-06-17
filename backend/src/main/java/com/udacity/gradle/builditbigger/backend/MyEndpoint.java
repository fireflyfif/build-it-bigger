package com.udacity.gradle.builditbigger.backend;

import com.example.android.joke.MyJoke;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.ArrayList;

import javax.inject.Named;

/** An endpoint class we are exposing */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.builditbigger.gradle.udacity.com",
                ownerName = "backend.builditbigger.gradle.udacity.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    /** A simple endpoint method that takes a name and says Hi back */
    @ApiMethod(name = "jokerMe")
    public MyJokesClass jokerMe() {

        MyJoke myJoke = new MyJoke();

        String myJokeString = myJoke.getJoke();

        MyJokesClass response = new MyJokesClass();
        response.setJokes(myJokeString);

        return response;
    }

}



