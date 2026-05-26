package org.example;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.*;


public class musicSystem {

    static Clip clip; // Static variable to hold the current clip being played
    static HashMap<String, Clip> clips = new HashMap<>(); // HashMap to store all the clips that have been played, with the file name as the key and the clip as the value

    static float volume = 0.5f; // Static variable to hold the current volume level, initialized to 0.5 (50% volume)
    
    public static void sequencer(String input, float volume, int repeat, boolean loop) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream audio = null; // Variable to hold the audio input stream

        File file = new File(input); // Check if the input is a file path
        if (file.exists()) { // If the file exists, get the audio input stream from the file
            audio = AudioSystem.getAudioInputStream(file); // Get the audio input stream from the file
        } else {
            var stream = musicSystem.class.getResourceAsStream("/" + input); // If the file does not exist, try to get the audio input stream from the resources folder
            if (stream != null) {
                audio = AudioSystem.getAudioInputStream(stream); // Get the audio input stream from the resources folder
            } else {
                System.err.println("Audio file not found: " + input);
                return;
            }
        }

        if(clips.get(input) != null) { // If the clip has already been played, stop it and remove it from the hashmap before playing it again
            if(clips.get(input).isRunning()) { // If the clip is currently running, stop it before removing it from the hashmap
                clips.get(input).stop(); // Stop the clip if it is currently running
            }
            clips.remove(input).close();
        }

        clip = AudioSystem.getClip(); // Get a new clip from the audio system
        clip.open(audio); // Open the audio input stream in the clip
        clips.put(input, clip); // Add the clip to the hashmap with the file name as the key and the clip as the value
        clip.start(); // Start playing the clip
        if(loop) {
            clip.loop(999999999); // loopps a tonnnnnnn of times so basically its forever
        } else if(repeat > 0) {// If the repeat parameter is greater than 0, loop the clip the specified number of times
            clip.loop(repeat); // Loop the clip the specified number of times
        }
    }

    public static void playClip(String clipName, int numRepeat, boolean loop) throws UnsupportedAudioFileException, LineUnavailableException, IOException { // Main method for playing music and sound effects

        sequencer(clipName, volume, numRepeat, loop); // Calls the sequencer method


    }

    // Safe method to stop the clip without null pointer exceptions
    public static void stopClip(String clipName) {

        Clip clip = clips.get(clipName); // Gets the clip from the hashmap

        if (clip != null && clip.isRunning()) { // Checks if the clip is not null and not running
            clip.stop(); // Stops the clip
            clip.close(); // Closes the clip
            clips.remove(clipName); // Removes the clip from the hashmap so it doesnt get constantly played
        }
    }


    public static void stopAllClips() { // Main method for stopping all the clips
        for(Map.Entry<String, Clip> entry : clips.entrySet()) { // Loops through all the clips in the hashmap
            stopClip(entry.getKey()); // Stops the clip
        }
    }

    public static void volumeChange(float volumeChange) { // Main method for changing the volume

        volume = volumeChange; // Increases or decreases the volume by the given value
        if (volume >= 1f) { // Ensures volume is between 0.0 and 1.0
            volume = 1f;
        } else if(volume <= 0f) {
            volume = 0.00001f; // Ensures volume is between 0.0 and 1.0
        }

        System.out.println(volume); // Print the current volume for debugging


        float dB = (float) (Math.log10(volume) * 20); // Convert volume (0.0 to 1.0) to decibels



        for(Clip c : clips.values()) { // Loops through all the clips in the hashmap
            if(c != null && c.isRunning()) { // Checks if the clip is not null and not running
                FloatControl volumeControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN); // Get the volume control from the clip
                volumeControl.setValue(dB); // Set the volume of the clip to the new value in decibels
            }
        }
    }
}