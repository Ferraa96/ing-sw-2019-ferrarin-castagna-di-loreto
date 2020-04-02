package it.polimi.ingsw.controller;

public class Server {

    public static void main(String[] args) {
        int port = 59898;
        while(true) {
            System.out.println("Server " + (port - 59897) + " online on port " + port);
            new SocketServer(port);
            port++;
        }
    }
}
