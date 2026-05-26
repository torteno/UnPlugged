package org.example.pages;

import org.example.frame;

import javax.swing.*;
import java.time.*;

public class goPage extends page {

    private int timetoGo;

    public goPage(frame myFrame) {
        super(myFrame, "goPage");
    }

    @Override
    public void initializePage() {

        JTextField timeField = new JTextField(1);
        timeField.setLocation(500, 500);
        addComponent(timeField);

        JButton setTimeButton = new JButton("Set Time");
        setTimeButton.setLocation(500, 550);
        setTimeButton.addActionListener(e -> {
            try {
                timetoGo = Integer.parseInt(timeField.getText());
                JOptionPane.showMessageDialog(setTimeButton, "Time to go set to: " + timetoGo + " minutes");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(setTimeButton, "Please enter a valid number");
            }


        });
        addComponent(setTimeButton);
    }
}
