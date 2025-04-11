package main.model;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

ArrayList<Card> cards = new ArrayList<Card>();
	
	public Deck() {
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
			cards.add(new Card(rank, suit));
			}
		}
		
	}
	
	public void shuffle() {
		Collections.shuffle(cards);
	}
	
	public void printDeck() {
		for (Card c : cards) {
			System.out.println(c.toString());
		}
	}
	
	public Card draw() {
		Card c = cards.get(0);
		cards.remove(0);
		
		return c;
	}
	
	public ArrayList<Card> getCards(int num) {
		ArrayList<Card> c = new ArrayList<Card>();
		for (int i = 0; i < num; i++) {
			c.add(cards.get(i));
			cards.remove(i);
		}
		return c;
	}

}
