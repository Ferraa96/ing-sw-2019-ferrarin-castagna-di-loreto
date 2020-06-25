package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Game.Board;
import it.polimi.ingsw.model.Game.Cell;
import it.polimi.ingsw.model.IO.IOHandler;
import it.polimi.ingsw.model.IO.SaveState;
import it.polimi.ingsw.model.Player.Card;
import it.polimi.ingsw.model.Game.Position;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class CardTest {

    Board board = new Board();
    Cell[][] map;
    IOHandler ioHandler = new IOHandler();
    List <Card> cardlist = new ArrayList<>();


    @Test
    public void checkParameters() {
        map = board.getMap();
        //set players
        for (int i = 0; i < 5; i++) {
            Card card = ioHandler.getCardList().get(i*2);
            card.setCard(board.getMap(), i, new SaveState());
            cardlist.add(card);
        }
        //extra
        assertEquals(false, cardlist.get(0).getDescription().isEmpty());
        for (int i = 0; i < 4; i++) {
            Card card = ioHandler.getCardList().get(i*2+1);
            card.setCard(board.getMap(), i, new SaveState());
            cardlist.add(card);
        }

        //set enemies
        for (Card curr: cardlist) {
            curr.setEnemies(cardlist);
        }

        //names
        assertEquals("Apollo", cardlist.get(0).getName());
        assertEquals("Athena", cardlist.get(1).getName());
        assertEquals("Demeter", cardlist.get(2).getName());
        assertEquals("Minotaur", cardlist.get(3).getName());
        assertEquals("Prometheus", cardlist.get(4).getName());

        //firstposition
        assertEquals(25,cardlist.get(0).availableFirstPositioning().size());
        assertEquals(25,cardlist.get(3).availableFirstPositioning().size());
        cardlist.get(0).firstPositioning(new Position(2,2), new Position(3,2));
        assertEquals(23,cardlist.get(0).availableFirstPositioning().size());
        assertEquals(23,cardlist.get(3).availableFirstPositioning().size());
        assertEquals(3,cardlist.get(0).getWorker2().getPosition().getRow());
        assertEquals(2,cardlist.get(0).getWorker2().getPosition().getColumn());

        //routine
        assertEquals(2,cardlist.get(0).getStandardRoutine().size());
        assertEquals(2,cardlist.get(0).getCardRoutine().size());
        assertEquals(2,cardlist.get(4).getStandardRoutine().size());
        assertEquals(3,cardlist.get(4).getCardRoutine().size());
        assertEquals(2,cardlist.get(1).getCardRoutine().size());

        //active card
        assertEquals(false, cardlist.get(0).checkCardActivation(cardlist.get(0).getWorker2()));
        assertEquals(false, cardlist.get(1).checkCardActivation(cardlist.get(0).getWorker2()));
        assertEquals(true, cardlist.get(2).checkCardActivation(cardlist.get(0).getWorker2()));


        assertEquals(7, cardlist.get(0).availablePositions(0,cardlist.get(0).getWorker2()).size());
        cardlist.get(0).setActivePower(true);
        assertEquals(0, cardlist.get(0).availablePositions(0,cardlist.get(0).getWorker2()).size());

        //real action simple
        cardlist.get(0).applyEffect(0,cardlist.get(0).getWorker1(),new Position(1,1));
        cardlist.get(0).applyEffect(1,cardlist.get(0).getWorker1(),new Position(2,2));
        assertEquals(1, cardlist.get(0).getWorker1().getPosition().getRow());
        assertEquals(0,map[1][1].getWorkerID());
        assertEquals(-1,map[2][2].getWorkerID());
        assertEquals(1,map[2][2].getHeight());

        //double move
        cardlist.get(1).firstPositioning(new Position(2,1), new Position(0, 0));
        cardlist.get(0).setActivePower(true);
        cardlist.get(0).applyEffect(0,cardlist.get(0).getWorker1(),new Position(0,0));
        assertEquals(0, cardlist.get(0).getWorker1().getPosition().getRow());
        assertEquals(0, cardlist.get(0).getWorker1().getPosition().getColumn());
        assertEquals(1, cardlist.get(1).getWorker2().getPosition().getRow());
        assertEquals(1, cardlist.get(1).getWorker2().getPosition().getColumn());
    }
}
