package org.example.pages;

import org.example.JLabelSystem;
import org.example.frame;
import org.example.userData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class focusPage extends page {

    private JLabel plant;
    private JLabel focusTimer;
    private JLabel startButton;
    private JLabel stopButton;

    boolean isFocusActive = false;

    double stageDifference = 60 * 0.1; // 8 minutes per stage, can be changed to whatever we want, just make sure to change the name of the variable to something more fitting if you do change it

    int activeStage = 4;

    private static int seconds = 0;
    private Timer timer;


    public focusPage(frame myFrame) {
        super(myFrame, "focusPage");
    }

    public static int getSeconds() {
        return seconds;
    }

    public static void setSeconds(int seconds) {
        focusPage.seconds = seconds;
    }

    @Override
    public void initializePage() {

        plant = labelSystem.assets(300, 540,200, 200, userData.getActiveSkin()[activeStage], false, 0, true, false);
        System.out.println(userData.getActiveSkin()[activeStage]);

        addComponent(plant);


        if(!myFrame.builder) {

        startButton = labelSystem.assets(422, 524, 200, 200, "images/UI/play.png", false, 0, true, false, true, new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        startFocus();
                        startButton.setVisible(false);
                        startButton.setComponentZOrder(startButton, 10);
                        stopButton.setVisible(true);
                        stopButton.setComponentZOrder(stopButton, 0);
                    }
                });
        } else {
            startButton = labelSystem.assets(422, 524, 200, 200, "images/UI/play.png", false, 0, true, false);
        }

        addComponent(startButton);

    if(!myFrame.builder) {
        stopButton = labelSystem.assets(422, 524, 200, 200, "images/UI/stop.png", false, 0, true, false, true, new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                stopFocus();
                stopButton.setVisible(false);
                stopButton.setComponentZOrder(stopButton, 10);
                startButton.setVisible(true);
                startButton.setComponentZOrder(startButton, 0);
            }
        });
    } else {
        stopButton = labelSystem.assets(422, 524, 200, 200, "images/UI/stop.png", false, 0, true, false);

    }
        addComponent(stopButton);




        timer = new Timer(1000, e -> {
            seconds++;
            focusTimer.setText(formatTime(seconds));

            if(seconds / userData.getActiveSkin().length > stageDifference) {
                activeStage++;
                plant.setIcon(new ImageIcon(userData.getActiveSkin()[activeStage]));
            }

        });

        focusTimer = new JLabel(formatTime(seconds), SwingConstants.CENTER);
        focusTimer.setFont(new Font("SansSerif", Font.BOLD, 48));
        focusTimer.setBounds(420, 160, 200, 50);
        addComponent(focusTimer);





    }



    public String formatTime(int numSecs) {

        int minutes = numSecs / 60;
        int secs = numSecs % 60;

        return String.format("%02d:%02d", minutes, secs); // Now it honestly doesnt look that bad: https://www.geeksforgeeks.org/java/java-string-format-method-with-examples/
    }

    public void startFocus() {

        if(!isFocusActive) {
            isFocusActive = true;
        }


    }

    public void stopFocus() {

        if(isFocusActive) {
            isFocusActive = false;
            timer.stop();
        }

    }






}
