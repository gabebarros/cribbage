package main.model;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
	
	private final Player player1;
    private final Player player2;
    private Deck deck;
    private ArrayList<Card> crib;
    private ArrayList<Card> playStack;
    
    private Player dealer;  // might not need?

	public Game(String p1Name, String p2Name) {
		this.player1 = new Player(p1Name);
		this.player2 = new Player(p2Name);
		this.deck = new Deck();
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
		
		
		playStack.add(0, deck.draw());  // draw the starting card
		
		
		
		
	}
	
	public static void main(String[] args) {
		Game g = new Game("Gabe", "CPU");
		
		g.playRound("Gabe");
	}

}
