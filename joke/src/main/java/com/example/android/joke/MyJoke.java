package com.example.android.joke;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyJoke {

    public String getJoke() {

        String randomJoke;
        Random randomNumber = new Random();

        List<String> jokeList = new ArrayList<>();

        jokeList.add("Q: What's the object-oriented way to become wealthy? " +
                "\n\nA: Inheritance");
        jokeList.add("Algorithm - " +
                "\nWord used by programmers when they don't want to explain what they did.");
        jokeList.add("Q: What is a programmer's favourite hangout place? " +
                "\n\nA: Foo Bar");
        jokeList.add("3 Database SQL walked into a NoSQL bar." +
                "\nA little while later... they walked out " +
                "\nBecause they couldn't find a table.");
        jokeList.add("if (sad() == true) {" +
                "\nsad.stop.();" +
                "\nbeAwesome();" +
                "\n}");
        jokeList.add("Android: " +
                "\nwhere ProgressBars go around in circles and Spinners don't spin");
        jokeList.add("\"I’d like to make the world a better place. " +
                "\nBut they won’t give me the source code…\"");
        jokeList.add("Java and C were telling jokes. " +
                "\nIt was C's turn, so he writes something on the wall, " +
                "\npoints to it and says " +
                "\n\"Do you get the reference?\" " +
                "\nBut Java didn't.");

        jokeList.add("first joke");
        jokeList.add("second joke");
        jokeList.add("third joke");
        jokeList.add("fourth joke");
        jokeList.add("fifth joke");

        randomJoke = jokeList.get(randomNumber.nextInt(jokeList.size()));

        return randomJoke;
    }

}
