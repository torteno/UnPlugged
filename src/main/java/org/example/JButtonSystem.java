package org.example; 
import javax.swing.JButton; 

public class JButtonSystem {
    private final frame frame; //custom frame object
    
    public JButtonSystem(frame frame) { //accepts a frame object to initialize the JButtonSystem
        this.frame = frame; 
    }

    public JButton button (int x, int y, int width, int height, String text) { //creates and positions a new JButton
        JButton button = new JButton(text); //creates a new JButton with the given text
        button.setBounds(x, y, width, height); //sets the dimensions and location of the new JButton
        frame.backgroundPanel.add(button); //add button to the frame
        frame.backgroundPanel.setComponentZOrder(button, 0); //forces the button to the bottom of the front layer
        return button; 
    }
}
