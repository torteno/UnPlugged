package org.example;

import java.awt.Graphics;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class BackgroundPanel extends JPanel {
    private ImageIcon gif;

    public BackgroundPanel(String gifPath) {
        URL url = getClass().getResource("/" + gifPath);
        if (url != null) {
            gif = new ImageIcon(url);
            gif.setImageObserver(this);
        }
        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gif != null) {
            g.drawImage(gif.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }
}