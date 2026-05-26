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

    private ArrayList<Component> pageComponents = new ArrayList<>();

    protected JLabelSystem labelSystem;

    protected frame myFrame;

    private boolean visibility;


    public page(frame myFrame, String page) {

        this.myFrame = myFrame;
        pageName = page;
        this.labelSystem = new JLabelSystem(myFrame);
    }

    public void addComponent(Component comp) {
        pageComponents.add(comp);
        myFrame.backgroundPanel.add(comp);

    } // AYYYYY I found out baout this new way to add components and stuff to JFrames, makes it so we can quickly add stuff rather then just Jlabels! very cool Oracle devs :D


    public abstract void initializePage();

    public void pageState(boolean state) {

        if(state) {
            for (Component comp : pageComponents) {
                comp.setVisible(true);
                comp.setLocation(comp.getX() - 10000, comp.getY()); // Move the component back to its original position
            }
        } else {
            for (Component comp : pageComponents) {
                comp.setVisible(false);
                comp.setLocation(comp.getX() + 10000, comp.getY()); // Move the component far away to prevent it from being interacted with
            }
        }

    }

    public ArrayList<Component> getPageComponents() {
        return pageComponents;
    }



}
