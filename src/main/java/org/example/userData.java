package org.example;

import com.google.gson.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class userData {

    private static int coins = 0;
    private static ArrayList<String[]> accessibleSkins = new ArrayList<>();
    private static int totalTimeOffPhone = 0;
    private static int totalTimeSleeping = 0;
    private static int totalTimeOutside = 0;



    private static String[] activeSkin;


    //private static time totalTimeOffPhone


    //accessibleSkins.add({"", ""});


    public void loadSkins() {
        accessibleSkins.add(new String[]{"tree1.png", "tree2.png", "tree3.png", "tree4.png", "tree5.png", "tree6.png"});



        activeSkin = accessibleSkins.get(0); // default
    }





    static public void saveData () {
        Gson builder = new GsonBuilder().setPrettyPrinting().create();
        gsonBridge bridge = new gsonBridge();

        bridge.coins = coins;
        bridge.availableSkins = accessibleSkins;
        bridge.totalTimeOffPhone = totalTimeOffPhone;
        bridge.totalTimeSleeping = totalTimeSleeping;
        bridge.totalTimeOutside = totalTimeOutside;



        try(FileWriter file = new FileWriter("userData.json")) {
            builder.toJson(bridge, file);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    static public void loadData() {
        Gson gson = new Gson();
        gsonBridge bridge = new gsonBridge();
        try {
            FileReader file = new FileReader("userData.json");
            userData data = gson.fromJson(file, userData.class);
            coins = data.coins;
            accessibleSkins = data.accessibleSkins;
            totalTimeOffPhone = data.totalTimeOffPhone;
            totalTimeSleeping = data.totalTimeSleeping;
            totalTimeOutside = data.totalTimeOutside;

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static String[] getActiveSkin() {
        return activeSkin;
    }

    public static void setActiveSkin(String[] activeSkin) {
        userData.activeSkin = activeSkin;
    }
}
