package org.example.pages;

import org.example.frame;
import org.example.userData;

import javax.swing.*;
import java.awt.*;

public class preferencesPage extends page {

    private JLabel title; // heading at the top of the preferences page
    private JLabel focusPrompt; // descriptive label above the default focus minutes input field
    private JTextField focusField; // input field where the user types their preferred default focus session length in minutes
    private JButton saveButton; // saves the preferences to userData when clicked

    public preferencesPage(frame myFrame) { // constructor for the preferencesPage class which takes a frame object as a parameter and passes it to the superclass constructor
        super(myFrame, "preferencesPage");
    }

    @Override
    public void initializePage() { // initializes the preferences page with a title, the default focus minutes field, and a save button

        title = new JLabel("Preferences", SwingConstants.CENTER); // heading so the user knows what page they are on
        title.setFont(new Font("SansSerif", Font.BOLD, 32));
        title.setBounds(300, 200, 400, 50);
        title.setForeground(Color.WHITE);
        addComponent(title);

        focusPrompt = new JLabel("Default focus session (minutes):", SwingConstants.CENTER); // descriptive label so the input field next to it isnt orphaned
        focusPrompt.setFont(new Font("SansSerif", Font.PLAIN, 16));
        focusPrompt.setBounds(300, 280, 400, 30);
        focusPrompt.setForeground(Color.WHITE);
        addComponent(focusPrompt);

        focusField = new JTextField(String.valueOf(userData.getDefaultFocusMinutes())); // input field pre filled with the value already stored in userData so the user can see the current setting
        focusField.setBounds(400, 320, 200, 30);
        focusField.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(focusField);

        saveButton = new JButton("Save"); // saves the preferences to userData and shows a confirmation dialog when clicked
        saveButton.setBounds(450, 380, 100, 30);
        saveButton.addActionListener(e -> { // action listener which parses the field and stores it in userData
            try {
                int minutes = Integer.parseInt(focusField.getText().trim());
                if (minutes <= 0) {
                    JOptionPane.showMessageDialog(null, "Please enter a positive number of minutes."); // negative or zero would make for a silly default focus length
                    return;
                }
                userData.setDefaultFocusMinutes(minutes); // stores the new default so future focus sessions can use it
                userData.saveData(); // persists right away so the preferences survive a relaunch
                JOptionPane.showMessageDialog(null, "Preferences saved.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number of minutes."); // bail out cleanly on bad input
            }
        });
        addComponent(saveButton);
    }
}
