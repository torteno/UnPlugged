package org.example;
import org.example.pages.page;

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
    private page activePage;

    MouseMotionListener mouseMotionListener = buildSystem();

    private ArrayList<page> pages = new ArrayList<>();

    private final int FRAME_WIDTH = 1000;
    private final int FRAME_HEIGHT = 1000;


        frame() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        frame = new JFrame("UnPlug");

        //frame.setSize(1000,1000);

        frame.setDefaultCloseOperation(EXIT_ON_CLOSE); //add a close operation to ask why they wanted to leave
        backgroundPanel = new JPanel();
        backgroundPanel.setLayout(null);


        //  backgroundPanel.setBackground(Color.cyan);
        backgroundPanel.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT
        ));

     //   frame.add(backgroundPanel);
        frame.setContentPane(backgroundPanel);

        JLabel background = label.assets(0, 0, 1000, 1000, "UI/background.gif", false, 100, true, true);

        backgroundPanel.revalidate();
        backgroundPanel.repaint();


        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setResizable(true);



        musicSystem.sequencer("music/nature.wav", 10, 0, true);
        //logo = label.assets(0, 0, frame.getWidth(), frame.getHeight(), "images/UI/UnPlug logo.png", true, 0, true, false);

        userData data = new userData();
        data.saveData();

        backgroundPanel.addMouseMotionListener(mouseMotionListener);
        backgroundPanel.addMouseListener(mouseListener);

        frame.setVisible(true);

    }


    public void setActivePage(page page) {
            activePage = page;
            for(page pg: pages) {
                if(pg == page) {
                    pg.pageState(true);
                } else {
                    pg.pageState(false);
                }
            }
    }

    public page getActivePage() {
            return activePage;
    }


    private MouseMotionListener buildSystem() {
            return new MouseMotionListener() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    Component label = backgroundPanel.getComponentAt(e.getPoint());
                    if(label instanceof JLabel) {
                        label.setBackground(Color.CYAN);
                        label.setLocation(e.getPoint());
                    }
                    label.setBackground(Color.BLUE);
                }

                @Override
                public void mouseMoved(MouseEvent e) {

                }
            };
    }


    MouseListener mouseListener = new MouseListener() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {

        }

        @Override
        public void mousePressed(java.awt.event.MouseEvent e) {

        }

        @Override
        public void mouseReleased(java.awt.event.MouseEvent e) {

        }

        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {

        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {

        }
    };


    public ArrayList<page> getPages() {
        return pages;
    }

    public void setPages(ArrayList<page> pages) {
        this.pages = pages;
    }
}
