package it.polimi.ingsw.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class CardsSelectionController extends GUIController{

    @FXML
    Button cardbutton;
    @FXML
    Button apollobutton;
    @FXML
    Button artemisbutton;
    @FXML
    Button athenabutton;
    @FXML
    Button atlasbutton;
    @FXML
    Button demeterbutton;
    @FXML
    Button hephaestusbutton;
    @FXML
    Button minotaurbutton;
    @FXML
    Button pambutton;
    @FXML
    Button prometeusbutton;

    @FXML
    ImageView Apollo;

    @FXML
    ImageView Artemis;

    private GUIHandler guiHandler;

    private int playerNumber;

    private int counter = 0;

    private boolean setted = false;

    List<Integer> chosen = new ArrayList<>();

    @FXML
    private void selectApollo(){
        if(!setted){
        getPlayers();
        setted = true;
        }
        chosen.add(counter,0);
        counter ++;
        Apollo.opacityProperty().setValue(5);
        apollobutton.disableProperty().setValue(true);
        if(counter==playerNumber){
               disableAll();
        }
    }

    @FXML
    private void selectArthemis(){
        if(!setted){
            getPlayers();
        }
        chosen.add(counter,1);
        counter ++;
        Artemis.opacityProperty().setValue(70);
        artemisbutton.disableProperty().setValue(true);
        if(counter==playerNumber){
                disableAll();
        }
    }

    @FXML
    private void selectAthena(){
        if(!setted){
            getPlayers();
        }
            chosen.add(counter,2);
            counter ++;
            Apollo.setOpacity(5); //non funge
            athenabutton.disableProperty().setValue(true);
        if(counter==playerNumber){
            disableAll();
        }
    }

    @FXML
    private void selectAtlas(){
        if(!setted){
            getPlayers();
        }
            chosen.add(counter,3);
            counter ++;
            Apollo.setOpacity(5); //non funge
            atlasbutton.disableProperty().setValue(true);
        if(counter==playerNumber){
            disableAll();
        }
    }

    @FXML
    private void selectDemeter(){
        if(!setted){
            getPlayers();
        }
            chosen.add(counter,4);
            counter ++;
            Apollo.setOpacity(5); //non funge
            demeterbutton.disableProperty().setValue(true);
            if(counter==playerNumber){
                disableAll();
            }
    }

    @FXML
    private void selectHephaestus(){
        if(!setted){
            getPlayers();
        }
            chosen.add(counter,5);
            counter ++;
            Apollo.setOpacity(5); //non funge
            hephaestusbutton.disableProperty().setValue(true);
            if(counter==playerNumber){
                disableAll();
            }
    }

    @FXML
    private void selectMinotaur(){
        if(!setted){
            getPlayers();
        }
            chosen.add(counter,6);
            counter ++;
            Apollo.setOpacity(5); //non funge
            minotaurbutton.disableProperty().setValue(true);
            if(counter==playerNumber){
                disableAll();
            }
    }

    @FXML
    private void selectPam(){
        if(!setted){
            getPlayers();
        }
            chosen.add(counter,7);
            counter ++;
            Apollo.setOpacity(5); //non funge
            pambutton.disableProperty().setValue(true);
            if(counter==playerNumber){
                disableAll();
            }
    }

    @FXML
    private void selectPrometeus(){
        if(!setted){
            getPlayers();
        }
            chosen.add(counter,8);
            counter ++;
            Apollo.opacityProperty().setValue(5);
            prometeusbutton.disableProperty().setValue(true);
            if(counter==playerNumber){
                disableAll();
            }
    }

//    private void selectGods() {
//        List<Button> godsButton = new ArrayList<>();
//        godsButton.add(apollobutton);
//        godsButton.add(artemisbutton);
//        godsButton.add(athenabutton);
//        godsButton.add(atlasbutton);
//        godsButton.add(demeterbutton);
//        godsButton.add(hephaestusbutton);
//        godsButton.add(minotaurbutton);
//        godsButton.add(pambutton);
//        godsButton.add(prometeusbutton);
//
//        for (int i = 0; i < 9; i++) {
//            if (!setted)
//                getPlayers();
//            chosen.add(i,i);
//            Apollo.opacityProperty().setValue(5);
//            godsButton.get(i).disableProperty().setValue(true);
//            if (i == playerNumber)
//                disableAll();
//        }
//    }

    private void disableAll() {
        apollobutton.disableProperty().setValue(true);
        artemisbutton.disableProperty().setValue(true);
        athenabutton.disableProperty().setValue(true);
        atlasbutton.disableProperty().setValue(true);
        demeterbutton.disableProperty().setValue(true);
        hephaestusbutton.disableProperty().setValue(true);
        minotaurbutton.disableProperty().setValue(true);
        pambutton.disableProperty().setValue(true);
        prometeusbutton.disableProperty().setValue(true);
    }

    @FXML
    private void chooseCards() {
        guiHandler.getSelectedCards(chosen);
    }

    private void getPlayers(){
        this.guiHandler = super.getGuiHandler();
        playerNumber = guiHandler.getPlayers();
        setted = true;
    }

}


