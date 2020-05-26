package it.polimi.ingsw.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class CardsSelectionController extends GUIController{

    @FXML
    Button cardbutton;

    @FXML
    ImageView Apollo,Artemis,Athena,Atlas,Demeter,Hephaestus,Minotaur,Pam,Prometheus;

    private GUIHandler guiHandler;

    private int playerNumber;

    private int counter = 0;

    private boolean setted = false;

    private final List<Integer> chosen = new ArrayList<>();

    @FXML
    private void selectApollo(){
        if(!setted){
        getPlayers();
        setted = true;
        }
        chosen.add(counter,0);
        counter ++;
        Apollo.setVisible(false);
        if(counter==playerNumber){
               disableAll();
        }
    }

    @FXML
    private void selectArtemis(){
        if(!setted){
            getPlayers();
        }
        chosen.add(counter,1);
        counter ++;
        Artemis.setVisible(false);
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
            Athena.setVisible(false);
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
            Atlas.setVisible(false);
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
            Demeter.setVisible(false);
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
            Hephaestus.setVisible(false);
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
            Minotaur.setVisible(false);
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
            Pam.setVisible(false);
            if(counter==playerNumber){
                disableAll();
            }
    }

    @FXML
    private void selectPrometheus(){
        if(!setted){
            getPlayers();
        }
            chosen.add(counter,8);
            counter ++;
            Prometheus.setVisible(false);
            if(counter==playerNumber){
                disableAll();
            }
    }

    private void disableAll() {
        Apollo.setVisible(false);
        Artemis.setVisible(false);
        Athena.setVisible(false);
        Atlas.setVisible(false);
        Demeter.setVisible(false);
        Hephaestus.setVisible(false);
        Minotaur.setVisible(false);
        Pam.setVisible(false);
        Prometheus.setVisible(false);
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


