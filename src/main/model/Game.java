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
    
    private List<GameObserver> observers = new ArrayList<>();

    public Game(String p1Name, String p2Name) {
        this.player1 = new Player(p1Name);
        this.player2 = new Player(p2Name);
        this.deck = new Deck();
        this.crib = new ArrayList<>();
        this.playStack = new ArrayList<>();
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
    
    private void playRound(String dealer) {
		Scanner scanner = new Scanner(System.in);  // use to get user input
		String input;
		
		
		
		dealHands();
		
		System.out.println(player1.getName() + ": what is the first card you want to add to the crib?");
		input = scanner.nextLine();
		crib.add(player1.playCard(Integer.parseInt(input)));
		
		System.out.println(player1.getName() + ": what is the second card you want to add to the crib?");
		input = scanner.nextLine();
		crib.add(player1.playCard(Integer.parseInt(input)));
		
		System.out.println(player2.getName() + ": what is the first card you want to add to the crib?");
		input = scanner.nextLine();
		crib.add(player2.playCard(Integer.parseInt(input)));
		
		System.out.println(player2.getName() + ": what is the second card you want to add to the crib?");
		input = scanner.nextLine();
		crib.add(player2.playCard(Integer.parseInt(input)));
		
		startCard = deck.draw();
		
		
		
	}
}
