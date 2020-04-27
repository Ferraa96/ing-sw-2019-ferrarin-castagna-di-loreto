package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * serialize and deserialize files
 */
public class IOHandler {
    private final Gson gson;
    private String saveStatePath;
    private static List<Card> godsList;


    public IOHandler() {
        gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            saveStatePath = new File(IOHandler.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * converts the json array to a list of cards, implements singleton pattern
     * @return the list generated
     */
    public List<Card> getCardList() {
        if(godsList != null) {
            return godsList;
        }
        InputStream input = getClass().getResourceAsStream("/godsList.json");
        BufferedReader bf = new BufferedReader(new InputStreamReader(input));
        Type selectedCard = new TypeToken<ArrayList<Card>>() {}.getType();
        godsList = gson.fromJson(bf, selectedCard);
        return godsList;
    }

    /**
     * deserialize the file that contains the save game
     * @return the SaveState
     */
    public SaveState getSaveState() {
        File file = new File(saveStatePath);
        try {
            BufferedReader bf = new BufferedReader(new FileReader(file));
            Type stateType = new TypeToken<SaveState>() {}.getType();
            return gson.fromJson(bf, stateType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * serialize state in a file
     * @param state the game state
     */
    public void save(SaveState state) {
        try {
            String output;
            File file = new File(saveStatePath);
            FileWriter fileWriter = new FileWriter(file);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            output = gson.toJson(state);
            printWriter.print(output);
            printWriter.close();
            System.out.println("Saved in " + saveStatePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteFile() {
        File toBeDeleted = new File(saveStatePath);
        if(toBeDeleted.delete()) {
            System.out.println("Save state deleted");
        } else {
            System.out.println("Failed to delete file" + saveStatePath);
        }
    }

    /**
     * verify the existance of the file
     * @param fileName the name of the file
     * @return true if the file exists
     */
    public boolean verifyFileExistance(String fileName) {
        saveStatePath = saveStatePath + "\\" + fileName + ".json";
        System.out.println("Checking for savestate file in " + saveStatePath);
        File file = new File(saveStatePath);
        return file.exists();
    }
}