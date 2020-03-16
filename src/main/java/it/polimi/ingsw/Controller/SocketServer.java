package it.polimi.ingsw.Controller;

import it.polimi.ingsw.View.CLI;
import it.polimi.ingsw.View.GUI;
import it.polimi.ingsw.View.ViewInterface;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SocketServer implements SocketInterface {
    private ViewInterface view;
    private ServerSocket serverSocket;
    private List<ServerThread> observers;
    public SocketServer(int playerNum) {
        observers = new ArrayList<>();
        int actualNum = 1;
        Socket socket;
        try {
            serverSocket = new ServerSocket(59898);
            System.out.println("The server is running...");
            while (actualNum != playerNum) {
                socket = serverSocket.accept();
                ServerThread serverThread = new ServerThread(socket, actualNum + 1);
                serverThread.start();
                observers.add(serverThread);
                actualNum++;
                System.out.println("Connected: " + socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("1: CLI\n2: GUI");
        if(scanner.nextInt() == 1) {
            view = new CLI(this);
        } else {
            view = new GUI(this);
        }
        new ServerViewUpdater(view);
    }

    public void closeServer() {
        try {
            serverSocket.close();
        } catch (IOException e) {
        }
        System.out.println("Closed: " + serverSocket);
    }

    @Override
    public void send(Commands commands) {
        for(ServerThread curr: observers) {
            curr.send(commands);
        }
    }

    public void sendTo(int clientID, Commands commands) {
        observers.get(clientID - 1).send(commands);
    }
}