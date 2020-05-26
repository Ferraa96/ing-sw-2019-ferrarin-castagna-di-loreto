package it.polimi.ingsw.controller.Client;

public class CLI {

    public static void main(String[] args) {
        SocketClient socketClient = new SocketClient();
        socketClient.connectCLI();
    }
}