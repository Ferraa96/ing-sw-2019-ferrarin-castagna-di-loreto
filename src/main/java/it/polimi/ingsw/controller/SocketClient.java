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

    /**
     * creates a client and instantiate the view
     */
    public SocketClient() {
        ViewInterface view;
        try {
            socket = new Socket("127.0.0.1",59898);
            outStream = new ObjectOutputStream(socket.getOutputStream());
            inStream = new ObjectInputStream(socket.getInputStream());
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
        new ClientViewUpdater(view);
        start();
    }

    /**
     * creates a thread listening to the commands from the server
     */
    @Override
    public void run() {
        Commands commands;
        try {
            do {
                commands = (Commands) inStream.readObject();
                ClientViewUpdater.receive(commands);
            } while(commands.getInstruction() != Instruction.endGame);
            inStream.close();
            socket.close();
        } catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * send the command to the server
     * @param commands the command to send
     */
    public void send(Commands commands) {
        try {
            outStream.writeObject(commands);
            outStream.reset();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}