package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.Instructions.*;
import it.polimi.ingsw.controller.SocketClient;
import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.IOHandler;
import it.polimi.ingsw.model.Position;

import java.util.*;

public class GUIController implements ViewInterface {

    private final SocketClient socketClient;
    private final Tile[][] gameMap;
    private static final Scanner scanner = new Scanner(System.in);
    private Map<Integer, Position> workerPos;
    private int playerID;
    private GUI gui;

    public GUIController(SocketClient socketClient) {
        this.socketClient = socketClient;
        gameMap = new Tile[5][5];
        try {
            gui = new GUI();
            gui.launch(GUI.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        workerPos = new HashMap<>();
    }

    @Override
    public void setName(List<String> stringList, int playerID) {
        this.playerID = playerID;
        socketClient.send(new SetNameInstr(verifyName(stringList)));
    }

    private String verifyName(List<String> alreadyChosen) {
        String name;
        boolean ok = true;
        while (true) {
            name = scanner.nextLine();
            for(String curr: alreadyChosen) {
                if(curr.equals(name)) {
                    ok = false;

                    break;
                }
            }
            if(ok) {
                return name;
            }
            //System.out.print("Nome " + name + " già preso, scegline un altro: ");
            ok = true;
        }
    }

    @Override
    public void chooseCard(List<Integer> cardList) {
        List<Card> cards;
        cards = new IOHandler().getCardList();
        System.out.println("Scegli una carta tra\n");
        for(Integer curr: cardList) {
            System.out.println(curr + ": " + cards.get(curr).getName());
            System.out.println(cards.get(curr).getDescription() + "\n");
        }
        socketClient.send(new ChooseCardInstr(verifyCard(cardList)));
    }

    private int verifyCard(List<Integer> list) {
        int card = scanner.nextInt();
        boolean ok = false;
        while(true) {
            for (Integer i : list) {
                if (i == card) {
                    ok = true;
                    list.remove(i);
                    break;
                }
            }
            if(ok) {
                return card;
            }
            System.out.print("Carta non valida, selezionane un'altra: ");
            card = scanner.nextInt();
        }
    }

    @Override
    public void chooseCardList(int num) {

    }

    @Override
    public void firstPositioning(List<Position> availablePos) {
        List<Position> list = new ArrayList<>();
        Map<Integer, Position> movement = new HashMap<>();
        Position pos;
        updateScreen();                                             //DA USARE PER SELEZIONARE POSIZIONI DISPONIBILI
        //System.out.print("Posizione iniziale lavoratore 1 (riga, colonna): ");  VEDERE SE BOX O A GRAFICA
        pos = verifyPosition(availablePos);
        movement.put(playerID * 2, pos);
        move(movement);
        updateScreen();
        list.add(pos);
        movement.clear();
        //System.out.print("Posizione iniziale lavoratore 2 (riga, colonna): ");
        pos = verifyPosition(availablePos);
        movement.put(playerID * 2 + 1, pos);
        move(movement);
        list.add(pos);
        socketClient.send(new FirstPositioningInstr(list));
        updateScreen();                                            //DA USARE PER MOSTRARE LE SCELTE

    }

    @Override
    public void chooseWorker() {
        Position posW1 = workerPos.get(playerID * 2);
        Position posW2 = workerPos.get(playerID * 2 + 1);
        /*markPosition(posW1, 'x');
        markPosition(posW2, 'x');*/
        updateScreen();
        //System.out.print("Seleziona il worker: ");
        List<Position> temp = new ArrayList<>();
        temp.add(posW1);
        temp.add(posW2);
        /*markPosition(posW1, ' ');
        markPosition(posW2, ' ');*/
        socketClient.send(new ChooseWorkerInstr(verifyPosition(temp)));
    }

    @Override
    public void choosePower() {
        socketClient.send(new SetPowerInstr(GUI.activatePower()));
    }

    @Override
    public void choosePosition(List<Position> list) {
        for(Position pos: list) {
            //markPosition(pos, 'x');
        }
        updateScreen();
        System.out.print("Scegli la posizione (riga, colonna): ");
        for(Position pos: list) {
            //markPosition(pos, ' ');
        }
        socketClient.send(new ChoosePosInstr(verifyPosition(list)));
    }

    @Override
    public void move(Map<Integer, Position> movement) {

    }

    @Override
    public void buildBlock(Position position, int height) {

    }

    @Override
    public void buildDome(Position position, int height) {

    }

    @Override
    public void updateScreen() {

    }

    private static Position verifyPosition(List<Position> temp) {
        Position pos = new Position(scanner.nextInt() - 1, scanner.nextInt() - 1);
        boolean ok = false;
        while(true) {
            Position[] list = new Position[0];
            for (Position curr : list) {
                if (pos.getRow() == curr.getRow() && pos.getColumn() == curr.getColumn()) {
                    ok = true;
                    //list.remove(curr);
                    break;
                }
            }
            if(ok) {
                return pos;
            }
            //System.out.print("Scelta non valida, inseriscine un'altra: ");
            pos = new Position(scanner.nextInt() - 1, scanner.nextInt() - 1);
        }
    }

    @Override
    public void askForReloadState() {

    }

    @Override
    public void reloadState(Cell[][] map) {

    }

    @Override
    public void handleEndGame(String message) {

    }

}
