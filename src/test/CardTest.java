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
	
	@Test
	void testEquals_Equal() {
		Card c1 = new Card(Rank.ACE, Suit.CLUBS);
		Card c2 = new Card(Rank.ACE, Suit.CLUBS);
		
		assertEquals(c1, c2);
	}
	
	@Test
	void testEquals_SameObj() {
		Card c1 = new Card(Rank.ACE, Suit.CLUBS);
		
		assertEquals(c1, c1);
	}
	
	@Test
	void testEquals_NotEqualRank() {
		Card c1 = new Card(Rank.ACE, Suit.CLUBS);
		Card c2 = new Card(Rank.TWO, Suit.CLUBS);
		
		assertNotEquals(c1, c2);
	}
	
	@Test
	void testEquals_NotEqualSuit() {
		Card c1 = new Card(Rank.ACE, Suit.CLUBS);
		Card c2 = new Card(Rank.ACE, Suit.HEARTS);
		
		assertNotEquals(c1, c2);
	}
	
	@Test
	void testEquals_NotEqualNull() {
		Card c1 = new Card(Rank.ACE, Suit.CLUBS);
		
		assertNotEquals(c1, null);
	}
	
	@Test
	void testEquals_NotEqualDifferentType() {
		Card c1 = new Card(Rank.ACE, Suit.CLUBS);
		
		assertNotEquals(c1, 1);
	}
	
	@Test
	void testHashCode() {
		Card c1 = new Card(Rank.ACE, Suit.CLUBS);
		Card c2 = new Card(Rank.ACE, Suit.CLUBS);
		
		assertEquals(c1.hashCode(), c2.hashCode());
	}

}
