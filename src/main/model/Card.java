package main.model;

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
	  
	  public boolean equals(Card other) {
		  return (this.rank.getValue() == other.getRank().getValue());
	  }
	
}
