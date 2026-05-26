package org.example;

import java.util.ArrayList;

public class gsonBridge {

    public int coins; // the amount of coins the player has
    public ArrayList<String[]> availableSkins; // the skins the player has access to, each skin is an array of strings representing the file paths of the images for that skin
    public int totalTimeOffPhone;// the total time the player has spent off their phone, in seconds
    public int totalTimeSleeping; // the total time the player has spent sleeping, in seconds
    public int totalTimeOutside; // the total time the player has spent outside, in seconds
    public int totalTimeSocial; // the total time the player has spent in social mode, in seconds, used for the stat readout on the social page
    public int sessionsCompleted; // total number of focus sessions the player has completed
    public String userName; // the name the user entered on the welcome screen, shown in the top bar
    public int streak; // the current daily streak in days
    public String lastOpenedDate; // ISO date string of the last app launch, used to compute streak transitions
    public int defaultFocusMinutes; // the user's preferred default focus session length set on the preferences page
    public ArrayList<String> ownedItems; // catalogue keys of shop items the user owns
    public String[] activeSkin; // the currently active skin, represented as an array of strings representing the file paths of the images for that skin



}
