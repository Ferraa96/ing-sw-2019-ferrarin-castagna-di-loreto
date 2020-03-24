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

    /**
     * handle the client connected to the server
     * @param socket the socket that represent the client
     * @param playerID the ID of the client
     */
    public ServerThread(Socket socket, int playerID) {
        this.socket = socket;
        this.playerID = playerID;
        try {
            outStream = new ObjectOutputStream(socket.getOutputStream());
            inStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Commands commands = new Commands();
        commands.setPlayer(playerID);
        commands.setInstruction(Instruction.setPlayerID);
        send(commands);
    }

    /**
     * creates a thread listening to the commands from the client
     */
    @Override
    public void run() {
        Commands commands;
        try {
            do {
                commands = (Commands) inStream.readObject();
                ServerModelUpdater.receive(commands);
            } while(commands.getInstruction() != Instruction.endGame);
            inStream.close();
            socket.close();
        } catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * send the command to the client
     * @param commands the command to send
     */
    public void send(Commands commands) {
        try {
            outStream.writeObject(commands);
            outStream.reset();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the ID of the client
     */
    public int getPlayerID() {
        return playerID;
    }
}