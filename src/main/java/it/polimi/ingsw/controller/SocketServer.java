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

    /**
     * creates the server and creates a thread for all the clients
     */
    public SocketServer() {
        int actualNum = 0;
        int max;
        System.out.println("Numero di giocatori: ");
        max = new Scanner(System.in).nextInt();
        observers = new ArrayList<>();
        Socket socket;
        try {
            serverSocket = new ServerSocket(59898);
            System.out.println("The server is running...");
            while (actualNum != max) {
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