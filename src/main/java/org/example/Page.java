package org.example;
import java.util.ArrayList;

import javax.swing.JLabel;

public abstract class Page {

    private final ArrayList<JLabel> pageLabels;

    private final JLabelSystem labelSystem; 
    private final JButtonSystem buttonSystem; 
    private final Frame myFrame; 

    public Page(Frame myFrame, String pageName, ArrayList<JLabel> pageLabels) {
        this.myFrame = myFrame;
        this.pageLabels = pageLabels;

        labelSystem = new JLabelSystem(myFrame); 
        buttonSystem = new JButtonSystem(myFrame);
    }

    public void loadNavigation() {
        addJLabel(labelSystem.assets(0, 0, 279, 207, "images/UI/logo.png", false, 1, true, false));
        addJLabel(labelSystem.assets(0, 0, 284, 113, "images/UI/focus.png", false, 1, true, false));
        addJLabel(labelSystem.assets(0, 0, 220, 113, "images/UI/go.png", false, 1, true, false));
        addJLabel(labelSystem.assets(0, 0, 284, 113, "images/UI/sleep.png", false, 1, true, false));
        addJLabel(labelSystem.assets(0, 0, 284, 113, "images/UI/social.png", false, 1, true, false));
        addJLabel(labelSystem.assets(0, 0, 294, 113, "images/UI/profile.png", false, 1, true, false));
    }

    public void addJLabel(JLabel label) {
        pageLabels.add(label);
    }

    public void loadButtons() {
        buttonSystem.button(300, 200, 200, 50, "Play"); 
    }
}