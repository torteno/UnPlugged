package org.example;

import com.google.gson.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class userData {

    private static int coins = 0;
    private static ArrayList<String> accessibleSkins = new ArrayList<>();
    //private static time totalTimeOffPhone

    static public void saveData () {
        Gson builder = new GsonBuilder().setPrettyPrinting().create();
        gsonBridge bridge = new gsonBridge();

        bridge.coins = coins;
        bridge.availableSkins = accessibleSkins;


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

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
