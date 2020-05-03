package it.polimi.ingsw.controller;

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
    private ServerModelUpdater serverModelUpdater;
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
            while(running) {
                Object obj = inStream.readObject();
                serverModelUpdater.receive(obj);
            }
        } catch(IOException | ClassNotFoundException e) {
            if(running) {
                serverModelUpdater.receive(clientID);
            }
            Thread.currentThread().interrupt();
        }
    }

    /**
     * send the command to the client
     * @param commands the command to send
     */
    public void send(Object commands) {
        try {
            outStream.writeObject(commands);
            outStream.reset();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public void closeConnection() {
        running = false;
        try {
            socketClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setServerModelUpdater(ServerModelUpdater serverModelUpdater) {
        this.serverModelUpdater = serverModelUpdater;
    }
}