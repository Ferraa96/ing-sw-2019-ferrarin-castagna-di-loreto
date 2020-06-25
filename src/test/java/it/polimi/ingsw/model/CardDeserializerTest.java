package it.polimi.ingsw.model;

import it.polimi.ingsw.model.IO.IOHandler;
import it.polimi.ingsw.model.IO.SaveState;
import it.polimi.ingsw.model.Player.Card;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CardDeserializerTest {

    @Test
    public void getCardList() {
        IOHandler cardDeserializer = new IOHandler();
        List<Card> cardList = cardDeserializer.getCardList();
        SaveState save1 = new SaveState();
        cardDeserializer.verifyFileExistance("prova");
        cardDeserializer.save(save1);
        cardDeserializer.getSaveState();
        cardDeserializer.deleteFile();
        assertEquals(9, cardList.size());
        assertEquals("Apollo", cardList.get(0).getName());
    }
}