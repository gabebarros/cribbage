package main.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Game {
    private final Player player1;
    private final Player player2;
    private Deck deck;
    private ArrayList<Card> crib;
    private ArrayList<Card> playStack;
    private Card startCard;
    private Scorer scorer;
    private ArrayList<Card> player1OriginalHand;
    private ArrayList<Card> player2OriginalHand;
    private Player dealer;
    
    private List<GameObserver> observers = new ArrayList<>();

    public Game(String p1Name, String p2Name) {
        this.player1 = new Player(p1Name);
        this.player2 = new Player(p2Name);
        this.deck = new Deck();
        this.crib = new ArrayList<>();
        this.playStack = new ArrayList<>();
        this.scorer = new Scorer();
    }

    public void dealHands() {
        deck.shuffle();
        player1.setHand(deck.getCards(6));
        player2.setHand(deck.getCards(6));
        player1.sortHand();
        player2.sortHand();
    }

    public Player getPlayer1() { 
    	return player1;
    }
    
    public Player getPlayer2() { 
    	return player2;
    }
    
    public ArrayList<Card> getCrib() {
    	return crib;
    }
    
    public Card getStartCard() {
    	return startCard; 
    }

    public void addToCrib(Card card) {
        crib.add(card);
        notifyCribUpdated();
    }

    public void drawStarterCard() {
        this.startCard = deck.draw();
        notifyStarterCardDrawn();
    }
    
    public void setPlayer1OriginalHand(ArrayList<Card> hand) {
        this.player1OriginalHand = hand;
    }

    public void setPlayer2OriginalHand(ArrayList<Card> hand) {
        this.player2OriginalHand = hand;
    }

    public ArrayList<Card> getPlayer1OriginalHand() {
        return player1OriginalHand;
    }

    public ArrayList<Card> getPlayer2OriginalHand() {
        return player2OriginalHand;
    }

    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    private void notifyCribUpdated() {
        for (GameObserver o : observers) {
            o.onCribUpdated();
        }
    }

    private void notifyStarterCardDrawn() {
        for (GameObserver o : observers) {
            o.onStarterCardDrawn(startCard);
        }
    }
    
    private void notifyPlayStackUpdated() {
        for (GameObserver o : observers) {
            o.onPlayStackUpdated(playStack);
        }
    }
    
    public void playCard(Card card, Player player) {
    	if (scorer.playstack_sum(playStack) + card.getValue() > 31 && scorer.playstack_sum(playStack) != 31) {
    		playStack.clear();
    		
    		if (player == player1) {
    			player2.addScore(1);
    		}
    		else {
    			player1.addScore(1);
    		}
    	}
    	
        playStack.add(card);
        int score = scorer.scorePlayStack(playStack);
        player.addScore(score);
        updateScore();
        notifyPlayStackUpdated();
    }
    
    public void updateScore() {
    	for (GameObserver o : observers) {
            o.onScoreUpdated(player1.getScore(), player2.getScore());  // Pass updated scores to observers
        }
    }
    
    public void resetForNewRound() {
        this.deck = new Deck();
        this.crib.clear();
        this.playStack.clear();
        this.startCard = null;
        
        notifyPlayStackUpdated();
    }
    
    public void resetForNewGame() {
        this.deck = new Deck();
        this.crib.clear();
        this.playStack.clear();
        this.startCard = null;
        this.player1.resetScore();
        this.player2.resetScore();
        
        updateScore();
        notifyPlayStackUpdated();
    }
    
    public void setDealer(Player p) {
    	this.dealer = p;
    }
    
    public Player getDealer() {
    	return this.dealer;
    }

    public ArrayList<Card> getPlayStack() {
    	return new ArrayList<Card>(this.playStack);
    }

}
