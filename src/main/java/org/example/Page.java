package org.example;
import java.util.ArrayList;

import javax.swing.JLabel;

public abstract class Page {

    private final ArrayList<JLabel> pageLabels;

    private final JLabelSystem labelSystem; 
    private final JButtonSystem buttonSystem; 
    private final Frame myFrame; 

    private JLabel coins;
    private JLabel streak;

    public Page(Frame myFrame, String pageName, ArrayList<JLabel> pageLabels) {
        this.myFrame = myFrame;
        this.pageLabels = pageLabels;

        labelSystem = new JLabelSystem(myFrame); 
        buttonSystem = new JButtonSystem(myFrame);
    }

    public void loadNavigation() {
        coins = labelSystem.assets(20, 20, 50, 50, "assets/coins.png", false, 1, true, false); 
        streak = labelSystem.assets(90, 20, 50, 50, "assets/streak.png", false, 1, true, false);
        addJLabel(coins); 
        addJLabel(streak); 
    }

    public void addJLabel(JLabel label) {
        pageLabels.add(label);
        myFrame.backgroundPanel.add(label);
    }

    public void loadButtons() {
        buttonSystem.button(300, 200, 200, 50, "Play"); 
    }
}