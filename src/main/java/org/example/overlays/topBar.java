package org.example.overlays;

import org.example.frame;
import org.example.userData;

import javax.swing.*;
import java.awt.*;

public class topBar extends overlay{


    private JLabel greeting; // "Hi [name]" label on the left side of the top bar
    private JLabel coinLabel; // "Coins: N" in the middle of the top bar
    private JLabel streakLabel; // "Streak: N days" on the right side of the top bar


    public topBar(frame myFrame) {
        super(myFrame, "topBar");


    }

    @Override
    public void initializeOverlay() { // initializes the three labels of the top bar, the greeting on the left, the coin balance in the middle, and the streak on the right

        greeting = new JLabel(buildGreeting(), SwingConstants.LEFT); // shows "Hi [name]" on the left side, refreshed by refresh()
        greeting.setFont(new Font("SansSerif", Font.BOLD, 18));
        greeting.setBounds(260, 10, 250, 30);
        greeting.setForeground(Color.WHITE);
        addComponent(greeting);

        coinLabel = new JLabel("Coins: " + userData.getCoins(), SwingConstants.CENTER); // shows the user's current coin balance in the middle of the top bar
        coinLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        coinLabel.setBounds(530, 10, 150, 30);
        coinLabel.setForeground(Color.WHITE);
        addComponent(coinLabel);

        streakLabel = new JLabel("Streak: " + userData.getStreak() + " days", SwingConstants.RIGHT); // shows the user's current daily streak on the right side of the top bar
        streakLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        streakLabel.setBounds(700, 10, 250, 30);
        streakLabel.setForeground(Color.WHITE);
        addComponent(streakLabel);
    }

    public void refresh() { // called whenever the name, coin balance, or streak changes so the labels display the latest values
        greeting.setText(buildGreeting());
        coinLabel.setText("Coins: " + userData.getCoins());
        streakLabel.setText("Streak: " + userData.getStreak() + " days");
    }

    private String buildGreeting() { // builds the greeting string from the user's name, falls back to a generic greeting if the name is empty so the bar still looks reasonable before the welcome screen completes
        String name = userData.getUserName();
        if (name == null || name.isEmpty()) {
            return "Hi there";
        }
        return "Hi " + name;
    }


}
