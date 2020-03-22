package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Turn;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketServer {
    private ServerSocket serverSocket;
    private List<ServerThread> observers;

    public SocketServer(int playerNum) {
        observers = new ArrayList<>();
        int actualNum = 0;
        Socket socket;
        try {
            serverSocket = new ServerSocket(59898);
            System.out.println("The server is running...");
            while (actualNum != playerNum) {
                socket = serverSocket.accept();
                ServerThread serverThread = new ServerThread(socket, actualNum);
                serverThread.start();
                observers.add(serverThread);
                actualNum++;
                System.out.println("Connected: " + socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Turn turn = new Turn(this, observers.size());
        new ServerModelUpdater(turn);
    }

    public void closeServer() {
        try {
            serverSocket.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        System.out.println("Closed: " + serverSocket);
    }

    public void sendToAllExcept(int excludedClient, Commands commands) {
        for(int i = 0; i < observers.size(); i++) {
            if(i != excludedClient) {
                observers.get(i).send(commands);
            }
        }
    }

    public void sendTo(int clientID, Commands commands) {
        observers.get(clientID).send(commands);
    }
}