// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
package org.example;

import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JLabel;

public class Main {
    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException {

        new frame();

    }
   public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
      Frame myFrame = new Frame();
      myFrame.setVisible(true);
      ArrayList<JLabel> pageLabels = new ArrayList<>();
      Page page = new Page(myFrame, "Home", pageLabels){};
      page.loadNavigation();
      page.loadButtons();
   }
}
