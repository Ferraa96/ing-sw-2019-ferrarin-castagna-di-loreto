package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Position;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class that take care of everything that must be displayed
 */
public class GUI extends Application {

    private static String playerName;
    private Stage window;
    Scene welcome, login, choosethree, chooseGod, board, selectWorker;
    javafx.scene.canvas.Canvas welcomecanvas = new javafx.scene.canvas.Canvas(960,540);
    GraphicsContext gcwelcome = welcomecanvas.getGraphicsContext2D();
    javafx.scene.canvas.Canvas mapcanvas = new Canvas(960,540);
    GraphicsContext gcmap = mapcanvas.getGraphicsContext2D();
    javafx.scene.canvas.Canvas selectioncanvas = new Canvas(960,540);
    GraphicsContext gcselection = selectioncanvas.getGraphicsContext2D();
    private List<String> players;
    private List<Integer> cards;
    private List<Position> positions;
    private static List<Integer> godNames =new ArrayList<Integer>();
    private static String godName;
    private static int godInt;


    /**
     * Method called at the creation of the GUI that displays a Welcome screen
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane pane = new AnchorPane();
        Button startGame = new Button("Start game");
        startGame.setLayoutX(420);
        startGame.setLayoutY(480);
        pane.getChildren().addAll(welcomecanvas,startGame);
        javafx.scene.image.Image sfondo = new javafx.scene.image.Image("images/Background.png");
        gcwelcome.drawImage(sfondo,0,0,960,540);
        welcome = new Scene(pane,960,540);

        window=primaryStage;
        window.setTitle("SANTORINI");
        window.setScene(welcome);
        window.show();

        startGame.setOnAction(e->login());

    }

    /**
     * Log in scene where the user can select his Nickname
     */
    public void login() {

        Label usr = new Label("Username:");
        TextField lab1 = new TextField("insert here");
        Button loginbutton = new Button("LogIn");

        GridPane lay = new GridPane();
        lay.setPadding(new javafx.geometry.Insets(10,10,10,10));
        lay.setVgap(8);
        lay.setHgap(10);
        GridPane.setConstraints(usr,0,1);
        GridPane.setConstraints(lab1,1,1);
        GridPane.setConstraints(loginbutton,1,3);
        lay.getChildren().addAll(usr,lab1,loginbutton);

        loginbutton.setOnAction(e->{
            playerName = lab1.getText();
            chooseThreeCard();
        });

        login = new Scene(lay, 250, 250);

        window.setScene(login);
    }

    /**
     * Scene where the first user is called to choose 3 God cards for the players
     */
    public void chooseThreeCard() {

        Button godc = new Button("Select");
        Label godsel = new Label("Select three cards");

        ChoiceBox<String> cb = new ChoiceBox<>();
        cb.getItems().addAll("Apollo","Artemis","Athena","Atlas","Demeter","Hephaestus","Minotaur","Pan","Prometheus");
        ChoiceBox<String> cb1 = new ChoiceBox<>();
        cb1.getItems().addAll("Apollo","Artemis","Athena","Atlas","Demeter","Hephaestus","Minotaur","Pan","Prometheus");
        ChoiceBox<String> cb2 = new ChoiceBox<>();
        cb2.getItems().addAll("Apollo","Artemis","Athena","Atlas","Demeter","Hephaestus","Minotaur","Pan","Prometheus");
        VBox choice = new VBox(20);
        choice.setPadding(new Insets(30,30,30,30));
        choice.getChildren().addAll(godsel,cb,cb1,cb2,godc);
        choosethree = new Scene(choice,250,250);

        window.setScene(choosethree);

        godc.setOnAction(e->{  //IMPLEMENTARE SE SCELTE LE STESSE CARTE
            godNames.add(godToInteger(cb.getValue()));
            godNames.add(godToInteger(cb1.getValue()));
            godNames.add(godToInteger(cb2.getValue()));
            chooseCard();
        });
    }

    /**
     * Scene where the user is called to choose his God card
     */
    public void chooseCard() {

        Button godc = new Button("Select");
        Label godsel = new Label("Select your Card");
        String god1 = integerToGod(godNames.get(0));
        String god2 = integerToGod(godNames.get(1));
        String god3 = integerToGod(godNames.get(2));


        ChoiceBox<String> bc = new ChoiceBox<>();
        bc.getItems().addAll(god1,god2,god3);
        VBox choice = new VBox(20);
        choice.setPadding(new Insets(30,30,30,30));
        choice.getChildren().addAll(godsel,bc,godc);
        chooseGod = new Scene(choice,250,250);

        window.setScene(chooseGod);

        godc.setOnAction(e->{
            godName = bc.getValue();
            godInt = godToInteger(godName);
            mapBuilder();
            showGodCard();
        });
    }

    /**
     * Creates the map and displays the players cards along with their usernames
     */
    public void mapBuilder() {
        Label name = new Label(playerName);
        Button chooseNow = new Button("Choose position");

        AnchorPane map = new AnchorPane();
        name.setLayoutX(100);
        name.setLayoutY(510);
        map.getChildren().addAll(mapcanvas,name,chooseNow);
        javafx.scene.image.Image panel = new Image("images/SantoriniBoard.png");
        gcmap.drawImage(panel,0,0,960,540);
        board = new Scene(map,960,540);

        chooseNow.setOnAction(e->firstSelection());

        window.setScene(board);

    }

    /**
     * Method that actually displays the selected card
     */
    private void showGodCard(){
        javafx.scene.image.Image img = new javafx.scene.image.Image("images/01.png");

        switch(godName){
            case "Apollo":{
                img = new javafx.scene.image.Image( "images/01.png" );
                break;
            }
            case "Artemis":{
                img = new javafx.scene.image.Image( "images/02.png" );
                break;
            }
            case "Athena":{
                img = new javafx.scene.image.Image( "images/03.png" );
                break;
            }
            case "Atlas":{
                img = new javafx.scene.image.Image( "images/04.png" );
                break;
            }
            case "Demeter":{
                img = new javafx.scene.image.Image( "images/05.png" );
                break;
            }
            case "Hephaestus":{
                img = new javafx.scene.image.Image( "images/06.png" );
                break;
            }
            case "Minotaur":{
                img = new javafx.scene.image.Image( "images/07.png" );
                break;
            }
            case "Pan":{
                img = new javafx.scene.image.Image( "images/08.png" );
                break;
            }
            case "Prometheus":{
                img = new javafx.scene.image.Image( "images/09.png" );
                break;
            }
        }

        javafx.scene.image.Image panel = new javafx.scene.image.Image("images/panel_heroPower.png");
        gcmap.drawImage(panel,20,480,200,100);
        gcmap.drawImage( img,70,320,100,180);

    }

    /**
     * Let the first user select from all the positions on the map
     */
    public void firstSelection() {
        AnchorPane selection = new AnchorPane();
        Label select = new Label("It's your turn to choose worker position");

        Image worker = new Image("images/workerGreen.png");
        javafx.scene.image.Image panel = new Image("images/SantoriniBoard.png");

        gcselection.drawImage(panel, 0, 0, 960, 540);
        selectWorker = new Scene(selection, 960, 540);
        selection.getChildren().addAll(selectioncanvas, select);

        //All available position
        Button[][] position = new Button[5][5];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                position[i][j] = new Button();
                position[i][j].setLayoutX(310 + (80 * j));
                position[i][j].setLayoutY(100 + (80 * i));
                selection.getChildren().add(position[i][j]);
            }
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                int finalI = i;
                int finalJ = j;
                position[i][j].setOnAction(e -> {
                    position[finalI][finalJ].setLayoutX(1111);
                    gcselection.drawImage(worker, 285 + (80 * finalJ), 80 * (finalI + 1), 70, 70);
                });
            }
        }

        window.setScene(selectWorker);
    }

    /**
     * Render the new screen whenever the user make an interaction
     */
    public void updateScreen() {

    }

    /**
     * Alert box to ask the user if the power of the card must be activated
     */
    public static boolean activatePower(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("GOD POWER");
        alert.setHeaderText(null);
        alert.setContentText("Do you want to activate the power?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Error shown if the user chooses an already selected name
     */
    public static void nameChosen(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ATTENTION");
        alert.setHeaderText(null);
        alert.setContentText("Name already chosen, pick another.");
        alert.showAndWait();
    }

    /**
     * Allow the GUIController to get the selected name
     */
    public static String setName(){
        return playerName;
    }

    /**
     * Allow the GUIController to get the selected list of cards
     */
    public static List<Integer> setCards(){
        return godNames;
    }

    /**
     * Allow the GUIController to get the selected card
     */
    public static int setCard(){
        return godInt;
    }

    private int godToInteger(String god){
        int number;

        switch(god){
            case "Apollo":
                number = 0;
                break;
            case "Artemis":
                number = 1;
                break;
            case "Athena":
                number = 2;
                break;
            case "Atlas":
                number = 3;
                break;
            case "Demeter":
                number = 4;
                break;
            case "Hephaestus":
                number = 5;
                break;
            case "Minotaur":
                number = 6;
                break;
            case "Pan":
                number = 7;
                break;
            case "Prometheus":
                number = 8;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + god);
        }
         return number;
    }

    private String integerToGod(int key){
        String name;

        switch(key){
            case 0:
                name = "Apollo";
                break;
            case 1:
                name = "Artemis";
                break;
            case 2:
                name = "Athena";
                break;
            case 3:
                name = "Atlas";
                break;
            case 4:
                name = "Demeter";
                break;
            case 5:
                name = "Hephaestus";
                break;
            case 6:
                name = "Minotaur";
                break;
            case 7:
                name = "Pan";
                break;
            case 8:
                name = "Prometheus";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + key);
        }
        return name;
    }

}