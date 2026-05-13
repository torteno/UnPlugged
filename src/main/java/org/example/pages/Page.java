package org.example.pages;
import java.util.ArrayList;

import javax.swing.JLabel;

import org.example.Frame;
import org.example.JLabelSystem;

public abstract class Page {

    private final String pageName;
    private final ArrayList<JLabel> pageLabels;

    private final JLabelSystem labelSystem; 
    private final Frame myFrame; 

    private JLabel coins;
    private JLabel streak;

    public Page(Frame myFrame, String pageName, ArrayList<JLabel> pageLabels) {
        this.myFrame = myFrame;
        this.pageName = pageName;
        this.pageLabels = pageLabels;

        labelSystem = new JLabelSystem(myFrame); 
    }

    public void loadNavigation() {
        coins = labelSystem.assets(20, 20, 50, 50, "assets/coins.png", false, 1, true, false); 
        streak = labelSystem.assets(90, 20, 50, 50, "assets/streak.png", false, 1, true, false);
    }

    public void addJLabel(JLabel label) {
        pageLabels.add(label);
        myFrame.add(label);
    }
}