package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.model.Card;
import main.model.Rank;
import main.model.Suit;

class CardTest {

	@Test
	void testGetRank() {
		Card c = new Card(Rank.ACE, Suit.CLUBS);
		assertEquals(c.getRank(), Rank.ACE);
	}
	
	@Test
	void testGetSuit() {
		Card c = new Card(Rank.ACE, Suit.CLUBS);
		assertEquals(c.getSuit(), Suit.CLUBS);
	}
	
	@Test
	void testGetValue() {
		Card c = new Card(Rank.ACE, Suit.CLUBS);
		assertEquals(c.getValue(), 1);
	}
	
	@Test
	void testCompareTo_Less() {
		Card c1 = new Card(Rank.ACE, Suit.CLUBS);
		Card c2 = new Card(Rank.KING, Suit.CLUBS);
		
		assertEquals(c1.compareTo(c2), -1);
	}
	
	@Test
	void testCompareTo_Equal() {
		Card c1 = new Card(Rank.ACE, Suit.CLUBS);
		Card c2 = new Card(Rank.ACE, Suit.CLUBS);
		
		assertEquals(c1.compareTo(c2), 0);
	}
	
	@Test
	void testCompareTo_Greater() {
		Card c1 = new Card(Rank.ACE, Suit.CLUBS);
		Card c2 = new Card(Rank.KING, Suit.CLUBS);
		
		assertEquals(c2.compareTo(c1), 1);
	}
	
	

}
