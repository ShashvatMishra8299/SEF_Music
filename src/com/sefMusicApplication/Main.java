package com.sefMusicApplication;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static boolean main(String[] args) {
        // You can create instances of the Artist class and test its functionality here.
        Artist artist = new Artist("567AABBB&*", "Shashvat Mishra", "Melbourne|Victoria|Australia",
                "05-11-1980", "Hello my name is Shashvat Mishra.",
                new ArrayList<>(List.of("Singer")), new ArrayList<>(List.of("rock", "classical")), new ArrayList<>(List.of("2022, Best award for song writing and composing")));

        // Add the artist to the "artists.txt" file
        if (artist.addArtist()) {
            return true;
        } else {
        	return false;  
        }
    }
}
