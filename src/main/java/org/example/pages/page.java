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

    private ArrayList<Component> pageComponents;

    private JLabelSystem labelSystem;

    private frame myFrame;

    private boolean visibility;


    public page(frame myFrame, String page) {

        this.myFrame = myFrame;
        pageName = page;
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
            }
        } else {
            for (Component comp : pageComponents) {
                comp.setVisible(false);
            }
        }

    }

    public ArrayList<Component> getPageComponents() {
        return pageComponents;
    }



}
