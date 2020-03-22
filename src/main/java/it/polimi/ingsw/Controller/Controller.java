package it.polimi.ingsw.Controller;

import it.polimi.ingsw.View.ViewInterface;

import java.util.Scanner;

public class Controller {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ViewInterface view;
        System.out.println("1: Crea server\n2: Connettiti al server");
        if(scanner.nextInt() == 1) {
            System.out.println("Quanti giocatori? ");
            new SocketServer(scanner.nextInt());
        } else {
            new SocketClient();
        }
    }
}
