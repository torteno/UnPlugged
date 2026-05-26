package org.example.overlays;

import org.example.JLabelSystem;
import org.example.frame;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class navigation extends overlay {

    JLabel logo; // logo of the app, also serves as a home button
    JLabel focus; // focus page button
    JLabel social; // social page button
    JLabel sleep; // sleep page button
    JLabel go; // go page button
    JButton shopButton; // shop page button, plain JButton since we dont have shop icon art yet
    JButton prefsButton; // preferences page button, plain JButton since we dont have a preferences icon yet

    private frame myFrame;



    public navigation(frame myFrame) { // constructor for the navigation overlay, takes in the main frame as a parameter
        super(myFrame, "navigation");
        this.myFrame = myFrame;
    }


    @Override
    public void initializeOverlay() {

        // Initialize the navigation buttons and add them to the overlay
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

        shopButton = new JButton("Shop"); // plain JButton with text since we dont have shop icon art, sits under the social nav button
        shopButton.setBounds(25, 860, 95, 40);
        shopButton.addActionListener(e -> myFrame.setActivePage(myFrame.shopPg));
        addComponent(shopButton);

        prefsButton = new JButton("Prefs"); // plain JButton with text since we dont have a preferences icon, sits next to the shop button
        prefsButton.setBounds(130, 860, 95, 40);
        prefsButton.addActionListener(e -> myFrame.setActivePage(myFrame.preferencesPg));
        addComponent(prefsButton);

    }
}
