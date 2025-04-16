package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import main.model.Card;
import main.model.Deck;
import main.model.Rank;
import main.model.Suit;

class DeckTest {

	@Test
	void testShuffleAndPrint() {
		Deck d = new Deck();
		
		d.printDeck();
		
		d.shuffle();
		System.out.println();
		
		d.printDeck();
	}
	
	@Test
	void testDraw_Card(){
		Deck d = new Deck();
		Card c = d.draw();
		
		assertEquals(c, Card.getCard(Rank.ACE, Suit.CLUBS));
	}
	
	@Test
	void testGetCards(){
		Deck d = new Deck();
		ArrayList<Card> hand = d.getCards(6);
		
		assertEquals(hand.size(), 6);
	}

}
