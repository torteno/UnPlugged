package org.example.pages;

import org.example.JLabelSystem;
import org.example.frame;
import org.example.userData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class focusPage extends page {

    private JLabel plant; // creates the plant JLabel which will grow with the time
    private JLabel focusTimer; // creates the timer JLabel which will show the time in minutes and seconds, it will be updated every second by the timer object that we will create later on
    private JLabel startButton; // creates the start button JLabel which will start the timer and the growth of the plant when clicked
    private JLabel stopButton; // creates the stop button JLabel which will stop the timer and the growth of the plant when clicked, it will be invisible at first and only become visible when the start button is clicked
    private JLabel statsLabel; // small readout in the corner showing total time focused and number of sessions completed, refreshed after every stop

    boolean isFocusActive = false; // creates a boolean variable to keep track of whether the focus mode is active or not, it will be used to prevent multiple timers from being started at the same time and to prevent the timer from being stopped when it is not active

    double stageDifference = 60 * 0.1; // how many seconds each plant growth stage should last before advancing to the next icon

    int activeStage = 0; // active stage of the plant growth, starts at 0 so the plant begins as a seedling and grows up over the session

    private static int seconds = 0; // creates a static integer variable to keep track of the number of seconds that have passed since the focus mode was started, it will be updated every second by the timer object that we will create later on and it will be used to update the timer JLabel and to determine when to change the plant growth stage
    private Timer timer; // creates a Timer object which will be used to update the seconds variable and the timer JLabel every second, it will be started when the start button is clicked and stopped when the stop button is clicked


    public focusPage(frame myFrame) { // constructor for the focusPage class which takes a frame object as a parameter and passes it to the superclass constructor
        super(myFrame, "focusPage");
    }

    public static int getSeconds() { // gets the seconds variable
        return seconds;
    }

    public static void setSeconds(int seconds) { // sets the seconds variable in a public method so it can be accessible in other classes
        focusPage.seconds = seconds;
    }

    @Override
    public void initializePage() { // initializes the focusPage by creating the plant JLabel, the timer JLabel, the start button JLabel and the stop button JLabel, it also sets the bounds and the icons for each of them and adds them to the page using the addComponent method from the superclass

        plant = labelSystem.assets(422, 281,200, 200, userData.getActiveSkin()[activeStage], false, 0, true, false);
        System.out.println(userData.getActiveSkin()[activeStage]);

        addComponent(plant); // adds the plant JLabel to the page using the addComponent method from the superclass


        if(!myFrame.builder) { // checks so there arent 2 mouse listeners for one jlabel running at once

        startButton = labelSystem.assets(422, 524, 200, 200, "images/UI/play.png", false, 0, true, false, true, new MouseAdapter() { // start button to start the focus timer
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (!e.getComponent().isEnabled()) { // verifies to check if its enabled so it doesnt run when you are on another page
                            return;
                        }
                        startFocus(); // calls the startFocus method to start the timer and the growth of the plant
                        startButton.setVisible(false); // makes the start button invisible when clicked
                        stopButton.setVisible(true); // makes the stop button visible when the start button is clicked
                    }
                });
        } else {
            startButton = labelSystem.assets(422, 524, 200, 200, "images/UI/play.png", false, 0, true, false); // if we are in builder mode we dont want the start button to have a mouse listener so we create it without one
        }

        addComponent(startButton); // adds the start button JLabel to the page using the addComponent method from the superclass

    if(!myFrame.builder) { // checks so there arent 2 mouse listeners for one jlabel running at once
        stopButton = labelSystem.assets(422, 524, 200, 200, "images/UI/stop.png", false, 0, true, false, true, new MouseAdapter() { // stop button to stop the focus timer
            @Override
            public void mouseClicked(MouseEvent e) { // calls the stopFocus method to stop the timer and the growth of the plant when clicked
                if (!e.getComponent().isEnabled()) { // verifies to check if its enabled so it doesnt run when you are on another page
                    return;
                }
                stopFocus(); // calls the stopFocus method to stop the timer and the growth of the plant when clicked
                stopButton.setVisible(false); // makes the stop button invisible when clicked
                startButton.setVisible(true); // makes the start button visible when the stop button is clicked
            }
        });
    } else {
        stopButton = labelSystem.assets(422, 524, 200, 200, "images/UI/stop.png", false, 0, true, false); // if we are in builder mode we dont want the stop button to have a mouse listener so we create it without one

    }
        addComponent(stopButton); // adds the stop button JLabel to the page using the addComponent method from the superclass




        timer = new Timer(1000, e -> { // creates a new Timer object that updates every 1000 milliseconds (1 second) and takes an ActionListener
            seconds++; // increments the seconds variable by 1 every time the timer updates
            focusTimer.setText(formatTime(seconds)); // updates the text of the timer JLabel to show the current time in minutes and seconds using the formatTime method to format the seconds variable into a string in the format of "mm:ss"

            int targetStage = (int)(seconds / stageDifference); // calculates which stage we should be on based on how many seconds have passed and how long each stage lasts
            if(targetStage >= userData.getActiveSkin().length) { // clamps the target to the last available skin frame so we never index past the array and crash
                targetStage = userData.getActiveSkin().length - 1;
            }
            if(targetStage > activeStage) { // only swap the icon when crossing into a new stage, not on every tick after the threshold
                activeStage = targetStage; // bumps the active stage variable up to the new stage
                plant.setIcon(labelSystem.getIcon(userData.getActiveSkin()[activeStage], 200, 200)); // updates the icon using the JLabelSystem cache so the image is loaded from the classpath and properly scaled, instead of new ImageIcon which only reads from disk
            }

        });

        focusTimer = new JLabel(formatTime(seconds), SwingConstants.CENTER); // creates a new JLabel for the timer and sets its text to the formatted time using the formatTime method and centers the text horizontally using SwingConstants.CENTER
        focusTimer.setFont(new Font("SansSerif", Font.BOLD, 48)); // sets the font of the timer JLabel to SansSerif, bold and size 48 to make it more visible and aesthetically pleasing
        focusTimer.setBounds(420, 160, 200, 50); // sets the bounds of the timer JLabel to position it on the page, you can adjust these values to move it around and find the best position for it
        addComponent(focusTimer);  // adds the timer JLabel to the page using the addComponent method from the superclass


        statsLabel = new JLabel(buildStatsText(), SwingConstants.CENTER); // small stat readout in the right side of the page, refreshed after every stopFocus so the user sees their progress right away
        statsLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        statsLabel.setBounds(700, 250, 250, 60);
        statsLabel.setForeground(Color.WHITE);
        addComponent(statsLabel);



    }



    public String formatTime(int numSecs) { // creates a method to format the seconds variable into a string in the format of "mm:ss" to be displayed on the timer JLabel, it takes an integer parameter numSecs which is the number of seconds that have passed since the focus mode was started and returns a string in the format of "mm:ss"

        int minutes = numSecs / 60; // calculates the number of minutes by dividing the numSecs variable by 60 using integer division to get the whole number of minutes
        int secs = numSecs % 60; // calculates the number of seconds by getting the remainder of the numSecs variable divided by 60 using the modulus operator to get the remaining seconds after the minutes have been calculated

        return String.format("%02d:%02d", minutes, secs); // Now it honestly doesnt look that bad: https://www.geeksforgeeks.org/java/java-string-format-method-with-examples/
    }

    public void startFocus() { // creates a method to start the focus mode which starts the timer and the growth of the plant, it also checks if the focus mode is already active to prevent multiple timers from being started at the same time

        if(!isFocusActive) { // checks if the focus mode is not already active to prevent multiple timers from being started at the same time
            isFocusActive = true; // sets the isFocusActive variable to true to indicate that the focus mode is now active
            timer.start(); // starts the timer which will update the seconds variable and the timer JLabel every second, it will also check if the plant growth stage needs to be updated every time it updates
        }


    }

    public void stopFocus() { // creates a method to stop the focus mode which stops the timer and the growth of the plant, it also checks if the focus mode is active to prevent the timer from being stopped when it is not active

        if(isFocusActive) {
            isFocusActive = false;
            timer.stop();
            userData.addTimeOffPhone(seconds); // banks the duration of this session into the running total used by the stats readout
            userData.addCoins(seconds / 60); // rewards one coin per full minute of focus, simple gamification per the proposal
            if(seconds >= 60) { // only counts as a completed session if the user focused for at least one minute, prevents accidental clicks from inflating the streak
                userData.incrementSessions();
            }
            seconds = 0; // resets the elapsed seconds for the next session so the timer starts fresh next time
            focusTimer.setText(formatTime(seconds));
            activeStage = 0; // resets the plant back to the first stage for the next session
            plant.setIcon(labelSystem.getIcon(userData.getActiveSkin()[activeStage], 200, 200));
            statsLabel.setText(buildStatsText()); // refreshes the stat readout so the user sees their new totals immediately after stopping
            myFrame.refreshTopBar(); // refreshes the top bar so the coin total stays in sync with what the user just earned
        }

    }

    private String buildStatsText() { // builds the string for the stats label, HTML is used so the JLabel can render two lines of text inside one component
        int minutes = userData.getTotalTimeOffPhone() / 60;
        return "<html><div style='text-align:center;'>Sessions: " + userData.getSessionsCompleted()
                + "<br>Total focus: " + minutes + " min</div></html>";
    }






}
