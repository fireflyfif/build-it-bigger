package com.example.android.joke;

import java.util.Random;

public class MyJoke {

    private static String[] jokeArray = {"Q: What's the object-oriented way to become wealthy? " +
            "\n\nA: Inheritance",
            "Algorithm - \n" +
            "\nWord used by programmers when they don't want to explain what they did.",
            "Q: What is a programmer's favourite hangout place? " +
                    "\n\nA: Foo Bar",
            "Three Database SQL walked into a NoSQL bar." +
                    "\n\nA little while later... they walked out " +
                    "\n\nBecause they couldn't find a table.",
            "if (sad() == true) {" +
                    "\nsad.stop.();" +
                    "\nbeAwesome();" +
                    "\n}",
            "Android: " +
                    "\n\nWhere ProgressBars go around in circles and Spinners don't spin",
            "\"I'd like to make the world a better place. " +
                    "\n\nBut they won't give me the source code...\"",
            "Java and C were telling jokes. " +
                    "\nIt was C's turn, so he writes something on the wall, " +
                    "\npoints to it and says: " +
                    "\n\"Do you get the reference?\" " +
                    "\n\nBut Java didn't."
    };


    public String getJoke() {

        Random random = new Random();
        int randomNumber = random.nextInt(jokeArray.length);

        return jokeArray[randomNumber];
    }

}
