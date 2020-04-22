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
    private Socket socket;
    private ObjectOutputStream outStream;
    private ObjectInputStream inStream;
    private ClientViewUpdater clientViewUpdater;

    /**
     * creates a client and instantiate the view
     */
    public SocketClient() {
        ViewInterface view;
        Scanner scanner = new Scanner(System.in);
        System.out.print("IP: ");
        String ip = scanner.nextLine();
        try {
            System.out.print("Port: ");
            socket = new Socket(ip, scanner.nextInt());
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
            e.printStackTrace();
        }
    }
}