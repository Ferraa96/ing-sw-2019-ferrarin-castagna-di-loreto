package it.polimi.ingsw.controller.Server;

import it.polimi.ingsw.controller.Instructions.MessageInterface;
import it.polimi.ingsw.controller.Instructions.MessageVisitor;
import it.polimi.ingsw.model.ModelInterface;
import it.polimi.ingsw.model.Turn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LobbyHandler {
    private List<ServerThread> observer;

    public LobbyHandler(List<ServerThread> observer) {
        this.observer = observer;
        createLobby();
    }

    private void createLobby() {
        ModelInterface turn = new Turn(this, observer.size());
        MessageVisitor modelUpdater = new ModelUpdater(turn);
        for(ServerThread currThread: observer) {
            currThread.setModelUpdater(modelUpdater);
            currThread.start();
        }
        turn.startGame();
    }

    /**
     * closes the server
     */
    public void closeServer(MessageInterface message) {
        for(ServerThread curr: observer) {
            curr.send(message);
            curr.closeConnection();
        }
    }

    /**
     * send the command to all the clients connected except for excludedClient
     * @param excludedClient the excluded client
     * @param commands the command to send
     */
    public void sendToAllExcept(int excludedClient, MessageInterface commands) {
        for(int i = 0; i < observer.size(); i++) {
            if(i != excludedClient) {
                observer.get(i).send(commands);
            }
        }
    }

    /**
     * send the command to a specific client
     * @param clientID the receiver client
     * @param commands the command to send
     */
    public void sendTo(int clientID, MessageInterface commands) {
        observer.get(clientID).send(commands);
    }

    /**
     * sort all the players in the same order of the game loaded
     * @param playerMap maps the actual order to the game loaded order
     */
    public void sortPlayers(Map<Integer, Integer> playerMap) {
        List<ServerThread> temp = new ArrayList<>();
        for(int i = 0; i < observer.size(); i++) {
            temp.add(observer.get(playerMap.get(i)));
            temp.get(i).setClientID(i);
        }
        observer = temp;
    }
}
