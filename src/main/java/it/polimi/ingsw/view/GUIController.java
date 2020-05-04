package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.Instructions.*;
import it.polimi.ingsw.controller.ServerThread;
import it.polimi.ingsw.controller.SocketClient;
import it.polimi.ingsw.model.*;

import java.util.*;

/**
 * Class that verify everything that is done in the GUI
 * and takes care of communicate those information
 */
public class GUIController implements ViewInterface {

    private final SocketClient socketClient;
    private final Tile[][] gameMap;
    private static final Scanner scanner = new Scanner(System.in);
    private Map<Integer, Position> workerPos;
    private int playerID;
    private GUI gui;
    private String playername;


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
    public void setName(List<String> stringList) {
        socketClient.send(new SetNameInstr(verifyName(stringList)));
    }

    private String verifyName(List<String> alreadyChosen) {
        String name;
        boolean ok = true;
        while (true) {
            name = GUI.setName();
            for(String curr: alreadyChosen) {
                if(curr.equals(name)) {
                    ok = false;

                    break;
                }
            }
            if(ok) {
                return name;
            }
            GUI.nameChosen();
            ok = true;
        }
    }

    @Override
    public void chooseCardList(int num) {
        socketClient.send(new ChooseCardListInstr(GUI.setCards()));
    }

    @Override
    public void chooseCard(List<Integer> cardList) {
        socketClient.send(new ChooseCardInstr(verifyCard(cardList)));
    }

    private int verifyCard(List<Integer> list) {
        int card = GUI.setCard();
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
            } //in teoria non arriva mai fuori dall'if se faccio controllo in gui,da vedere
            System.out.print("Carta non valida, selezionane un'altra: ");
            card = scanner.nextInt();
        }
    }

    @Override
    public void firstPositioning(List<Position> availablePos) {   //LO FANNO TUTTI I GIOCATORI
        List<Position> list = new ArrayList<>();
        Map<Integer, Position> movement = new HashMap<>();
        Position pos;
        updateScreen();                                             //DA USARE PER SELEZIONARE POSIZIONI DISPONIBILI
        //System.out.print("Posizione iniziale lavoratore 1 (riga, colonna): ");  
        pos = verifyPosition(availablePos);
        movement.put(playerID * 2, pos);
        move((List<Movement>) movement);
        updateScreen();
        list.add(pos);
        movement.clear();
        //System.out.print("Posizione iniziale lavoratore 2 (riga, colonna): ");
        pos = verifyPosition(availablePos);
        movement.put(playerID * 2 + 1, pos);
        move((List<Movement>) movement);
        list.add(pos);
        socketClient.send(new FirstPositioningInstr(list));    //PASSA LISTA AL TURNO
        updateScreen();

    }

    @Override
    public void chooseWorker(List<Position> availableWorkers) {
        for(Position pos: availableWorkers) {
            //markPosition(pos, 'x');
        }
        updateScreen();
        System.out.print("Seleziona il worker: ");
        Position chosen = verifyPosition(availableWorkers);
        for(Position pos: availableWorkers) {
            //markPosition(pos, ' ');
        }
        socketClient.send(new ChooseWorkerInstr(chosen));
    }

    @Override
    public void choosePower() {
        socketClient.send(new SetPowerInstr(GUI.activatePower()));
    }

    @Override
    public void choosePosition(List<Position> list) {  //EFFETTUI LA MOSSA 
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
    public void move(List<Movement> movements) {
        for(Movement currMove: movements) {
            int height;
            Position oldPos = currMove.getOldPos();
            Position newPos = currMove.getNewPos();
            TileGetter tileGetter = null;
            if(oldPos != null) {
                height = gameMap[oldPos.getRow()][oldPos.getColumn()].getHeight();
                gameMap[oldPos.getRow()][oldPos.getColumn()] = tileGetter.getTile(0, height, false);
            }
            height = gameMap[newPos.getRow()][newPos.getColumn()].getHeight();
            gameMap[newPos.getRow()][newPos.getColumn()] = tileGetter.getTile(1, height, false);
        }
    }

    private void removeFromPreviousPosition(int key) {
        if(workerPos.containsKey(key)) {
            Position oldPos = workerPos.get(key);
            workerPos.remove(key);
            for(Map.Entry<Integer, Position> entry: workerPos.entrySet()) {
                if(entry.getValue().equals(oldPos)) {
                    return;
                }
            }
            int height = gameMap[oldPos.getRow()][oldPos.getColumn()].getHeight();
            TileGetter tileGetter = null;
            gameMap[oldPos.getRow()][oldPos.getColumn()] = tileGetter.getTile(0, height, false);
        }
    }

    @Override
    public void buildBlock(Position position, int height) {

    }

    @Override
    public void buildDome(Position position, int height) {

    }

    @Override
    public void updateScreen() {
        System.out.println(String.format("%11d" + "%14d" + "%14d" + "%14d" + "%14d", 1, 2, 3, 4, 5));
        System.out.println("    -----------------------------------------------------------------------");
        for(int raw = 0; raw < 5; raw++) {
            for(int line = 0; line < 5; line++) {
                if(line == 2) {
                    System.out.print(" " + (raw + 1) + "  ");
                } else {
                    System.out.print("    ");
                }
                for(int column = 0; column < 5; column++) {
                    System.out.print("|" + gameMap[raw][column].getLine(line));  //COSTRUISCE I WORKER/BUILD
                }
                System.out.print("|\n");
            }
            System.out.println("    -----------------------------------------------------------------------");
        }
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
