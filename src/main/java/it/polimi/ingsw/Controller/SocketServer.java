package it.polimi.ingsw.Controller;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketServer implements SocketInterface {
    private ServerSocket serverSocket;
    private ObjectOutputStream objectOutputStream;
    private List<Socket> observers;

    public SocketServer(int playerNum) {
        observers = new ArrayList<>();
        int actualNum = 0;
        Socket socket;
        try {
            serverSocket = new ServerSocket(59898);
            System.out.println("The server is running...");
            while (actualNum != playerNum) {
                socket = serverSocket.accept();
                observers.add(socket);
                actualNum++;
                System.out.println("Connected: " + socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void send(Commands commands) {
        try {
            for(Socket curr: observers) {
                objectOutputStream = new ObjectOutputStream(curr.getOutputStream());
                objectOutputStream.writeObject(commands);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeServer() {
        try {
            serverSocket.close();
        } catch (IOException e) {
        }
        System.out.println("Closed: " + serverSocket);
    }
}