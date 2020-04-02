package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Turn;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * the socket server
 */
public class SocketServer implements ControllerInterface {
    private ServerSocket serverSocket;
    private List<ServerThread> observers;
    private int port = 59898;
    private ServerModelUpdater serverModelUpdater;

    /**
     * creates the server and creates a thread for all the clients
     */
    public SocketServer(int port) {
        this.port = port;
        observers = new ArrayList<>();
        addClients();
    }

    private void addClients() {
        int actualNum = 0;
        int min = 2;
        int max = 3;
        try {
            serverSocket = new ServerSocket(port);
            serverModelUpdater = new ServerModelUpdater();
            while (actualNum != max) {
                Socket socket = serverSocket.accept();
                ServerThread serverThread = new ServerThread(socket, actualNum, serverModelUpdater);
                serverThread.start();
                observers.add(serverThread);
                actualNum++;
                System.out.println("Connected: " + socket);
                if (actualNum == min) {
                    System.out.println("Timer start");
                    serverSocket.setSoTimeout(10000);
                }
            }
        } catch (IOException e) {
            if(!(e.getMessage().equals("Accept timed out"))) {
                e.printStackTrace();
            }
        }
        gameStart();
    }

    private void gameStart() {
        serverModelUpdater.setTurn(new Turn(this, observers.size()));
        System.out.println("Game start");
        for(ServerThread curr: observers) {
            System.out.println(curr);
        }
    }

    /**
     * closes the server
     */
    @Override
    public void closeServer() {
        try {
            serverSocket.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        System.out.println("Closed: " + serverSocket);
    }

    /**
     * send the command to all the clients connected except for excludedClient
     * @param excludedClient the excluded client
     * @param commands the command to send
     */
    @Override
    public void sendToAllExcept(int excludedClient, Commands commands) {
        for(int i = 0; i < observers.size(); i++) {
            if(i != excludedClient) {
                observers.get(i).send(commands);
            }
        }
    }

    /**
     * send the command to a specific client
     * @param clientID the receiver client
     * @param commands the command to send
     */
    @Override
    public void sendTo(int clientID, Commands commands) {
        observers.get(clientID).send(commands);
    }
}