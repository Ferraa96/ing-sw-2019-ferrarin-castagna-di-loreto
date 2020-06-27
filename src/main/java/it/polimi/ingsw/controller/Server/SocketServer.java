package it.polimi.ingsw.controller.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * the socket server
 */
public class SocketServer {
    private final int port;
    private ServerSocket serverSocket;
    private final boolean[] aborted = {false};

    /**
     * creates the server and creates a thread for all the clients
     */
    public SocketServer(int port) {
        this.port = port;
        addClients();
    }

    /**
     * instantiates a ServerThread for all the clients that connect
     */
    private void addClients() {
        int maxLobbies = 100;
        int serverNum = 1;
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(1000);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        while (serverNum < maxLobbies) {
            System.out.println("Server " + serverNum + " online on port " + port);
            Thread thread = new Thread(this::createLobby);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            serverNum++;
        }
    }

    private void createLobby() {
        int time = 100000;
        int actualNum = 0;
        int min = 2;
        int max = 3;
        final boolean[] start = {false};
        LobbyHandler lobby = new LobbyHandler(aborted);
        while (actualNum < max) {
            if(aborted[0]) {
                aborted[0] = false;
                return;
            }
            try {
                Socket socketClient = serverSocket.accept();
                ServerThread serverThread = new ServerThread(socketClient);
                serverThread.setClientID(actualNum);
                lobby.addClient(actualNum, serverThread);
                actualNum++;
                System.out.println("Connected: " + socketClient);
                if (actualNum == min) {
                    TimerTask task = new TimerTask() {
                        public void run() {
                            start[0] = true;
                        }
                    };
                    Timer timer = new Timer("Timer");
                    timer.schedule(task, time);
                }
            } catch (SocketTimeoutException e) {
                if(start[0]) {
                    System.out.println("Timer timed out, starting game");
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        lobby.numPlayerReached();
    }
}