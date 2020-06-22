package it.polimi.ingsw;

import it.polimi.ingsw.controller.Server.SocketServer;
import it.polimi.ingsw.view.Cli.CLIHandler;
import it.polimi.ingsw.view.GUI.GUI;

public class Launcher {

    public static void main(String[] args) {
        if(args.length > 0) {
            switch (args[0]) {
                case "s":
                    int port = 59898;
                    new SocketServer(port);
                    break;
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
