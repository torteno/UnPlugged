package org.example; 

import javax.swing.JButton; 

public class JButtonSystem {
    private final Frame frame; 
    
    public JButtonSystem(Frame frame) {
        this.frame = frame; 
    }

    public JButton button (int x, int y, int width, int height, String text) {
        JButton button = new JButton(text); 
        button.setBounds(x, y, width, height); 
        frame.backgroundPanel.add(button); 
        frame.revalidate(); 
        frame.repaint(); 
        return button; 
    }
}