package it.polimi.ingsw.controller.Client;

public class CLI {

    public static void main(String[] args) {
//        String code;
//        for(int i = 0; i < 16; i++) {
//            for (int j = 0; j < 16; j++) {
//                code = String.valueOf(i * 16 + j);
//                System.out.print("\u001b[48;5;" + code + "m " + code + "\t");
//            }
//            System.out.println("\u001b[0m");
//        }
        SocketClient socketClient = new SocketClient();
        socketClient.connectCLI();
    }
}