package it.polimi.ingsw.model.IO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.Player.Card;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * serialize and deserialize files
 */
public class IOHandler {
    private final Gson gson;
    private String saveStatePath;


    public IOHandler() {
        gson = new GsonBuilder().setPrettyPrinting().create();
        saveStatePath = (new File("").getAbsolutePath());
//        try {
//            System.out.println(new File(IOHandler.class.getProtectionDomain().getCodeSource().getLocation()
//                    .toURI()).getPath());
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//        String path = IOHandler.class.getProtectionDomain().getCodeSource().getLocation().getPath();
//        String decodedPath = URLDecoder.decode(path, StandardCharsets.UTF_8);
//        System.out.println(decodedPath);
//        ClassLoader loader = IOHandler.class.getClassLoader();
//        System.out.println(loader.getResource("foo/Test.class"));
    }

    /**
     * converts the json array to a list of cards, implements singleton pattern
     * @return the list generated
     */
    public List<Card> getCardList() {
        InputStream input = getClass().getResourceAsStream("/godsList.json");
        BufferedReader bf = new BufferedReader(new InputStreamReader(input));
        Type selectedCard = new TypeToken<ArrayList<Card>>() {}.getType();
        List<Card> godsList = gson.fromJson(bf, selectedCard);
        return new ArrayList<>(godsList);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * called when the game ends, it deletes the save file
     */
    public void deleteFile() {
        File toBeDeleted = new File(saveStatePath);
        toBeDeleted.delete();
    }

    /**
     * verify the existance of the file
     * @param fileName the name of the file
     * @return true if the file exists
     */
    public boolean verifyFileExistance(String fileName) {
        saveStatePath = saveStatePath + "/" + fileName + ".json";
        System.out.println("Checking for savestate file in " + saveStatePath);
        File file = new File(saveStatePath);
        return file.exists();
    }
}
