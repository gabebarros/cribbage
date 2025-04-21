package main.model;

import java.util.Objects;

public class Card implements Comparable<Card>{
	
	private Rank rank;
	private Suit suit;
	private static Card[][] store = new Card[13][4];


	private Card(Rank rank, Suit suit){
		this.rank = rank;
		this.suit = suit;
	}
	
	public Rank getRank() {
		return this.rank;
	}
	
	public Suit getSuit() {
		return this.suit;
	}
	
	public int getValue() {
		return this.rank.getValue();
	}
	
	@Override
	public int compareTo(Card other) {
		if (this.rank.getObjectiveValue() < other.getRank().getObjectiveValue()) {
	    	return -1;
	    }
	    if (this.rank.getObjectiveValue() > other.getRank().getObjectiveValue()) {
	    	return 1;
	    }
	    return 0;
	  }
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    Card other = (Card) obj;
	    return rank == other.rank && suit == other.suit;
	}

	@Override
	public int hashCode() {
	    return Objects.hash(rank, suit);
	}
	
	public String toString() {
		  String rank = "";
		  String suit = "";
		  if (this.suit == Suit.CLUBS) {
			  suit += '\u2663';
		  }
		  if (this.suit == Suit.DIAMONDS) {
			  suit += '\u2666';
		  }
		  if (this.suit == Suit.HEARTS) {
			  suit += '\u2665';
		  }
		  if (this.suit == Suit.SPADES) {
			  suit += '\u2660';
		  }
		  if (this.getValue() == 1) {
			  rank += "A";
		  }
		  if (this.getRank().getObjectiveValue() == 11) {
			  rank += "J";
		  }
		  if (this.getRank().getObjectiveValue() == 12) {
			  rank += "Q";
		  }
		  if (this.getRank().getObjectiveValue() == 13) {
			  rank += "K";
		  }
		  if (this.getRank().getObjectiveValue() < 11 && this.getRank().getObjectiveValue() > 1) {
			  rank += Integer.toString(this.getValue());
		  }
		  return rank + suit;
	  }
	
	static {
		Rank[] ranks = {Rank.ACE, Rank.TWO, Rank.THREE, Rank.FOUR, Rank.FIVE, Rank.SIX,
				Rank.SEVEN, Rank.EIGHT, Rank.NINE, Rank.TEN,
				Rank.JACK, Rank.QUEEN, Rank.KING};
		
		Suit[] suits = {Suit.CLUBS, Suit.DIAMONDS, Suit.HEARTS, Suit.SPADES};
		
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 4; j++) {
				store[i][j] = new Card(ranks[i], suits[j]);
			}
		}
	}
	
	public static Card getCard(Rank rank, Suit suit) {
		if(suit == Suit.CLUBS) {
		return store[rank.getObjectiveValue() - 1][0];
		}
		if(suit == Suit.DIAMONDS) {
		return store[rank.getObjectiveValue() - 1][1];
		}
		if(suit == Suit.HEARTS) {
		return store[rank.getObjectiveValue() - 1][2];
		}
		if(suit == Suit.SPADES) {
		return store[rank.getObjectiveValue() - 1][3];

		}
		
		return null;
	}
	
}
