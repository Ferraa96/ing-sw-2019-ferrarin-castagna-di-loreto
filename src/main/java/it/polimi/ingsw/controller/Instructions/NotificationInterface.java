package it.polimi.ingsw.controller.Instructions;

/**
 * the interface implemented by all the instructions, used to implement the visitor pattern
 */
public interface NotificationInterface {

    void accept(NotificationVisitor v);
    void setClientID(int clientID);
    int getClientID();
}
