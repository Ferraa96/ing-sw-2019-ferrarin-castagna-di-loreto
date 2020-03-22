package it.polimi.ingsw.Controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread {
    private Socket socket;
    private ObjectOutputStream outStream;
    private ObjectInputStream inStream;
    private int playerID;

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

    public void send(Commands commands) {
        try {
            outStream.writeObject(commands);
            outStream.reset();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public int getPlayerID() {
        return playerID;
    }
}