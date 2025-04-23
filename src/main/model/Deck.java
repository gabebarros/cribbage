/*
 * Functionality:
 * 		an object representing a deck of cards, used for the game
 */

package main.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Deck implements Iterable<Card> { //deck is iterable, following cards.iterator()

	ArrayList<Card> cards = new ArrayList<Card>(); //underlying list, main point of the class
	
	//card storage accessor + cards filler
	public Deck() {
		//uses same structure that cards inner storage uses to store cards, perfect match
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
			cards.add(Card.getCard(rank, suit));
			}
		}
	}
	
	// shuffle method for deck
	public void shuffle() {
		Collections.shuffle(cards);
	}
	
	//debug method for printing cards in the deck
	public void printDeck() {
		for (Card c : cards) {
			System.out.println(c.toString());
		}
	}
	
	//method for drawing a card from the top of the deck
	public Card draw() {
		Card c = cards.get(0); //get card
		cards.remove(0); //remove card
		return c; //return card
	}
	
	/*
	 * method for drawing multiple cards for full deck
	 * @param num:
	 * 		number of cards to remove from deck
	 */
	public ArrayList<Card> getCards(int num) {
		ArrayList<Card> c = new ArrayList<Card>();
		for (int i = 0; i < num; i++) {
			c.add(cards.get(i)); //add card to hand
			cards.remove(i); //remove card from deck
		}
		return c; //return hand
	}

	@Override
	/*
	 * Override of iterator method for iterable interface
	 */
	public Iterator<Card> iterator() {
		return cards.iterator();
	}

}
