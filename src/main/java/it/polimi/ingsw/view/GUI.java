package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.SocketClient;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Position;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Parent;
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

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GUI extends Application {

    private Stage window;
    Scene welcome, login, chooseGod, board;
    javafx.scene.canvas.Canvas canvas1 = new javafx.scene.canvas.Canvas(960,540);
    GraphicsContext gc = canvas1.getGraphicsContext2D();
    javafx.scene.canvas.Canvas canvas2 = new Canvas(960,540);
    GraphicsContext gc2 = canvas2.getGraphicsContext2D();
    private List<String> players;
    private List<Integer> cards;
    private List<Position> positions;
    private String godName;
    public String playerName;
    private GUIController guiController;

  /* public GUI(GUIController guiController){
       this.guiController=guiController;
    }*/

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane pane = new AnchorPane();
        Button startGame = new Button("Start game");
        startGame.setLayoutX(420);
        startGame.setLayoutY(480);
        pane.getChildren().addAll(canvas1,startGame);
        javafx.scene.image.Image sfondo = new javafx.scene.image.Image("images/Background.png");
        gc.drawImage(sfondo,0,0,960,540);
        welcome = new Scene(pane,960,540);

        window=primaryStage;
        window.setTitle("SANTORINI");
        window.setScene(welcome);
        window.show();

        startGame.setOnAction(e->login());

    }

    public void login() {
        GridPane lay = new GridPane();
        lay.setPadding(new javafx.geometry.Insets(10,10,10,10));
        lay.setVgap(8);
        lay.setHgap(10);
        Label usr = new Label("Username:");
        GridPane.setConstraints(usr,0,1);
        TextField lab1 = new TextField("insert here");
        GridPane.setConstraints(lab1,1,1);
        Button loginbutton = new Button("LogIn");
        GridPane.setConstraints(loginbutton,1,3);
        lay.getChildren().addAll(usr,lab1,loginbutton);
        login = new Scene(lay,250,250);
        window.setScene(login);

        loginbutton.setOnAction(e->{
            playerName = lab1.getText();
            guiController.setName(players);
            chooseCard();
        });
    }


    public void chooseCard() {   //DA RIVEDERE LA MECCANICA DELLA SELEZIONE DELLE TRE CARTE
        ChoiceBox<String> cb = new ChoiceBox<>();
        cb.getItems().addAll("Apollo","Artemis","Athena","Atlas","Demeter","Hephaestus");
        VBox choice = new VBox(20);
        choice.setPadding(new Insets(30,30,30,30));
        Button godc = new Button("Select");
        Label godsel = new Label("Select your Card");
        choice.getChildren().addAll(godsel,cb,godc);
        chooseGod = new Scene(choice,250,250);

        window.setScene(chooseGod);

        godc.setOnAction(e->{
            godName=cb.getValue();
            firstPositioning(positions);
            //GUIController.setGodCard(godName);
            showGodCard();
        });
    }

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
        }

        javafx.scene.image.Image panel = new javafx.scene.image.Image("images/panel_heroPower.png");
        gc2.drawImage(panel,20,480,200,100);
        gc2.drawImage( img,70,320,100,180);

    }

    public void firstPositioning(List<Position> availablePos) {
        AnchorPane map = new AnchorPane();
        Label name = new Label(playerName);
        name.setLayoutX(100);
        name.setLayoutY(510);
        map.getChildren().addAll(canvas2,name);
        javafx.scene.image.Image panel = new Image("images/SantoriniBoard.png");
        gc2.drawImage(panel,0,0,960,540);
        board = new Scene(map,960,540);

        window.setScene(board);
    }


    public void updateScreen() {

    }

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


}