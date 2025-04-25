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
	void testTwoForHisHeels_True() {
		Scorer s = new Scorer();
		Card startCard = Card.getCard(Rank.JACK, Suit.HEARTS);
		Card startCard2 = Card.getCard(Rank.JACK, Suit.DIAMONDS);
		Card startCard3 = Card.getCard(Rank.JACK, Suit.CLUBS);
		Card startCard4 = Card.getCard(Rank.JACK, Suit.SPADES);
		
		assertEquals(s.twoForHisHeels(startCard),2);
		assertEquals(s.twoForHisHeels(startCard2),2);
		assertEquals(s.twoForHisHeels(startCard3),2);
		assertEquals(s.twoForHisHeels(startCard4),2);
	}
	
	@Test
	void testTwoForHisHeels_False() {
		Scorer s = new Scorer();
		Card startCard = Card.getCard(Rank.EIGHT, Suit.HEARTS);
		assertEquals(s.twoForHisHeels(startCard),0);
	}
	
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
	
	@Test

	void testScoreHand() {
		Scorer s = new Scorer();
		
		ArrayList<Card> hand = new ArrayList<Card>();

		Card startCard = Card.getCard(Rank.JACK, Suit.HEARTS);
		
		hand.add(Card.getCard(Rank.FIVE, Suit.HEARTS));
		hand.add(Card.getCard(Rank.JACK, Suit.HEARTS));
		hand.add(Card.getCard(Rank.SEVEN, Suit.CLUBS));
		hand.add(Card.getCard(Rank.EIGHT, Suit.SPADES));
		hand.add(Card.getCard(Rank.NINE, Suit.SPADES));
		
		
		assertEquals(s.scoreHand(hand, startCard),9);
	}

	void testRun_FaceCards() {

	
		Scorer s = new Scorer();
		
		ArrayList<Card> hand = new ArrayList<Card>();

		hand.add(Card.getCard(Rank.ACE, Suit.HEARTS));
		hand.add(Card.getCard(Rank.TEN, Suit.SPADES));
		hand.add(Card.getCard(Rank.JACK, Suit.DIAMONDS));
		hand.add(Card.getCard(Rank.QUEEN, Suit.SPADES));
		hand.add(Card.getCard(Rank.KING, Suit.CLUBS));
		
		assertEquals(s.run(hand), 4);

	}
	
	@Test
	void testFlush_4() {
		Scorer s = new Scorer();
		
		ArrayList<Card> hand = new ArrayList<Card>();
		
		hand.add(Card.getCard(Rank.ACE, Suit.HEARTS));
		hand.add(Card.getCard(Rank.TEN, Suit.HEARTS));
		hand.add(Card.getCard(Rank.JACK, Suit.HEARTS));
		hand.add(Card.getCard(Rank.QUEEN, Suit.HEARTS));
		
		assertEquals(s.flush(Card.getCard(Rank.QUEEN, Suit.SPADES), hand), 4);
	}
	
	@Test
	void testFlush_5() {
		Scorer s = new Scorer();
		
		ArrayList<Card> hand = new ArrayList<Card>();
		
		hand.add(Card.getCard(Rank.ACE, Suit.HEARTS));
		hand.add(Card.getCard(Rank.TEN, Suit.HEARTS));
		hand.add(Card.getCard(Rank.JACK, Suit.HEARTS));
		hand.add(Card.getCard(Rank.QUEEN, Suit.HEARTS));
		
		assertEquals(s.flush(Card.getCard(Rank.FOUR, Suit.HEARTS), hand), 5);
	}
	
	@Test
	void testFlush_NoFlush() {
		Scorer s = new Scorer();
		
		ArrayList<Card> hand = new ArrayList<Card>();
		
		hand.add(Card.getCard(Rank.ACE, Suit.HEARTS));
		hand.add(Card.getCard(Rank.TEN, Suit.SPADES));
		hand.add(Card.getCard(Rank.JACK, Suit.HEARTS));
		hand.add(Card.getCard(Rank.QUEEN, Suit.HEARTS));
		
		assertEquals(s.flush(Card.getCard(Rank.FOUR, Suit.HEARTS), hand), 0);
	}
	
	@Test
	void testPlayStackSum_Fifteen() {
		Scorer s = new Scorer();
		
		ArrayList<Card> playStack = new ArrayList<Card>();
		
		playStack.add(Card.getCard(Rank.FIVE, Suit.HEARTS));
		playStack.add(Card.getCard(Rank.TEN, Suit.SPADES));
		
		assertEquals(s.playstack_sum(playStack), 15);
	}
	
	@Test
	void testPlayStackPair_NoPair() {
		Scorer s = new Scorer();
		
		ArrayList<Card> playStack = new ArrayList<Card>();
		
		playStack.add(Card.getCard(Rank.FIVE, Suit.HEARTS));
		playStack.add(Card.getCard(Rank.SIX, Suit.HEARTS));
		
		assertEquals(s.playstack_pair(playStack), 0);
	}
	
	@Test
	void testPlayStackPair_Pair() {
		Scorer s = new Scorer();
		
		ArrayList<Card> playStack = new ArrayList<Card>();
		
		playStack.add(Card.getCard(Rank.FIVE, Suit.HEARTS));
		playStack.add(Card.getCard(Rank.FIVE, Suit.SPADES));
		
		assertEquals(s.playstack_pair(playStack), 2);
	}
	
	@Test
	void testPlayStackPairRoyal_LessThan3() {
		Scorer s = new Scorer();
		
		ArrayList<Card> playStack = new ArrayList<Card>();
		
		playStack.add(Card.getCard(Rank.FIVE, Suit.HEARTS));
		playStack.add(Card.getCard(Rank.FIVE, Suit.SPADES));
		
		assertEquals(s.playstack_pairRoyal(playStack), 0);
	}
	
	@Test
	void testPlayStackPairRoyal_NoPair() {
		Scorer s = new Scorer();
		
		ArrayList<Card> playStack = new ArrayList<Card>();
		
		playStack.add(Card.getCard(Rank.FIVE, Suit.HEARTS));
		playStack.add(Card.getCard(Rank.FIVE, Suit.SPADES));
		playStack.add(Card.getCard(Rank.SIX, Suit.SPADES));
		
		assertEquals(s.playstack_pairRoyal(playStack), 0);
	}
	
	@Test
	void testPlayStackPairRoyal_Pair() {
		Scorer s = new Scorer();
		
		ArrayList<Card> playStack = new ArrayList<Card>();
		
		playStack.add(Card.getCard(Rank.FIVE, Suit.HEARTS));
		playStack.add(Card.getCard(Rank.FIVE, Suit.SPADES));
		playStack.add(Card.getCard(Rank.FIVE, Suit.CLUBS));
		
		assertEquals(s.playstack_pairRoyal(playStack), 6);
	}
	
	@Test
	void testPlayStackPairDoubleRoyal_LessThan4() {
		Scorer s = new Scorer();
		
		ArrayList<Card> playStack = new ArrayList<Card>();
		
		playStack.add(Card.getCard(Rank.FIVE, Suit.HEARTS));
		playStack.add(Card.getCard(Rank.FIVE, Suit.SPADES));
		playStack.add(Card.getCard(Rank.FIVE, Suit.CLUBS));
		
		assertEquals(s.playstack_pairDoubleRoyal(playStack), 0);
	}
	
	@Test
	void testPlayStackPairDoubleRoyal_NoPair() {
		Scorer s = new Scorer();
		
		ArrayList<Card> playStack = new ArrayList<Card>();
		
		playStack.add(Card.getCard(Rank.FIVE, Suit.HEARTS));
		playStack.add(Card.getCard(Rank.FIVE, Suit.SPADES));
		playStack.add(Card.getCard(Rank.FIVE, Suit.CLUBS));
		playStack.add(Card.getCard(Rank.FOUR, Suit.CLUBS));
		
		assertEquals(s.playstack_pairDoubleRoyal(playStack), 0);
	}
	
	@Test
	void testPlayStackPairDoubleRoyal_Pair() {
		Scorer s = new Scorer();
		
		ArrayList<Card> playStack = new ArrayList<Card>();
		
		playStack.add(Card.getCard(Rank.FIVE, Suit.HEARTS));
		playStack.add(Card.getCard(Rank.FIVE, Suit.SPADES));
		playStack.add(Card.getCard(Rank.FIVE, Suit.CLUBS));
		playStack.add(Card.getCard(Rank.FIVE, Suit.DIAMONDS));
		
		assertEquals(s.playstack_pairDoubleRoyal(playStack), 12);
	}
	
	@Test
	void testPlayStackRun_lessThan3() {
		Scorer s = new Scorer();
		
		ArrayList<Card> playStack = new ArrayList<Card>();
		
		playStack.add(Card.getCard(Rank.FIVE, Suit.HEARTS));
		playStack.add(Card.getCard(Rank.SIX, Suit.SPADES));
		
		assertEquals(s.playstack_Run(playStack), 0);
	}
	
	@Test
	void testPlayStackRun_noRun() {
		Scorer s = new Scorer();
		
		ArrayList<Card> playStack = new ArrayList<Card>();
		
		playStack.add(Card.getCard(Rank.FIVE, Suit.HEARTS));
		playStack.add(Card.getCard(Rank.SIX, Suit.SPADES));
		playStack.add(Card.getCard(Rank.EIGHT, Suit.SPADES));
		
		assertEquals(s.playstack_Run(playStack), 0);
	}
	
	@Test
	void testPlayStackRun_noRun2() {
		Scorer s = new Scorer();
		
		ArrayList<Card> playStack = new ArrayList<Card>();
		
		playStack.add(Card.getCard(Rank.TEN, Suit.HEARTS));
		playStack.add(Card.getCard(Rank.JACK, Suit.SPADES));
		playStack.add(Card.getCard(Rank.QUEEN, Suit.SPADES));
		playStack.add(Card.getCard(Rank.FOUR, Suit.SPADES));
		
		assertEquals(s.playstack_Run(playStack), 0);
	}
	
	@Test
	void testPlayStackRun_Run() {
		Scorer s = new Scorer();
		
		ArrayList<Card> playStack = new ArrayList<Card>();
		
		playStack.add(Card.getCard(Rank.TEN, Suit.HEARTS));
		playStack.add(Card.getCard(Rank.JACK, Suit.SPADES));
		playStack.add(Card.getCard(Rank.QUEEN, Suit.SPADES));
		
		assertEquals(s.playstack_Run(playStack), 3);
	}
	
	@Test
	void testPlayStackRun_Run2() {
		Scorer s = new Scorer();
		
		ArrayList<Card> playStack = new ArrayList<Card>();
		
		playStack.add(Card.getCard(Rank.TEN, Suit.HEARTS));
		playStack.add(Card.getCard(Rank.JACK, Suit.SPADES));
		playStack.add(Card.getCard(Rank.QUEEN, Suit.SPADES));
		playStack.add(Card.getCard(Rank.KING, Suit.SPADES));
		
		assertEquals(s.playstack_Run(playStack), 4);
	}

}
