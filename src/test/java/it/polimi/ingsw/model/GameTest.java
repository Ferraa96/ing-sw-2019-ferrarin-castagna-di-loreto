package it.polimi.ingsw.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.*;


public class GameTest {

    Board board = new Board();
    Cell[][] map;
    IOHandler ioHandler = new IOHandler();
    List <Card> cardlist = new ArrayList<>();


    @Test
    public void Scene1() {
        //set players
        for (int i = 0; i < 3; i++) {
            Card card = ioHandler.getCardList().get(i*2);
            card.setCard(board.getMap(), i);
            cardlist.add(card);
        }

        //set enemies
        for (Card curr: cardlist) {
            curr.setEnemies(cardlist);
        }

        //board situation
        map = board.getMap();
        cardlist.get(0).firstPositioning(new Position(1,1), new Position(2,4));
        cardlist.get(1).firstPositioning(new Position(2,1), new Position(3,4));
        cardlist.get(2).firstPositioning(new Position(1,3), new Position(3,2));
        map[1][1].setHeight(1);
        map[1][2].setHeight(1);
        map[1][3].setHeight(2);
        map[2][2].setHeight(2);
        map[2][3].setHeight(1);
        map[2][4].setHeight(1);
        map[3][1].setHeight(2);
        map[3][2].setHeight(1);

        //start test
        // Apollo -> si scambia con demeter -> non vince
        cardlist.get(0).setActivePower(true);
        assertEquals(2,cardlist.get(0).getCardRoutine().get(0).availableWithGod(cardlist.get(0).getWorker2()).size());
        cardlist.get(0).applyEffect(0, cardlist.get(0).getWorker2(), new Position(1,3));
        assertEquals(1, cardlist.get(0).getCardRoutine().get(0).getDownUp());
        assertEquals(1, map[1][3].getWorkerID());
        assertEquals(4, map[2][4].getWorkerID());
        assertEquals(false,cardlist.get(0).checkWin(0,new Position(1,3)));
        cardlist.get(0).applyEffect(1, cardlist.get(0).getWorker2(), new Position(2,3));
        assertEquals(2, map[2][3].getHeight());
        assertEquals(false,cardlist.get(0).checkWin(1,new Position(2,3)));

        // Athena -> muove up e blocca tutti -> non vince
        cardlist.get(1).applyEffect(0, cardlist.get(1).getWorker1(), new Position(1,2));
        assertEquals(false,cardlist.get(1).checkWin(0,new Position(1,2)));
        cardlist.get(1).applyEffect(1, cardlist.get(1).getWorker1(), new Position(2,2));

        // Demeter -> attiva la carta -> non vince
        cardlist.get(2).setActivePower(true);
        assertEquals(2, cardlist.get(2).getCardRoutine().get(0).availableWithGod(cardlist.get(2).getWorker1()).size());
        cardlist.get(2).applyEffect(0, cardlist.get(2).getWorker1(), new Position(3,3));
        assertEquals(false,cardlist.get(1).checkWin(0,new Position(3,3)));
        cardlist.get(2).applyEffect(1, cardlist.get(2).getWorker1(), new Position(2,3));
        cardlist.get(2).applyEffect(2, cardlist.get(2).getWorker1(), new Position(2,4));

        // Apollo -> non può vincere perchè bloccato
        cardlist.get(0).setActivePower(false);
        assertEquals(5, cardlist.get(0).getStandardRoutine().get(0).availableWithGod(cardlist.get(0).getWorker2()).size());
        cardlist.get(0).setActivePower(true);
        assertEquals(1, cardlist.get(0).getCardRoutine().get(0).availableWithGod(cardlist.get(0).getWorker2()).size());
        cardlist.get(0).applyEffect(0, cardlist.get(0).getWorker1(), new Position(1,2));
        assertEquals(false,cardlist.get(0).checkWin(0,new Position(1,2)));
        cardlist.get(0).applyEffect(1, cardlist.get(0).getWorker1(), new Position(2,2));

        // Athena -> non muove up -> non vince
        cardlist.get(1).applyEffect(0, cardlist.get(1).getWorker1(), new Position(2,1));
        assertEquals(false,cardlist.get(1).checkWin(0,new Position(2,1)));
        cardlist.get(1).applyEffect(1, cardlist.get(1).getWorker1(), new Position(3,1));

        // Demeter -> non riesce a vincere
        cardlist.get(2).setActivePower(true);
        cardlist.get(2).applyEffect(0, cardlist.get(2).getWorker2(), new Position(4,1));
        assertEquals(false,cardlist.get(1).checkWin(0,new Position(4,1)));
        cardlist.get(2).applyEffect(1, cardlist.get(2).getWorker2(), new Position(3,1));
        cardlist.get(2).applyEffect(2, cardlist.get(2).getWorker2(), new Position(3,2));

        // Apollo -> vince
        cardlist.get(0).setActivePower(false);
        cardlist.get(0).applyEffect(0, cardlist.get(0).getWorker2(), new Position(2,3));
        assertEquals(true, cardlist.get(0).checkWin(0,new Position(2,3)));

        // map situation
        assertEquals(1, map[1][1].getHeight());
        assertEquals(1, map[1][2].getHeight());
        assertEquals(2, map[1][3].getHeight());
        assertEquals(0, map[1][4].getHeight());
        assertEquals(0, map[2][1].getHeight());
        assertEquals(4, map[2][2].getHeight());
        assertEquals(3, map[2][3].getHeight());
        assertEquals(2, map[2][4].getHeight());
        assertEquals(4, map[3][1].getHeight());
        assertEquals(2, map[3][2].getHeight());
        assertEquals(0, map[3][3].getHeight());
        assertEquals(0, map[3][4].getHeight());

        // workers
        assertEquals(0, map[1][2].getWorkerID());
        assertEquals(1, map[2][3].getWorkerID());
        assertEquals(2, map[2][1].getWorkerID());
        assertEquals(3, map[3][4].getWorkerID());
        assertEquals(4, map[3][3].getWorkerID());
        assertEquals(5, map[4][1].getWorkerID());
    }
}
