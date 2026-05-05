package org.example;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import java.awt.*;
import java.io.IOException;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;


public class frame {

    JFrame frame;
    public JPanel backgroundPanel;
    JLabelSystem label = new JLabelSystem(this);

    //ONLY FOR A TEST DO NOT CONTINUE CREATING JLABELS IN THIS WAY
    JLabel logo;
    JLabel background;


    frame() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        frame = new JFrame("UnPlug");
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setSize(1000,1000);

        frame.setDefaultCloseOperation(EXIT_ON_CLOSE); //add a close operation to ask why they wanted to leave
        backgroundPanel = new JPanel();
        backgroundPanel.setLayout(null);
      //  backgroundPanel.setBackground(Color.cyan);


        frame.add(backgroundPanel);



        musicSystem.sequencer("music/nature.wav", 10, 0, true);
        logo = label.assets(0, 0, frame.getWidth(), frame.getHeight(), "images/UI/UnPlug logo.png", true, 0, true, false);
        background = label.assets(0, 0, frame.getWidth(), frame.getHeight(), "images/UI/background.png", false, 100, true, true);


        backgroundPanel.setLayout(null);
        frame.setContentPane(backgroundPanel);







    }









}
