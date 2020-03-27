package it.polimi.ingsw.model;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CardDeserializerTest {

    @Test
    public void getCardList() {
        CardDeserializer cardDeserializer = new CardDeserializer();
        List<Card> cardList = cardDeserializer.getCardList();
        assertEquals(9, cardList.size());
        assertEquals("Apollo", cardList.get(0).getName());
    }
}