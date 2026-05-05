package org.example.pages;

import org.example.JLabelSystem;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class page {

    private String pageName;

    private ArrayList<JLabel> pageLabels;




    public page(String page, ArrayList<Label> pageLabels) {




    }

    public void addJLabel(JLabel label) {

        JLabelSystem label = new JLabelSystem(label);

    }



}
