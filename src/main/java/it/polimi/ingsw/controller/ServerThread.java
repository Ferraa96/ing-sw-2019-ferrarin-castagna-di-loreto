package it.polimi.ingsw.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * the thread that handle the client
 */
public class ServerThread extends Thread {
    private Socket socket;
    private ObjectOutputStream outStream;
    private ObjectInputStream inStream;
    private int playerID;
    private ServerModelUpdater serverModelUpdater;

    /**
     * handle the client connected to the server
     * @param socket the socket that represent the client
     * @param playerID the ID of the client
     */
    public ServerThread(Socket socket, int playerID, ServerModelUpdater serverModelUpdater) {
        this.socket = socket;
        this.playerID = playerID;
        this.serverModelUpdater = serverModelUpdater;
        try {
            outStream = new ObjectOutputStream(socket.getOutputStream());
            inStream = new ObjectInputStream(socket.getInputStream());
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
                serverModelUpdater.receive(inStream.readObject());
            }
        } catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
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
}