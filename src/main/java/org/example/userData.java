package org.example;

import com.google.gson.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;


public class userData {

    private static int coins = 0;
    private static ArrayList<String[]> accessibleSkins = new ArrayList<>();
    private static int totalTimeOffPhone = 0;
    private static int totalTimeSleeping = 0;
    private static int totalTimeOutside = 0;
    private static int totalTimeSocial = 0; // total seconds spent in social mode, used for the stat readout on the social page
    private static int sessionsCompleted = 0; // running count of how many focus sessions the user has finished, shown on the focus page
    private static String userName = ""; // name entered on the welcome screen, shown in the top bar
    private static int streak = 0; // current daily streak in days, bumped every consecutive day the app is opened
    private static String lastOpenedDate = ""; // ISO date string of the most recent app launch, used by updateStreak to figure out how to move the streak
    private static int defaultFocusMinutes = 25; // user's preferred default focus duration, edited on the preferences page
    private static ArrayList<String> ownedItems = new ArrayList<>(); // catalogue keys of shop items the user has purchased



    private static String[] activeSkin;


    //private static time totalTimeOffPhone


    //accessibleSkins.add({"", ""});


    public void loadSkins() {
        accessibleSkins.add(new String[]{"images/UI/tree1.png", "images/UI/tree2.png", "images/UI/tree3.png", "images/UI/tree4.png", "images/UI/tree5.png", "images/UI/tree6.png"});



        activeSkin = accessibleSkins.get(0); // default
    }


    public static void updateStreak() { // updates the daily streak based on today's date compared to the last time the app was opened, called once on startup right after loadData
        LocalDate today = LocalDate.now();
        String todayStr = today.toString();

        if (lastOpenedDate == null || lastOpenedDate.isEmpty()) {
            streak = 1; // first ever launch so the streak starts at day 1
        } else {
            try {
                LocalDate last = LocalDate.parse(lastOpenedDate);
                long daysBetween = ChronoUnit.DAYS.between(last, today);
                if (daysBetween == 1) {
                    streak++; // user came back the very next day so the streak continues
                } else if (daysBetween > 1) {
                    streak = 1; // user missed at least one day so the streak resets back to day 1
                }
                // daysBetween == 0 means same day, streak is unchanged
            } catch (Exception e) {
                streak = 1; // saved string was malformed so we just start fresh
            }
        }
        lastOpenedDate = todayStr;
    }


    static public void saveData () {
        Gson builder = new GsonBuilder().setPrettyPrinting().create();
        gsonBridge bridge = new gsonBridge();

        bridge.coins = coins;
        bridge.availableSkins = accessibleSkins;
        bridge.totalTimeOffPhone = totalTimeOffPhone;
        bridge.totalTimeSleeping = totalTimeSleeping;
        bridge.totalTimeOutside = totalTimeOutside;
        bridge.totalTimeSocial = totalTimeSocial;
        bridge.sessionsCompleted = sessionsCompleted;
        bridge.userName = userName;
        bridge.streak = streak;
        bridge.lastOpenedDate = lastOpenedDate;
        bridge.defaultFocusMinutes = defaultFocusMinutes;
        bridge.ownedItems = ownedItems;
        bridge.activeSkin = activeSkin;



        try(FileWriter file = new FileWriter("userData.json")) {
            builder.toJson(bridge, file);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    static public void loadData() {
        Gson gson = new Gson();
        try {
            FileReader file = new FileReader("userData.json");
            gsonBridge bridge = gson.fromJson(file, gsonBridge.class); // reads into a gsonBridge since that is the schema we save with, not into userData directly
            if (bridge != null) {
                coins = bridge.coins;
                if (bridge.availableSkins != null) accessibleSkins = bridge.availableSkins;
                totalTimeOffPhone = bridge.totalTimeOffPhone;
                totalTimeSleeping = bridge.totalTimeSleeping;
                totalTimeOutside = bridge.totalTimeOutside;
                totalTimeSocial = bridge.totalTimeSocial;
                sessionsCompleted = bridge.sessionsCompleted;
                if (bridge.userName != null) userName = bridge.userName;
                streak = bridge.streak;
                if (bridge.lastOpenedDate != null) lastOpenedDate = bridge.lastOpenedDate;
                if (bridge.defaultFocusMinutes > 0) defaultFocusMinutes = bridge.defaultFocusMinutes;
                if (bridge.ownedItems != null) ownedItems = bridge.ownedItems;
                if (bridge.activeSkin != null) activeSkin = bridge.activeSkin;
            }
        } catch (Exception e) {
            // no save file yet or it was malformed, defaults set at the top of the class stay in place which is fine for a fresh install
        }


    }

    public static String[] getActiveSkin() {
        return activeSkin;
    }

    public static void setActiveSkin(String[] activeSkin) {
        userData.activeSkin = activeSkin;
    }

    public static int getCoins() {
        return coins;
    }

    public static void setCoins(int c) {
        coins = c;
    }

    public static void addCoins(int amount) { // helper used by the focus/sleep/go/social pages to reward the user with coins after a completed session
        coins = coins + amount;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String name) {
        userName = name;
    }

    public static int getStreak() {
        return streak;
    }

    public static int getDefaultFocusMinutes() {
        return defaultFocusMinutes;
    }

    public static void setDefaultFocusMinutes(int minutes) {
        defaultFocusMinutes = minutes;
    }

    public static int getSessionsCompleted() {
        return sessionsCompleted;
    }

    public static void incrementSessions() { // bumps the focus session counter, used by the focus page after a session is stopped with at least one minute of focus on the clock
        sessionsCompleted = sessionsCompleted + 1;
    }

    public static int getTotalTimeOffPhone() {
        return totalTimeOffPhone;
    }

    public static void addTimeOffPhone(int seconds) {
        totalTimeOffPhone = totalTimeOffPhone + seconds;
    }

    public static int getTotalTimeSleeping() {
        return totalTimeSleeping;
    }

    public static void addTimeSleeping(int seconds) {
        totalTimeSleeping = totalTimeSleeping + seconds;
    }

    public static int getTotalTimeOutside() {
        return totalTimeOutside;
    }

    public static void addTimeOutside(int seconds) {
        totalTimeOutside = totalTimeOutside + seconds;
    }

    public static int getTotalTimeSocial() {
        return totalTimeSocial;
    }

    public static void addTimeSocial(int seconds) {
        totalTimeSocial = totalTimeSocial + seconds;
    }

    public static boolean hasItem(String key) {
        return ownedItems.contains(key);
    }

    public static void addOwnedItem(String key) {
        if (!ownedItems.contains(key)) {
            ownedItems.add(key);
        }
    }

    public static ArrayList<String> getOwnedItems() {
        return ownedItems;
    }
}
