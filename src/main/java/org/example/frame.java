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
    private static page activePage;
    public static final boolean builder = true;
    private final int EDGE = 8;  // pixels from edge that count as a resize handle

    public focusPage focusPg = new focusPage(this);


    public goPage goPg = new goPage(this);


    public socialPage socialPg = new socialPage(this);


    public sleepPage sleepPg = new sleepPage(this);

    public navigation nav = new navigation(this);


 //   MouseMotionListener mouseMotionListener = buildSystem();

    private ArrayList<page> pages = new ArrayList<>();

    private final int FRAME_WIDTH = 1000;
    private final int FRAME_HEIGHT = 1000;


        frame() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        frame = new JFrame("UnPlug");

        //frame.setSize(1000,1000);

        frame.setDefaultCloseOperation(EXIT_ON_CLOSE); //add a close operation to ask why they wanted to leave
        backgroundPanel = new GradientPanel();
        backgroundPanel.setLayout(null);


            userData data = new userData();
            data.saveData();

            data.loadSkins();



        //  backgroundPanel.setBackground(Color.cyan);
        backgroundPanel.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT
        ));

     //   frame.add(backgroundPanel);
        frame.setContentPane(backgroundPanel);

        //JLabel background = label.assets(0, 0, 1000, 1000, "images/UI/background.gif", false, 100, true, true);
        //backgroundPanel.setComponentZOrder(background, backgroundPanel.getComponentCount() - 1);
        backgroundPanel.revalidate();
        backgroundPanel.repaint();


        nav.initializeOverlay();



        focusPg.initializePage();


        goPg.initializePage();


        socialPg.initializePage();


        sleepPg.initializePage();

        activePage = focusPg;


        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setResizable(true);



        musicSystem.sequencer("music/nature.wav", 10, 0, true);
        //logo = label.assets(0, 0, frame.getWidth(), frame.getHeight(), "images/UI/UnPlug logo.png", true, 0, true, false);



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

        pages.add(focusPg);
        pages.add(goPg);
        pages.add(socialPg);
        pages.add(sleepPg);

    }


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
