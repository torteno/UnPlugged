package org.example;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;


public class musicSystem {

    static Clip clip;

    static HashMap<String, Clip> clips = new HashMap<>();

    public static void sequencer(String input, float volume, int repeat, boolean loop) throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        File file = new File(input);
        AudioInputStream audio = null;

        if(file.exists()) {
            audio = AudioSystem.getAudioInputStream(file);
        } else {

            //try grabbing it from the class path, neec to update  it because it needs to work with mac and windows because it was causing crashes in my last comp scoi project :sob:
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



