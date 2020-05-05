package it.polimi.ingsw.controller.Server;

public class Server {

    public static void main(String[] args) {
        int port = 59898;
        new SocketServer(port);
    }
}
