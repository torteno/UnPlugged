package org.example.pages;

import org.example.JLabelSystem;
import org.example.frame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;

public abstract class page {

    private String pageName;

    private ArrayList<Component> pageComponents = new ArrayList<>(); // holds all the page components
    protected JLabelSystem labelSystem; // // referneces the Jlabel class to be used
    protected frame myFrame; // refernece to the original frame

    private boolean visibility; //boolean that determines whether the page is currently visible or not, this is used to hide and show the components on the page when switching pages


    public page(frame myFrame, String page) { // constructs the page that every other page will be built off of

        this.myFrame = myFrame;
        pageName = page;
        this.labelSystem = new JLabelSystem(myFrame);
    }

    public void addComponent(Component comp) { // adds components
        pageComponents.add(comp); // adds the component to the page components arraylist
        myFrame.backgroundPanel.add(comp); // adds the component to the background

    } // AYYYYY I found out baout this new way to add components and stuff to JFrames, makes it so we can quickly add stuff rather then just Jlabels! very cool Oracle devs :D


    public abstract void initializePage(); // method to initial each page

    public void pageState(boolean state) { // wehter the page is visible or not

        if(state) {
            for (Component comp : pageComponents) {
                comp.setVisible(true);
                comp.setEnabled(true);
              //  comp.setLocation(comp.getX() - 10000, comp.getY()); // Move the component back to its original position
            }
        } else {
            for (Component comp : pageComponents) {
                comp.setVisible(false);
                comp.setEnabled(false);
                //comp.setLocation(comp.getX() + 10000, comp.getY()); // Move the component far away to prevent it from being interacted with
            }
        }

    }

    public ArrayList<Component> getPageComponents() {
        return pageComponents;
    }



}
