package it.polimi.ingsw.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.*;

/**
 * the socket server
 */
public class SocketServer {
    private final int port;

    /**
     * creates the server and creates a thread for all the clients
     */
    public SocketServer(int port) {
        this.port = port;
        addClients();
    }

    /**
     * instanciate a ServerThread for all the clients that connect
     */
    private void addClients() {
        int timer = 1000;
        List<ServerThread> observer;
        int actualNum = 0;
        int serverNum = 1;
        int min = 2;
        int max = 3;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                observer = new ArrayList<>();
                System.out.println("Server " + serverNum + " online");
                while (actualNum != max) {
                    try {
                        Socket socket = serverSocket.accept();
                        ServerThread serverThread = new ServerThread(socket);
                        serverThread.setClientID(actualNum);
                        observer.add(serverThread);
                        actualNum++;
                        System.out.println("Connected: " + socket);
                        if (actualNum == min) {
                            System.out.println("Timer start, " + timer / 1000 + " seconds left to join");
                            serverSocket.setSoTimeout(timer);
                        }
                    } catch (SocketTimeoutException e) {
                        System.out.println("Timer timed out, starting game");
                        break;
                    }
                }
                serverSocket.setSoTimeout(0);
                new Thread(new GameHandler(observer));
                actualNum = 0;
                serverNum++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}