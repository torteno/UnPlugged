package org.example.overlays;

import org.example.JLabelSystem;
import org.example.frame;

import java.awt.*;
import java.util.ArrayList;

public abstract class overlay {
    

        private String overlayName;

        private ArrayList<Component> overlayComponents;

        protected JLabelSystem labelSystem;

        private frame myFrame;

        private boolean visibility;


        public overlay(frame myFrame, String overlay) {

            this.myFrame = myFrame;
            overlayName = overlay;
            this.labelSystem = new JLabelSystem(myFrame);
            this.overlayComponents = new ArrayList<>();
        }

        public void addComponent(Component comp) {
            overlayComponents.add(comp);
            myFrame.backgroundPanel.add(comp);

        } // AYYYYY I found out baout this new way to add components and stuff to JFrames, makes it so we can quickly add stuff rather then just Jlabels! very cool Oracle devs :D


        public abstract void initializeOverlay();

        public void overlayState(boolean state) {

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
        }



    }


