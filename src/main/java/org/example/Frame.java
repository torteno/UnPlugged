package org.example;
import java.io.IOException; 

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Frame extends JFrame {
    public JPanel backgroundPanel;
    JLabelSystem label; 
    JLabel logo;
    JLabel background;


    Frame() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        label = new JLabelSystem(this); 
        setTitle("UnPlug");
        setResizable(true);
        setSize(1000,1000);

        setDefaultCloseOperation(EXIT_ON_CLOSE); //add a close operation to ask why they wanted to leave
        backgroundPanel = new JPanel();
        backgroundPanel.setLayout(null);
        setContentPane(backgroundPanel);

        musicSystem.sequencer("music/nature.wav", 10, 0, true);
        logo = label.assets(0, 0, getWidth(), getHeight(), "images/UI/UnPlug logo.png", true, 0, true, false);
        background = label.assets(0, 0, getWidth(), getHeight(), "images/UI/background.png", false, 100, true, true);

        backgroundPanel.add(background); 
        backgroundPanel.setComponentZOrder(background, backgroundPanel.getComponentCount() - 1);
        backgroundPanel.add(logo); 
        setVisible(true);
        revalidate(); 
        repaint(); 
    }
}