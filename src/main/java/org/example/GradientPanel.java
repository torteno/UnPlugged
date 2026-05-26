package org.example;

import javax.swing.*;
import java.awt.*;

public class GradientPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Define the dark blue gradient colors
        Color color1 = new Color(0, 0, 30);  // Near black-blue
        Color color2 = new Color(0, 20, 70); // Deep dark blue

        // Create a vertical gradient
        GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);

        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}
