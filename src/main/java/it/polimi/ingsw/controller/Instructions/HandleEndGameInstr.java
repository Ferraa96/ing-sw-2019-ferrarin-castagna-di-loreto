package it.polimi.ingsw.controller.Instructions;

import java.io.Serializable;

/**
 * notify the disconnection of one client, the game end
 */
public class HandleEndGameInstr implements Serializable, MessageInterface {
    private String message;
    int clientID;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public int getClientID() {
        return clientID;
    }

    @Override
    public void accept(MessageVisitor v) {
        v.visit(this);
    }
}
