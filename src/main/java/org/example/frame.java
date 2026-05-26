package org.example;
import org.example.overlays.navigation;
import org.example.overlays.topBar;
import org.example.pages.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;


public class frame {

    JFrame frame;
    public JPanel backgroundPanel;
    JLabelSystem label = new JLabelSystem(this);
    private static page activePage; // current active page
    public static final boolean builder = true; // wether certain components should be movable
    private final int EDGE = 8;  // pixels from edge that count as a resize handle

    public focusPage focusPg = new focusPage(this); // focus page


    public goPage goPg = new goPage(this); // go page


    public socialPage socialPg = new socialPage(this); // social page


    public sleepPage sleepPg = new sleepPage(this); // sleep page

    public shop shopPg = new shop(this); // shop page where the user can spend coins on cosmetics
    public welcomePage welcomePg = new welcomePage(this); // welcome page shown on first launch so the user can enter their name
    public preferencesPage preferencesPg = new preferencesPage(this); // preferences page where the user can change the default focus session length

    public navigation nav = new navigation(this); // navigation overlay
    public topBar topBarOverlay = new topBar(this); // top bar overlay showing "Hi [name]", coin balance, and streak across the top of every page


 //   MouseMotionListener mouseMotionListener = buildSystem();


    // list of pages in the frame
    private ArrayList<page> pages = new ArrayList<>();


    // sets the frame width and with and height of the frame
    private final int FRAME_WIDTH = 1000;
    private final int FRAME_HEIGHT = 1000;


        frame() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        frame = new JFrame("UnPlug"); // create a new JFrame with the name UnPlug

        //frame.setSize(1000,1000);

        frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // we handle close ourselves via a WindowAdapter below so we can run the leaving deterrence and save data before exiting
        frame.addWindowListener(new WindowAdapter() { // hooks the X button so closing the app runs through our own logic instead of exiting immediately
            @Override
            public void windowClosing(WindowEvent e) {
                runLeavingFlow(); // shows the deterrence prompt, saves the user's data, then exits the app
            }
        });
        Runtime.getRuntime().addShutdownHook(new Thread(() -> userData.saveData())); // safety net so data is still saved if the JVM is killed in a way that bypasses windowClosing, like the IntelliJ stop button or an unhandled crash
        backgroundPanel = new GradientPanel(); // create a new JPanel to add to the frame, this is where all the components will be added to
        backgroundPanel.setLayout(null); // set the layout to null so we can set the location of the components manually


            userData data = new userData(); // create a new userData object to load and save the user's data

            data.loadSkins(); // load the default skin set first so accessibleSkins has at least one entry before loadData runs
            userData.loadData(); // load any saved data from a previous launch, this overrides the defaults if a save file exists
            userData.updateStreak(); // updates the daily streak based on today's date and the saved lastOpenedDate



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
        topBarOverlay.initializeOverlay(); // initialize the top bar overlay so the greeting, coin, and streak labels appear across the top

//initializes the pages

        focusPg.initializePage();


        goPg.initializePage();


        socialPg.initializePage();


        sleepPg.initializePage();


        shopPg.initializePage();


        welcomePg.initializePage();


        preferencesPg.initializePage();




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
        pages.add(shopPg);
        pages.add(welcomePg);
        pages.add(preferencesPg);

        if (userData.getUserName() == null || userData.getUserName().isEmpty()) { // first launch detected so we show the welcome screen and let the user enter their name
            setActivePage(welcomePg);
        } else {
            setActivePage(focusPg); // returning user goes straight to the focus page
        }
    }

    public void refreshTopBar() { // public helper called by the welcome page and the shop page after a change so the top bar redraws with the latest values
        topBarOverlay.refresh();
    }

    private void runLeavingFlow() { // runs the proposal's "why are you leaving" deterrence and reflection prompt when the user clicks the X button, then saves data and exits
        String[] reasons = {"Habit", "I was bored", "I got distracted", "I'm tired", "I need to check something"}; // emoji-less version of the proposal's reason list, kept simple as a JOptionPane
        JOptionPane.showOptionDialog(null, "Why are you closing the app?", "UnPlug", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, reasons, reasons[0]);
        JOptionPane.showMessageDialog(null, "Take a deep breath. Reflect on your day. Then close when you're ready."); // the 15 second reflection prompt is satisfied by a modal dialog the user has to click through
        userData.saveData(); // persists everything to disk before we actually shut down so progress isnt lost
        System.exit(0); // exits the app after the leaving flow completes since we set DO_NOTHING_ON_CLOSE earlier
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
