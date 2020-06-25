package it.polimi.ingsw.controller.Client;

import it.polimi.ingsw.controller.Instructions.DisconnectionNotification;
import it.polimi.ingsw.controller.Instructions.MessageInterface;
import it.polimi.ingsw.controller.Instructions.MessageVisitor;
import it.polimi.ingsw.view.ViewInterface;

import java.io.*;
import java.net.Socket;

/**
 * the socket client
 */
public class SocketClient extends Thread {
    private ObjectOutputStream outStream;
    private ObjectInputStream inStream;
    private MessageVisitor clientUpdater;
    private Socket socket;
    private boolean running = true;

    /**
     * creates a client and instantiate the view
     */

    public SocketClient() { }

    /**
     * connect the view to the server
     * @param ip the ip of the server
     * @param view the view
     * @return true if the connection is successful, false otherwise
     */
    public boolean connect(String ip, int port, ViewInterface view) {
        try {
            socket = new Socket(ip, port);
            outStream = new ObjectOutputStream(socket.getOutputStream());
            inStream = new ObjectInputStream(socket.getInputStream());
            clientUpdater = new ClientUpdater(view);
            start();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * creates a thread listening to the commands from the server
     */
    @Override
    public void run() {
        try {
            while(running){
                MessageInterface msg = (MessageInterface) inStream.readObject();
                msg.accept(clientUpdater);
            }
        } catch(IOException | ClassNotFoundException e) {
            MessageInterface msg = new DisconnectionNotification("Server offline");
            msg.accept(clientUpdater);
            closeClient();
        }
    }

    /**
     * closes the client
     */
    public void closeClient() {
        try {
            running = false;
            interrupt();
            socket.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * send the command to the server
     * @param commands the command to send
     */
    public void send(MessageInterface commands) {
        try {
            outStream.writeObject(commands);
            outStream.reset();
        } catch(IOException e) {
            System.out.println("Server offline");
            closeClient();
        }
    }
}