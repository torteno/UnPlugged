package org.example.pages;

import org.example.frame;
import org.example.userData;

import javax.swing.*;
import java.awt.*;

public class welcomePage extends page {

    private JLabel title; // big greeting at the top of the welcome screen
    private JLabel prompt; // descriptive label sitting above the name input field so the user knows what to type
    private JTextField nameField; // input field where the user types their name on first launch
    private JButton continueButton; // saves the name and switches to the focus page when clicked

    public welcomePage(frame myFrame) { // constructor for the welcomePage class which takes a frame object as a parameter and passes it to the superclass constructor
        super(myFrame, "welcomePage");
    }

    @Override
    public void initializePage() { // initializes the welcome screen with a title, name input field, and continue button

        title = new JLabel("UnPlugged, lets get you off that phone :D - Josh Quan probably", SwingConstants.CENTER); // big heading so the user knows what app they just opened
        title.setFont(new Font("SansSerif", Font.BOLD, 36));
        title.setBounds(300, 200, 400, 60);
        title.setForeground(Color.WHITE);
        addComponent(title);

        prompt = new JLabel("Name:", SwingConstants.CENTER); // descriptive label so the input field next to it isnt orphaned
        prompt.setFont(new Font("SansSerif", Font.PLAIN, 18));
        prompt.setBounds(300, 300, 400, 30);
        prompt.setForeground(Color.WHITE);
        addComponent(prompt);

        nameField = new JTextField(); // single line input field for the user's name
        nameField.setBounds(400, 350, 200, 30);
        nameField.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(nameField);

        continueButton = new JButton("Continue"); // saves the name and switches to the focus page when clicked
        continueButton.setBounds(450, 400, 100, 30);
        continueButton.addActionListener(e -> { // action listener which reads the name out of the field and stores it
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a name to continue."); // bail out cleanly so the user cant skip past the welcome screen with an empty name
                return;
            }
            userData.setUserName(name); // stores the name so the top bar can show it
            userData.saveData(); // persists right away so the welcome flow doesnt run again next launch
            myFrame.refreshTopBar(); // tells the top bar to redraw with the new name and the current streak/coin values
            myFrame.setActivePage(myFrame.focusPg); // switches to the focus page so the user can start using the app
        });
        addComponent(continueButton);
    }
}
