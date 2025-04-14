package main.model;

import java.util.ArrayList;
import main.controller.Observer;
import java.util.Scanner;

public class Game {
	
	private final Player player1;
    private final Player player2;
    private Deck deck;
    private ArrayList<Card> crib;
    private ArrayList<Card> playStack;
    private ArrayList<Observer> observers;
    
    private Player dealer;  // might not need?

	public Game(String p1Name, String p2Name) {
		this.player1 = new Player(p1Name);
		this.player2 = new Player(p2Name);
		this.deck = new Deck();
		this.observers = new ArrayList<Observer>();
	}
	
	private void playRound(String dealer) {
		Scanner scanner = new Scanner(System.in);  // use to get user input
		String input;
		
		crib = new ArrayList<Card>();
		playStack = new ArrayList<Card>();
		
		deck.shuffle();
		
		player1.setHand(deck.getCards(6));
		player2.setHand(deck.getCards(6));
		
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
		
		
		for (Card c : crib) {
			System.out.println(c);
		}
		
		
		Card startCard = deck.draw();  // draw the starting card
		
		
		
		
	}
	
	public void registerObserver(Observer observer) {
		this.observers.add(observer);
	}
	
	public void deregisterObserver(Observer observer) {
		this.observers.remove(observer);
	}
	
	/* PRIVATE METHODS */
	private void notifyObservers(int num) {
		for(Observer o : observers) {
			o.newNumber(num);
		}
	}
	
	public static void main(String[] args) {
		Game g = new Game("Gabe", "CPU");
		
		g.playRound("Gabe");
	}

}
