package com.sefMusicApplication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public class Artist {
    private String ID;
    private String Name;
    private String Address;
    private String Birthdate;
    private String Bio;
    private ArrayList<String> Occupations;
    private ArrayList<String> Genres;
    private ArrayList<String> Awards;

    public Artist(String artistid, String artistname, String artistadd, String artistbirthday, String bio,
                  ArrayList<String> occupations, ArrayList<String> musicgenres, ArrayList<String> awards) {
        ID = artistid;
        Name = artistname;
        Address = artistadd;
        Birthdate = artistbirthday;
        Bio = bio;
        Occupations = occupations;
        Genres = musicgenres;
        Awards = awards;
    }

    private boolean isValidID(String artistid) {
        // Check if the ID has the correct length
        if (artistid.length() != 10) {
            return false;
        }

        // Check the first three characters are numbers between 5 to 9
        for (int i = 0; i < 3; i++) {
            char c = artistid.charAt(i);
            if (c < '5' || c > '9') {
                return false;
            }
        }

        // Check the next five characters are upper-case letters (A to Z)
        for (int i = 3; i < 8; i++) {
            char c = artistid.charAt(i);
            if (c < 'A' || c > 'Z') {
                return false;
            }
        }

        // Check the last two characters are special characters
        for (int i = 8; i < 10; i++) {
            char c = artistid.charAt(i);
            if ("!@#$%^&*()_+-=[]{};:'\",<.>/?".indexOf(c) == -1) {
                return false;
            }
        }
        
        return true;
        
    }

    private boolean isValidBirthdate(String artistbirthdate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false); // Disallow lenient parsing

        try {
            dateFormat.parse(artistbirthdate);
            
            return true; // If parsing succeeds, the date is valid
        } catch (ParseException e) {
        	
            return false; // Parsing failed, so the date is not valid
        }
    }

    private boolean isValidBio(String bio) {
        // Initialize word count to zero
        int biolength = 0;

        // Initialize a flag to track spaces
        boolean initialStage = false;

        for (char c : bio.toCharArray()) {
            if (Character.isWhitespace(c)) {
                // If a space is encountered, set the inWord flag to false
                initialStage = false;
            } else if (!initialStage) {
                // If a non-whitespace character is encountered and the inWord flag is false,
                // it means a new word has started
                biolength++;
                initialStage = true;
            }
        }
        
        // Check if the word count is between 10 and 30
        return biolength >= 10 && biolength <= 30;
    }

    
    private boolean isValidAddress(String artistaddr) {
        // Split the address using the pipe character '|'
        String[] parts = artistaddr.split("\\|");

        // Check if there are exactly three parts (City, State, Country)
        if (parts.length != 3) {
            return false;
        }

        // Check if each part contains at least one word character or space
        for (String part : parts) {
            if (!part.matches("[\\w\\s]+")) {
                return false;
            }
        }
       
        return true;
    }

    private boolean isValidOccupations(ArrayList<String> occupations) {
        //System.out.println("occupation entry"+ occupations);
    	int noofOccupation = 0; // Initialize the occupation count to zero

        // Loop through the occupations and count them
        for (String occupation : occupations) {
            if (!occupation.isEmpty()) {
            	noofOccupation++;
            }

            // If you've reached the maximum allowed occupations, break out of the loop
            if (noofOccupation >= 5) {
                break;
            }
        }
        //System.out.println("Validoccupation");
        // Check if there's at least one occupation and at most five
        return noofOccupation > 0 && noofOccupation <= 5;
    }


    private boolean isValidGenres(ArrayList<String> musicgenres) {
        // Check if there are at least two genres, at most five, and no 'pop' and 'rock' at the same time
        if (musicgenres.size() < 2 || musicgenres.size() > 5) {
            return false;
        }
        
        return !(musicgenres.contains("pop") && musicgenres.contains("rock"));
    }
    
    private boolean isValidAwards(ArrayList<String> awards) {
        // Check if each award matches the format "Year, Title" and the title is between 4 to 10 words
        for (String award : awards) {
            String[] parts = award.split(", ");
            if (parts.length != 2) {
                return false;
            }
            String year = parts[0];
            String title = parts[1];
            if (!Pattern.matches("\\d{4}", year) || title.split("\\s+").length < 4 || title.split("\\s+").length > 10) {
                return false;
            }
        }
        
        return true;
    }

    
    public boolean addArtist() {
        //isValidOccupations(Occupations);
    	if (isValidID(ID)&& isValidOccupations(Occupations)&& isValidBirthdate(Birthdate)&& isValidBio(Bio)&& isValidAwards(Awards)&& isValidGenres(Genres)&& isValidAddress(Address))
            {
    		System.out.println("Inside");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("artists.txt", true))) {
                // Format the artist information and write it to the file
                String artistInfo = ID + "|" + Name + "|" + Address + "|" + Birthdate + "|" + Bio + "|"
                        + String.join(",", Occupations) + "|" + String.join(",", Genres) + "|"
                        + String.join(",", Awards);
                System.out.println(artistInfo);
                writer.write(artistInfo + "\n");
                writer.close();
                return true;
            } catch (IOException e) {
                System.err.println("An error occurred while writing to 'artists.txt': " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    private boolean isArtistDataValid() {
        return (isValidID(ID) && isValidBirthdate(Birthdate) && isValidAddress(Address) &&
                isValidBio(Bio) && isValidOccupations(Occupations) && isValidAwards(Awards) &&
                isValidGenres(Genres));
    }

    
    //UPDATING THE ARTIST
    public boolean updateArtist(String updatedName, String updatedAddress, String updatedBirthdate, String updatedBio,
                            ArrayList<String> updatedOccupations, ArrayList<String> updatedGenres, ArrayList<String> updatedAwards) {
        // Check if the provided data meets all the constraints
        if (!isValidBirthdate(updatedBirthdate) || !isValidAddress(updatedAddress) ||
            !isValidBio(updatedBio) || !isValidGenres(updatedGenres) || !isValidAwards(updatedAwards)) {
            return false; // New data doesn't meet constraints
        }

        // Check the birthdate constraint
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date birthDate = dateFormat.parse(Birthdate);
            @SuppressWarnings("unused")
			Date newBirthDate = dateFormat.parse(updatedBirthdate);

            if (birthDate.before(dateFormat.parse("01-01-2000"))) {
                // Artist was born before 2000, so we can't change occupations or awards
                if (!Occupations.equals(updatedOccupations) || !isAwardsChangeable(updatedAwards)) {
                    return false;
                }
            }
        } catch (ParseException e) {
            return false; // Error parsing birthdates
        }

        // If all constraints are met, update the artist's information
        Name = updatedName;
        Address = updatedAddress;
        Birthdate = updatedBirthdate;
        Bio = updatedBio;
        Genres = updatedGenres;

        // If the artist was born before 2000, we cannot change their awards, so we only update the title of awards given after 2000
        if (Awards != null) {
            for (int i = 0; i < updatedAwards.size(); i++) {
                if (Awards.size() > i) {
                    String[] newAwardParts = updatedAwards.get(i).split(", ");
                    String[] existingAwardParts = Awards.get(i).split(", ");
                    if (newAwardParts.length == 2 && existingAwardParts.length == 2) {
                        int newAwardYear = Integer.parseInt(newAwardParts[0]);
                        int existingAwardYear = Integer.parseInt(existingAwardParts[0]);
                        if (existingAwardYear >= 2000 && newAwardYear >= 2000) {
                            Awards.set(i, updatedAwards.get(i)); // Update the title for awards after 2000
                        }
                    }
                }
            }
        }

        // Save the updated information to the TXT file
        if (isArtistDataValid()) {
            // Define the file paths
            String rfilePath = "data/artists.txt";
            String wfilePath = "data/artists.txt";

            try {
                // Open the input file for reading and a temporary file for writing
                BufferedReader buffread = new BufferedReader(new FileReader(rfilePath));
                BufferedWriter buffwrite = new BufferedWriter(new FileWriter(wfilePath));

                String line;
                String artistID = ID;
                String newArtistInfo = artistID + "|" + Name + "|" + Address + "|" + Birthdate + "|" + Bio + "|"
                        + String.join(",", Occupations) + "|" + String.join(",", Genres) + "|"
                        + String.join(",", Awards);

                while ((line = buffread.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    if (parts.length > 0 && parts[0].equals(artistID)) {
                        // Update the artist's information
                        buffwrite.write(newArtistInfo + "\n");
                    } else {
                        // Write the original line to the temporary file
                        buffwrite.write(line + "\n");
                    }
                }

                // Close the reader and writer
                buffread.close();
                buffwrite.close();

                // Rename the temporary file to the original file to apply the update
                File inputFile = new File(rfilePath);
                File tempFile = new File(wfilePath);
                if (tempFile.renameTo(inputFile)) {
                    return true; // Update successful
                } else {
                    System.err.println("Error while updating artist related information.");
                }
            } catch (IOException e) {
                System.err.println("Error while updating artist related information: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return false; 
    }// Artist data is not valid or update failed


    private boolean isAwardsChangeable(ArrayList<String> updatedAwards) {
        // Check if an artist's awards (year and title) can be changed
        if (Awards != null) {
            for (int i = 0; i < updatedAwards.size(); i++) {
                if (Awards.size() > i) {
                    String[] newAwards = updatedAwards.get(i).split(", ");
                    String[] existingAwards = Awards.get(i).split(", ");
                    if (newAwards.length == 2 && existingAwards.length == 2) {
                        int newAwardYear = Integer.parseInt(newAwards[0]);
                        int previousYearAwrds = Integer.parseInt(existingAwards[0]);
                        if (previousYearAwrds < 2000 || newAwardYear < 2000) {
                            return false; // Cannot change awards given before 2000
                        }
                    }
                }
            }
        }
        return true;
    }
}





