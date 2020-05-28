package it.polimi.ingsw.view.Cli;

import it.polimi.ingsw.model.Position;

import java.util.Scanner;

public class ScannerListener extends Thread {
    private Request request = Request.ignore;
    private boolean running = true;
    private final CLIHandler cliHandler;
    private final Scanner scanner;

    public ScannerListener(CLIHandler cliHandler) {
        this.cliHandler = cliHandler;
        scanner = new Scanner(System.in);
    }

    /**
     * listen to user input and send it to the right method of CLI
     */
    @Override
    public void run() {
        while (running) {
            if (scanner.hasNextLine()) {
                switch (request) {
                    case name:
                        request = Request.ignore;
                        cliHandler.verifyName(scanner.nextLine());
                        break;
                    case askReload:
                        request = Request.ignore;
                        cliHandler.reloadStateAnswer(scanner.nextLine());
                        break;
                    case cardList:
                        cliHandler.verifyCardList(waitForInt());
                        break;
                    case card:
                        cliHandler.verifyCard(waitForInt());
                        break;
                    case firstPos:
                        cliHandler.verifyFirstPos(controlTwoInt());
                        break;
                    case worker:
                        cliHandler.verifyWorker(controlTwoInt());
                        break;
                    case power:
                        request = Request.ignore;
                        cliHandler.verifyPower(scanner.nextLine());
                        break;
                    case position:
                        cliHandler.verifyPosition(controlTwoInt());
                        break;
                    default:
                        scanner.nextLine();
                }
            }
        }
    }

    /**
     * wait for the input of 2 consecutive numbers
     * @return the position generated by the 2 numbers (raw, column)
     */
    private Position controlTwoInt() {
        int x, y;
        while (running) {
            if(scanner.hasNextInt()) {
                x = scanner.nextInt();
                if(scanner.hasNextInt()) {
                    y = scanner.nextInt();
                    request = Request.ignore;
                    return new Position(x - 1, y - 1);
                }
            }
            scanner.nextLine();
            System.out.print("Inserisci una posizione valida: ");
        }
        return null;
    }

    /**
     * wait for the input of 1 number > 0
     * @return the number
     */
    private int waitForInt() {
        int num;
        while(running) {
            if(scanner.hasNextInt()) {
                num = scanner.nextInt();
                if(num >= 0) {
                    request = Request.ignore;
                    return num;
                }
            }
            System.out.print("Inserisci l'indice della carta: ");
            scanner.nextLine();
        }
        return 0;
    }

    /**
     * set the request to wait for
     * @param request the type of request
     */
    public void setRequest(Request request) {
        this.request = request;
    }

    /**
     * stops the thread
     */
    public void stopReading() {
        running = false;
        interrupt();
    }
}
