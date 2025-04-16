package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import main.model.Card;
import main.model.Rank;
import main.model.Scorer;
import main.model.Suit;

class ScorerTest {

	@Test
	void testOneForHisKnob_True() {
		Scorer s = new Scorer();
		
		ArrayList<Card> hand = new ArrayList<Card>();
		
		hand.add(Card.getCard(Rank.JACK, Suit.HEARTS));
		hand.add(Card.getCard(Rank.ACE, Suit.HEARTS));
		hand.add(Card.getCard(Rank.FIVE, Suit.HEARTS));
		hand.add(Card.getCard(Rank.SIX, Suit.SPADES));
		hand.add(Card.getCard(Rank.TWO, Suit.HEARTS));
		
		Card startCard = Card.getCard(Rank.THREE, Suit.HEARTS);
		
		assertEquals(s.oneForHisKnob(hand, startCard), 1);
	}
	
	@Test
	void testOneForHisKnob_False() {
		Scorer s = new Scorer();
		
		ArrayList<Card> hand = new ArrayList<Card>();
		
		hand.add(Card.getCard(Rank.JACK, Suit.SPADES));
		hand.add(Card.getCard(Rank.ACE, Suit.HEARTS));
		hand.add(Card.getCard(Rank.FIVE, Suit.HEARTS));
		hand.add(Card.getCard(Rank.SIX, Suit.SPADES));
		hand.add(Card.getCard(Rank.TWO, Suit.HEARTS));
		
		Card startCard = Card.getCard(Rank.THREE, Suit.HEARTS);
		
		assertEquals(s.oneForHisKnob(hand, startCard), 0);
	}
	
	@Test
	void testFifteen_Zero() {
		Scorer s = new Scorer();
		
		ArrayList<Card> hand = new ArrayList<Card>();
		
		hand.add(Card.getCard(Rank.JACK, Suit.HEARTS));
		hand.add(Card.getCard(Rank.JACK, Suit.SPADES));
		hand.add(Card.getCard(Rank.JACK, Suit.DIAMONDS));
		hand.add(Card.getCard(Rank.KING, Suit.SPADES));
		hand.add(Card.getCard(Rank.KING, Suit.HEARTS));
		
		assertEquals(s.countFifteens(hand, 0, 0), 0);
	}
	
	@Test
	void testFifteen_One() {
		Scorer s = new Scorer();
		
		ArrayList<Card> hand = new ArrayList<Card>();
		
		hand.add(Card.getCard(Rank.JACK, Suit.HEARTS));
		hand.add(Card.getCard(Rank.SEVEN, Suit.SPADES));
		hand.add(Card.getCard(Rank.SEVEN, Suit.DIAMONDS));
		hand.add(Card.getCard(Rank.NINE, Suit.SPADES));
		hand.add(Card.getCard(Rank.FIVE, Suit.HEARTS));
		
		assertEquals(s.countFifteens(hand, 0, 0), 1);
	}
	
	@Test
	void testFifteen_Many() {
		Scorer s = new Scorer();
		
		ArrayList<Card> hand = new ArrayList<Card>();
		
		hand.add(Card.getCard(Rank.JACK, Suit.HEARTS));
		hand.add(Card.getCard(Rank.JACK, Suit.SPADES));
		hand.add(Card.getCard(Rank.JACK, Suit.DIAMONDS));
		hand.add(Card.getCard(Rank.KING, Suit.SPADES));
		hand.add(Card.getCard(Rank.FIVE, Suit.HEARTS));
		
		assertEquals(s.countFifteens(hand, 0, 0), 4);
	}
	
	@Test
	void testPairs_None() {
		Scorer s = new Scorer();
		
		ArrayList<Card> hand = new ArrayList<Card>();
		
		hand.add(Card.getCard(Rank.JACK, Suit.HEARTS));
		hand.add(Card.getCard(Rank.SIX, Suit.SPADES));
		hand.add(Card.getCard(Rank.EIGHT, Suit.CLUBS));
		hand.add(Card.getCard(Rank.NINE, Suit.SPADES));
		hand.add(Card.getCard(Rank.TEN, Suit.HEARTS));
		
		assertEquals(s.scoreCombinations(hand), 0);
	}
	
	@Test
	void testPairs_One() {
		Scorer s = new Scorer();
		
		ArrayList<Card> hand = new ArrayList<Card>();
		
		hand.add(Card.getCard(Rank.JACK, Suit.HEARTS));
		hand.add(Card.getCard(Rank.SIX, Suit.SPADES));
		hand.add(Card.getCard(Rank.SEVEN, Suit.DIAMONDS));
		hand.add(Card.getCard(Rank.NINE, Suit.SPADES));
		hand.add(Card.getCard(Rank.JACK, Suit.HEARTS));
		
		assertEquals(s.scoreCombinations(hand), 2);
	}
	
	@Test
	void testPairs_Two() {
		Scorer s = new Scorer();
		
		ArrayList<Card> hand = new ArrayList<Card>();
		
		hand.add(Card.getCard(Rank.JACK, Suit.HEARTS));
		hand.add(Card.getCard(Rank.SIX, Suit.SPADES));
		hand.add(Card.getCard(Rank.SEVEN, Suit.DIAMONDS));
		hand.add(Card.getCard(Rank.JACK, Suit.SPADES));
		hand.add(Card.getCard(Rank.SIX, Suit.HEARTS));
		
		assertEquals(s.scoreCombinations(hand), 4);
	}
	
	@Test
	void testThreeOfAKind() {
		Scorer s = new Scorer();
		
		ArrayList<Card> hand = new ArrayList<Card>();
		
		hand.add(Card.getCard(Rank.JACK, Suit.HEARTS));
		hand.add(Card.getCard(Rank.SIX, Suit.SPADES));
		hand.add(Card.getCard(Rank.SEVEN, Suit.DIAMONDS));
		hand.add(Card.getCard(Rank.JACK, Suit.SPADES));
		hand.add(Card.getCard(Rank.JACK, Suit.CLUBS));
		
		assertEquals(s.scoreCombinations(hand), 6);
	}
	
	@Test
	void testFourOfAKind() {
		Scorer s = new Scorer();
		
		ArrayList<Card> hand = new ArrayList<Card>();
		
		hand.add(Card.getCard(Rank.JACK, Suit.HEARTS));
		hand.add(Card.getCard(Rank.SIX, Suit.SPADES));
		hand.add(Card.getCard(Rank.JACK, Suit.DIAMONDS));
		hand.add(Card.getCard(Rank.JACK, Suit.SPADES));
		hand.add(Card.getCard(Rank.JACK, Suit.CLUBS));
		
		assertEquals(s.scoreCombinations(hand), 12);
	}
	
	@Test
	void testNoCombinations() {
		Scorer s = new Scorer();
		
		ArrayList<Card> hand = new ArrayList<Card>();
		
		hand.add(Card.getCard(Rank.JACK, Suit.HEARTS));
		hand.add(Card.getCard(Rank.SIX, Suit.SPADES));
		hand.add(Card.getCard(Rank.SEVEN, Suit.DIAMONDS));
		hand.add(Card.getCard(Rank.EIGHT, Suit.SPADES));
		hand.add(Card.getCard(Rank.NINE, Suit.CLUBS));
		
		assertEquals(s.scoreCombinations(hand), 0);
	}
	
	@Test
	void testRun_3() {
		Scorer s = new Scorer();
		
		ArrayList<Card> hand = new ArrayList<Card>();
		
		hand.add(Card.getCard(Rank.FIVE, Suit.HEARTS));
		hand.add(Card.getCard(Rank.TWO, Suit.SPADES));
		hand.add(Card.getCard(Rank.THREE, Suit.DIAMONDS));
		hand.add(Card.getCard(Rank.FOUR, Suit.SPADES));
		hand.add(Card.getCard(Rank.NINE, Suit.CLUBS));
		
		assertEquals(s.run(hand), 3);
	}
	
	@Test
	void testRun_4() {
		Scorer s = new Scorer();
		
		ArrayList<Card> hand = new ArrayList<Card>();
		
		hand.add(Card.getCard(Rank.ACE, Suit.HEARTS));
		hand.add(Card.getCard(Rank.TWO, Suit.SPADES));
		hand.add(Card.getCard(Rank.THREE, Suit.DIAMONDS));
		hand.add(Card.getCard(Rank.FOUR, Suit.SPADES));
		hand.add(Card.getCard(Rank.NINE, Suit.CLUBS));
		
		assertEquals(s.run(hand), 4);
	}
	
	@Test
	void testRun_5() {
		Scorer s = new Scorer();
		
		ArrayList<Card> hand = new ArrayList<Card>();
		
		hand.add(Card.getCard(Rank.ACE, Suit.HEARTS));
		hand.add(Card.getCard(Rank.TWO, Suit.SPADES));
		hand.add(Card.getCard(Rank.THREE, Suit.DIAMONDS));
		hand.add(Card.getCard(Rank.FOUR, Suit.SPADES));
		hand.add(Card.getCard(Rank.FIVE, Suit.CLUBS));
		
		assertEquals(s.run(hand), 5);
	}
	
	@Test
	void testRun_NoRun() {
		Scorer s = new Scorer();
		
		ArrayList<Card> hand = new ArrayList<Card>();
		
		hand.add(Card.getCard(Rank.ACE, Suit.HEARTS));
		hand.add(Card.getCard(Rank.TWO, Suit.SPADES));
		hand.add(Card.getCard(Rank.SEVEN, Suit.DIAMONDS));
		hand.add(Card.getCard(Rank.FOUR, Suit.SPADES));
		hand.add(Card.getCard(Rank.FIVE, Suit.CLUBS));
		
		assertEquals(s.run(hand), 0);
	}
	
	

}
