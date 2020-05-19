package it.polimi.ingsw.controller.Client;

import it.polimi.ingsw.controller.Instructions.MessageInterface;
import it.polimi.ingsw.controller.Instructions.MessageVisitor;
import it.polimi.ingsw.view.Cli.CLI;
import it.polimi.ingsw.view.GUI.GUIHandler;
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
    private MessageVisitor clientUpdater;
    private Socket socket;

    /**
     * creates a client and instantiate the view
     */
    public SocketClient() {
            connect();
    }

    public SocketClient(int i) {

    }

    private void connect() {
        int port = 59898;
        ViewInterface view;
        Scanner scanner = new Scanner(System.in);
        System.out.print("IP: ");
        String ip = scanner.nextLine();
        try {
            socket = new Socket(ip, port);
            outStream = new ObjectOutputStream(socket.getOutputStream());
            inStream = new ObjectInputStream(socket.getInputStream());
            view = new CLI(this);
            clientUpdater = new ClientUpdater(view);
            start();
        } catch (IOException e) {
            System.out.println("Server non trovato");
            connect();
        }
    }

    public boolean connectGUI(String ip, GUIHandler guiHandler) {
        int port = 59898;
        ViewInterface view;
        try {
            socket = new Socket(ip, port);
            outStream = new ObjectOutputStream(socket.getOutputStream());
            inStream = new ObjectInputStream(socket.getInputStream());
            view = guiHandler;
            clientUpdater = new ClientUpdater(view);
            start();
            return true;
        } catch (IOException e) {
            System.out.println("Server non trovato");
            return false;
        }
    }

    /**
     * creates a thread listening to the commands from the server
     */
    @Override
    public void run() {
        try {
            while(true){
                MessageInterface msg = (MessageInterface) inStream.readObject();
                msg.accept(clientUpdater);
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
    public void send(MessageInterface commands) {
        try {
            outStream.writeObject(commands);
            outStream.reset();
        } catch(IOException e) {
            System.out.println("Server offline");
            closeClient();
        }
    }
}