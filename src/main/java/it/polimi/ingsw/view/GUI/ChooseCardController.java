package it.polimi.ingsw.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;

public class ChooseCardController extends GUIController {

    @FXML
    ImageView GOD1;
    @FXML
    ImageView GOD2;
    @FXML
    ImageView GOD3;

    @FXML
    AnchorPane pane;

    @FXML
    Button god1 = new Button("CHOOSE");
    @FXML
    Button god2 = new Button("CHOOSE");
    @FXML
    Button god3 = new Button("CHOOSE");

    private final List<ImageView> godsImages = new ArrayList<>();

    private final List<Button> godsButtons = new ArrayList<>();

    private GUIHandler guiHandler;

    private final List<Integer> chosen = new ArrayList<>();

    @Override
    public void start(){
        show();
    }

    @FXML
    public void show(){
        godsImages.add(GOD1);
        godsImages.add(GOD2);
        godsImages.add(GOD3);
        godsButtons.add(god1);
        godsButtons.add(god2);
        godsButtons.add(god3);
        setChosen();

        for (int i = 0; i < chosen.size(); i++) {
            final int finalI = i;
            godsImages.get(offsetX(chosen.size(),finalI)).setImage(new Image(convertGod(chosen.get(finalI)), 300, 450, true, true));
            godsButtons.get(finalI).setLayoutY(300);
            godsButtons.get(finalI).setLayoutX(offsetX(chosen.size(),finalI)*175+50);
            pane.getChildren().addAll(godsButtons.get(finalI));
            godsButtons.get(finalI).setOnAction( e -> {
                setCard(finalI);
                disableAll();
            });
        }
    }

    private int offsetX( int num, int actual) {
        if (num == 3)
            return actual;
        if (num == 2)
            return actual*2;
        return 1;
    }

    private void setChosen(){
        this.guiHandler = super.getGuiHandler();
        chosen.addAll(guiHandler.getGodList());
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

    private void setCard(int index){
       guiHandler.getCard(chosen.get(index),convertGod(chosen.get(index)));
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
