package org.example.pages;

import org.example.frame;

import javax.swing.*;
import java.time.*;

public class goPage extends page {

    private int timetoGo;

    public goPage(frame myFrame) { // Constructor for the goPage class, takes a frame object as a parameter
        super(myFrame, "goPage");
    }

    @Override
    public void initializePage() {

        JTextField timeField = new JTextField(1); // Field to input time to go
        timeField.setLocation(500, 500); // Position the field on the page
        timeField.setBounds(500, 500, 200, 30); // Set size of the field
        addComponent(timeField); // Add the field to the page

        JButton setTimeButton = new JButton("Set Time"); // Button to set the time to go
        setTimeButton.setLocation(500, 550); // Position the button on the page
        setTimeButton.setBounds(500, 550, 200, 30); // Set size of the button
        setTimeButton.addActionListener(e -> { // Action listener for the button
            try {
                timetoGo = Integer.parseInt(timeField.getText()); // Parse the input time to go
                JOptionPane.showMessageDialog(setTimeButton, "Time to go set to: " + timetoGo + " minutes"); // Show a message confirming the time to go has been set
            } catch (NumberFormatException ex) { // Catch block to handle invalid input
                JOptionPane.showMessageDialog(setTimeButton, "Please enter a valid number");
            }


        });
        addComponent(setTimeButton); // Add the button to the page
    }
}
