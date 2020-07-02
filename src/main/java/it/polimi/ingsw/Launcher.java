package it.polimi.ingsw;

import it.polimi.ingsw.controller.Server.SocketServer;
import it.polimi.ingsw.view.Cli.CLIHandler;
import it.polimi.ingsw.view.GUI.GUI;

public class Launcher {

    public static void main(String[] args) {
        if(args.length > 0) {
            switch (args[0]) {
                case "s":
                    int inputPort = 10000;
                    int delay = 120000;
                    if(args.length > 1) {
                        try {
                            inputPort = Integer.parseInt(args[1]);
                        } catch (NumberFormatException e) {
                            System.out.println("Not valid port parameter, using default port");
                        }
                    }
                    if(args.length > 2) {
                        try {
                            delay = Integer.parseInt(args[2]);
                        } catch (NumberFormatException e) {
                            System.out.println("Not valid delay parameter, using default delay");
                        }
                    }
                    new SocketServer(inputPort, delay);
                case "g":
                    GUI gui = new GUI();
                    gui.initialize();
                    break;
                case "c":
                    CLIHandler cli = new CLIHandler();
                    cli.connect();
                    break;
                default:
            }
        } else {
            GUI gui = new GUI();
            gui.initialize();
        }
    }
}
