package org.example;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.*;


public class musicSystem {

    static Clip clip;
    static HashMap<String, Clip> clips = new HashMap<>();

    static float volume = 0.5f;
    
    public static void sequencer(String input, float volume, int repeat, boolean loop) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream audio = null;

        File file = new File(input);
        if (file.exists()) {
            audio = AudioSystem.getAudioInputStream(file);
        } else {
            var stream = musicSystem.class.getResourceAsStream("/" + input);
            if (stream != null) {
                audio = AudioSystem.getAudioInputStream(stream);
            } else {
                System.err.println("Audio file not found: " + input);
                return;
            }
        }

        if(clips.get(input) != null) {
            if(clips.get(input).isRunning()) {
                clips.get(input).stop();
            }
            clips.remove(input).close();
        }

        clip = AudioSystem.getClip();
        clip.open(audio);
        clips.put(input, clip);
        clip.start();
        if(loop) {
            clip.loop(999999999);
        } else if(repeat > 0) {
            clip.loop(repeat);
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