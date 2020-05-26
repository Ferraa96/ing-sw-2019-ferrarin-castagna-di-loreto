package it.polimi.ingsw.controller.Client;

public class Client {

    public static void main(String[] args) {
        SocketClient socketClient = new SocketClient();
        socketClient.connectCLI();
    }
}