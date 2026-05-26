package org.example;
import org.example.overlays.navigation;
import org.example.pages.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ArrayList;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;


public class frame {

    JFrame frame;
    public JPanel backgroundPanel;
    JLabelSystem label = new JLabelSystem(this);
    private static page activePage; // current active page
    public static final boolean builder = false; // wether certain components should be movable
    private final int EDGE = 8;  // pixels from edge that count as a resize handle

    public focusPage focusPg = new focusPage(this); // focus page


    public goPage goPg = new goPage(this); // go page


    public socialPage socialPg = new socialPage(this); // social page


    public sleepPage sleepPg = new sleepPage(this); // sleep page

    public navigation nav = new navigation(this); // navigation overlay


 //   MouseMotionListener mouseMotionListener = buildSystem();


    // list of pages in the frame
    private ArrayList<page> pages = new ArrayList<>();


    // sets the frame width and with and height of the frame
    private final int FRAME_WIDTH = 1000;
    private final int FRAME_HEIGHT = 1000;


        frame() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        frame = new JFrame("UnPlug"); // create a new JFrame with the name UnPlug

        //frame.setSize(1000,1000);

        frame.setDefaultCloseOperation(EXIT_ON_CLOSE); //add a close operation to ask why they wanted to leave
        backgroundPanel = new GradientPanel(); // create a new JPanel to add to the frame, this is where all the components will be added to
        backgroundPanel.setLayout(null); // set the layout to null so we can set the location of the components manually


            userData data = new userData(); // create a new userData object to load and save the user's data
            data.saveData(); // save the user's data to a file

            data.loadSkins(); // load the user's accessible skins, this is just a placeholder for now, it will be replaced with a method that loads the skins from the user's data file



        //  backgroundPanel.setBackground(Color.cyan);
        backgroundPanel.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT
        ));

     //   frame.add(backgroundPanel);
        frame.setContentPane(backgroundPanel);

        //JLabel background = label.assets(0, 0, 1000, 1000, "images/UI/background.gif", false, 100, true, true);
        //backgroundPanel.setComponentZOrder(background, backgroundPanel.getComponentCount() - 1);
        backgroundPanel.revalidate(); // revalidate the background panel to update the layout of the components
        backgroundPanel.repaint(); // repaint the background panel to update the display of the components


        nav.initializeOverlay(); // initialize the navigation overlay, this will add the navigation buttons to the background panel

//initializes the pages

        focusPg.initializePage();


        goPg.initializePage();


        socialPg.initializePage();


        sleepPg.initializePage();

        activePage = focusPg;


        frame.pack(); // pack the frame to fit the size of the background panel and its components

        frame.setLocationRelativeTo(null); // set the location of the frame to the center of the screen
        frame.setResizable(true); // allow the frame to be resizable, this is important for the builder mode to work properly



        musicSystem.sequencer("music/nature.wav", 10, 0, true); // play the background music, this is just a placeholder for now, it will be replaced with a method that plays the music from the user's data file
        //logo = label.assets(0, 0, frame.getWidth(), frame.getHeight(), "images/UI/UnPlug logo.png", true, 0, true, false);




            // BUILDER MODEMADE ENTIRLY BY AI, JUST TO MAKE OUR LIVES EASIER SO WE CAN DRAG THE COMPONENTS AROUND AND REZISE THEM, I WILL DELETE FOR THE FINAL SUBMISSION

            if (builder) {
                java.awt.event.MouseAdapter m = new java.awt.event.MouseAdapter() {
                    Component sel; Point start; Rectangle init; int mode; // 0=move 1=R 2=L 3=B 4=T

                    boolean onActivePage(Component c) {
                        return activePage != null && activePage.getPageComponents().contains(c);
                    }

                    public void mousePressed(MouseEvent e) {
                        Component c = backgroundPanel.getComponentAt(e.getPoint());
                        System.out.println("clicked hash=" + System.identityHashCode(c)
                                + "  activePage hash=" + System.identityHashCode(activePage)
                                + "  list size=" + activePage.getPageComponents().size());
                        for (Component pc : activePage.getPageComponents()) {
                            System.out.println("  in list: " + System.identityHashCode(pc)
                                    + "  matches=" + (pc == c));
                        }
                        if (c == null || c == backgroundPanel || !onActivePage(c)) return;

                        if (SwingUtilities.isRightMouseButton(e)) {
                            for (Component pc : activePage.getPageComponents()) {
                                Rectangle r = pc.getBounds();
                                String path = pc.getName() == null ? "PATH" : pc.getName();
                                System.out.println("labelSystem.assets(" + r.x + "," + r.y + ","
                                        + r.width + "," + r.height + ",\"" + path + "\",false,1,true,false);");
                            }
                            return;
                        }

                        sel = c; start = e.getPoint(); init = c.getBounds();
                        int rx = e.getX() - init.x, ry = e.getY() - init.y;
                        if      (rx > init.width  - EDGE) mode = 1;
                        else if (rx < EDGE)               mode = 2;
                        else if (ry > init.height - EDGE) mode = 3;
                        else if (ry < EDGE)               mode = 4;
                        else                              mode = 0;
                    }

                    public void mouseDragged(MouseEvent e) {
                        if (sel == null) return;
                        int dx = e.getX() - start.x, dy = e.getY() - start.y;
                        Rectangle r = new Rectangle(init);
                        if      (mode == 0) { r.x += dx; r.y += dy; }
                        else if (mode == 1) { r.width  += dx; }
                        else if (mode == 2) { r.x += dx; r.width  -= dx; }
                        else if (mode == 3) { r.height += dy; }
                        else                { r.y += dy; r.height -= dy; }
                        sel.setBounds(r);

                        if (sel instanceof JLabel && sel.getName() != null) {
                            ((JLabel) sel).setIcon(label.gifs(sel.getName(), r.width, r.height));
                        }
                    }

                    public void mouseReleased(MouseEvent e) { sel = null; }
                };
                backgroundPanel.addMouseListener(m);
                backgroundPanel.addMouseMotionListener(m);
            }

      //  backgroundPanel.addMouseMotionListener(mouseMotionListener);
    //    backgroundPanel.addMouseListener(mouseListener);

        frame.setVisible(true);


        // adds all the pages to the array
        pages.add(focusPg);
        pages.add(goPg);
        pages.add(socialPg);
        pages.add(sleepPg);

    }



    // sets the active page to the page that is passed in, this is used to switch between pages when the navigation buttons are clicked, it also sets the visibility of the components on the page to true and the components on the other pages to false
    public void setActivePage(page page) {
            if(page != activePage) {
                activePage = page;
                for (page pg : pages) {
                    if (pg == page) {
                        pg.pageState(true);
                    } else {
                        pg.pageState(false);
                    }
                }
            }
    }

    public page getActivePage() {
            return activePage;
    }



    public ArrayList<page> getPages() {
        return pages;
    }

    public void setPages(ArrayList<page> pages) {
        this.pages = pages;
    }
}
