package org.example;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class musicSystem {

    static Clip clip;
    static HashMap<String, Clip> clips = new HashMap<>();
    
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

    public static void volume(float volume) {

       // clip != null

    }
}