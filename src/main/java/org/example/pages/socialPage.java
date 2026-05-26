package org.example.pages;

import org.example.frame;
import org.example.userData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class socialPage extends page {

    private JLabel plant; // creates the plant JLabel which will grow with the time
    private JLabel socialTimer; // creates the timer JLabel which will show the remaining time in minutes and seconds, it will be updated every second by the timer object that we will create later on
    private JLabel startButton; // creates the start button JLabel which will start the timer and the growth of the plant when clicked
    private JLabel stopButton; // creates the stop button JLabel which will stop the timer and the growth of the plant when clicked, it will be invisible at first and only become visible when the start button is clicked
    private JLabel starterLabel; // creates the JLabel which displays a conversation starter when the user reaches for their phone during a social gathering
    private JLabel timePrompt; // descriptive label above the input field so the user knows what number to type in
    private JTextField timeField; // input field for the estimated end of the social gathering in minutes
    private JButton setButton; // dedicated Set button under the text field so the user has an obvious way to confirm their input before pressing play
    private JLabel statsLabel; // small readout in the corner showing total time spent in social mode

    boolean isSocialActive = false; // creates a boolean variable to keep track of whether social mode is active, used to prevent multiple timers from being started at the same time and to prevent the timer from being stopped when it is not active

    double stageDifference = 60 * 0.1; // how long each plant growth stage should last, matched to the focus page so the experience feels consistent

    int activeStage = 0; // active stage of the plant growth

    private static int seconds = 0; // creates a static integer variable to keep track of the number of seconds remaining in the social gathering, used to update the timer JLabel and decide when to advance the plant stage
    private int totalSeconds = 0; // stores the original gathering length so the plant stage can advance against a known total
    private Timer timer; // the Timer object which updates the seconds variable, the timer JLabel and the plant stage every second, started when the start button is clicked and stopped when the stop button is clicked

    private final String[] conversationStarters = { // pool of conversation starters cycled through whenever the user tries to leave the gathering early
            "What's the best thing that happened to you this week at Crescent?",
            "If you could travel anywhere right now, where would you go?",
            "What show or movie have you been into lately?",
            "How was FNL last week? Incredible game by the whole team",
            "I heard that you made OFSSA for ultimate frisbee! Congrats! How did the game go to qualify?"
    };
    private int starterIndex = 0; // index into the conversationStarters array so the user sees a fresh prompt each time

    public socialPage(frame myFrame) { // constructor for the socialPage class which takes a frame object as a parameter and passes it to the superclass constructor
        super(myFrame, "socialPage");
    }

    public static int getSeconds() { // gets the seconds variable
        return seconds;
    }

    public static void setSeconds(int seconds) { // sets the seconds variable in a public method so it can be accessible in other classes
        socialPage.seconds = seconds;
    }

    @Override
    public void initializePage() { // initializes the socialPage by creating the plant JLabel, the timer JLabel, the start button JLabel and the stop button JLabel, it also sets the bounds and the icons for each of them and adds them to the page using the addComponent method from the superclass

        plant = labelSystem.assets(300, 540, 200, 200, userData.getActiveSkin()[activeStage], false, 0, true, false);
        System.out.println(userData.getActiveSkin()[activeStage]);

        addComponent(plant); // adds the plant JLabel to the page using the addComponent method from the superclass

        timePrompt = new JLabel("Gathering length (minutes):", SwingConstants.CENTER); // descriptive label so the user knows what to type into the text field below
        timePrompt.setFont(new Font("SansSerif", Font.BOLD, 18)); // bolder and larger so the label cant be missed
        timePrompt.setBounds(370, 65, 300, 25);
        timePrompt.setForeground(Color.WHITE);
        addComponent(timePrompt);

        timeField = new JTextField("60"); // input field for the gathering length in minutes, default of 60 minutes so the user can hit start immediately
        timeField.setBounds(420, 95, 200, 30);
        timeField.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(timeField);

        setButton = new JButton("Set"); // dedicated Set button so the user has an explicit way to confirm their input, validates the number before play picks it up
        setButton.setBounds(420, 130, 200, 30);
        setButton.addActionListener(e -> { // action listener which validates the field and shows a friendly confirmation so the user knows their input is accepted
            try {
                int minutes = Integer.parseInt(timeField.getText().trim());
                if (minutes <= 0) {
                    JOptionPane.showMessageDialog(null, "Please enter a positive number of minutes.");
                    return;
                }
                JOptionPane.showMessageDialog(null, "Gathering set to " + minutes + " minutes. Press play to start.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number of minutes.");
            }
        });
        addComponent(setButton);

        if(!myFrame.builder) { // checks so there arent 2 mouse listeners for one jlabel running at once

            startButton = labelSystem.assets(422, 524, 200, 200, "images/UI/play.png", false, 0, true, false, true, new MouseAdapter() { // start button to start the social timer
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (!e.getComponent().isEnabled()) { // verifies to check if its enabled so it doesnt run when you are on another page
                        return;
                    }
                    startSocial(); // calls the startSocial method to start the timer and the growth of the plant
                    startButton.setVisible(false); // makes the start button invisible when clicked
                    stopButton.setVisible(true); // makes the stop button visible when the start button is clicked
                }
            });
        } else {
            startButton = labelSystem.assets(422, 524, 200, 200, "images/UI/play.png", false, 0, true, false); // if we are in builder mode we dont want the start button to have a mouse listener so we create it without one
        }

        addComponent(startButton); // adds the start button JLabel to the page using the addComponent method from the superclass

        if(!myFrame.builder) { // checks so there arent 2 mouse listeners for one jlabel running at once
            stopButton = labelSystem.assets(422, 524, 200, 200, "images/UI/stop.png", false, 0, true, false, true, new MouseAdapter() { // stop button to stop the social timer
                @Override
                public void mouseClicked(MouseEvent e) { // calls the stopSocial method to stop the timer and the growth of the plant, and surfaces a conversation starter to encourage the user to stay engaged in the gathering
                    if (!e.getComponent().isEnabled()) { // verifies to check if its enabled so it doesnt run when you are on another page
                        return;
                    }
                    showConversationStarter(); // surfaces the next conversation starter from the pool
                    stopSocial();
                    stopButton.setVisible(false); // makes the stop button invisible when clicked
                    startButton.setVisible(true); // makes the start button visible when the stop button is clicked
                }
            });
        } else {
            stopButton = labelSystem.assets(422, 524, 200, 200, "images/UI/stop.png", false, 0, true, false); // if we are in builder mode we dont want the stop button to have a mouse listener so we create it without one
        }
        addComponent(stopButton); // adds the stop button JLabel to the page using the addComponent method from the superclass


        timer = new Timer(1000, e -> { // creates a new Timer object that updates every 1000 milliseconds (1 second) and takes an ActionListener
            seconds--; // counts DOWN toward the end of the gathering
            socialTimer.setText(formatTime(seconds));

            int elapsed = totalSeconds - seconds; // how many seconds of the gathering have already happened, used to drive the plant stage forward
            int targetStage = (int)(elapsed / stageDifference); // calculates which stage we should be on based on elapsed seconds and the time per stage
            if(targetStage >= userData.getActiveSkin().length) { // clamps the target to the last available skin frame so we never index past the array and crash
                targetStage = userData.getActiveSkin().length - 1;
            }
            if(targetStage > activeStage) { // only swap the icon when crossing into a new stage, not on every tick after the threshold
                activeStage = targetStage; // bumps the active stage variable up to the new stage
                plant.setIcon(labelSystem.getIcon(userData.getActiveSkin()[activeStage], 200, 200)); // updates the icon using the JLabelSystem cache so the image is loaded from the classpath and properly scaled
            }

            if (seconds <= 0) { // gathering reached its end time, stop the timer automatically so the user gets rewarded for completing the session
                stopSocial();
                stopButton.setVisible(false);
                startButton.setVisible(true);
            }
        });

        socialTimer = new JLabel(formatTime(seconds), SwingConstants.CENTER); // creates a new JLabel for the timer and sets its text to the formatted time using the formatTime method and centers the text horizontally using SwingConstants.CENTER
        socialTimer.setFont(new Font("SansSerif", Font.BOLD, 48)); // sets the font of the timer JLabel to SansSerif, bold and size 48 to make it more visible and aesthetically pleasing
        socialTimer.setBounds(420, 160, 200, 50); // sets the bounds of the timer JLabel to position it on the page, you can adjust these values to move it around and find the best position for it
        addComponent(socialTimer); // adds the timer JLabel to the page using the addComponent method from the superclass

        starterLabel = new JLabel("", SwingConstants.CENTER); // empty until the user reaches for their phone, when it will display a conversation starter to keep them engaged with the people around them
        starterLabel.setFont(new Font("SansSerif", Font.ITALIC, 16));
        starterLabel.setBounds(100, 230, 800, 50);
        starterLabel.setForeground(Color.WHITE);
        starterLabel.setVisible(false);
        addComponent(starterLabel);

        statsLabel = new JLabel(buildStatsText(), SwingConstants.CENTER); // small stat readout in the right side of the page, refreshed after every stopSocial so the user sees their progress right away
        statsLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        statsLabel.setBounds(700, 250, 250, 40);
        statsLabel.setForeground(Color.WHITE);
        addComponent(statsLabel);
    }


    public String formatTime(int numSecs) { // creates a method to format the seconds variable into a string in the format of "mm:ss" to be displayed on the timer JLabel, it takes an integer parameter numSecs which is the number of seconds remaining in the social gathering and returns a string in the format of "mm:ss"

        int safe = Math.max(0, numSecs); // guard against negative values which can briefly appear right as the timer hits zero
        int minutes = safe / 60;
        int secs = safe % 60;
        return String.format("%02d:%02d", minutes, secs);
    }

    public void startSocial() { // creates a method to start social mode which starts the timer and the growth of the plant, it also checks if social mode is already active to prevent multiple timers from being started at the same time

        if(!isSocialActive) {
            try {
                int minutes = Integer.parseInt(timeField.getText().trim()); // parses the user's estimated gathering length from the input field
                totalSeconds = minutes * 60;
                seconds = totalSeconds;
                socialTimer.setText(formatTime(seconds));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number of minutes."); // bail out cleanly so a typo doesnt start an invalid gathering timer
                return;
            }
            isSocialActive = true;
            timer.start();
        }
    }

    public void stopSocial() { // creates a method to stop social mode which stops the timer and the growth of the plant, it also checks if social mode is active to prevent the timer from being stopped when it is not active

        if(isSocialActive) {
            isSocialActive = false;
            timer.stop();
            int elapsed = totalSeconds - Math.max(0, seconds); // how many seconds of the gathering the user actually completed before stopping
            userData.addTimeSocial(elapsed); // banks the elapsed time into the running total used by the stats readout
            userData.addCoins(elapsed / 60); // rewards one coin per full minute spent at the gathering
            seconds = 0;
            socialTimer.setText(formatTime(seconds));
            activeStage = 0; // resets the plant back to the first stage for the next gathering
            plant.setIcon(labelSystem.getIcon(userData.getActiveSkin()[activeStage], 200, 200));
            statsLabel.setText(buildStatsText());
            myFrame.refreshTopBar(); // refreshes the top bar so the coin total stays in sync with what the user just earned
        }
    }

    private String buildStatsText() { // builds the string for the stats label, shows how much total time the user has spent in social mode
        int minutes = userData.getTotalTimeSocial() / 60;
        return "Time social: " + minutes + " min";
    }

    private void showConversationStarter() { // pulls the next conversation starter out of the pool and displays it on the page for a few seconds, then hides it so the user can return to the gathering

        starterLabel.setText("Try: " + conversationStarters[starterIndex]);
        starterIndex = (starterIndex + 1) % conversationStarters.length;
        starterLabel.setVisible(true);

        Timer hide = new Timer(5000, e -> starterLabel.setVisible(false));
        hide.setRepeats(false);
        hide.start();
    }
}