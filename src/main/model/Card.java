package main.model;

import java.util.Objects;

public class Card implements Comparable<Card>{
	
	private Rank rank;
	private Suit suit;


	public Card(Rank rank, Suit suit){
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
		if (this.rank.getValue() < other.getRank().getValue()) {
	    	return -1;
	    }
	    if (this.rank.getValue() > other.getRank().getValue()) {
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
	
	@Override
	public String toString(){
		return this.rank + " of " + this.suit;
	}
	
}
