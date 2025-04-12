package main.model;

import java.util.ArrayList;

public class Player {
	
	private final String name;
	private ArrayList<Card> hand;
	private int score;

	public Player(String name) {
		this.name = name;
		this.score = 0;
	}
	
	public void addScore(int addedScore) {
		this.score += addedScore;
	}
	
	public void setHand(ArrayList<Card> cards) {
		this.hand = new ArrayList<Card>();
		
		for (Card c : cards) {
			this.hand.add(c);
		}
    }

    public ArrayList<Card> getHand() {
        ArrayList<Card> copyHand = new ArrayList<Card>();
        
        for (Card c : hand) {
        	copyHand.add(c);
        }
        
        return copyHand;
    }
    
    public Card playCard(int index) {
    	Card c = this.hand.remove(index);
    	
    	return c;
    }
    
    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

}
