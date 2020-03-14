package it.polimi.ingsw.Model;

import it.polimi.ingsw.Controller.SocketClient;
import it.polimi.ingsw.Controller.SocketInterface;
import it.polimi.ingsw.Controller.SocketServer;
import it.polimi.ingsw.View.CLI;
import it.polimi.ingsw.View.GUI;
import it.polimi.ingsw.View.ViewInterface;

import java.util.Scanner;

public class Game {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ViewInterface view;
        SocketInterface socket;
        Card card = new Card();
        System.out.println("1: Crea server\n2: Connettiti al server");
        if(scanner.nextInt() == 1) {
            System.out.println("Quanti giocatori? ");
            socket = new SocketServer(scanner.nextInt());
            card.setFirstPlayer(true);
        } else {
            socket = new SocketClient();
            card.setFirstPlayer(false);
        }
        System.out.println("1: CLI\n2: GUI");
        if(scanner.nextInt() == 1) {
            view = new CLI();
        } else {
            view = new GUI();
        }
        Turn turn = new Turn(socket, view, card);
    }
}
