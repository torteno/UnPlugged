package org.example;
import java.io.IOException; 

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame extends JFrame {
    public JPanel backgroundPanel;
    JLabelSystem label; 

    Frame() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        label = new JLabelSystem(this); 
        setTitle("UnPlug");
        setResizable(true);
        setSize(1000,1000);

        setDefaultCloseOperation(EXIT_ON_CLOSE); //add a close operation to ask why they wanted to leave
        backgroundPanel = new BackgroundPanel("images/UI/background.gif");
        setContentPane(backgroundPanel);

        musicSystem.sequencer("music/nature.wav", 10, 0, true);

        setVisible(true);
        revalidate(); 
        repaint(); 
    }
}