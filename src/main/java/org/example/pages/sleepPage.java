package org.example.pages;

import org.example.frame;
import org.example.userData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class sleepPage extends page {

    private JLabel celestial; // creates the celestial JLabel (moon/sun) which will brighten with the time, swapped out as the active stage advances
    private JLabel sleepTimer; // creates the timer JLabel which shows the time elapsed since bedtime in minutes and seconds, it will be updated every second by the timer object created later on
    private JLabel startButton; // creates the start button JLabel which will start the timer and the brightening of the celestial when clicked
    private JLabel stopButton; // creates the stop button JLabel which will stop the timer and the brightening of the celestial when clicked, it will be invisible at first and only become visible when the start button is clicked
    private JLabel quoteLabel; // creates the JLabel which will display an inspirational quote during the 15 second deterrence window
    private JLabel statsLabel; // small readout in the corner showing total time slept

    boolean isSleepActive = false; // creates a boolean variable to keep track of whether sleep mode is active, used to prevent multiple timers from being started at the same time and to prevent the timer from being stopped when it is not active

    double stageDifference = 60 * 0.1; // how long each celestial brightness stage should last, can be tuned to taste

    int activeStage = 0; // active stage of the celestial brightening

    private static int seconds = 0; // creates a static integer variable to keep track of the number of seconds that have passed since sleep mode was started, used to update the timer JLabel and decide when to advance the celestial stage
    private Timer timer; // the Timer object which updates the seconds variable, the timer JLabel and the celestial stage every second, started when the start button is clicked and stopped when the stop button is clicked

    private Timer deterrenceTimer; // separate Timer used to count down the 15 second reflection prompt before the user is allowed to abandon sleep mode
    private int deterrenceSeconds = 0; // tracks how many seconds of the 15 second deterrence window have elapsed

    public sleepPage(frame myFrame) { // constructor for the sleepPage class which takes a frame object as a parameter and passes it to the superclass constructor
        super(myFrame, "sleepPage");
    }

    public static int getSeconds() { // gets the seconds variable
        return seconds;
    }

    public static void setSeconds(int seconds) { // sets the seconds variable in a public method so it can be accessible in other classes
        sleepPage.seconds = seconds;
    }

    @Override
    public void initializePage() { // initializes the sleepPage by creating the celestial JLabel, the timer JLabel, the start button JLabel and the stop button JLabel, it also sets the bounds and the icons for each of them and adds them to the page using the addComponent method from the superclass

        celestial = labelSystem.assets(300, 540, 200, 200, userData.getActiveSkin()[activeStage], false, 0, true, false); // reuses the active skin for the brightening visual, can be swapped for a celestial skin set later
        System.out.println(userData.getActiveSkin()[activeStage]);

        addComponent(celestial); // adds the celestial JLabel to the page using the addComponent method from the superclass


        if(!myFrame.builder) { // checks so there arent 2 mouse listeners for one jlabel running at once

            startButton = labelSystem.assets(422, 524, 200, 200, "images/UI/play.png", false, 0, true, false, true, new MouseAdapter() { // start button to start the sleep timer
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (!e.getComponent().isEnabled()) { // verifies to check if its enabled so it doesnt run when you are on another page
                        return;
                    }
                    startSleep(); // calls the startSleep method to start the timer and the brightening of the celestial
                    startButton.setVisible(false); // makes the start button invisible when clicked
                    stopButton.setVisible(true); // makes the stop button visible when the start button is clicked
                }
            });
        } else {
            startButton = labelSystem.assets(422, 524, 200, 200, "images/UI/play.png", false, 0, true, false); // if we are in builder mode we dont want the start button to have a mouse listener so we create it without one
        }

        addComponent(startButton); // adds the start button JLabel to the page using the addComponent method from the superclass

        if(!myFrame.builder) { // checks so there arent 2 mouse listeners for one jlabel running at once
            stopButton = labelSystem.assets(422, 524, 200, 200, "images/UI/stop.png", false, 0, true, false, true, new MouseAdapter() { // stop button to stop the sleep timer
                @Override
                public void mouseClicked(MouseEvent e) { // begins the 15 second deterrence window which displays an inspirational quote before allowing the user to actually stop sleep mode
                    if (!e.getComponent().isEnabled()) { // verifies to check if its enabled so it doesnt run when you are on another page
                        return;
                    }
                    triggerDeterrence(); // shows the inspirational quote and runs the 15 second reflection countdown before letting the user fully abandon sleep mode
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
            sleepTimer.setText(formatTime(seconds)); // updates the text of the timer JLabel to show the current time in minutes and seconds using the formatTime method to format the seconds variable into a string in the format of "mm:ss"

            int targetStage = (int)(seconds / stageDifference); // calculates which stage we should be on based on how many seconds have passed and how long each stage lasts
            if(targetStage >= userData.getActiveSkin().length) { // clamps the target to the last available skin frame so we never index past the array and crash
                targetStage = userData.getActiveSkin().length - 1;
            }
            if(targetStage > activeStage) { // only swap the icon when crossing into a new stage, not on every tick after the threshold
                activeStage = targetStage; // bumps the active stage variable up to the new stage
                celestial.setIcon(labelSystem.getIcon(userData.getActiveSkin()[activeStage], 200, 200)); // updates the icon using the JLabelSystem cache so the image is loaded from the classpath and properly scaled
            }
        });

        sleepTimer = new JLabel(formatTime(seconds), SwingConstants.CENTER); // creates a new JLabel for the timer and sets its text to the formatted time using the formatTime method and centers the text horizontally using SwingConstants.CENTER
        sleepTimer.setFont(new Font("SansSerif", Font.BOLD, 48)); // sets the font of the timer JLabel to SansSerif, bold and size 48 to make it more visible and aesthetically pleasing
        sleepTimer.setBounds(420, 160, 200, 50); // sets the bounds of the timer JLabel to position it on the page, you can adjust these values to move it around and find the best position for it
        addComponent(sleepTimer); // adds the timer JLabel to the page using the addComponent method from the superclass

        quoteLabel = new JLabel("", SwingConstants.CENTER); // empty until the deterrence window is triggered, when it will display an inspirational quote and the 15 second countdown
        quoteLabel.setFont(new Font("SansSerif", Font.ITALIC, 18));
        quoteLabel.setBounds(150, 230, 700, 50);
        quoteLabel.setForeground(Color.WHITE);
        quoteLabel.setVisible(false);
        addComponent(quoteLabel);

        statsLabel = new JLabel(buildStatsText(), SwingConstants.CENTER); // small stat readout in the right side of the page, refreshed after every stopSleep so the user sees their progress right away
        statsLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        statsLabel.setBounds(700, 250, 250, 40);
        statsLabel.setForeground(Color.WHITE);
        addComponent(statsLabel);
    }


    public String formatTime(int numSecs) { // creates a method to format the seconds variable into a string in the format of "mm:ss" to be displayed on the timer JLabel, it takes an integer parameter numSecs which is the number of seconds that have passed since sleep mode was started and returns a string in the format of "mm:ss"

        int minutes = numSecs / 60; // calculates the number of minutes by dividing the numSecs variable by 60 using integer division to get the whole number of minutes
        int secs = numSecs % 60; // calculates the number of seconds by getting the remainder of the numSecs variable divided by 60 using the modulus operator to get the remaining seconds after the minutes have been calculated

        return String.format("%02d:%02d", minutes, secs);
    }

    public void startSleep() { // creates a method to start sleep mode which starts the timer and the brightening of the celestial, it also checks if sleep mode is already active to prevent multiple timers from being started at the same time

        if(!isSleepActive) { // checks if sleep mode is not already active to prevent multiple timers from being started at the same time
            isSleepActive = true; // sets the isSleepActive variable to true to indicate that sleep mode is now active
            timer.start(); // starts the timer which will update the seconds variable and the timer JLabel every second, it will also check if the celestial stage needs to be updated every time it updates
        }
    }

    public void stopSleep() { // creates a method to stop sleep mode which stops the timer and the brightening of the celestial, it also checks if sleep mode is active to prevent the timer from being stopped when it is not active

        if(isSleepActive) {
            isSleepActive = false;
            timer.stop();
            userData.addTimeSleeping(seconds); // banks the duration of this session into the running total used by the stats readout
            userData.addCoins(seconds / 60); // rewards one coin per full minute of sleep, simple gamification per the proposal
            seconds = 0; // resets the elapsed seconds for the next session so the timer starts fresh next time
            sleepTimer.setText(formatTime(seconds));
            activeStage = 0; // resets the celestial back to the first stage for the next session
            celestial.setIcon(labelSystem.getIcon(userData.getActiveSkin()[activeStage], 200, 200));
            statsLabel.setText(buildStatsText());
            myFrame.refreshTopBar(); // refreshes the top bar so the coin total stays in sync with what the user just earned
        }
    }

    private String buildStatsText() { // builds the string for the stats label, shows how much total time the user has spent in sleep mode
        int minutes = userData.getTotalTimeSleeping() / 60;
        return "Time sleeping: " + minutes + " min";
    }

    private void triggerDeterrence() { // shows an inspirational quote for 15 seconds before the user is allowed to fully exit sleep mode, this is the proposal's "take a deep breath / reflect / do a body check" prompt

        deterrenceSeconds = 0;
        quoteLabel.setText("Rest is productive. Breathe in... breathe out. (15)");
        quoteLabel.setVisible(true);

        deterrenceTimer = new Timer(1000, e -> {
            deterrenceSeconds++;
            int remaining = 15 - deterrenceSeconds;
            quoteLabel.setText("Rest is productive. Breathe in... breathe out. (" + remaining + ")");

            if (deterrenceSeconds >= 15) { // after 15 seconds, give the user the choice to actually stop or continue sleeping
                deterrenceTimer.stop();
                quoteLabel.setVisible(false);

                int choice = JOptionPane.showConfirmDialog(null, "Continue sleep session?", "UnPlug", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.NO_OPTION) {
                    stopSleep();
                }
            }
        });
        deterrenceTimer.start();
    }
}