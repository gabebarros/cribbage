package test;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.model.Card;
import main.model.Player;
import main.model.Rank;
import main.model.Suit;

import java.util.ArrayList;

public class PlayerTest {
	@Test
	public void testAddScore() {
		Player p1 = new Player("p1");
		
		assertEquals(p1.getScore(), 0);
		
		p1.addScore(5);
		
		assertEquals(p1.getScore(), 5);
		
		Player p2 = new Player("p2");
		
		assertEquals(p2.getScore(), 0);
		assertEquals(p1.getScore(), 5);
		
		p2.addScore(2);
		
		assertEquals(p2.getScore(), 2);
		assertEquals(p1.getScore(), 5);
		
	}
	
	@Test
	public void testSetHand() {
		Player p1 = new Player("p1");
		
		ArrayList<Card> hand = new ArrayList<Card>();
		
		hand.add(Card.getCard(Rank.ACE, Suit.CLUBS));
		hand.add(Card.getCard(Rank.TWO, Suit.CLUBS));
		hand.add(Card.getCard(Rank.THREE, Suit.CLUBS));
		hand.add(Card.getCard(Rank.FOUR, Suit.CLUBS));
		
		p1.setHand(hand);
		
		assertEquals(p1.getHand().size(), 4);
		
		Player p2 = new Player("p2");

		assertEquals(p2.getHand().size(), 0);
	}
}
