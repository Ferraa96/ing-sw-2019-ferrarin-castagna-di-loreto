package it.polimi.ingsw.Controller;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient implements SocketInterface {
    private Socket socket;
    private ObjectOutputStream objectOutputStream;

    public SocketClient() {
        String ip;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Scrivi l'IP: ");
        ip = scanner.nextLine();
        try {
            socket = new Socket(ip, 59898);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void send(Commands commands) {
        try {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(commands);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
