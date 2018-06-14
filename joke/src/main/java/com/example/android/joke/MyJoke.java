package com.example.android.joke;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyJoke {

    public String getJoke() {

        String randomJoke;
        Random randomNumber = new Random();

        List<String> jokeList = new ArrayList<>();
        jokeList.add("first joke");
        jokeList.add("second joke");
        jokeList.add("third joke");
        jokeList.add("fourth joke");
        jokeList.add("fifth joke");

        randomJoke = jokeList.get(randomNumber.nextInt(jokeList.size()));

        return randomJoke;
    }
}
