package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class JLabelSystem extends JLabel {




    private static final Map<String, ImageIcon> SPRITE_CACHE = new HashMap<>();

    public Map<String, JLabel> assets = new HashMap<>();

    private final frame myFrame;


    public JLabelSystem(frame myFrame) {
        this.myFrame = myFrame;
    }

    private ImageIcon getSprite(String filePath, int width, int height) {
        String key = filePath + "|" + width + "x" + height; // Create a unique key for the sprite based on its file path and dimensions
        ImageIcon cached = SPRITE_CACHE.get(key); // Check if the sprite is already cached
        if (cached != null) return cached; // If the sprite is already cached, return it

        ImageIcon raw; // Load the source image as an ImageIcon
        URL imageURL = getClass().getResource("/" + filePath); // Try to load the image from the classpath
        if (imageURL != null) {
            raw = new ImageIcon(imageURL); // If the image is found in the classpath, create an ImageIcon from the URL
        } else {
            raw = new ImageIcon(filePath);
        }

        // Render the source image into a sized BufferedImage now, so the source can be GC'd.

        // Taken from my old project, Pumpkin Quest: https://github.com/torteno/PumpkinQuest

        BufferedImage scaled = new BufferedImage(Math.max(1, width), Math.max(1, height), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = scaled.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(raw.getImage(), 0, 0, width, height, null);
        g.dispose();
        raw.getImage().flush();

        ImageIcon icon = new ImageIcon(scaled);
        SPRITE_CACHE.put(key, icon);
        return icon;
    }

    public JLabel assets(int x, int y, int width, int height, String filePath, boolean opaque, int zOrder, boolean visible, boolean gif) {
        ImageIcon Icon = getSprite(filePath, width, height);




        if(gif) {
            Icon = gifs(filePath, width, height);
        }

        JLabel label = new JLabel(Icon);
        label.setName(filePath);
        label.setBounds(x, y, width, height); // Set the bounds of the JLabel to the specified x, y, width, and height
        label.setOpaque(opaque); // If the asset should be opaque, set the JLabel to be opaque

        myFrame.backgroundPanel.add(label);

        if (visible) {
            label.setVisible(true); // If the asset should be visible, set the JLabel to be visible
        } else {
            label.setVisible(false); // If the asset should not be visible, set the JLabel to be invisible
        }

        int maxZOrder = myFrame.backgroundPanel.getComponentCount() - 1; // fixed with gpt // Get the maximum z-order based on the number of components in the background panel
        zOrder = Math.max(0, Math.min(zOrder, maxZOrder)); // fixed with gpt // Ensure the z-order is within valid bounds (0 to maxZOrder)
        myFrame.backgroundPanel.setComponentZOrder(label, zOrder); // Set the z-order of the JLabel in the background panel to the specified zOrder




        return label; // Return the JLabel representing the asset
    }

    public ImageIcon getIcon(String filePath, int width, int height) {  // A public method to get an ImageIcon with the specified file path and dimensions, this can be used to get the icon for buttons and stuff
        return getSprite(filePath, width, height);
    }

    public ImageIcon gifs(String filePath) {
        URL location = getClass().getResource("/" + filePath);
        if (location == null) {
            System.err.println("GIF not found: " + filePath);
            return new ImageIcon();
        }
        return new ImageIcon(location);
    }

    public JLabel assets(int x, int y, int width, int height, String filePath, boolean opaque, int zOrder, boolean visible, boolean gif, boolean button, MouseAdapter mouseListener) {
        ImageIcon Icon = getSprite(filePath, width, height);




        if(gif) {
            Icon = gifs(filePath, width, height);
        }

        JLabel label = new JLabel(Icon);
        label.setBounds(x, y, width, height); // Set the bounds of the JLabel to the specified x, y, width, and height


        if (opaque) {
            label.setOpaque(true); // If the asset should be opaque, set the JLabel to be opaque
        } else {
            label.setOpaque(false); // If the asset should not be opaque, set the JLabel to be transparent
        }

        myFrame.backgroundPanel.add(label); // Add the JLabel to the background panel

        if(visible) {
            label.setVisible(true); // If the asset should be visible, set the JLabel to be visible
        } else {
            label.setVisible(false); // If the asset should not be visible, set the JLabel to be invisible
        }

        if(button && mouseListener != null) {
            label.addMouseListener(mouseListener);
        }

        int maxZOrder = myFrame.backgroundPanel.getComponentCount() - 1; // fixed with gpt // Get the maximum z-order based on the number of components in the background panel
        zOrder = Math.max(0, Math.min(zOrder, maxZOrder)); // fixed with gpt // Ensure the z-order is within valid bounds (0 to maxZOrder)
        myFrame.backgroundPanel.setComponentZOrder(label, zOrder); // Set the z-order of the JLabel in the background panel to the specified zOrder




        return label; // Return the JLabel representing the asset
    }


    public ImageIcon gifs(String filePath, int width, int height) {


        URL url = getClass().getResource("/" + filePath);
        //ImageIcon icon = new ImageIcon(url);
        //Image scaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);

        return new ImageIcon(url);



    }


   // @Override
    protected void paintComponent(Graphics g, ImageIcon gif, int x, int y, int width, int height) { // paints gifs
        super.paintComponent(g);
        g.drawImage(gif.getImage(), x, y, width, height, this);



    }
}