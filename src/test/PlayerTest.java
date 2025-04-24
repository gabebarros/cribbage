package test;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.model.Card;
import main.model.Player;
import main.model.Rank;
import main.model.Suit;

import java.util.ArrayList;
import java.util.Collections;

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
	
	@Test
	public void testPlayCard() {
		Player p1 = new Player("p1");
		
		ArrayList<Card> hand = new ArrayList<Card>();
		
		hand.add(Card.getCard(Rank.ACE, Suit.CLUBS));
		hand.add(Card.getCard(Rank.TWO, Suit.CLUBS));
		hand.add(Card.getCard(Rank.THREE, Suit.CLUBS));
		hand.add(Card.getCard(Rank.FOUR, Suit.CLUBS));
		
		p1.setHand(hand);
		
		assertEquals(p1.playCard(0), Card.getCard(Rank.ACE, Suit.CLUBS));
		assertEquals(p1.playCard(0), Card.getCard(Rank.TWO, Suit.CLUBS));
		assertEquals(p1.playCard(0), Card.getCard(Rank.THREE, Suit.CLUBS));
		assertEquals(p1.playCard(0), Card.getCard(Rank.FOUR, Suit.CLUBS));
	}
	
	@Test
	public void testGetName() {
		Player p1 = new Player("p1");
		Player p2 = new Player("p2");
		Player p3 = new Player("p3");
		Player p4 = new Player("");

		
		assertEquals(p1.getName(), "p1");
		assertEquals(p2.getName(), "p2");
		assertEquals(p3.getName(), "p3");
		assertEquals(p4.getName(), "");
	}
	
	@Test
	public void testSortHand() {
		Player p1 = new Player("p1");
		
		ArrayList<Card> hand = new ArrayList<Card>();
		
		hand.add(Card.getCard(Rank.ACE, Suit.CLUBS));
		hand.add(Card.getCard(Rank.TWO, Suit.CLUBS));
		hand.add(Card.getCard(Rank.THREE, Suit.CLUBS));
		hand.add(Card.getCard(Rank.FOUR, Suit.CLUBS));
		hand.add(Card.getCard(Rank.ACE, Suit.DIAMONDS));
		
		p1.setHand(hand);
		p1.sortHand();
		
		Collections.sort(hand);
		
		for(int i = 0; i < 5; i++) {
			assertEquals(hand.get(i), p1.getHand().get(i));
		}
		
		
	
	}
}
