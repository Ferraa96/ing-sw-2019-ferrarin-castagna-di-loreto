package it.polimi.ingsw.controller.Server;

import it.polimi.ingsw.controller.Instructions.DisconnectionNotification;
import it.polimi.ingsw.controller.Instructions.NotificationInterface;
import it.polimi.ingsw.controller.Instructions.NotificationVisitor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * the thread that handle the client
 */
public class ServerThread extends Thread {
    private ObjectOutputStream outStream;
    private ObjectInputStream inStream;
    private NotificationVisitor modelUpdater;
    private int clientID;
    private final Socket socketClient;
    private boolean running = true;

    /**
     * handle the client connected to the server
     * @param socketClient the socket that represent the client
     */
    public ServerThread(Socket socketClient) {
        this.socketClient = socketClient;
        try {
            outStream = new ObjectOutputStream(socketClient.getOutputStream());
            inStream = new ObjectInputStream(socketClient.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * creates a thread listening to the commands from the client
     */
    @Override
    public void run() {
        try {
            while(true) {
                NotificationInterface msg = (NotificationInterface) inStream.readObject();
                if(running) {
                    msg.setClientID(clientID);
                    msg.accept(modelUpdater);
                } else {
                    return;
                }
            }
        } catch(IOException | ClassNotFoundException e) {
            if(running) {
                DisconnectionNotification disconnection = new DisconnectionNotification("");
                disconnection.setClientID(clientID);
                disconnection.accept(modelUpdater);
                Thread.currentThread().interrupt();
            }
            try {
                socketClient.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /**
     * send the command to the client
     * @param commands the command to send
     */
    public void send(NotificationInterface commands) {
        try {
            outStream.writeObject(commands);
            outStream.reset();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * associate the current serverThread with the clientID
     * @param clientID the id of the connected player
     */
    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    /**
     * closes the connection
     */
    public void closeConnection() {
        running = false;
    }

    /**
     * set the modelUpdater
     * @param modelUpdater the modelUpdater
     */
    public void setModelUpdater(NotificationVisitor modelUpdater) {
        this.modelUpdater = modelUpdater;
    }
}