package org.example.overlays;

import org.example.JLabelSystem;
import org.example.frame;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class navigation extends overlay {

    JLabel logo;
    JLabel focus;
    JLabel social;
    JLabel sleep;
    JLabel go;

    private frame myFrame;



    public navigation(frame myFrame) {
        super(myFrame, "navigation");
        this.myFrame = myFrame;
    }


    @Override
    public void initializeOverlay() {

        logo = labelSystem.assets(25, 0, 200, 200, "images/UI/logo.png", false, 0, true, false);
        addComponent(logo);

        focus = labelSystem.assets(25, 250, 200, 150, "images/UI/focus.png", false, 0, true, false, true, new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                myFrame.setActivePage(myFrame.focusPg);
            }
        });
        addComponent(focus);

        go = labelSystem.assets(25, 400, 200, 150, "images/UI/go.png", false, 0, true, false, true, new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                myFrame.setActivePage(myFrame.goPg);
            }
        });
        addComponent(go);

        sleep = labelSystem.assets(25, 550, 200, 150, "images/UI/sleep.png", false, 0, true, false , true, new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                myFrame.setActivePage(myFrame.sleepPg);
            }
        });
        addComponent(sleep);


        social = labelSystem.assets(25, 700, 200, 150, "images/UI/social.png", false, 0, true, false, true, new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                myFrame.setActivePage(myFrame.socialPg);
            }
        });
        addComponent(social);

    }
}
