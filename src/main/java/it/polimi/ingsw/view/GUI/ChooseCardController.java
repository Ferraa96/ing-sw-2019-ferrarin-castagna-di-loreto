package it.polimi.ingsw.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;

public class ChooseCardController extends GUIController{

    @FXML
    ImageView GOD1;
    @FXML
    ImageView GOD2;
    @FXML
    ImageView GOD3;

    @FXML
    AnchorPane pane;

    @FXML
    Button god1;
    @FXML
    Button god2;
    @FXML
    Button god3;
    @FXML
    Button show;

    private GUIHandler guiHandler;

    private List<Integer> chosen = new ArrayList<>();

    @FXML
    public void showCards(){ //non funziona per test singleplayer
        setChosen();
        if(chosen.size() == 3) {
            GOD2.setImage(new Image(convertGod(chosen.get(2)), 300, 450, true, true));
            GOD1.setImage(new Image(convertGod(chosen.get(0)), 300, 450, true, true));
            GOD3.setImage(new Image(convertGod(chosen.get(1)), 300, 450, true, true));
            god1 = new Button("CHOOSE");
            god1.setLayoutY(300);
            god1.setLayoutX(50);
            god3 = new Button("CHOOSE");
            god3.setLayoutY(300);
            god3.setLayoutX(400);
            god2 = new Button("CHOOSE");
            god2.setLayoutY(300);
            god2.setLayoutX(220);
            pane.getChildren().addAll(god1,god2,god3);
            god2.setOnAction( e -> {
                setCard0();
                disableAll();
            });
            god3.setOnAction( e -> {
                setCard1();
                disableAll();
            });
            god1.setOnAction( e -> {
                setCard2();
                disableAll();
            });
        }
        if(chosen.size() == 2){
            GOD1.setImage(new Image(convertGod(chosen.get(0)), 300, 450, true, true));
            GOD3.setImage(new Image(convertGod(chosen.get(1)), 300, 450, true, true));
            god1 = new Button("CHOOSE");
            god1.setLayoutY(300);
            god1.setLayoutX(50);
            god3 = new Button("CHOOSE");
            god3.setLayoutY(300);
            god3.setLayoutX(400);
            pane.getChildren().addAll(god1, god3);
            god1.setOnAction( e -> {
                setCard0();
                disableAll();
            });
            god3.setOnAction( e -> {
                setCard1();
                disableAll();
            });
            show.setVisible(false);
        }
        if(chosen.size() == 1){
            GOD3.setImage(new Image(convertGod(chosen.get(0)), 300, 450, true, true));
            god3 = new Button("CHOOSE");
            god3.setLayoutY(300);
            god3.setLayoutX(400);
            pane.getChildren().addAll(god3);
            god3.setOnAction( e -> {
                setCard0();
                disableAll();
            });
            show.setVisible(false);
        }
    }

    private void setChosen(){
        this.guiHandler = super.getGuiHandler();
        chosen.addAll(guiHandler.setGodList());
    }

    private String convertGod(int godName){
        switch(godName){
            case 0:{
                return "/images/01.png";
            }
            case 1:{
                return "/images/02.png";
            }
            case 2:{
                return "/images/03.png";
            }
            case 3:{
                return "/images/04.png";
            }
            case 4:{
                return "/images/05.png";
            }
            case 5:{
                return "/images/06.png";
            }
            case 6:{
                return "/images/08.png";
            }
            case 7:{
                return "/images/09.png";
            }
            case 8:{
                return "/images/10.png";
            }
            default:{
                return "Invalid Choise";
            }
        }
    }

    private void setCard0(){
       guiHandler.getCard(chosen.get(0),convertGod(chosen.get(0)));
    }

    private void setCard1(){
        guiHandler.getCard(chosen.get(1),convertGod(chosen.get(1)));
    }

    private void setCard2(){
        guiHandler.getCard(chosen.get(2),convertGod(chosen.get(2)));
    }

    private void disableAll(){
        if(god1!=null)
        god1.setVisible(false);
        if(god2!=null)
        god2.setVisible(false);
        if(god3!=null)
        god3.setVisible(false);
    }

}
