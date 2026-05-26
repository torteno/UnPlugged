package org.example.overlays;

import org.example.JLabelSystem;
import org.example.frame;

import java.awt.*;
import java.util.ArrayList;

public abstract class overlay {
    

        private String overlayName; // name of the overlay, used for identification and stuff, not really important but it can be useful for debugging and stuff

        private ArrayList<Component> overlayComponents; //list of overlay components

        protected JLabelSystem labelSystem;

        private frame myFrame;

        private boolean visibility;


        public overlay(frame myFrame, String overlay) { // constructor for the overlay class, takes in the frame and the name of the overlay, then initializes the label system and the arraylist for the components

            this.myFrame = myFrame;
            overlayName = overlay;
            this.labelSystem = new JLabelSystem(myFrame);
            this.overlayComponents = new ArrayList<>();
        }

        public void addComponent(Component comp) { // ads components to the overlay
            overlayComponents.add(comp);
            myFrame.backgroundPanel.add(comp);

        } // AYYYYY I found out baout this new way to add components and stuff to JFrames, makes it so we can quickly add stuff rather then just Jlabels! very cool Oracle devs :D


        public abstract void initializeOverlay();

        public void overlayState(boolean state) { // overlay state to basically say iif its visible or not

            if(state) {
                for (Component comp : overlayComponents) {
                    comp.setVisible(true);
                }
            } else {
                for (Component comp : overlayComponents) {
                    comp.setVisible(false);
                }
            }

        }

        public ArrayList<Component> getoverlayComponents() {
            return overlayComponents;
        } // gets the overlay components



    }


