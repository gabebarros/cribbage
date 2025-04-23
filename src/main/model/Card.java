/*
 * Functionality: A flyweight class for representing the cards in a deck, which are totally immutable and stored within this class as well
 * 
 * Notes:
 * 		Must include store, private constructor, and access method
 */

package main.model;

import java.util.Objects;

public class Card implements Comparable<Card>{
	
	//card atts
	private Rank rank;
	private Suit suit;
	
	private static Card[][] store = new Card[13][4]; //card store required for flyweight design pattern

	//private constructor required for flyweight design pattern
	private Card(Rank rank, Suit suit){
		this.rank = rank;
		this.suit = suit;
	}
	
	//Standard getter
	public Rank getRank() {
		return this.rank;
	}
	
	//Standard getter
	public Suit getSuit() {
		return this.suit;
	}
	
	//Standard getter
	public int getValue() {
		return this.rank.getValue() + 0; //not an actual reference
	}
	
	@Override
	/*
	 * compareTo method from comparable interface
	 * @param other: 
	 * 		other card to compare to
	 * @pre:
	 * 		other is expected to only ever be a card.
	 */
	public int compareTo(Card other) {
		// if less than , return -1
		if (this.rank.getObjectiveValue() < other.getRank().getObjectiveValue()) {
	    	return -1;
	    }
		// if greater than, return 1
		else if (this.rank.getObjectiveValue() > other.getRank().getObjectiveValue()) {
	    	return 1;
	    }
		//else return 0, should only happen if equal
	    return 0;
	  }
	
	@Override
	/*
	 * override of Object.equals()
	 * @param obj:
	 * 		object to compare to
	 */
	public boolean equals(Object obj) {
	    if (this == obj) return true; //if this is that, true
	    if (obj == null || getClass() != obj.getClass()) return false; //if classes differ, false
	    Card other = (Card) obj; //cast because class is verified
	    return rank == other.rank && suit == other.suit; //if atts match, true
	}

	@Override
	/*
	 * override of Object.hash(), required due to hash contract with equals
	 */
	public int hashCode() {
	    return Objects.hash(rank, suit); //return default hash
	}
	
	@Override
	/*
	 * override of Object.toString(), provides a string representation of card object, mostly for debug
	 */
	public String toString() {
		  String rank = "";
		  String suit = "";
		  
		  // suit checks
		  if (this.suit == Suit.CLUBS) {
			  suit += '\u2663';
		  }
		  else if (this.suit == Suit.DIAMONDS) {
			  suit += '\u2666';
		  }
		  else if (this.suit == Suit.HEARTS) {
			  suit += '\u2665';
		  }
		  else if (this.suit == Suit.SPADES) {
			  suit += '\u2660';
		  }
		  
		  //rank checks, with behavior logic for aces and royal cards
		  if (this.getValue() == 1) { //aces low
			  rank += "A";
		  }
		  else if (this.getRank().getObjectiveValue() == 11) { //ordinal value essentially
			  rank += "J";
		  }
		  else if (this.getRank().getObjectiveValue() == 12) { //^
			  rank += "Q";
		  }
		  else if (this.getRank().getObjectiveValue() == 13) { //^
			  rank += "K";
		  }
		  else if (this.getRank().getObjectiveValue() < 11 && this.getRank().getObjectiveValue() > 1) {
			  rank += Integer.toString(this.getValue());
		  }
		  
		  //return representation
		  return rank + suit;
	  }
	
	static { //enum lists, not definitions
		Rank[] ranks = {Rank.ACE, Rank.TWO, Rank.THREE, Rank.FOUR, Rank.FIVE, Rank.SIX,
				Rank.SEVEN, Rank.EIGHT, Rank.NINE, Rank.TEN,
				Rank.JACK, Rank.QUEEN, Rank.KING};
		
		Suit[] suits = {Suit.CLUBS, Suit.DIAMONDS, Suit.HEARTS, Suit.SPADES};
		
		//store all possible cards, immutable, and irreplicable
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 4; j++) {
				store[i][j] = new Card(ranks[i], suits[j]);
			}
		}
	}
	
	/*
	 * access method for card store, required for flyweight design pattern
	 * @param rank:
	 * 		rank enum, representing the number/letter on the card
	 * @param suit:
	 * 		suit enum, representing the symbol on the card
	 */
	public static Card getCard(Rank rank, Suit suit) {
		if(suit == Suit.CLUBS) {
		return store[rank.getObjectiveValue()-1][0];
		}
		if(suit == Suit.DIAMONDS) {
		return store[rank.getObjectiveValue()-1][1];
		}
		if(suit == Suit.HEARTS) {
		return store[rank.getObjectiveValue()-1][2];
		}
		if(suit == Suit.SPADES) {
		return store[rank.getObjectiveValue()-1][3];
		}
		return null; //null if invalid somehow, impossible.
	}
	
}
