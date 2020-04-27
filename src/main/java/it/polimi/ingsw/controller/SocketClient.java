package it.polimi.ingsw.controller;

import it.polimi.ingsw.view.CLI;
import it.polimi.ingsw.view.GUI;
import it.polimi.ingsw.view.ViewInterface;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * the socket client
 */
public class SocketClient extends Thread {
    private ObjectOutputStream outStream;
    private ObjectInputStream inStream;
    private final ClientViewUpdater clientViewUpdater;
    private Socket socket;

    /**
     * creates a client and instantiate the view
     */
    public SocketClient() {
        int port = 59898;
        ViewInterface view;
        Scanner scanner = new Scanner(System.in);
        System.out.print("IP: ");
        String ip = scanner.nextLine();
        try {
            socket = new Socket(ip, port);
            outStream = new ObjectOutputStream(socket.getOutputStream());
            inStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("1: CLI\n2: GUI");
        if(scanner.nextInt() == 1) {
            view = new CLI(this);
        } else {
            view = new GUI(this);
        }
        clientViewUpdater = new ClientViewUpdater(view);
        start();
    }

    /**
     * creates a thread listening to the commands from the server
     */
    @Override
    public void run() {
        try {
            while(true){
                clientViewUpdater.receive(inStream.readObject());
            }
        } catch(IOException | ClassNotFoundException e) {
            System.out.println("Server offline");
            closeClient();
        }
    }

    /**
     * closes the client
     */
    public void closeClient() {
        try {
            socket.close();
            System.out.println("Closed: " + socket);
            interrupt();
            System.exit(0);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * send the command to the server
     * @param commands the command to send
     */
    public void send(Object commands) {
        try {
            outStream.writeObject(commands);
            outStream.reset();
        } catch(IOException e) {
            System.out.println("Server offline");
            closeClient();
        }
    }
}