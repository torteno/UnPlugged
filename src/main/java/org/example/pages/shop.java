package org.example.pages;

import org.example.frame;
import org.example.userData;

import javax.swing.*;
import java.awt.*;

public class shop extends page {

    private JLabel title; // shop heading at the top of the page
    private JLabel coinDisplay; // shows how many coins the user has, updated whenever an item is purchased

    // four categories from the proposal: backgrounds, seeds (plant skins), emojis, music
    // these three arrays line up by index, so itemLabels[i] is priced at itemCosts[i] and identified by itemKeys[i]
    private final String[] itemLabels = {"Background Pack 1", "Cherry Seed", "Emoji Pack 1", "Lo-Fi Music Pack"};
    private final int[] itemCosts = {50, 100, 25, 150};
    private final String[] itemKeys = {"background_1", "seed_cherry", "emoji_pack_1", "music_lofi"};

    private JLabel[] itemNames; // the human readable name displayed under each preview slot, kept as a field so the buy method can update the text to "(owned)"

    public shop(frame myFrame) { // constructor for the shop page which takes a frame object as a parameter and passes it to the superclass constructor
        super(myFrame, "shop");
    }

    @Override
    public void initializePage() { // initializes the shop by creating the heading, the coin balance display, and a grid of item slots with name / price / buy button under each one

        title = new JLabel("Shop", SwingConstants.CENTER); // big heading at the top so the user knows what page they are on
        title.setFont(new Font("SansSerif", Font.BOLD, 36));
        title.setBounds(400, 40, 240, 50);
        title.setForeground(Color.WHITE);
        addComponent(title);

        coinDisplay = new JLabel("Coins: " + userData.getCoins(), SwingConstants.CENTER); // shows how much currency the user currently has, will be refreshed on every purchase
        coinDisplay.setFont(new Font("SansSerif", Font.BOLD, 20));
        coinDisplay.setBounds(400, 100, 240, 30);
        coinDisplay.setForeground(Color.WHITE);
        addComponent(coinDisplay);

        itemNames = new JLabel[itemLabels.length]; // one name JLabel per item, stored as an array so we can update one entry without rebuilding the page

        for (int i = 0; i < itemLabels.length; i++) { // builds a simple 2x2 grid of item cards, each card stacked vertically with preview + name + price + buy button
            int col = i % 2; // 0 for left column, 1 for right column
            int row = i / 2; // 0 for top row, 1 for bottom row
            int x = 280 + col * 220; // x position of this item card, columns are 220 pixels apart
            int y = 180 + row * 250; // y position of this item card, rows are 250 pixels apart

            JLabel preview = new JLabel("graphic coming soon", SwingConstants.CENTER); // placeholder for the item preview image, swap this for labelSystem.assets(...) once the shop art exists
            preview.setBounds(x, y, 180, 130);
            preview.setOpaque(true);
            preview.setBackground(new Color(40, 60, 100));
            preview.setForeground(Color.WHITE);
            addComponent(preview);

            String displayName = itemLabels[i]; // base name shown under the preview
            if (userData.hasItem(itemKeys[i])) { // mark items the user already owns so they dont buy them twice
                displayName = displayName + " (owned)";
            }
            JLabel name = new JLabel(displayName, SwingConstants.CENTER);
            name.setFont(new Font("SansSerif", Font.BOLD, 14));
            name.setBounds(x, y + 135, 180, 20);
            name.setForeground(Color.WHITE);
            addComponent(name);
            itemNames[i] = name; // remember this label so buyItem can update its text to "(owned)" after a purchase

            JLabel price = new JLabel(itemCosts[i] + " coins", SwingConstants.CENTER);
            price.setFont(new Font("SansSerif", Font.PLAIN, 12));
            price.setBounds(x, y + 158, 180, 18);
            price.setForeground(Color.LIGHT_GRAY);
            addComponent(price);

            final int index = i; // captured for the click handler since we cant reference a non-final loop variable inside a lambda
            JButton buyButton = new JButton("BUY"); // plain JButton is the simplest path for a clickable element with text
            buyButton.setBounds(x + 50, y + 180, 80, 30);
            buyButton.addActionListener(e -> buyItem(index)); // delegates to the buyItem helper which handles the coin math and the feedback dialog
            addComponent(buyButton);
        }
    }

    private void buyItem(int index) { // attempts to purchase the item at the given index, deducting coins and adding to the owned items list, or telling the user they cant afford it

        if (userData.hasItem(itemKeys[index])) { // already owned, dont charge them again
            JOptionPane.showMessageDialog(null, "You already own this item.");
            return;
        }

        int cost = itemCosts[index]; // price of the item the user is trying to buy
        if (userData.getCoins() >= cost) {
            userData.setCoins(userData.getCoins() - cost); // deducts the cost from the user's coin balance
            userData.addOwnedItem(itemKeys[index]); // records the purchase so the user keeps the item across sessions once saveData is called
            coinDisplay.setText("Coins: " + userData.getCoins()); // refreshes the coin display so the user sees the new balance
            itemNames[index].setText(itemLabels[index] + " (owned)"); // marks the item card as owned
            myFrame.refreshTopBar(); // refreshes the top bar so the coin total stays in sync with what the user just spent
            JOptionPane.showMessageDialog(null, "Purchased " + itemLabels[index] + "!");
        } else {
            int shortBy = cost - userData.getCoins(); // how many more coins the user would need
            JOptionPane.showMessageDialog(null, "Not enough coins. You need " + shortBy + " more.");
        }
    }
}
