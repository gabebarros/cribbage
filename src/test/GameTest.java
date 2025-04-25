package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import main.model.Card;
import main.model.Game;
import main.model.Rank;
import main.model.Suit;
import main.model.GameObserver;


public class GameTest {
	@Test
	public void testDealHands() {
		Game g1 = new Game("a", "b");
		
		assertEquals(g1.getPlayer1().getName(), "a");
		assertEquals(g1.getPlayer2().getName(), "b");
		
		g1.dealHands();
		
		assertEquals(g1.getPlayer1().getHand().size(), 6);
		assertEquals(g1.getPlayer2().getHand().size(), 6);
		
		ArrayList<Card> h1 = g1.getPlayer1().getHand();
		ArrayList<Card> h2 = g1.getPlayer2().getHand();
		
		Collections.sort(h1);
		Collections.sort(h2);
		
		for(int i = 0; i < 6; i++) {
			assertEquals(g1.getPlayer1().getHand().get(i), h1.get(i));
			assertEquals(g1.getPlayer2().getHand().get(i), h2.get(i));
		}
	}
	
	@Test
	public void testAddToCrib() {
		Game g1 = new Game("a", "b");
		
		ArrayList<Card> oCrib = g1.getCrib();
		
		oCrib.add(Card.getCard(Rank.ACE, Suit.SPADES));
		g1.addToCrib(Card.getCard(Rank.ACE, Suit.SPADES));
		
		assertTrue(g1.getCrib().contains(Card.getCard(Rank.ACE, Suit.SPADES)));
		
		assertEquals(oCrib.get(0), g1.getCrib().get(0));
	}
	
	@Test
	public void testPlayCard() {
		Game g1 = new Game("a", "b");
		
		ArrayList<Card> h1 = new ArrayList<Card>();
		ArrayList<Card> h2 = new ArrayList<Card>();
		
		h1.add(Card.getCard(Rank.ACE, Suit.CLUBS));
		h1.add(Card.getCard(Rank.ACE, Suit.SPADES));
		
		h2.add(Card.getCard(Rank.ACE, Suit.HEARTS));
		h2.add(Card.getCard(Rank.ACE, Suit.DIAMONDS));
		
		ArrayList<Card> ps = new ArrayList<Card>();
		
		ps.add(Card.getCard(Rank.JACK, Suit.HEARTS));
		ps.add(Card.getCard(Rank.QUEEN, Suit.HEARTS));
		ps.add(Card.getCard(Rank.KING, Suit.HEARTS));
		ps.add(Card.getCard(Rank.ACE, Suit.HEARTS));
		
		g1.setPlayStack(ps);

		g1.setPlayer1OriginalHand(h1);
		g1.getPlayer1().setHand(h1);
		
		g1.setPlayer2OriginalHand(h2);
		g1.getPlayer2().setHand(h2);

		
		g1.playCard(g1.getPlayer1().playCard(0), g1.getPlayer1());
		
		assertEquals(g1.getPlayStack().size(), 1);
		
		ps.add(Card.getCard(Rank.FIVE, Suit.SPADES));
		
		g1.setPlayStack(ps);
		
		
		g1.playCard(g1.getPlayer1().playCard(0), g1.getPlayer1());
		assertEquals(g1.getPlayStack().size(), 3);
		assertEquals(g1.getPlayer1().getScore(), 0);
		assertEquals(g1.getPlayer2().getScore(), 0);
	}
	
	@Test
	public void testPlayCardPlaystack31() {
		Game g1 = new Game("a", "b");
		
		ArrayList<Card> h1 = new ArrayList<Card>();
		ArrayList<Card> h2 = new ArrayList<Card>();
		
		h1.add(Card.getCard(Rank.ACE, Suit.CLUBS));
		h1.add(Card.getCard(Rank.ACE, Suit.SPADES));
		
		h2.add(Card.getCard(Rank.ACE, Suit.HEARTS));
		h2.add(Card.getCard(Rank.ACE, Suit.DIAMONDS));
		
		g1.setPlayer1OriginalHand(h1);
		g1.getPlayer1().setHand(h1);
		
		g1.setPlayer2OriginalHand(h2);
		g1.getPlayer2().setHand(h2);
		
		ArrayList<Card> ps = new ArrayList<Card>();
		
		ps.add(Card.getCard(Rank.JACK, Suit.HEARTS));
		ps.add(Card.getCard(Rank.QUEEN, Suit.HEARTS));
		ps.add(Card.getCard(Rank.KING, Suit.HEARTS));
		ps.add(Card.getCard(Rank.THREE, Suit.HEARTS));
		
		g1.setPlayStack(ps);

		g1.playCard(g1.getPlayer1().playCard(0), g1.getPlayer1());
		
		assertEquals(ps.size(), 1);
		assertEquals(g1.getPlayer1().getScore(), 0);
		assertEquals(g1.getPlayer2().getScore(), 1);
		
		ps.add(Card.getCard(Rank.JACK, Suit.HEARTS));
		ps.add(Card.getCard(Rank.QUEEN, Suit.HEARTS));
		ps.add(Card.getCard(Rank.KING, Suit.HEARTS));
		ps.add(Card.getCard(Rank.THREE, Suit.HEARTS));
		
		g1.setPlayStack(ps);
		
		g1.playCard(g1.getPlayer2().playCard(0), g1.getPlayer2());
		assertEquals(ps.size(), 1);
		assertEquals(g1.getPlayer1().getScore(), 1);
		assertEquals(g1.getPlayer2().getScore(), 1);
	}
	
	@Test
	public void testResetRound() {
		Game g1 = new Game("a", "b");
		
		ArrayList<Card> h1 = new ArrayList<Card>();
		ArrayList<Card> h2 = new ArrayList<Card>();
		
		h1.add(Card.getCard(Rank.ACE, Suit.CLUBS));
		h1.add(Card.getCard(Rank.ACE, Suit.SPADES));
		
		h2.add(Card.getCard(Rank.ACE, Suit.HEARTS));
		h2.add(Card.getCard(Rank.ACE, Suit.DIAMONDS));
		
		g1.setPlayer1OriginalHand(h1);
		g1.getPlayer1().setHand(h1);
		
		g1.setPlayer2OriginalHand(h2);
		g1.getPlayer2().setHand(h2);
		
		ArrayList<Card> ps = new ArrayList<Card>();
		
		ps.add(Card.getCard(Rank.JACK, Suit.HEARTS));
		ps.add(Card.getCard(Rank.QUEEN, Suit.HEARTS));
		ps.add(Card.getCard(Rank.KING, Suit.HEARTS));
		ps.add(Card.getCard(Rank.THREE, Suit.HEARTS));
		
		g1.setPlayStack(ps);
		g1.playCard(g1.getPlayer1().playCard(0), g1.getPlayer1());
		g1.playCard(g1.getPlayer2().playCard(0), g1.getPlayer2());
		
		g1.resetForNewRound();
		
		assertEquals(g1.getCrib().size(), 0);
		assertEquals(g1.getPlayStack().size(), 0);
		assertEquals(g1.getStartCard(), null);
		assertEquals(g1.getPlayer1().getScore(), 0);
		assertEquals(g1.getPlayer2().getScore(), 3);
	}
	
	@Test
	public void testResetGame() {
		Game g1 = new Game("a", "b");
		
		ArrayList<Card> h1 = new ArrayList<Card>();
		ArrayList<Card> h2 = new ArrayList<Card>();
		
		h1.add(Card.getCard(Rank.ACE, Suit.CLUBS));
		h1.add(Card.getCard(Rank.ACE, Suit.SPADES));
		
		h2.add(Card.getCard(Rank.ACE, Suit.HEARTS));
		h2.add(Card.getCard(Rank.ACE, Suit.DIAMONDS));
		
		g1.setPlayer1OriginalHand(h1);
		g1.getPlayer1().setHand(h1);
		
		g1.setPlayer2OriginalHand(h2);
		g1.getPlayer2().setHand(h2);
		
		ArrayList<Card> ps = new ArrayList<Card>();
		
		ps.add(Card.getCard(Rank.JACK, Suit.HEARTS));
		ps.add(Card.getCard(Rank.QUEEN, Suit.HEARTS));
		ps.add(Card.getCard(Rank.KING, Suit.HEARTS));
		ps.add(Card.getCard(Rank.THREE, Suit.HEARTS));
		
		g1.setPlayStack(ps);
		g1.playCard(g1.getPlayer1().playCard(0), g1.getPlayer1());
		g1.playCard(g1.getPlayer2().playCard(0), g1.getPlayer2());
		
		g1.resetForNewGame();
		
		assertEquals(g1.getCrib().size(), 0);
		assertEquals(g1.getPlayStack().size(), 0);
		assertEquals(g1.getStartCard(), null);
		assertEquals(g1.getPlayer1().getScore(), 0);
		assertEquals(g1.getPlayer2().getScore(), 0);
		
	}
	
	@Test
	public void testDealer() {
		Game g1 = new Game("a", "b");
		
		g1.setDealer(g1.getPlayer1());
		assertEquals(g1.getDealer(), g1.getPlayer1());
		
		g1.setDealer(g1.getPlayer2());
		assertEquals(g1.getDealer(), g1.getPlayer2());
	}
	
	@Test
	public void testWinning() {
		Game g1 = new Game("a", "b");
		assertEquals(g1.getPlayer1Wins(), 0);
		assertEquals(g1.getPlayer2Wins(), 0);
		
		g1.incrementPlayer1Wins();
		g1.incrementPlayer1Wins();
		g1.incrementPlayer1Wins();

		g1.incrementPlayer2Wins();
		
		assertEquals(g1.getPlayer1Wins(), 3);
		assertEquals(g1.getPlayer2Wins(), 1);
	}
	
	@Test
	public void testOriginalHands() {
		Game g1 = new Game("a", "b");
		
		ArrayList<Card> h1 = new ArrayList<Card>();
		ArrayList<Card> h2 = new ArrayList<Card>();
		
		h1.add(Card.getCard(Rank.ACE, Suit.CLUBS));
		h1.add(Card.getCard(Rank.ACE, Suit.SPADES));
		
		h2.add(Card.getCard(Rank.ACE, Suit.HEARTS));
		h2.add(Card.getCard(Rank.ACE, Suit.DIAMONDS));
		
		g1.setPlayer1OriginalHand(h1);
		g1.getPlayer1().setHand(h1);
		
		g1.setPlayer2OriginalHand(h2);
		g1.getPlayer2().setHand(h2);
		
		ArrayList<Card> ps = new ArrayList<Card>();
		
		ps.add(Card.getCard(Rank.JACK, Suit.HEARTS));
		ps.add(Card.getCard(Rank.QUEEN, Suit.HEARTS));
		ps.add(Card.getCard(Rank.KING, Suit.HEARTS));
		ps.add(Card.getCard(Rank.THREE, Suit.HEARTS));
		
		g1.setPlayStack(ps);
		
		for(int i = 0; i < 2; i++) {
			assertEquals(h1.get(i), g1.getPlayer1OriginalHand().get(i));
			assertEquals(h2.get(i), g1.getPlayer2OriginalHand().get(i));
		}
		
	}
	
	public class TestObserver implements GameObserver {
		public TestObserver() {
		}
		
	    public void onCribUpdated() {};
	    public void onStarterCardDrawn(Card card) {};
	    public void onPlayStackUpdated(ArrayList<Card> playStack) {};
	    public void onScoreUpdated(int player1Score, int player2Score) {};
	}
	
	@Test
	public void testControllerFunction() {
		TestObserver to = new TestObserver();
		Game g1 = new Game("a", "b");
		
		assertEquals(g1.getObserverCount(), 0);
		
		g1.addObserver((main.model.GameObserver)to);
		g1.updateScore();
		
		assertEquals(g1.getObserverCount(), 1);
		
		g1.drawStarterCard();
		assertEquals(g1.getObserverCount(), 1);
		assertTrue(g1.getStartCard()!=null);
	
	}
	

}
