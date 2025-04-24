package main.model;

import java.util.ArrayList;
import java.util.Collections;

public class Player {
	
	private final String name;
	private ArrayList<Card> hand;
	private int score;

	public Player(String name) {
		this.name = name;
		this.score = 0;
		this.hand = new ArrayList<Card>();
	}
	
	public void addScore(int addedScore) {
		this.score += addedScore;
	}
	
	public void setHand(ArrayList<Card> cards) {
		this.hand = new ArrayList<Card>(cards);
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
    
    public void sortHand() {
    	Collections.sort(hand);
    }

}
