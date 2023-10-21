package com.sefMusicApplication;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class TestingArtist {

	private Main main;
	private ByteArrayOutputStream errorMessage;
	
	@Before
	public void setUp() {
		// Setting up initial conditions before each test
		setMain(new Main());
		errorMessage = new ByteArrayOutputStream();// Creating a stream to capture error messages
		System.setErr(new PrintStream(errorMessage));// Redirecting standard error stream
	}
	
	 @Test
	 public void testAddArtist_ValidData() {
	    Artist artist = new Artist("567AABBB&*", "Shashvat Mishra", "Melbourne|Victoria|Australia",
	            "05-11-1980", "Hello my name is Shashvat Mishra and I am a song writer and composer.",
	            new ArrayList<>(List.of("Singer")), new ArrayList<>(List.of("rock", "classical")), new ArrayList<>(List.of("2022, Best award for song writing and composing")));

	        assertTrue(artist.addArtist());
	    }

    @Test
    public void testing_InvalidID() {
        Artist artist = new Artist("123AABBB&*", "Shashvat Mishra", "Melbourne|Victoria|Australia",
                "15-10-2023", "Hello my name is Shashvat Mishra.",
                new ArrayList<>(List.of("Singer")), new ArrayList<>(List.of("rock", "classical")), new ArrayList<>(List.of("2022, Best award for song writing and composing")));

        assertFalse(artist.addArtist());
    }

    @Test
    public void testing_InvalidBirthdate() {
        Artist artist = new Artist("567AABBB&*", "Shashvat Mishra", "Melbourne|Victoria|Australia",
                "15/10/2023", "Hello my name is Shashvat Mishra.",
                new ArrayList<>(List.of("Singer")), new ArrayList<>(List.of("rock", "classical")), new ArrayList<>(List.of("2022, Best award for song writing and composing")));

        assertFalse(artist.addArtist());
    }

    @Test
    public void testing_InvalidAddress() {
        Artist artist = new Artist("567AABBB&*", "Shashvat Mishra", "Melbourne,Victoria,Australia",
        		"15-10-2023", "Hello my name is Shashvat Mishra.",
                new ArrayList<>(List.of("Singer")), new ArrayList<>(List.of("rock", "classical")), new ArrayList<>(List.of("2022, Best award for song writing and composing")));

        assertFalse(artist.addArtist());
    }

    @Test
    public void testing_InvalidBioLength() {
        Artist artist = new Artist("567AABBB&*", "Shashvat Mishra", "Melbourne|Victoria|Australia",
        		"15-10-2023", "Hello",
                new ArrayList<>(List.of("Singer")), new ArrayList<>(List.of("rock", "classical")), new ArrayList<>(List.of("2022, Best award for song writing and composing")));

        assertFalse(artist.addArtist());
    }

    @Test
    public void testing_InvalidOccupations() {
        Artist artist = new Artist("567AABBB&*", "Shashvat Mishra", "Melbourne|Victoria|Australia",
        		"15-10-2023", "Hello my name is Shashvat Mishra.",
                new ArrayList<>(List.of("Singer", "Writer", "Composer", "Director", "Lyricist", "DJ")), new ArrayList<>(List.of("rock", "classical")), new ArrayList<>(List.of("2022, Best award for song writing and composing")));

        assertFalse(artist.addArtist());
    }

    @Test
    public void testing_InvalidAwards() {
        Artist artist = new Artist("567AABBB&*", "Shashvat Mishra", "Melbourne|Victoria|Australia",
        		"15-10-2023", "Hello my name is Shashvat Mishra.",
                new ArrayList<>(List.of("Singer")), new ArrayList<>(List.of("rock", "classical")),
                new ArrayList<>(List.of("2022, Title")));

        assertFalse(artist.addArtist());
    }

    @Test
    public void testing_InvalidGenres() {
        Artist artist = new Artist("567AABBB&*", "Shashvat Mishra", "Melbourne|Victoria|Australia",
        		"15-10-2023", "Hello my name is Shashvat Mishra.",
                new ArrayList<>(List.of("Singer")), new ArrayList<>(List.of("pop", "rock", "classical")), new ArrayList<>(List.of("2022, Best award for song writing and composing")));

        assertFalse(artist.addArtist());
    }
    

    @After
    public void tearDown() {
	// Cleaning up resources after each test
    	System.setErr(System.err);
    }
	public Main getMain() {
		return main;
	}
	public void setMain(Main main) {
		this.main = main;
	}
}

